package com.savio.config;

import com.savio.model.DataDompet;
import com.savio.model.DataSesi;
import com.savio.model.ModelTransaksi;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.UUID;

/**
 * KoneksiJSON - Kelas utama pengelola database JSON untuk SAVIO.
 *
 * Format database (database_savio.json):
 * {
 *   "users": [
 *     {
 *       "email": "user@example.com",
 *       "nama_pengguna": "USER",
 *       "password_aktif": "password123",
 *       "saldo_aktif": 0.0,
 *       ...
 *     }
 *   ]
 * }
 */
public class KoneksiJSON {

    // 🔥 PATH ABSOLUT: Disimpan di folder home user agar selalu bisa ditemukan
    private static final String FILE_PATH = System.getProperty("user.home") + File.separator + "database_savio.json";

    /**
     * Muat data user yang sedang login ke memori DataDompet & DataSesi.
     * Dipanggil setelah DataSesi.setUsernameAktif() sudah di-set.
     */
    public static void muatDataDariJSON() {
        String emailLogin = DataSesi.getUsernameAktif();
        if (emailLogin == null || emailLogin.trim().isEmpty()) {
            System.err.println("⚠️ muatDataDariJSON: username aktif belum di-set, skip.");
            return;
        }
        muatDataUserDariJSON(emailLogin.toLowerCase().trim());
    }

    /**
     * Muat data spesifik milik email tertentu.
     */
    public static void muatDataUserDariJSON(String email) {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            resetMemoriDompet();
            return;
        }

