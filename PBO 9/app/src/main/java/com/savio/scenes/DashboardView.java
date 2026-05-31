package com.savio.scenes;

import com.savio.model.DataDompet;
import com.savio.model.DataSesi;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.shape.Arc;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeLineCap;

public class DashboardView extends VBox {
    private final Label lblSaldoAtas;
    private final Label lblDaruratAtas;
    private final Label lblAlokasiAtas;
    private final Label lblKebutuhanAtas;
    
    private final Label lblIncomeKanan;
    private final Label lblOutcomeKanan;
    private final Label lblSisaKanan;

    private final Label lblLegendaKebutuhan;
    private final Label lblLegendaKeinginan;
    private final Label lblLegendaDarurat;
    
    private final Label lblValKebutuhan;
    private final Label lblValKeinginan;
    private final Label lblValDarurat;
    private final Label lblCenterPieVal;

    private final Arc arcKebutuhan;
    private final Arc arcKeinginan;
    private final Arc arcDarurat;

    private final MainLayout mainLayout;
    private final Label lblTextInsight;

    public DashboardView(MainLayout mainLayout) {
        this.mainLayout = mainLayout;
        this.setSpacing(20);
        this.setPadding(new Insets(25, 30, 25, 30));
        this.setStyle("-fx-background-color: #0F1123;");

        //Header
        HBox topRow = new HBox();
        topRow.setAlignment(Pos.CENTER_LEFT);

        VBox welcomeBox = new VBox(5);
        Label lblWelcome = new Label();
        lblWelcome.setStyle("-fx-text-fill: white; -fx-font-size: 26px; -fx-font-weight: bold;");
        lblWelcome.textProperty().bind(Bindings.concat("Halo, ", DataSesi.namaPenggunaProperty(), "! 👋"));
        
        Label lblSub = new Label("Kelola keuanganmu dengan bijak.");
        lblSub.setStyle("-fx-text-fill: #A0A4B8; -fx-font-size: 14px;");
        welcomeBox.getChildren().addAll(lblWelcome, lblSub);

        Region topSpacer = new Region();
        HBox.setHgrow(topSpacer, Priority.ALWAYS);
        Button btnNotif = new Button("");
        btnNotif.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-font-size: 18px; -fx-cursor: hand;");
        topRow.getChildren().addAll(welcomeBox, topSpacer, btnNotif);
        this.getChildren().add(topRow);

        //Row 1
        HBox topCardsRow = new HBox(15);
        topCardsRow.setAlignment(Pos.CENTER_LEFT);

        VBox cardSaldo = buatKartuAtas("Total Saldo Aktif", "linear-gradient(to bottom right, #0d155d, #1D2A6D)", "Saldo yang dapat digunakan", "");
        lblSaldoAtas = (Label) cardSaldo.getChildren().get(1);

        VBox cardDarurat = buatKartuAtas("Dana Darurat (Terkunci)", "linear-gradient(to bottom right, #5A1A6B, #340D40)", "Aman dan tidak dapat digunakan", "🔒");
        lblDaruratAtas = (Label) cardDarurat.getChildren().get(1);

        VBox cardAlokasi = buatKartuAtas("Saldo Keinginan Bulan Ini", "linear-gradient(to bottom right, #2e54a5, #245379)", "Dari total alokasi dana", "☕");
        lblAlokasiAtas = (Label) cardAlokasi.getChildren().get(1);

        VBox cardKebutuhan = buatKartuAtas("Saldo Kebutuhan Bulan Ini", "linear-gradient(to bottom right, #0E7E6B, #065A4A)", "Anggaran kebutuhan pokok", "🛒");
        lblKebutuhanAtas = (Label) cardKebutuhan.getChildren().get(1);

        HBox.setHgrow(cardSaldo, Priority.ALWAYS); 
        HBox.setHgrow(cardKebutuhan, Priority.ALWAYS);
        HBox.setHgrow(cardAlokasi, Priority.ALWAYS);
        HBox.setHgrow(cardDarurat, Priority.ALWAYS); 

        topCardsRow.getChildren().addAll(cardSaldo, cardKebutuhan, cardAlokasi, cardDarurat);
        this.getChildren().add(topCardsRow);

        //Row 2
        HBox middleRow = new HBox(20);
        middleRow.setAlignment(Pos.TOP_LEFT);

        VBox boxAlokasi = new VBox(15);
        boxAlokasi.setPadding(new Insets(20));
        boxAlokasi.setStyle("-fx-background-color: #1A1D36; -fx-background-radius: 12;");
        HBox.setHgrow(boxAlokasi, Priority.ALWAYS);

        Label lblTitleAlokasi = new Label("Alokasi Dana");
        lblTitleAlokasi.setStyle("-fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold;");
        
        HBox chartContent = new HBox(30);
        chartContent.setAlignment(Pos.CENTER_LEFT);
        
        arcKebutuhan = new Arc(0, 0, 70, 70, 90, 0);
        arcKebutuhan.setFill(javafx.scene.paint.Color.TRANSPARENT);
        arcKebutuhan.setStroke(javafx.scene.paint.Color.web("#0E7E6B"));
        arcKebutuhan.setStrokeWidth(22);
        arcKebutuhan.setStrokeLineCap(StrokeLineCap.BUTT); 

        arcKeinginan = new Arc(0, 0, 70, 70, 0, 0);
        arcKeinginan.setFill(javafx.scene.paint.Color.TRANSPARENT);
        arcKeinginan.setStroke(javafx.scene.paint.Color.web("#2e54a5"));
        arcKeinginan.setStrokeWidth(22);
        arcKeinginan.setStrokeLineCap(StrokeLineCap.BUTT);

        arcDarurat = new Arc(0, 0, 70, 70, 0, 0);
        arcDarurat.setFill(javafx.scene.paint.Color.TRANSPARENT);
        arcDarurat.setStroke(javafx.scene.paint.Color.web("#5A1A6B"));
        arcDarurat.setStrokeWidth(22);
        arcDarurat.setStrokeLineCap(StrokeLineCap.BUTT);

        Group donutGroup = new Group(arcKebutuhan, arcKeinginan, arcDarurat);

        StackPane doughnutWrapper = new StackPane();
        doughnutWrapper.setPrefSize(180, 180);
        
        VBox chartText = new VBox(2);
        chartText.setAlignment(Pos.CENTER);
        Label lblChartTitle = new Label("Total Income");
        lblChartTitle.setStyle("-fx-text-fill: #A0A4B8; -fx-font-size: 11px;");
        lblCenterPieVal = new Label("Rp 0");
        lblCenterPieVal.setStyle("-fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold;");
        chartText.getChildren().addAll(lblChartTitle, lblCenterPieVal);
        
        doughnutWrapper.getChildren().addAll(donutGroup, chartText);

        VBox itemKeb = buatItemLegendaLengkap("Kebutuhan", "#0E7E6B"); 
        lblLegendaKebutuhan = (Label) ((HBox) itemKeb.getChildren().get(0)).getChildren().get(1);
        lblValKebutuhan = (Label) itemKeb.getChildren().get(1);

        VBox itemKei = buatItemLegendaLengkap("Keinginan", "#2e54a5"); 
        lblLegendaKeinginan = (Label) ((HBox) itemKei.getChildren().get(0)).getChildren().get(1);
        lblValKeinginan = (Label) itemKei.getChildren().get(1);

        VBox itemDar = buatItemLegendaLengkap("Dana Darurat", "#5A1A6B"); 
        lblLegendaDarurat = (Label) ((HBox) itemDar.getChildren().get(0)).getChildren().get(1);
        lblValDarurat = (Label) itemDar.getChildren().get(1);

        VBox legendaBox = new VBox(15);
        legendaBox.setAlignment(Pos.CENTER_LEFT);
        legendaBox.getChildren().addAll(itemKeb, itemKei, itemDar);
        
        chartContent.getChildren().addAll(doughnutWrapper, legendaBox);

        boxAlokasi.getChildren().addAll(lblTitleAlokasi, chartContent);

        VBox boxRingkasan = new VBox(15);
        boxRingkasan.setPadding(new Insets(20)); 
        boxRingkasan.setPrefWidth(350);
        boxRingkasan.setStyle("-fx-background-color: #1A1D36; -fx-background-radius: 12;");

        Label lblTitleRingkasan = new Label("Ringkasan Bulan Ini");
        lblTitleRingkasan.setStyle("-fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold;");
        
        HBox rowIncome = buatRowRingkasan("💼", "Total Income", "#2fac40"); 
        lblIncomeKanan = (Label) ((VBox) rowIncome.getChildren().get(1)).getChildren().get(1);
        
        HBox rowOutcome = buatRowRingkasan("📤", "Total Outcome", "#a31324"); 
        lblOutcomeKanan = (Label) ((VBox) rowOutcome.getChildren().get(1)).getChildren().get(1);
        
        HBox rowSisa = buatRowRingkasan("💰", "Sisa Aktif", "#db911a"); 
        lblSisaKanan = (Label) ((VBox) rowSisa.getChildren().get(1)).getChildren().get(1);

        boxRingkasan.getChildren().addAll(lblTitleRingkasan, rowIncome, rowOutcome, rowSisa);
        
        middleRow.getChildren().addAll(boxAlokasi, boxRingkasan);
        this.getChildren().add(middleRow);

        HBox bottomRow = new HBox(20);
        
        //Row 3
        VBox boxInsight = new VBox(10);
        boxInsight.setPadding(new Insets(20));
        boxInsight.setStyle("-fx-background-color: linear-gradient(to right, #1E1238, #18112C); -fx-background-radius: 12;");
        HBox.setHgrow(boxInsight, Priority.ALWAYS);
        
        HBox headerInsight = new HBox(10);
        headerInsight.setAlignment(Pos.CENTER_LEFT);
        Label iconInsight = new Label("📈"); iconInsight.setStyle("-fx-font-size: 24px; -fx-text-fill: #BE84EE;");
        Label lblTitleInsight = new Label("Insight SAVIO");
        lblTitleInsight.setStyle("-fx-text-fill: white; -fx-font-size: 15px; -fx-font-weight: bold;");
        headerInsight.getChildren().addAll(iconInsight, lblTitleInsight);
        
        lblTextInsight = new Label("Mengkalkulasi tren penggunaan saku keuangan Anda...");
        lblTextInsight.setStyle("-fx-text-fill: #A0A4B8; -fx-font-size: 13px;");
        lblTextInsight.setWrapText(true);
        
        boxInsight.getChildren().addAll(headerInsight, lblTextInsight);

        bottomRow.getChildren().addAll(boxInsight);

        this.getChildren().add(bottomRow);

        Region bottomSpacer = new Region();
        VBox.setVgrow(bottomSpacer, Priority.ALWAYS);

        this.getChildren().add(bottomSpacer);

        refreshDashboardData();

        refreshDashboardData();
        DataDompet.SALDO_AKTIF.addListener((o, oldV, newV) -> refreshDashboardData());
        DataDompet.NOMINAL_KEBUTUHAN.addListener((o, oldV, newV) -> refreshDashboardData());
        DataDompet.NOMINAL_KEINGINAN.addListener((o, oldV, newV) -> refreshDashboardData());
    }

