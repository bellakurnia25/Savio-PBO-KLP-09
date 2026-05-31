package com.savio.scenes;

import com.savio.model.DataDompet;
import com.savio.model.ModelTransaksi;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class LaporanView extends VBox {

    private final Label lblKeb;
    private final Label lblKei;
    private final Label lblStatusKeb;
    private final Label lblStatusKei;
    private final HBox chipStatusKeb;
    private final HBox chipStatusKei;
    
    private final Label lblDetailBulananKeb;
    private final Label lblDetailBulananKei;
    
    private final Label lblTotalIncome;
    private final Label lblTotalOutcome;

    private final Region fillKeb;
    private final Region fillKei;
    private final StackPane trackKeb;
    private final StackPane trackKei;

    private int modeHari = 1;
    private final Label lblTitleKeb;
    private final Label lblTitleKei;

    public LaporanView() {
        this.setSpacing(25);
        this.setPadding(new Insets(30, 40, 30, 40));
        this.setStyle("-fx-background-color: " + "#0F1123" + ";");

        //Header
Label lblTitle = new Label("Analisis Batas Aman Kas 📈");
        lblTitle.setStyle("-fx-text-fill: white; -fx-font-size: 26px; -fx-font-weight: bold;");
        
        Label lblSubTitle = new Label("Pantau sisa jatah harian, mingguan, atau bulananmu di sini.");
        lblSubTitle.setStyle("-fx-text-fill: #A0A4B8; -fx-font-size: 14px;");
        
        VBox titleBox = new VBox(5, lblTitle, lblSubTitle);

        HBox btnGroup = new HBox(8);
        btnGroup.setAlignment(Pos.CENTER);
        Button btnHarian = buatTombolFilter("Harian");
        Button btnMingguan = buatTombolFilter("Mingguan");
        Button btnBulanan = buatTombolFilter("Bulanan");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox topRow = new HBox();
        topRow.setAlignment(Pos.CENTER_LEFT);
        topRow.getChildren().addAll(titleBox, spacer, btnGroup);
        this.getChildren().add(topRow);

        String styleActive = "-fx-background-color: linear-gradient(to right, #9B5CF6, #F72BB0); -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 8; -fx-cursor: hand; -fx-padding: 8 20;";
        String styleInactive = "-fx-background-color: transparent; -fx-border-color: #2D314A; -fx-border-radius: 8; -fx-text-fill: " + "#A0A4B8" + "; -fx-font-weight: bold; -fx-cursor: hand; -fx-padding: 8 20;";

        btnHarian.setStyle(styleInactive);
        btnMingguan.setStyle(styleInactive);
        btnBulanan.setStyle(styleActive);
        btnGroup.getChildren().addAll(btnHarian, btnMingguan, btnBulanan);

        // Row 1
        HBox summaryCard = new HBox(40);
        summaryCard.setPadding(new Insets(20, 30, 20, 30));
        summaryCard.setStyle("-fx-background-color: " + "#1A1D36" + "; -fx-background-radius: 16; -fx-border-color: #2D314A; -fx-border-radius: 16;");
        summaryCard.setAlignment(Pos.CENTER);

        VBox incBox = buatBlokRingkasan("📥", "TOTAL PEMASUKAN MURNI", "#2ECC71");
        lblTotalIncome = (Label) incBox.getChildren().get(2);

        Region lineSpacer = new Region();
        lineSpacer.setPrefSize(1, 40);
        lineSpacer.setStyle("-fx-background-color: #2D314A;");

        VBox outBox = buatBlokRingkasan("💸", "TOTAL PENGELUARAN AKTIF", "#E74C3C");
        lblTotalOutcome = (Label) outBox.getChildren().get(2);

        summaryCard.getChildren().addAll(incBox, lineSpacer, outBox);
        HBox.setHgrow(incBox, Priority.ALWAYS);
        HBox.setHgrow(outBox, Priority.ALWAYS);
        this.getChildren().add(summaryCard);

        // Row 2
        GridPane gridCards = new GridPane();
        gridCards.setHgap(20);
        gridCards.setVgap(20);

        lblTitleKeb = new Label("Sisa Jatah Kebutuhan (Bulan Ini)");
        lblKeb = new Label("Rp 0");
        lblDetailBulananKeb = new Label("Sisa saku bulanan asli: Rp 0");
        lblStatusKeb = new Label("Aman");
        chipStatusKeb = buatChipStatus(lblStatusKeb, "#2ECC71");
        fillKeb = new Region();
        trackKeb = buatProgressBar(fillKeb, "#b66700");
        VBox cardKeb = buatKartuDetail(lblTitleKeb, lblKeb, trackKeb, lblDetailBulananKeb, chipStatusKeb, "🛒");

        lblTitleKei = new Label("Sisa Jatah Keinginan (Bulan Ini)");
        lblKei = new Label("Rp 0");
        lblDetailBulananKei = new Label("Sisa saku bulanan asli: Rp 0");
        lblStatusKei = new Label("Aman");
        chipStatusKei = buatChipStatus(lblStatusKei, "#2ECC71");
        fillKei = new Region();
        trackKei = buatProgressBar(fillKei, "#2e54a5");
        VBox cardKei = buatKartuDetail(lblTitleKei, lblKei, trackKei, lblDetailBulananKei, chipStatusKei, "☕");

        gridCards.add(cardKeb, 0, 0);
        gridCards.add(cardKei, 1, 0);

        GridPane.setHgrow(cardKeb, Priority.ALWAYS);
        GridPane.setHgrow(cardKei, Priority.ALWAYS);

        javafx.scene.layout.ColumnConstraints cc1 = new javafx.scene.layout.ColumnConstraints();
        cc1.setPercentWidth(50);
        javafx.scene.layout.ColumnConstraints cc2 = new javafx.scene.layout.ColumnConstraints();
        cc2.setPercentWidth(50);
        gridCards.getColumnConstraints().addAll(cc1, cc2);

        this.getChildren().add(gridCards);

        hitungBatasAman();

        btnHarian.setOnAction(e -> {
            modeHari = 30;
            btnHarian.setStyle(styleActive);
            btnMingguan.setStyle(styleInactive);
            btnBulanan.setStyle(styleInactive);
            lblTitleKeb.setText("Sisa Jatah Kebutuhan (Hari Ini)");
            lblTitleKei.setText("Sisa Jatah Keinginan (Hari Ini)");
            hitungBatasAman();
        });

        btnMingguan.setOnAction(e -> {
            modeHari = 4;
            btnHarian.setStyle(styleInactive);
            btnMingguan.setStyle(styleActive);
            btnBulanan.setStyle(styleInactive);
            lblTitleKeb.setText("Sisa Jatah Kebutuhan (Minggu Ini)");
            lblTitleKei.setText("Sisa Jatah Keinginan (Minggu Ini)");
            hitungBatasAman();
        });

        btnBulanan.setOnAction(e -> {
            modeHari = 1;
            btnHarian.setStyle(styleInactive);
            btnMingguan.setStyle(styleInactive);
            btnBulanan.setStyle(styleActive);
            lblTitleKeb.setText("Sisa Jatah Kebutuhan (Bulan Ini)");
            lblTitleKei.setText("Sisa Jatah Keinginan (Bulan Ini)");
            hitungBatasAman();
        });
        DataDompet.SALDO_AKTIF.addListener((obs, oldVal, newVal) -> hitungBatasAman());
        DataDompet.NOMINAL_KEBUTUHAN.addListener((obs, oldVal, newVal) -> hitungBatasAman());
        DataDompet.NOMINAL_KEINGINAN.addListener((obs, oldVal, newVal) -> hitungBatasAman());
    }

    private Button buatTombolFilter(String teks) {
        Button btn = new Button(teks);
        btn.setCursor(Cursor.HAND);
        return btn;
    }

    private VBox buatBlokRingkasan(String icon, String judul, String warnaTeks) {
        VBox box = new VBox(8);
        box.setAlignment(Pos.CENTER);
        
        Label lblIcon = new Label(icon);
        lblIcon.setFont(Font.font(20));
        lblIcon.setStyle("-fx-text-fill: #FFFFFF;"); 
        
        Label lblJdl = new Label(judul);
        lblJdl.setStyle("-fx-text-fill: " + "#A0A4B8" + "; -fx-font-size: 11px; -fx-font-weight: bold; -fx-letter-spacing: 1px;");
        
        Label lblVal = new Label("Rp 0");
        lblVal.setStyle("-fx-text-fill: " + warnaTeks + "; -fx-font-size: 20px; -fx-font-weight: bold;");
        
        box.getChildren().addAll(lblIcon, lblJdl, lblVal);
        return box;
    }

    private VBox buatKartuDetail(Label lblTitle, Label lblValMain, StackPane bar, Label lblDetail, HBox chipStatus, String iconHeader) {
        VBox card = new VBox(15);
        card.setPadding(new Insets(25));
        card.setStyle("-fx-background-color: " + "#1A1D36" + "; -fx-background-radius: 16;");

        HBox header = new HBox();
        header.setAlignment(Pos.CENTER_LEFT);
        lblTitle.setStyle("-fx-text-fill: " + "#A0A4B8" + "; -fx-font-size: 14px; -fx-font-weight: bold;");
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        Label lblIco = new Label(iconHeader);
        lblIco.setFont(Font.font(18));
        header.getChildren().addAll(lblTitle, spacer, lblIco);

        lblValMain.setStyle("-fx-text-fill: " + "white" + "; -fx-font-size: 28px; -fx-font-weight: bold;");

        lblDetail.setStyle("-fx-text-fill: " + "#A0A4B8" + "; -fx-font-size: 12px;");

        VBox footerInfo = new VBox(8, bar, lblDetail, chipStatus);
        VBox.setMargin(chipStatus, new Insets(10, 0, 0, 0));

        card.getChildren().addAll(header, lblValMain, footerInfo);
        return card;
    }

    private StackPane buatProgressBar(Region fill, String warnaAksen) {
        StackPane track = new StackPane();
        track.setStyle("-fx-background-color: #2D314A; -fx-background-radius: 10;");
        track.setPrefHeight(8);
        track.setAlignment(Pos.CENTER_LEFT);

        fill.setStyle("-fx-background-color: " + warnaAksen + "; -fx-background-radius: 10;");
        fill.setPrefHeight(8);
        fill.setPrefWidth(0); 
        fill.setMaxWidth(Region.USE_PREF_SIZE);

        track.getChildren().add(fill);
        return track;
    }

    private HBox buatChipStatus(Label lblText, String warna) {
        HBox chip = new HBox();
        chip.setAlignment(Pos.CENTER);
        chip.setPadding(new Insets(6, 12, 6, 12));
        
        lblText.setStyle("-fx-text-fill: " + warna + "; -fx-font-size: 12px; -fx-font-weight: bold;");
        
        chip.setStyle("-fx-background-color: " + warna + "22; -fx-background-radius: 20; -fx-border-color: " + warna + "44; -fx-border-radius: 20;");
        chip.getChildren().add(lblText);
        chip.setMaxWidth(Region.USE_PREF_SIZE); 
        return chip;
    }

    private void updateChipStyle(HBox chip, Label lblText, String warna) {
        lblText.setStyle("-fx-text-fill: " + warna + "; -fx-font-size: 12px; -fx-font-weight: bold;");
        chip.setStyle("-fx-background-color: " + warna + "22; -fx-background-radius: 20; -fx-border-color: " + warna + "44; -fx-border-radius: 20;");
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

        double jatahAwalKeb = totalIncomeRiil * (DataDompet.PERSEN_KEBUTUHAN.get() / 100.0);
        double jatahAwalKei = totalIncomeRiil * (DataDompet.PERSEN_KEINGINAN.get() / 100.0);

        lblTotalIncome.setText("Rp " + String.format("%,.0f", totalIncomeRiil));
        lblTotalOutcome.setText("Rp " + String.format("%,.0f", totalOutcomeRiil));

        lblDetailBulananKeb.setText("Sisa saku bulanan asli: Rp " + String.format("%,.0f", sisaKebBulanan));
        lblDetailBulananKei.setText("Sisa saku bulanan asli: Rp " + String.format("%,.0f", sisaKeiBulanan));

        double batasKeb = sisaKebBulanan / modeHari;
        double batasKei = sisaKeiBulanan / modeHari;

        if (batasKeb < 0) batasKeb = 0;
        if (batasKei < 0) batasKei = 0;

        lblKeb.setText("Rp " + String.format("%,.0f", batasKeb));
        lblKei.setText("Rp " + String.format("%,.0f", batasKei));

        double persenKeb = jatahAwalKeb > 0 ? (sisaKebBulanan / jatahAwalKeb) : 0;
        if (persenKeb < 0) persenKeb = 0;
        if (persenKeb > 1) persenKeb = 1;
        fillKeb.prefWidthProperty().bind(trackKeb.widthProperty().multiply(persenKeb));

        double persenKei = jatahAwalKei > 0 ? (sisaKeiBulanan / jatahAwalKei) : 0;
        if (persenKei < 0) persenKei = 0;
        if (persenKei > 1) persenKei = 1;
        fillKei.prefWidthProperty().bind(trackKei.widthProperty().multiply(persenKei));

        if (sisaKebBulanan <= 0) {
            lblStatusKeb.setText("🚨 Jatah Kebutuhan HABIS! Stop pengeluaran!");
            updateChipStyle(chipStatusKeb, lblStatusKeb, "#E74C3C");
        } else if (sisaKebBulanan < (totalIncomeRiil * 0.1)) {
            lblStatusKeb.setText("⚠️ Dompet Kebutuhan sekarat, hematlah!");
            updateChipStyle(chipStatusKeb, lblStatusKeb, "#F1C40F");
        } else {
            String rentang = modeHari == 30 ? "harian" : (modeHari == 4 ? "mingguan" : "bulanan");
            lblStatusKeb.setText("✅ Aman untuk belanja " + rentang);
            updateChipStyle(chipStatusKeb, lblStatusKeb, "#2ECC71");
        }

        if (sisaKeiBulanan <= 0) {
            lblStatusKei.setText("🚨 Kantong Keinginan Kritis! Puasa jajan!");
            updateChipStyle(chipStatusKei, lblStatusKei, "#E74C3C");
        } else if (sisaKeiBulanan < (totalIncomeRiil * 0.05)) {
            lblStatusKei.setText("⚠️ Jatah jajan hampir menyentuh limit!");
            updateChipStyle(chipStatusKei, lblStatusKei, "#F1C40F");
        } else {
            lblStatusKei.setText("✅ Kondisi kantong jajan stabil");
            updateChipStyle(chipStatusKei, lblStatusKei, "#2ECC71");
        }
    }
}