        try {
            String content = bacaFile(file);
            if (content.isEmpty()) {
                resetMemoriDompet();
                return;
            }

            String userBlock = ekstrakBlokUser(content, email);
            if (userBlock.isEmpty()) {
                resetMemoriDompet();
                return;
            }

            // Muat Profil User
            String namaMurni = ekstrakNilaiString(userBlock, "nama_pengguna");
            String passMurni = ekstrakNilaiString(userBlock, "password_aktif");
            if (!namaMurni.isEmpty()) DataSesi.setNamaPengguna(namaMurni);
            if (!passMurni.isEmpty()) DataSesi.setPasswordAktif(passMurni);

            // Muat Data Keuangan
            DataDompet.SALDO_AKTIF.set(ekstrakNilaiDouble(userBlock, "saldo_aktif"));
            DataDompet.NOMINAL_KEBUTUHAN.set(ekstrakNilaiDouble(userBlock, "nominal_kebutuhan"));
            DataDompet.NOMINAL_KEINGINAN.set(ekstrakNilaiDouble(userBlock, "nominal_keinginan"));
            DataDompet.DANA_DARURAT = ekstrakNilaiDouble(userBlock, "dana_darurat");

            // Muat Rasio Slider
            double pKeb = ekstrakNilaiDouble(userBlock, "persen_kebutuhan");
            double pKei = ekstrakNilaiDouble(userBlock, "persen_keinginan");
            double pTab = ekstrakNilaiDouble(userBlock, "persen_tabungan");
            if (pKeb > 0) DataDompet.PERSEN_KEBUTUHAN.set(pKeb);
            if (pKei > 0) DataDompet.PERSEN_KEINGINAN.set(pKei);
            if (pTab > 0) DataDompet.PERSEN_TABUNGAN.set(pTab);

            // Muat Riwayat Transaksi
            DataDompet.LIST_TRANSAKSI.clear();
            int indexBracket = userBlock.indexOf("\"list_transaksi\"");
            if (indexBracket != -1) {
                int arrayStart = userBlock.indexOf("[", indexBracket);
                int arrayEnd = userBlock.lastIndexOf("]");

                if (arrayStart != -1 && arrayEnd > arrayStart + 1) {
                    String arrayContent = userBlock.substring(arrayStart + 1, arrayEnd).trim();
                    if (!arrayContent.isEmpty()) {
                        String[] objekTransaksi = arrayContent.split("(?<=\\})\\s*,\\s*(?=\\{)");
                        for (String tStr : objekTransaksi) {
                            tStr = tStr.trim();
                            if (tStr.isEmpty() || tStr.equals(",")) continue;

                            String id = ekstrakNilaiString(tStr, "id");
                            String deskripsi = ekstrakNilaiString(tStr, "deskripsi");
                            String kategori = ekstrakNilaiString(tStr, "kategori");
                            double nominal = ekstrakNilaiDouble(tStr, "nominal");
                            String tanggal = ekstrakNilaiString(tStr, "tanggal");

                            if (id.isEmpty()) id = UUID.randomUUID().toString();
                            ModelTransaksi t = new ModelTransaksi(id, deskripsi, kategori, nominal, tanggal);
                            DataDompet.LIST_TRANSAKSI.add(t);
                        }
                    }
                }
            }

            System.out.println("✅ Data berhasil dimuat untuk: " + email);

        } catch (Exception e) {
            System.err.println("⚠️ Gagal memuat data: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Verifikasi login: cek apakah email + password cocok di database.
     */
    public static boolean verifikasiLoginSistem(String email, String password) {
        File file = new File(FILE_PATH);
        if (!file.exists()) return false;
        try {
            String content = bacaFile(file);
            String userBlock = ekstrakBlokUser(content, email.toLowerCase().trim());
            if (userBlock.isEmpty()) return false;
            String passDb = ekstrakNilaiString(userBlock, "password_aktif");
            return passDb.equals(password);
        } catch (Exception e) {
            System.err.println("⚠️ Gagal verifikasi login: " + e.getMessage());
            return false;
        }
    }

    /**
     * Cek apakah email sudah terdaftar di database.
     */
    public static boolean cekEmailSudahTerdaftar(String email) {
        File file = new File(FILE_PATH);
        if (!file.exists()) return false;
        try {
            String content = bacaFile(file);
            // Cek dengan format JSON yang benar: "email": "user@example.com"
            String target = "\"email\": \"" + email.toLowerCase().trim() + "\"";
            return content.contains(target);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Simpan/Update data user yang sedang aktif ke database JSON.
     * Mendukung multi-user: user lain tidak akan terhapus.
     */
    public static void simpanDataKeJSON() {
        String emailAktif = DataSesi.getUsernameAktif();
        if (emailAktif == null || emailAktif.trim().isEmpty()) {
            System.err.println("⚠️ simpanDataKeJSON: username aktif kosong, batal menyimpan.");
            return;
        }
        emailAktif = emailAktif.toLowerCase().trim();

        // Bangun JSON blok user aktif
        String userAktifJson = buatJsonUser(emailAktif);

        File file = new File(FILE_PATH);
        String isiLama = "";
        if (file.exists()) {
            try {
                isiLama = bacaFile(file);
            } catch (Exception e) {
                System.err.println("⚠️ Gagal membaca file lama: " + e.getMessage());
            }
        }

        StringBuilder jsonBaru = new StringBuilder();
        jsonBaru.append("{\n  \"users\": [\n");

        if (isiLama.contains("\"users\":")) {
            // File sudah ada dalam format multi-user baru
            int startArray = isiLama.indexOf("[", isiLama.indexOf("\"users\":"));
            int endArray = isiLama.lastIndexOf("]");

            if (startArray == -1 || endArray == -1) {
                // Format tidak valid, tulis ulang dengan user aktif saja
                jsonBaru.append(userAktifJson);
            } else {
                String subArray = isiLama.substring(startArray + 1, endArray).trim();
                String[] semuaUserBlok = pisahkanBlokUser(subArray);
                boolean terupdate = false;
                boolean pertama = true;

                for (String blok : semuaUserBlok) {
                    blok = blok.trim();
                    if (blok.isEmpty()) continue;

                    if (!pertama) jsonBaru.append(",\n");
                    pertama = false;

                    String emailBlok = ekstrakNilaiString(blok, "email");
                    if (emailBlok.equalsIgnoreCase(emailAktif)) {
                        // Ganti dengan data terbaru user aktif
                        jsonBaru.append(userAktifJson);
                        terupdate = true;
                    } else {
                        // Pertahankan data user lain
                        // Pastikan tidak ada trailing koma
                        String blokBersih = blok.replaceAll(",\\s*$", "").trim();
                        jsonBaru.append("    ").append(blokBersih);
                    }
                }

                if (!terupdate) {
                    if (!pertama) jsonBaru.append(",\n");
                    jsonBaru.append(userAktifJson);
                }
            }
        } else {
            // File baru / format lama — tulis hanya user aktif
            jsonBaru.append(userAktifJson);
        }

        jsonBaru.append("\n  ]\n}");

        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            writer.write(jsonBaru.toString());
            System.out.println("✅ Database berhasil disimpan: " + FILE_PATH);
        } catch (IOException e) {
            System.err.println("❌ Gagal menulis database: " + e.getMessage());
        }
    }

    /**
     * Inisialisasi awal aplikasi: hanya cek/buat file database jika belum ada.
     * JANGAN load data di sini karena user belum login.
     */
    public static void inisialisasiDatabase() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            System.out.println("ℹ️ Database belum ada, akan dibuat saat user pertama mendaftar.");
        } else {
            System.out.println("✅ Database ditemukan di: " + FILE_PATH);
        }
    }

    // ==================== PRIVATE HELPER METHODS ====================

    private static String buatJsonUser(String email) {
        StringBuilder sb = new StringBuilder();
        sb.append("    {\n");
        sb.append("      \"email\": \"").append(email).append("\",\n");
        sb.append("      \"nama_pengguna\": \"").append(escapeJson(DataSesi.getNamaPengguna())).append("\",\n");
        sb.append("      \"password_aktif\": \"").append(escapeJson(DataSesi.getPasswordAktif())).append("\",\n");
        sb.append("      \"saldo_aktif\": ").append(DataDompet.SALDO_AKTIF.get()).append(",\n");
        sb.append("      \"nominal_kebutuhan\": ").append(DataDompet.NOMINAL_KEBUTUHAN.get()).append(",\n");
        sb.append("      \"nominal_keinginan\": ").append(DataDompet.NOMINAL_KEINGINAN.get()).append(",\n");
        sb.append("      \"dana_darurat\": ").append(DataDompet.DANA_DARURAT).append(",\n");
        sb.append("      \"persen_kebutuhan\": ").append(DataDompet.PERSEN_KEBUTUHAN.get()).append(",\n");
        sb.append("      \"persen_keinginan\": ").append(DataDompet.PERSEN_KEINGINAN.get()).append(",\n");
        sb.append("      \"persen_tabungan\": ").append(DataDompet.PERSEN_TABUNGAN.get()).append(",\n");
        sb.append("      \"list_transaksi\": [\n");

        for (int i = 0; i < DataDompet.LIST_TRANSAKSI.size(); i++) {
            ModelTransaksi t = DataDompet.LIST_TRANSAKSI.get(i);
            sb.append("        {\n");
            sb.append("          \"id\": \"").append(escapeJson(t.getId())).append("\",\n");
            sb.append("          \"deskripsi\": \"").append(escapeJson(t.getDeskripsi())).append("\",\n");
            sb.append("          \"kategori\": \"").append(escapeJson(t.getKategori())).append("\",\n");
            sb.append("          \"nominal\": ").append(t.getNominal()).append(",\n");
            sb.append("          \"tanggal\": \"").append(escapeJson(t.getTanggal())).append("\"\n");
            sb.append("        }");
            if (i < DataDompet.LIST_TRANSAKSI.size() - 1) sb.append(",");
            sb.append("\n");
        }

        sb.append("      ]\n    }");
        return sb.toString();
    }

    /**
     * Memisahkan blok-blok user JSON menggunakan bracket counting.
     */
    private static String[] pisahkanBlokUser(String subArray) {
        java.util.List<String> bloks = new java.util.ArrayList<>();
        int i = 0;
        while (i < subArray.length()) {
            if (subArray.charAt(i) == '{') {
                int count = 0;
                int start = i;
                while (i < subArray.length()) {
                    if (subArray.charAt(i) == '{') count++;
                    else if (subArray.charAt(i) == '}') count--;
                    if (count == 0) {
                        bloks.add(subArray.substring(start, i + 1));
                        i++;
                        break;
                    }
                    i++;
                }
            } else {
                i++;
            }
        }
        return bloks.toArray(new String[0]);
    }

    /**
     * Ekstrak blok JSON milik user dengan email tertentu.
     * Menggunakan bracket counting untuk presisi tinggi.
     */
    private static String ekstrakBlokUser(String json, String email) {
        String target = "\"email\": \"" + email + "\"";
        int idx = json.indexOf(target);
        if (idx == -1) return "";

        int startBlock = json.lastIndexOf("{", idx);
        if (startBlock == -1) return "";

        int count = 0;
        for (int i = startBlock; i < json.length(); i++) {
            char c = json.charAt(i);
            if (c == '{') count++;
            else if (c == '}') count--;
            if (count == 0) {
                return json.substring(startBlock, i + 1);
            }
        }
        return "";
    }

    private static void resetMemoriDompet() {
        DataDompet.SALDO_AKTIF.set(0.0);
        DataDompet.NOMINAL_KEBUTUHAN.set(0.0);
        DataDompet.NOMINAL_KEINGINAN.set(0.0);
        DataDompet.DANA_DARURAT = 0.0;
        DataDompet.PERSEN_KEBUTUHAN.set(50.0);
        DataDompet.PERSEN_KEINGINAN.set(30.0);
        DataDompet.PERSEN_TABUNGAN.set(20.0);
        DataDompet.LIST_TRANSAKSI.clear();
    }

    private static String bacaFile(File file) throws IOException {
        return new String(Files.readAllBytes(Paths.get(file.toURI())));
    }

    private static double ekstrakNilaiDouble(String json, String key) {
        try {
            String target = "\"" + key + "\":";
            int start = json.indexOf(target);
            if (start == -1) {
                // Coba dengan spasi setelah titik dua
                target = "\"" + key + "\": ";
                start = json.indexOf(target);
                if (start == -1) return 0.0;
            }
            start += target.length();
            // Lewati spasi
            while (start < json.length() && json.charAt(start) == ' ') start++;
            int end = start;
            while (end < json.length() && json.charAt(end) != ',' && json.charAt(end) != '}' && json.charAt(end) != '\n') {
                end++;
            }
            String val = json.substring(start, end).replace("\"", "").trim();
            return val.isEmpty() ? 0.0 : Double.parseDouble(val);
        } catch (Exception e) {
            return 0.0;
        }
    }

    private static String ekstrakNilaiString(String json, String key) {
        try {
            String target = "\"" + key + "\": \"";
            int start = json.indexOf(target);
            if (start == -1) {
                // Coba tanpa spasi
                target = "\"" + key + "\":\"";
                start = json.indexOf(target);
                if (start == -1) return "";
            }
            start += target.length();
            // Cari penutup quote, dengan escape handling
            StringBuilder result = new StringBuilder();
            while (start < json.length()) {
                char c = json.charAt(start);
                if (c == '\\' && start + 1 < json.length()) {
                    char next = json.charAt(start + 1);
                    if (next == '"') {
                        result.append('"');
                        start += 2;
                        continue;
                    } else if (next == '\\') {
                        result.append('\\');
                        start += 2;
                        continue;
                    } else if (next == 'n') {
                        result.append('\n');
                        start += 2;
                        continue;
                    }
                }
                if (c == '"') break;
                result.append(c);
                start++;
            }
            return result.toString().trim();
        } catch (Exception e) {
            return "";
        }
    }

    private static String escapeJson(String s) {
        if (s == null) return "";
        return s.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }
}