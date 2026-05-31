package com.savio.scenes;

import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.text.*;
import javafx.scene.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class TransaksiView extends VBox {

    private final VBox listContainer;
    private String filterAktif = "Semua";

    private final Button btnFilterSemua;
    private final Button btnFilterIncome;
    private final Button btnFilterOutcome;

    private final DateTimeFormatter formatter =
        DateTimeFormatter.ofPattern("dd MMM yyyy");

    private final javafx.collections.ObservableList<com.savio.model.ModelTransaksi> daftarTransaksi = com.savio.model.DataDompet.LIST_TRANSAKSI;

    public TransaksiView() {
        this.setSpacing(0);
        this.setStyle("-fx-background-color: " + "#0F1123" + ";");
        this.setPrefWidth(750);

        ScrollPane scroll = new ScrollPane();
        scroll.setFitToWidth(true);
        scroll.setStyle("-fx-background: transparent; -fx-background-color: transparent; -fx-border-color: transparent;");
        VBox.setVgrow(scroll, Priority.ALWAYS);

        VBox mainContent = new VBox(20);
        mainContent.setPadding(new Insets(28, 28, 28, 28));
        mainContent.setStyle("-fx-background-color: " + "#0F1123" + ";");

        HBox titleRow = new HBox();
        titleRow.setAlignment(Pos.CENTER_LEFT);

        VBox headerBox = new VBox(5);
        Label lblTitle = new Label("Transaksi");
        lblTitle.setStyle("-fx-text-fill: white; -fx-font-size: 26px; -fx-font-weight: bold;");
        Label lblSub = new Label("Catatan riwayat pemasukan dan pengeluaran");
        lblSub.setStyle("-fx-text-fill: #A0A4B8; -fx-font-size: 14px;");
        headerBox.getChildren().addAll(lblTitle, lblSub);

        Region sp = new Region(); HBox.setHgrow(sp, Priority.ALWAYS);

        Button btnTambah = buildPrimaryButton("+ Tambah Transaksi");
        btnTambah.setOnAction(e -> bukaPopUpForm(false, null));

        titleRow.getChildren().addAll(headerBox, sp, btnTambah);

        HBox filterRow = new HBox(8);
        filterRow.setAlignment(Pos.CENTER_LEFT);

        btnFilterSemua   = buildFilterTab("Semua");
        btnFilterIncome  = buildFilterTab("Income");
        btnFilterOutcome = buildFilterTab("Outcome");

        btnFilterSemua  .setOnAction(e -> setFilter("Semua"));
        btnFilterIncome .setOnAction(e -> setFilter("Income"));
        btnFilterOutcome.setOnAction(e -> setFilter("Outcome"));

        filterRow.getChildren().addAll(btnFilterSemua, btnFilterIncome, btnFilterOutcome);
        updateStyleFilter();

        listContainer = new VBox(0);
        listContainer.setStyle(
            "-fx-background-color: " + "#120E2E" + ";" +
            "-fx-background-radius: 16;" +
            "-fx-border-color: " + "#2D314A" + ";" +
            "-fx-border-radius: 16;" +
            "-fx-border-width: 1;"
        );

        mainContent.getChildren().addAll(titleRow, filterRow, listContainer);
        scroll.setContent(mainContent);

        this.getChildren().addAll(scroll);
        refreshDaftarTransaksi();
    }

    private Button buildFilterTab(String teks) {
        Button btn = new Button(teks);
        btn.setPadding(new Insets(7, 18, 7, 18));
        btn.setFont(Font.font("System", FontWeight.BOLD, 12));
        btn.setCursor(Cursor.HAND);
        return btn;
    }

    private void setFilter(String filter) {
        this.filterAktif = filter;
        updateStyleFilter();
        refreshDaftarTransaksi();
    }

    private void updateStyleFilter() {
        String aktif = "-fx-background-color: " + "linear-gradient(to right, #9B5CF6, #F72BB0)" + "; -fx-text-fill: white; -fx-background-radius: 20; -fx-border-width: 0; -fx-cursor: hand;";
        String pasif = "-fx-background-color: rgba(255,255,255,0.07); -fx-text-fill: " + "#A0A4B8" + "; -fx-background-radius: 20; -fx-border-width: 0; -fx-cursor: hand;";
        btnFilterSemua  .setStyle(filterAktif.equals("Semua")   ? aktif : pasif);
        btnFilterIncome .setStyle(filterAktif.equals("Income")  ? aktif : pasif);
        btnFilterOutcome.setStyle(filterAktif.equals("Outcome") ? aktif : pasif);
    }

    private void refreshDaftarTransaksi() {
        listContainer.getChildren().clear();

        List<com.savio.model.ModelTransaksi> filtered = new ArrayList<>();
        for (com.savio.model.ModelTransaksi t : daftarTransaksi) {
            if (filterAktif.equals("Semua") || t.getKategori().equalsIgnoreCase(filterAktif)) {
                filtered.add(t);
            }
        }

        if (filtered.isEmpty()) {
            Label lbl = new Label("Belum ada catatan transaksi.");
            lbl.setFont(Font.font("System", FontWeight.NORMAL, 14));
            lbl.setTextFill(Color.web("#A0A4B8"));
            lbl.setPadding(new Insets(28, 20, 28, 20));
            listContainer.getChildren().add(lbl);
            return;
        }

        String prevTanggal = "";
        boolean firstGroup = true;

        for (int i = 0; i < filtered.size(); i++) {
            com.savio.model.ModelTransaksi t = filtered.get(i);

            if (!t.getTanggal().equals(prevTanggal)) {
                if (!firstGroup) {
                    Separator sep = new Separator();
                    sep.setStyle("-fx-background-color: " + "#2D314A" + "; -fx-border-width: 0;");
                    VBox.setMargin(sep, new Insets(0));
                    listContainer.getChildren().add(sep);
                }
                firstGroup = false;

                HBox dateHeader = new HBox();
                dateHeader.setPadding(new Insets(14, 20, 6, 20));
                dateHeader.setStyle("-fx-background-color: rgba(255,255,255,0.025);");
                Label lblDate = new Label(t.getTanggal());
                lblDate.setFont(Font.font("System", FontWeight.BOLD, 11));
                lblDate.setTextFill(Color.web("#6C7293"));
                dateHeader.getChildren().add(lblDate);
                listContainer.getChildren().add(dateHeader);

                prevTanggal = t.getTanggal();
            }

            HBox row = buildTransaksiRow(t);
            listContainer.getChildren().add(row);
        }

        Region pad = new Region(); pad.setPrefHeight(8);
        listContainer.getChildren().add(pad);
    }

    private HBox buildTransaksiRow(com.savio.model.ModelTransaksi t) {
        HBox row = new HBox(14);
        row.setAlignment(Pos.CENTER_LEFT);
        row.setPadding(new Insets(13, 20, 13, 20));
        row.setStyle("-fx-background-color: transparent; -fx-cursor: hand;");

        // PERUBAHAN WARNA ICON BOX
        // Income = Tetap, Kebutuhan (Default Outcome) = Mengambil warna keinginan lama (Oranye)
        String iconColor = t.getKategori().equalsIgnoreCase("Income")
            ? "rgba(0,212,170,0.15)" : "rgba(242,140,0,0.15)"; 
            
        // Keinginan = Menggunakan warna baru (#798EFA -> R:121, G:142, B:250)
        if (t.getDeskripsi().toLowerCase().contains("[keinginan]") ||
            t.getDeskripsi().toLowerCase().contains("nongkrong") ||
            t.getDeskripsi().toLowerCase().contains("belanja"))
            iconColor = "rgba(121,142,250,0.15)";

        StackPane iconBox = new StackPane();
        iconBox.setPrefSize(42, 42);
        iconBox.setMinSize(42, 42);
        iconBox.setStyle("-fx-background-color: " + iconColor + "; -fx-background-radius: 12;");
        Label ico = new Label(tentukanIkon(t.getDeskripsi(), t.getKategori()));
        ico.setFont(Font.font(18));
        iconBox.getChildren().add(ico);

        String descClean = t.getDeskripsi()
            .replace("[Kebutuhan] ", "")
            .replace("[Keinginan] ", "");

        String subKat = t.getKategori();
        if (t.getDeskripsi().contains("[Kebutuhan]")) subKat = "Kebutuhan";
        if (t.getDeskripsi().contains("[Keinginan]")) subKat = "Keinginan";

        Label lblDesc = new Label(descClean);
        lblDesc.setFont(Font.font("System", FontWeight.BOLD, 13));
        lblDesc.setTextFill(Color.web("white"));

        Label chip = buildChip(subKat, t.getKategori());

        HBox descRow = new HBox(8, lblDesc, chip);
        descRow.setAlignment(Pos.CENTER_LEFT);

        VBox info = new VBox(3, descRow);
        HBox.setHgrow(info, Priority.ALWAYS);

        boolean isIncome = t.getKategori().equalsIgnoreCase("Income");
        Label lblNominal = new Label(
            (isIncome ? "+" : "-") + "Rp " +
            String.format("%,.0f", t.getNominal()).replace(',', '.')
        );
        lblNominal.setFont(Font.font("System", FontWeight.BOLD, 13));
        lblNominal.setTextFill(Color.web(isIncome ? "#00D4AA" : "#F72BB0"));

        Label btnEdit = new Label("✏️");
        btnEdit.setFont(Font.font(14));
        btnEdit.setOpacity(0);
        btnEdit.setCursor(Cursor.HAND);

        row.getChildren().addAll(iconBox, info, lblNominal, btnEdit);

        row.setOnMouseEntered(e -> {
            row.setStyle("-fx-background-color: rgba(155,92,246,0.08); -fx-cursor: hand;");
            btnEdit.setOpacity(1);
        });
        row.setOnMouseExited(e -> {
            row.setStyle("-fx-background-color: transparent; -fx-cursor: hand;");
            btnEdit.setOpacity(0);
        });
        row.setOnMouseClicked(e -> bukaPopUpForm(true, t));

        return row;
    }

    private Label buildChip(String subKat, String kategori) {
        Label chip = new Label(subKat);
        chip.setFont(Font.font("System", FontWeight.NORMAL, 10));
        String chipColor, chipBg;
        
        // PERUBAHAN WARNA CHIP
        if (kategori.equalsIgnoreCase("Income")) {
            chipColor = "#00D4AA";  chipBg = "rgba(0,212,170,0.12)";
        } else if (subKat.equalsIgnoreCase("Keinginan")) {
            chipColor = "#798EFA"; chipBg = "rgba(121,142,250,0.12)"; // Warna Baru Keinginan
        } else {
            chipColor = "#F28C00"; chipBg = "rgba(242,140,0,0.12)";   // Warna Lama Keinginan Dipindah Ke Kebutuhan
        }
        
        chip.setTextFill(Color.web(chipColor));
        chip.setPadding(new Insets(2, 8, 2, 8));
        chip.setStyle(
            "-fx-background-color: " + chipBg + ";" +
            "-fx-background-radius: 10;" +
            "-fx-border-color: " + chipColor + "44;" +
            "-fx-border-radius: 10;"
        );
        return chip;
    }

    private void bukaPopUpForm(boolean isEdit, com.savio.model.ModelTransaksi lama) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle(isEdit ? "Edit Transaksi" : "Tambah Transaksi Baru");

        dialog.getDialogPane().setStyle(
            "-fx-background-color: " + "#120E2E" + ";" +
            "-fx-border-color: " + "#2D314A" + ";" +
            "-fx-border-width: 2;" +
            "-fx-border-radius: 16;" +
            "-fx-background-radius: 16;"
        );
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        Button okBtn = (Button) dialog.getDialogPane().lookupButton(ButtonType.OK);
        okBtn.setText("Simpan");
        okBtn.setStyle("-fx-background-color: " + "linear-gradient(to right, #9B5CF6, #F72BB0)" + "; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 10; -fx-padding: 10 22 10 22;");
        
        Button cancelBtn = (Button) dialog.getDialogPane().lookupButton(ButtonType.CANCEL);
        cancelBtn.setText("Batal");
        cancelBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: " + "#A0A4B8" + "; -fx-border-color: " + "#2D314A" + "; -fx-border-radius: 10; -fx-padding: 9 20 9 20;");

        VBox form = new VBox(14);
        form.setPadding(new Insets(20, 20, 8, 20));
        form.setPrefWidth(380);

        Label formTitle = new Label(isEdit ? "✏️  Edit Transaksi" : "➕  Transaksi Baru");
        formTitle.setFont(Font.font("System", FontWeight.BOLD, 16));
        formTitle.setTextFill(Color.web("white"));
        form.getChildren().add(formTitle);

        TextField txtDesc = buildInput("Deskripsi (Gaji, Makan Siang...)");

        ComboBox<String> cbKat = new ComboBox<>();
        cbKat.getItems().addAll("Income", "Outcome");
        cbKat.setMaxWidth(Double.MAX_VALUE);
        styleComboBox(cbKat);

        Label lblPos = buildFormLabel("Potong dari Pos Anggaran:");
        ComboBox<String> cbPos = new ComboBox<>();
        cbPos.getItems().addAll("Kebutuhan", "Keinginan");
        cbPos.setValue("Kebutuhan");
        cbPos.setMaxWidth(Double.MAX_VALUE);
        styleComboBox(cbPos);

        TextField txtNominal = buildInput("Nominal (contoh: 50000)");

        DatePicker dp = new DatePicker();
        dp.setMaxWidth(Double.MAX_VALUE);
        dp.setValue(LocalDate.now());
        dp.setStyle("-fx-background-color: " + "#0A0818" + "; -fx-border-color: " + "#2D314A" + "; -fx-border-radius: 10; -fx-background-radius: 10; -fx-control-inner-background: " + "#0A0818" + "; -fx-text-fill: white;");

        cbKat.valueProperty().addListener((o, ov, nv) -> {
            boolean out = nv != null && nv.equalsIgnoreCase("Outcome");
            lblPos.setVisible(out); lblPos.setManaged(out);
            cbPos.setVisible(out); cbPos.setManaged(out);
        });

        if (isEdit && lama != null) {
            txtDesc.setText(lama.getDeskripsi().replace("[Kebutuhan] ", "").replace("[Keinginan] ", ""));
            cbKat.setValue(lama.getKategori());
            txtNominal.setText(String.format("%.0f", lama.getNominal()));
            cbPos.setValue(lama.getDeskripsi().contains("[Keinginan]") ? "Keinginan" : "Kebutuhan");
            try { dp.setValue(LocalDate.parse(lama.getTanggal(), formatter)); }
            catch (Exception ex) { dp.setValue(LocalDate.now()); }
        } else {
            cbKat.setValue("Outcome");
        }

        boolean isOutcome = cbKat.getValue() != null && cbKat.getValue().equalsIgnoreCase("Outcome");
        lblPos.setVisible(isOutcome); lblPos.setManaged(isOutcome);
        cbPos.setVisible(isOutcome); cbPos.setManaged(isOutcome);

        form.getChildren().addAll(
            buildFormLabel("Deskripsi Transaksi:"), txtDesc,
            buildFormLabel("Kategori Arus Kas:"), cbKat,
            lblPos, cbPos,
            buildFormLabel("Nominal Uang (Rp):"), txtNominal,
            buildFormLabel("Tanggal Transaksi:"), dp
        );

        if (isEdit && lama != null) {
            Separator lineSep = new Separator();
            lineSep.setStyle("-fx-background-color: " + "#2D314A" + ";");
            VBox.setMargin(lineSep, new Insets(10, 0, 0, 0));

            Button btnHapus = new Button("🗑️   Hapus Transaksi Ini");
            btnHapus.setMaxWidth(Double.MAX_VALUE);
            btnHapus.setPadding(new Insets(11));
            btnHapus.setFont(Font.font("System", FontWeight.BOLD, 13));
            btnHapus.setCursor(Cursor.HAND);
            btnHapus.setStyle("-fx-background-color: rgba(247,43,176,0.08); -fx-border-color: " + "#F72BB0" + "; -fx-border-radius: 10; -fx-background-radius: 10; -fx-text-fill: " + "#F72BB0" + ";");

            final com.savio.model.ModelTransaksi lamaFinal = lama;
btnHapus.setOnAction(e -> {
                Alert konfirm = new Alert(Alert.AlertType.CONFIRMATION);
                konfirm.setTitle("Konfirmasi Hapus");
                konfirm.setHeaderText(null);
                konfirm.setContentText("Hapus transaksi \"" + lamaFinal.getDeskripsi().replace("[Kebutuhan] ","").replace("[Keinginan] ","") + "\"?");
                konfirm.getDialogPane().setStyle("-fx-background-color: " + "#120E2E" + ";");
                konfirm.showAndWait().ifPresent(r -> {
                    if (r == ButtonType.OK) {
                        // --- TAMBAHAN FIX: Kembalikan saldo sebelum dihapus ---
                        if (lamaFinal.getKategori().equalsIgnoreCase("Income")) {
                            com.savio.model.DataDompet.SALDO_AKTIF.set(com.savio.model.DataDompet.SALDO_AKTIF.get() - lamaFinal.getNominal());
                        } else {
                            com.savio.model.DataDompet.SALDO_AKTIF.set(com.savio.model.DataDompet.SALDO_AKTIF.get() + lamaFinal.getNominal());
                            if (lamaFinal.getDeskripsi().contains("[Keinginan]")) {
                                com.savio.model.DataDompet.NOMINAL_KEINGINAN.set(com.savio.model.DataDompet.NOMINAL_KEINGINAN.get() + lamaFinal.getNominal());
                            } else {
                                com.savio.model.DataDompet.NOMINAL_KEBUTUHAN.set(com.savio.model.DataDompet.NOMINAL_KEBUTUHAN.get() + lamaFinal.getNominal());
                            }
                        }
                        // -----------------------------------------------------

                        daftarTransaksi.remove(lamaFinal);
                        com.savio.utils.KoneksiJSON.simpanDataKeJSON();
                        refreshDaftarTransaksi();
                        dialog.close();
                    }
                });
            });
            form.getChildren().addAll(lineSep, btnHapus);
        }

        dialog.getDialogPane().setContent(form);

        dialog.showAndWait().ifPresent(res -> {
            if (res != ButtonType.OK) return;
            try {
                String rawDesc  = txtDesc.getText().trim();
                double nominal  = Double.parseDouble(txtNominal.getText().trim().replace(".", "").replace(",", ""));
                String kategori = cbKat.getValue();
                String pos      = cbPos.getValue();
                String tgl      = (dp.getValue() != null) ? dp.getValue().format(formatter) : LocalDate.now().format(formatter);

                if (rawDesc.isEmpty() || nominal <= 0) return;

                String descFinal = kategori.equalsIgnoreCase("Outcome") ? "[" + pos + "] " + rawDesc : rawDesc;

                if (isEdit && lama != null) {
                    // Balikkan efek saldo lama terlebih dahulu secara proporsional
                    if (lama.getKategori().equalsIgnoreCase("Income")) {
                        com.savio.model.DataDompet.SALDO_AKTIF.set(com.savio.model.DataDompet.SALDO_AKTIF.get() - lama.getNominal());
                    } else {
                        com.savio.model.DataDompet.SALDO_AKTIF.set(com.savio.model.DataDompet.SALDO_AKTIF.get() + lama.getNominal());
                        if (lama.getDeskripsi().contains("[Keinginan]")) {
                            com.savio.model.DataDompet.NOMINAL_KEINGINAN.set(com.savio.model.DataDompet.NOMINAL_KEINGINAN.get() + lama.getNominal());
                        } else {
                            com.savio.model.DataDompet.NOMINAL_KEBUTUHAN.set(com.savio.model.DataDompet.NOMINAL_KEBUTUHAN.get() + lama.getNominal());
                        }
                    }

                    lama.setDeskripsi(descFinal);
                    lama.setKategori(kategori);
                    lama.setNominal(nominal);
                    lama.setTanggal(tgl);

                    if (kategori.equalsIgnoreCase("Income")) {
                        com.savio.model.DataDompet.SALDO_AKTIF.set(com.savio.model.DataDompet.SALDO_AKTIF.get() + nominal);
                    } else {
                        com.savio.model.DataDompet.SALDO_AKTIF.set(com.savio.model.DataDompet.SALDO_AKTIF.get() - nominal);
                        if (pos.equalsIgnoreCase("Keinginan")) {
                            com.savio.model.DataDompet.NOMINAL_KEINGINAN.set(com.savio.model.DataDompet.NOMINAL_KEINGINAN.get() - nominal);
                        } else {
                            com.savio.model.DataDompet.NOMINAL_KEBUTUHAN.set(com.savio.model.DataDompet.NOMINAL_KEBUTUHAN.get() - nominal);
                        }
                    }
                } else {
                    com.savio.model.ModelTransaksi transaksiBaruObj = new com.savio.model.ModelTransaksi(
                        UUID.randomUUID().toString(), descFinal, kategori, nominal, tgl
                    );
                    daftarTransaksi.add(0, transaksiBaruObj);

                    if (kategori.equalsIgnoreCase("Income")) {
                        com.savio.model.DataDompet.SALDO_AKTIF.set(com.savio.model.DataDompet.SALDO_AKTIF.get() + nominal);
                    } else {
                        com.savio.model.DataDompet.SALDO_AKTIF.set(com.savio.model.DataDompet.SALDO_AKTIF.get() - nominal);
                        if (pos.equalsIgnoreCase("Keinginan")) {
                            com.savio.model.DataDompet.NOMINAL_KEINGINAN.set(com.savio.model.DataDompet.NOMINAL_KEINGINAN.get() - nominal);
                        } else {
                            com.savio.model.DataDompet.NOMINAL_KEBUTUHAN.set(com.savio.model.DataDompet.NOMINAL_KEBUTUHAN.get() - nominal);
                        }
                    }
                }
                
                com.savio.utils.KoneksiJSON.simpanDataKeJSON();
                refreshDaftarTransaksi();
            } catch (Exception ex) {
                System.out.println("Gagal proses form: " + ex.getMessage());
            }
        });
    }

    private Button buildPrimaryButton(String text) {
        Button btn = new Button(text);
        btn.setFont(Font.font("System", FontWeight.BOLD, 13));
        btn.setTextFill(Color.web("white"));
        btn.setPadding(new Insets(10, 20, 10, 20));
        btn.setCursor(Cursor.HAND);
        btn.setStyle("-fx-background-color: " + "linear-gradient(to right, #9B5CF6, #F72BB0)" + "; -fx-background-radius: 11; -fx-border-width: 0;");
        return btn;
    }

    private Label buildFormLabel(String text) {
        Label l = new Label(text);
        l.setFont(Font.font("System", FontWeight.BOLD, 12));
        l.setTextFill(Color.web("white"));
        return l;
    }

    private TextField buildInput(String prompt) {
        TextField tf = new TextField();
        tf.setPromptText(prompt);
        tf.setFont(Font.font(13));
        tf.setStyle("-fx-background-color: " + "#0A0818" + "; -fx-text-fill: white; -fx-prompt-text-fill: " + "#6C7293" + "; -fx-border-color: " + "#2D314A" + "; -fx-border-radius: 10; -fx-background-radius: 10; -fx-padding: 11 14 11 14;");
        return tf;
    }

    private void styleComboBox(ComboBox<String> cb) {
        cb.setStyle("-fx-background-color: " + "#0A0818" + "; -fx-text-fill: white; -fx-border-color: " + "#2D314A" + "; -fx-border-radius: 10; -fx-background-radius: 10; -fx-padding: 4;");
    }

    private String tentukanIkon(String desc, String kat) {
        String d = desc.toLowerCase();
        if (d.contains("gaji") || d.contains("bulanan") || d.contains("salary")) return "💼";
        if (d.contains("makan") || d.contains("food") || d.contains("lunch") || d.contains("siang")) return "🍲";
        if (d.contains("transport") || d.contains("gojek") || d.contains("bensin") || d.contains("bus")) return "🚌";
        if (d.contains("nongkrong") || d.contains("kopi") || d.contains("cafe")) return "☕";
        if (d.contains("belanja") || d.contains("shopping") || d.contains("online")) return "🛒";
        if (d.contains("listrik") || d.contains("pln")) return "⚡";
        if (d.contains("internet") || d.contains("wifi")) return "🌐";
        if (d.contains("kesehatan") || d.contains("obat") || d.contains("dokter")) return "🏥";
        if (d.contains("hiburan") || d.contains("film") || d.contains("game")) return "🎮";
        if (d.contains("tabungan") || d.contains("saving")) return "🐷";
        return kat.equalsIgnoreCase("Income") ? "📥" : "💸";
    }
}