    private VBox buatKartuAtas(String judul, String warnaGradient, String sub, String icon) {
        VBox card = new VBox(8);
        card.setPadding(new Insets(20));
        card.setStyle("-fx-background-color: " + warnaGradient + "; -fx-background-radius: 12;");
        
        HBox header = new HBox();
        Label lblT = new Label(judul); lblT.setStyle("-fx-text-fill: #E0E0E0; -fx-font-size: 13px;");
        Region spacer = new Region(); HBox.setHgrow(spacer, Priority.ALWAYS);
        Label lblIco = new Label(icon); lblIco.setStyle("-fx-text-fill: white; -fx-font-size: 16px; -fx-opacity: 0.7;");
        header.getChildren().addAll(lblT, spacer, lblIco);
        
        Label lblV = new Label("Rp 0"); lblV.setStyle("-fx-text-fill: white; -fx-font-size: 26px; -fx-font-weight: bold;");
        Label lblS = new Label(sub); lblS.setStyle("-fx-text-fill: #B0B0B0; -fx-font-size: 11px;");
        card.getChildren().addAll(header, lblV, lblS);
        return card;
    }

    private VBox buatItemLegendaLengkap(String teks, String warnaHex) {
        VBox container = new VBox(2);
        HBox rowTop = new HBox(8); rowTop.setAlignment(Pos.CENTER_LEFT);
        Circle dot = new Circle(5, javafx.scene.paint.Color.web(warnaHex));
        Label lblPersen = new Label(teks + " (0%)"); lblPersen.setStyle("-fx-text-fill: #D1D5DB; -fx-font-size: 12px;");
        rowTop.getChildren().addAll(dot, lblPersen);
        
        Label lblNominal = new Label("Rp 0"); 
        lblNominal.setStyle("-fx-text-fill: #A0A4B8; -fx-font-size: 11px;");
        lblNominal.setPadding(new Insets(0, 0, 0, 18)); 
        
        container.getChildren().addAll(rowTop, lblNominal);
        return container;
    }

