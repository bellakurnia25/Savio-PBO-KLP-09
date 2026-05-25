package com.savio.model;

public class User {
    private String nama;
    private String password;
    private double saldoAktif;

    public User(String nama, String password, double saldoAwal) {
        this.nama = nama;
        this.password = password;
        this.saldoAktif = saldoAwal;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        // 🚨 PANGKAS: Hapus pengecekan isEmpty()
        // Biarkan Teman B yang menyusun pengondisian validasi nama pengguna.
        this.nama = nama;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        // 🚨 PANGKAS: Hapus pengecekan panjang karakter >= 6
        // Biarkan Teman B yang mengetik aturan keamanan password di level model ini.
        this.password = password;
    }

    public double getSaldoAktif() {
        return saldoAktif;
    }

    public void sesuaikanSaldo(double jumlah) {
        // 🚨 PANGKAS / KOSONGKAN LOGIKA DI SINI:
        // Biarkan Teman B yang mengetik rumus kalkulasi perubahan saldo user (+= jumlah).
    }
}