package com.savio.scenes;

import com.savio.utils.ColorPalette;
import com.savio.models.DataDompet;
import com.savio.models.ModelTransaksi;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class LaporanView extends VBox {
    private final Label lblKeb;
    private final Label lblKei;
    private final Label lblStatusKeb;
    private final Label lblStatusKei;
    
    private final Label lblDetailBulananKeb;
    private final Label lblDetailBulananKei;
    
    private final Label lblTotalIncome;
    private final Label lblTotalOutcome;

    private int modeHari = 1; // Default awal: Bulanan (tidak dibagi)
    private final Label lblTitleKeb;
    private final Label lblTitleKei;

    public LaporanView() {
        this.setSpacing(20);
        this.setPadding(new Insets(30));
        this.setStyle("-fx-background-color: " + ColorPalette.BG_PRIMARY + ";");

        // ==================== HEADER HALAMAN ====================
        Label lblTitle = new Label("Analisis Batas Aman Kas 📈");
        lblTitle.setStyle("-fx-text-fill: white; -fx-font-size: 22px; -fx-font-weight: bold;");

        // 🔥 SEKARANG LENGKAP 3 TOMBOL NAVIGASI KAS
        HBox btnGroup = new HBox(10);
        Button btnHarian = new Button("Harian");
        Button btnMingguan = new Button("Mingguan");
        Button btnBulanan = new Button("Bulanan");

        String styleActive = "-fx-background-color: " + ColorPalette.ACCENT_KEINGINAN + "; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 20; -fx-cursor: hand;";
        String styleInactive = "-fx-background-color: " + ColorPalette.BG_CARD + "; -fx-text-fill: #888888; -fx-font-weight: bold; -fx-background-radius: 20; -fx-cursor: hand;";

        // Set default aktif awal di menu Bulanan
        btnHarian.setStyle(styleInactive);
        btnMingguan.setStyle(styleInactive);
        btnBulanan.setStyle(styleActive);
        btnGroup.getChildren().addAll(btnHarian, btnMingguan, btnBulanan);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox topRow = new HBox();
        topRow.setAlignment(Pos.CENTER_LEFT);
        topRow.getChildren().addAll(lblTitle, spacer, btnGroup);
        this.getChildren().add(topRow);

        // ==================== PANEL RINGKASAN REALS ====================
        HBox summaryCard = new HBox(30);
        summaryCard.setPadding(new Insets(15, 20, 15, 20));
        summaryCard.setStyle("-fx-background-color: #1a153a; -fx-background-radius: 12; -fx-border-color: #2d2565; -fx-border-radius: 12;");
        summaryCard.setAlignment(Pos.CENTER);

        VBox incBox = new VBox(5);
        incBox.setAlignment(Pos.CENTER);
        Label lblIncTitle = new Label("TOTAL PEMASUKAN MURNI");
        lblIncTitle.setStyle("-fx-text-fill: #888888; -fx-font-size: 11px; -fx-font-weight: bold;");
        lblTotalIncome = new Label("Rp 0");
        lblTotalIncome.setStyle("-fx-text-fill: #2ECC71; -fx-font-size: 16px; -fx-font-weight: bold;");
        incBox.getChildren().addAll(lblIncTitle, lblTotalIncome);

        Region lineSpacer = new Region();
        lineSpacer.setPrefSize(1, 30);
        lineSpacer.setStyle("-fx-background-color: #2d2565;");

        VBox outBox = new VBox(5);
        outBox.setAlignment(Pos.CENTER);
        Label lblOutTitle = new Label("TOTAL PENGELUARAN AKTIF");
        lblOutTitle.setStyle("-fx-text-fill: #888888; -fx-font-size: 11px; -fx-font-weight: bold;");
        lblTotalOutcome = new Label("Rp 0");
        lblTotalOutcome.setStyle("-fx-text-fill: " + ColorPalette.TEXT_DANGER + "; -fx-font-size: 16px; -fx-font-weight: bold;");
        outBox.getChildren().addAll(lblOutTitle, lblTotalOutcome);

        summaryCard.getChildren().addAll(incBox, lineSpacer, outBox);
        HBox.setHgrow(incBox, Priority.ALWAYS);
        HBox.setHgrow(outBox, Priority.ALWAYS);
        this.getChildren().add(summaryCard);

        // ==================== KARTU NOTIFIKASI / STATUS ALARM ====================
        GridPane gridCards = new GridPane();
        gridCards.setHgap(15);
        gridCards.setVgap(15);

        // 1. Kartu Pos Kebutuhan
        VBox cardKeb = new VBox(8);
        cardKeb.setPadding(new Insets(20));
        cardKeb.setStyle("-fx-background-color: " + ColorPalette.BG_CARD + "; -fx-background-radius: 12;");
        lblTitleKeb = new Label("Sisa Jatah Kebutuhan Bulan Ini");
        lblTitleKeb.setStyle("-fx-text-fill: #888888; -fx-font-size: 14px;");
        lblKeb = new Label("Rp 0");
        lblKeb.setStyle("-fx-text-fill: white; -fx-font-size: 24px; -fx-font-weight: bold;");
        
        lblDetailBulananKeb = new Label("Sisa saku bulanan asli: Rp 0");
        lblDetailBulananKeb.setStyle("-fx-text-fill: #9FC5F8; -fx-font-size: 12px; -fx-font-weight: bold;");
        
        lblStatusKeb = new Label("Aman untuk belanja");
        lblStatusKeb.setStyle("-fx-text-fill: #2ECC71; -fx-font-size: 12px; -fx-font-style: italic;");
        cardKeb.getChildren().addAll(lblTitleKeb, lblKeb, lblDetailBulananKeb, lblStatusKeb);

        // 2. Kartu Pos Keinginan
        VBox cardKei = new VBox(8);
        cardKei.setPadding(new Insets(20));
        cardKei.setStyle("-fx-background-color: " + ColorPalette.BG_CARD + "; -fx-background-radius: 12;");
        lblTitleKei = new Label("Sisa Jatah Keinginan Bulan Ini");
        lblTitleKei.setStyle("-fx-text-fill: #888888; -fx-font-size: 14px;");
        lblKei = new Label("Rp 0");
        lblKei.setStyle("-fx-text-fill: white; -fx-font-size: 24px; -fx-font-weight: bold;");
        
        lblDetailBulananKei = new Label("Sisa saku bulanan asli: Rp 0");
        lblDetailBulananKei.setStyle("-fx-text-fill: #B80E7A; -fx-font-size: 12px; -fx-font-weight: bold;");
        
        lblStatusKei = new Label("Kondisi kantong jajan stabil");
        lblStatusKei.setStyle("-fx-text-fill: #2ECC71; -fx-font-size: 12px; -fx-font-style: italic;");
        cardKei.getChildren().addAll(lblTitleKei, lblKei, lblDetailBulananKei, lblStatusKei);

        gridCards.add(cardKeb, 0, 0);
        gridCards.add(cardKei, 1, 0);
        GridPane.setHgrow(cardKeb, Priority.ALWAYS);
        GridPane.setHgrow(cardKei, Priority.ALWAYS);
        this.getChildren().add(gridCards);

        // ==================== LOGIKA INTERAKSI AKSI KLIK TOMBOL ====================
        hitungBatasAman();

        btnHarian.setOnAction(e -> {
            modeHari = 30;
            btnHarian.setStyle(styleActive);
            btnMingguan.setStyle(styleInactive);
            btnBulanan.setStyle(styleInactive);
            lblTitleKeb.setText("Sisa Jatah Kebutuhan Hari Ini");
            lblTitleKei.setText("Sisa Jatah Keinginan Hari Ini");
            hitungBatasAman();
        });

        btnMingguan.setOnAction(e -> {
            modeHari = 4;
            btnHarian.setStyle(styleInactive);
            btnMingguan.setStyle(styleActive);
            btnBulanan.setStyle(styleInactive);
            lblTitleKeb.setText("Sisa Jatah Kebutuhan Minggu Ini");
            lblTitleKei.setText("Sisa Jatah Keinginan Minggu Ini");
            hitungBatasAman();
        });

        // 🔥 AKSI UNTUK TOMBOL BULANAN
        btnBulanan.setOnAction(e -> {
            modeHari = 1; // Angka utuh tanpa dibagi jatah berkala
            btnHarian.setStyle(styleInactive);
            btnMingguan.setStyle(styleInactive);
            btnBulanan.setStyle(styleActive);
            lblTitleKeb.setText("Sisa Jatah Kebutuhan Bulan Ini");
            lblTitleKei.setText("Sisa Jatah Keinginan Bulan Ini");
            hitungBatasAman();
        });
    }

    public void hitungBatasAman() {
        double sisaKebBulanan = DataDompet.NOMINAL_KEBUTUHAN.get();
        double sisaKeiBulanan = DataDompet.NOMINAL_KEINGINAN.get();

        double totalIncomeRiil = 0;
        double totalOutcomeRiil = 0;

        for (ModelTransaksi t : DataDompet.LIST_TRANSAKSI) {
            if (t.getKategori().equalsIgnoreCase("Income")) {
                totalIncomeRiil += t.getNominal();
            } else if (t.getKategori().equalsIgnoreCase("Outcome")) {
                totalOutcomeRiil += t.getNominal();
            }
        }

        lblTotalIncome.setText("Rp " + String.format("%,.0f", totalIncomeRiil));
        lblTotalOutcome.setText("Rp " + String.format("%,.0f", totalOutcomeRiil));

        lblDetailBulananKeb.setText("Sisa saku bulanan asli: Rp " + String.format("%,.0f", sisaKebBulanan));
        lblDetailBulananKei.setText("Sisa saku bulanan asli: Rp " + String.format("%,.0f", sisaKeiBulanan));

        // Kalkulasi pembagian dinamis
        double batasKeb = sisaKebBulanan / modeHari;
        double batasKei = sisaKeiBulanan / modeHari;

        if (batasKeb < 0) batasKeb = 0;
        if (batasKei < 0) batasKei = 0;

        lblKeb.setText("Rp " + String.format("%,.0f", batasKeb));
        lblKei.setText("Rp " + String.format("%,.0f", batasKei));

        // Status Rem Keuangan
        if (sisaKebBulanan <= 0) {
            lblStatusKeb.setText("🚨 Jatah Kebutuhan HABIS! Stop pengeluaran!");
            lblStatusKeb.setStyle("-fx-text-fill: " + ColorPalette.TEXT_DANGER + "; -fx-font-size: 12px; -fx-font-weight: bold;");
        } else if (sisaKebBulanan < (totalIncomeRiil * 0.1)) {
            lblStatusKeb.setText("⚠️ Dompet Kebutuhan sekarat, hematlah!");
            lblStatusKeb.setStyle("-fx-text-fill: #F1C40F; -fx-font-size: 12px; -fx-font-style: italic;");
        } else {
            String rentang = modeHari == 30 ? "harian" : (modeHari == 4 ? "mingguan" : "bulanan");
            lblStatusKeb.setText("✅ Aman untuk belanja kas " + rentang);
            lblStatusKeb.setStyle("-fx-text-fill: #2ECC71; -fx-font-size: 12px; -fx-font-style: italic;");
        }

        if (sisaKeiBulanan <= 0) {
            lblStatusKei.setText("🚨 Kantong Keinginan Kritis! Dilarang self-reward!");
            lblStatusKei.setStyle("-fx-text-fill: " + ColorPalette.TEXT_DANGER + "; -fx-font-size: 12px; -fx-font-weight: bold;");
        } else if (sisaKeiBulanan < (totalIncomeRiil * 0.05)) {
            lblStatusKei.setText("⚠️ Jatah jajan hampir menyentuh limit!");
            lblStatusKei.setStyle("-fx-text-fill: #F1C40F; -fx-font-size: 12px; -fx-font-style: italic;");
        } else {
            lblStatusKei.setText("✅ Kondisi kantong jajan stabil");
            lblStatusKei.setStyle("-fx-text-fill: #2ECC71; -fx-font-size: 12px; -fx-font-style: italic;");
        }
    }
}