    private HBox buatRowRingkasan(String icon, String judul, String iconBgColor) {
        HBox row = new HBox(15); 
        row.setAlignment(Pos.CENTER_LEFT); 
        row.setPadding(new Insets(5, 0, 5, 0));
        
        StackPane iconPane = new StackPane();
        iconPane.setMinSize(42, 42);
        iconPane.setPrefSize(42, 42);
        iconPane.setStyle("-fx-background-color: " + iconBgColor + "33; -fx-background-radius: 21;");

        Label lblIco = new Label(icon); 
        lblIco.setStyle("-fx-font-family: 'Segoe UI Symbol', 'Arial'; -fx-font-size: 20px; -fx-text-fill: " + iconBgColor + ";"); 
        
        iconPane.getChildren().add(lblIco);

        VBox textCol = new VBox(2);
        Label lblJdl = new Label(judul); lblJdl.setStyle("-fx-text-fill: #A0A4B8; -fx-font-size: 13px;");
        Label lblVal = new Label("Rp 0"); lblVal.setStyle("-fx-text-fill: white; -fx-font-size: 15px; -fx-font-weight: bold;");
        textCol.getChildren().addAll(lblJdl, lblVal);
        
        row.getChildren().addAll(iconPane, textCol);
        return row;
    }

    public void refreshDataKalkulasiPusat() {
        refreshDashboardData();
    }

    public void refreshDashboardData() {
        double saldoActive = DataDompet.SALDO_AKTIF.get();
        double emergencyFund = DataDompet.DANA_DARURAT.get();
        double nominalKeb = DataDompet.NOMINAL_KEBUTUHAN.get();
        double nominalKei = DataDompet.NOMINAL_KEINGINAN.get();

        double pKeb = DataDompet.PERSEN_KEBUTUHAN.get();
        double pKei = DataDompet.PERSEN_KEINGINAN.get();
        double pTab = DataDompet.PERSEN_TABUNGAN.get();
        
        double valKeb = Math.max(0, nominalKeb);
        double valKei = Math.max(0, nominalKei);
        double valDar = Math.max(0, emergencyFund);

        double total = valKeb + valKei + valDar;
        if (total <= 0) {
            valKeb = 1;
            total = 1;
        }

        double angleKeb = (valKeb / total) * 360.0;
        double angleKei = (valKei / total) * 360.0;
        double angleDar = (valDar / total) * 360.0;

        Platform.runLater(() -> {
            arcKebutuhan.setStartAngle(90);
            arcKebutuhan.setLength(-angleKeb);

            arcKeinginan.setStartAngle(90 - angleKeb);
            arcKeinginan.setLength(-angleKei);

            arcDarurat.setStartAngle(90 - angleKeb - angleKei);
            arcDarurat.setLength(-angleDar);

            lblSaldoAtas.setText("Rp " + String.format("%,.0f", saldoActive));
            lblDaruratAtas.setText("Rp " + String.format("%,.0f", emergencyFund));
            lblAlokasiAtas.setText("Rp " + String.format("%,.0f", nominalKei));
            lblKebutuhanAtas.setText("Rp " + String.format("%,.0f", nominalKeb));

            lblLegendaKebutuhan.setText(String.format("Kebutuhan (%.0f%%)", pKeb));
            lblValKebutuhan.setText("Rp " + String.format("%,.0f", nominalKeb));
            
            lblLegendaKeinginan.setText(String.format("Keinginan (%.0f%%)", pKei));
            lblValKeinginan.setText("Rp " + String.format("%,.0f", nominalKei));
            
            lblLegendaDarurat.setText(String.format("Dana Darurat (%.0f%%)", pTab));
            lblValDarurat.setText("Rp " + String.format("%,.0f", emergencyFund));

            double totalIncomeMurni = 0;
            double totalOutcomeMurni = 0;

            for (com.savio.model.ModelTransaksi t : DataDompet.LIST_TRANSAKSI) {
                if (t.getKategori().equalsIgnoreCase("Income")) {
                    totalIncomeMurni += t.getNominal();
                } else if (t.getKategori().equalsIgnoreCase("Outcome")) {
                    totalOutcomeMurni += t.getNominal();
                }
            }

            lblCenterPieVal.setText("Rp " + String.format("%,.0f", totalIncomeMurni));

            lblIncomeKanan.setText("Rp " + String.format("%,.0f", totalIncomeMurni)); 
            lblOutcomeKanan.setText("Rp " + String.format("%,.0f", totalOutcomeMurni)); 
            lblSisaKanan.setText("Rp " + String.format("%,.0f", saldoActive));

            if (nominalKei < 0) {
                lblTextInsight.setText("Pengeluaran keinginanmu sudah melewati batas alokasi! Rem dulu jajanmu hari ini.");
            } else if (nominalKei == 0) {
                lblTextInsight.setText("Kantong keinginanmu pas Rp 0. Jangan lakukan transaksi self-reward lagi sampai bulan depan ya.");
            } else {
                lblTextInsight.setText("Pengeluaran keinginanmu aman. Pertahankan alokasi bijak ini!");
            }
        });
    }

    @SuppressWarnings("unused")
    private double kalkulasiSisaOutcome(double totalIncome, double keb, double kei) {
        double jatahKebutuhanAwal = totalIncome * (DataDompet.PERSEN_KEBUTUHAN.get() / 100.0);
        double jatahKeinginanAwal = totalIncome * (DataDompet.PERSEN_KEINGINAN.get() / 100.0);
        return (jatahKebutuhanAwal - keb) + (jatahKeinginanAwal - kei);
    }
}