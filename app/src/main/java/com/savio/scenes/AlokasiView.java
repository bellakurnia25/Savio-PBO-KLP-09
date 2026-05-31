package com.savio.scenes;

import com.savio.model.DataDompet;
import com.savio.model.ModelTransaksi;
import com.savio.utils.KoneksiJSON;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.*;
import javafx.scene.shape.Arc;
import javafx.scene.shape.StrokeLineCap;

public class AlokasiView extends VBox {
    private final Slider sliderKebutuhan;
    private final Slider sliderKeinginan;
    private final Slider sliderTabungan;

    private final Label lblPctKeb;
    private final Label lblPctKei;
    private final Label lblPctTab;

    private final Label lblRpKeb;
    private final Label lblRpKei;
    private final Label lblRpTab;

    private final Label lblSimKebVal;
    private final Label lblSimKeiVal;
    private final Label lblSimTabVal;
    private final Label lblSimKebPct;
    private final Label lblSimKeiPct;
    private final Label lblSimTabPct;

    private final Arc arcKebutuhan;
    private final Arc arcKeinginan;
    private final Arc arcDarurat;
    private final Label lblCenterTotal;
    
    private final Label lblTitleSimulasi;

    private double totalIncomeSaatIni = 0;

    public AlokasiView() {
        this.setSpacing(25);
        this.setPadding(new Insets(30, 40, 30, 40));
        this.setStyle("-fx-background-color: #0F1123;");
        this.setAlignment(Pos.TOP_LEFT);

        hitungTotalIncome();

        //Header
        VBox headerBox = new VBox(5);
        Label lblTitle = new Label("Atur Alokasi Dana");
        lblTitle.setStyle("-fx-text-fill: white; -fx-font-size: 26px; -fx-font-weight: bold;");
        Label lblSub = new Label("Tentukan persentase alokasi dari total income");
        lblSub.setStyle("-fx-text-fill: #A0A4B8; -fx-font-size: 14px;");
        headerBox.getChildren().addAll(lblTitle, lblSub);
        this.getChildren().add(headerBox);

        //Row 1
        HBox topContent = new HBox(40);
        topContent.setAlignment(Pos.CENTER_LEFT);

        VBox slidersBox = new VBox(25);
        slidersBox.setPrefWidth(450);
        slidersBox.setAlignment(Pos.CENTER_LEFT);

        lblPctKeb = new Label(); lblRpKeb = new Label();
        lblPctKei = new Label(); lblRpKei = new Label();
        lblPctTab = new Label(); lblRpTab = new Label();

        sliderKebutuhan = buatSliderKustom(DataDompet.PERSEN_KEBUTUHAN.get());
        sliderKeinginan = buatSliderKustom(DataDompet.PERSEN_KEINGINAN.get());
        sliderTabungan = buatSliderKustom(DataDompet.PERSEN_TABUNGAN.get());

        HBox rowKeb = buatBarisSlider("Kebutuhan", sliderKebutuhan, lblPctKeb, lblRpKeb, "#b66700");
        HBox rowKei = buatBarisSlider("Keinginan", sliderKeinginan, lblPctKei, lblRpKei, "#4759b1");
        HBox rowTab = buatBarisSlider("Dana Darurat", sliderTabungan, lblPctTab, lblRpTab, "#b12463");

        slidersBox.getChildren().addAll(rowKeb, rowKei, rowTab);

        VBox chartBox = new VBox();
        chartBox.setAlignment(Pos.CENTER);
        chartBox.setPrefSize(250, 250);

        arcKebutuhan = buatArc("#b66700");
        arcKeinginan = buatArc("#4759b1");
        arcDarurat = buatArc("#b12463");

        Arc arcBg = new Arc(0, 0, 90, 90, 0, 360);
        arcBg.setFill(javafx.scene.paint.Color.TRANSPARENT);
        arcBg.setStroke(javafx.scene.paint.Color.web("#2D314A"));
        arcBg.setStrokeWidth(28);

        Group donutGroup = new Group(arcBg, arcKebutuhan, arcKeinginan, arcDarurat);
        StackPane doughnutPane = new StackPane();
        doughnutPane.setPrefSize(220, 220);

        VBox chartText = new VBox(2);
        chartText.setAlignment(Pos.CENTER);
        Label lblChartTitle = new Label("Total Income");
        lblChartTitle.setStyle("-fx-text-fill: #A0A4B8; -fx-font-size: 13px;");
        lblCenterTotal = new Label("Rp " + String.format("%,.0f", totalIncomeSaatIni));
        lblCenterTotal.setStyle("-fx-text-fill: white; -fx-font-size: 18px; -fx-font-weight: bold;");
        chartText.getChildren().addAll(lblChartTitle, lblCenterTotal);

        doughnutPane.getChildren().addAll(donutGroup, chartText);
        chartBox.getChildren().add(doughnutPane);

        topContent.getChildren().addAll(slidersBox, chartBox);
        this.getChildren().add(topContent);

        //Row 2
        VBox midContent = new VBox(15);
        
        lblTitleSimulasi = new Label("Simulasi Alokasi (Berdasarkan Income Rp " + String.format("%,.0f", totalIncomeSaatIni) + ")");
        lblTitleSimulasi.setStyle("-fx-text-fill: #A0A4B8; -fx-font-size: 14px; -fx-font-weight: bold;");
        
        HBox cardsRow = new HBox(15);
        
        lblSimKebVal = new Label(); lblSimKebPct = new Label();
        VBox cardKeb = buatKartuSimulasi("Kebutuhan", lblSimKebPct, lblSimKebVal, "#b66700");
        
        lblSimKeiVal = new Label(); lblSimKeiPct = new Label();
        VBox cardKei = buatKartuSimulasi("Keinginan", lblSimKeiPct, lblSimKeiVal, "#4759b1");
        
        lblSimTabVal = new Label(); lblSimTabPct = new Label();
        VBox cardTab = buatKartuSimulasi("Dana Darurat", lblSimTabPct, lblSimTabVal, "#b12463");

        HBox.setHgrow(cardKeb, Priority.ALWAYS);
        HBox.setHgrow(cardKei, Priority.ALWAYS);
        HBox.setHgrow(cardTab, Priority.ALWAYS);
        cardsRow.getChildren().addAll(cardKeb, cardKei, cardTab);

        midContent.getChildren().addAll(lblTitleSimulasi, cardsRow);
        this.getChildren().add(midContent);

        //Row 3
        HBox bottomRow = new HBox(20);
        bottomRow.setAlignment(Pos.CENTER_LEFT);

        HBox infoCard = new HBox(15);
        infoCard.setAlignment(Pos.CENTER_LEFT);
        infoCard.setPadding(new Insets(15, 20, 15, 20));
        infoCard.setStyle("-fx-background-color: #1A1D36; -fx-background-radius: 12;");
        HBox.setHgrow(infoCard, Priority.ALWAYS);

        StackPane lockIconPane = new StackPane();
        lockIconPane.setPrefSize(40, 40);
        lockIconPane.setStyle("-fx-border-color: #2D314A; -fx-border-width: 2; -fx-border-radius: 10; -fx-background-radius: 10;");
        Label lblLock = new Label("🔒");
        lblLock.setStyle("-fx-font-size: 18px; -fx-text-fill: #A0A4B8;");
        lockIconPane.getChildren().add(lblLock);

        VBox infoText = new VBox(3);
        Label lblInfoTitle = new Label("Dana Darurat Terkunci");
        lblInfoTitle.setStyle("-fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold;");
        Label lblInfoDesc = new Label("Dana darurat adalah cadangan untuk keadaan darurat.\nDana ini tidak dapat digunakan untuk transaksi biasa.");
        lblInfoDesc.setStyle("-fx-text-fill: #A0A4B8; -fx-font-size: 12px;");
        infoText.getChildren().addAll(lblInfoTitle, lblInfoDesc);

        infoCard.getChildren().addAll(lockIconPane, infoText);

        Button btnSimpan = new Button("Simpan Alokasi");
        btnSimpan.setPrefHeight(60);
        btnSimpan.setPrefWidth(200);
        btnSimpan.setStyle("-fx-background-color: linear-gradient(to right, #D81B60, #8E24AA); -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 10; -fx-cursor: hand; -fx-font-size: 15px;");
        btnSimpan.setOnAction(e -> prosesSimpanAlokasi());

        bottomRow.getChildren().addAll(infoCard, btnSimpan);
        this.getChildren().add(bottomRow);

        sliderTabungan.setDisable(true); 

        sliderKebutuhan.valueProperty().addListener((o, ov, nv) -> {
            double kebVal = Math.round(nv.doubleValue());
            double keiVal = Math.round(sliderKeinginan.getValue());

            if (kebVal + keiVal > 100) {
                kebVal = 100 - keiVal;
                sliderKebutuhan.setValue(kebVal);
            }

            sliderTabungan.setValue(100 - (kebVal + keiVal));
            updateSemuaUI();
        });

        sliderKeinginan.valueProperty().addListener((o, ov, nv) -> {
            double keiVal = Math.round(nv.doubleValue());
            double kebVal = Math.round(sliderKebutuhan.getValue());

            if (kebVal + keiVal > 100) {
                keiVal = 100 - kebVal;
                sliderKeinginan.setValue(keiVal);
            }

            sliderTabungan.setValue(100 - (kebVal + keiVal));
            updateSemuaUI();
        });
        updateSemuaUI();
    }

    private void hitungTotalIncome() {
        totalIncomeSaatIni = 0;
        for (ModelTransaksi t : DataDompet.LIST_TRANSAKSI) {
            if (t.getKategori().equalsIgnoreCase("Income")) {
                totalIncomeSaatIni += t.getNominal();
            }
        }
    }

    private Arc buatArc(String hexWarna) {
        Arc arc = new Arc(0, 0, 90, 90, 0, 0);
        arc.setFill(javafx.scene.paint.Color.TRANSPARENT);
        arc.setStroke(javafx.scene.paint.Color.web(hexWarna));
        arc.setStrokeWidth(28);
        arc.setStrokeLineCap(StrokeLineCap.BUTT);
        return arc;
    }

    private Slider buatSliderKustom(double valueAwal) {
        Slider slider = new Slider(0, 100, valueAwal);
        slider.setShowTickMarks(false);
        slider.setShowTickLabels(false);
        slider.setBlockIncrement(1.0);
        slider.setMajorTickUnit(1.0);
        slider.setSnapToTicks(true);
        slider.setStyle("-fx-control-inner-background: #2D314A;"); 
        return slider;
    }

    private HBox buatBarisSlider(String title, Slider slider, Label lblPct, Label lblRp, String hexColor) {
        HBox row = new HBox(15);
        row.setAlignment(Pos.CENTER_LEFT);

        Label lblTitle = new Label(title);
        lblTitle.setPrefWidth(100);
        lblTitle.setStyle("-fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold;");

        HBox.setHgrow(slider, Priority.ALWAYS);

        VBox textValues = new VBox(0);
        textValues.setAlignment(Pos.CENTER_RIGHT);
        textValues.setPrefWidth(100);
        
        lblPct.setStyle("-fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold;");
        lblRp.setStyle("-fx-text-fill: #6C7293; -fx-font-size: 12px;");
        textValues.getChildren().addAll(lblPct, lblRp);

        row.getChildren().addAll(lblTitle, slider, textValues);
        return row;
    }

    private VBox buatKartuSimulasi(String title, Label lblPct, Label lblVal, String hexBgColor) {
        VBox card = new VBox(10);
        card.setPadding(new Insets(20));
        card.setStyle("-fx-background-color: " + hexBgColor + "33; -fx-background-radius: 12;");
        
        HBox header = new HBox(5);
        header.setAlignment(Pos.CENTER_LEFT);
        Label lblTitle = new Label(title);
        lblTitle.setStyle("-fx-text-fill: #A0A4B8; -fx-font-size: 13px;");
        lblPct.setStyle("-fx-text-fill: #A0A4B8; -fx-font-size: 13px;");
        header.getChildren().addAll(lblTitle, lblPct);

        lblVal.setStyle("-fx-text-fill: white; -fx-font-size: 20px; -fx-font-weight: bold;");

        card.getChildren().addAll(header, lblVal);
        return card;
    }

    private void updateSemuaUI() {
        double vKeb = Math.round(sliderKebutuhan.getValue());
        double vKei = Math.round(sliderKeinginan.getValue());
        double vTab = Math.round(sliderTabungan.getValue());

        double totalPct = vKeb + vKei + vTab;

        double nomKeb = totalIncomeSaatIni * (vKeb / 100.0);
        double nomKei = totalIncomeSaatIni * (vKei / 100.0);
        double nomTab = totalIncomeSaatIni * (vTab / 100.0);

        Platform.runLater(() -> {
            lblPctKeb.setText((int)vKeb + "%");
            lblRpKeb.setText("Rp " + String.format("%,.0f", nomKeb));
            lblPctKei.setText((int)vKei + "%");
            lblRpKei.setText("Rp " + String.format("%,.0f", nomKei));
            lblPctTab.setText((int)vTab + "%");
            lblRpTab.setText("Rp " + String.format("%,.0f", nomTab));

            lblSimKebPct.setText("(" + (int)vKeb + "%)");
            lblSimKebVal.setText("Rp " + String.format("%,.0f", nomKeb));
            lblSimKeiPct.setText("(" + (int)vKei + "%)");
            lblSimKeiVal.setText("Rp " + String.format("%,.0f", nomKei));
            lblSimTabPct.setText("(" + (int)vTab + "%)");
            lblSimTabVal.setText("Rp " + String.format("%,.0f", nomTab));

            if (totalPct > 0) {
                double angleKeb = (vKeb / totalPct) * 360.0;
                double angleKei = (vKei / totalPct) * 360.0;
                double angleDar = (vTab / totalPct) * 360.0;

                arcKebutuhan.setStartAngle(90);
                arcKebutuhan.setLength(-angleKeb);

                arcKeinginan.setStartAngle(90 - angleKeb);
                arcKeinginan.setLength(-angleKei);

                arcDarurat.setStartAngle(90 - angleKeb - angleKei);
                arcDarurat.setLength(-angleDar);
            } else {
                arcKebutuhan.setLength(0); arcKeinginan.setLength(0); arcDarurat.setLength(0);
            }
        });
    }

    private void prosesSimpanAlokasi() {
        int keb = (int) Math.round(sliderKebutuhan.getValue());
        int kei = (int) Math.round(sliderKeinginan.getValue());
        int tab = (int) Math.round(sliderTabungan.getValue());
        int total = keb + kei + tab;

        if (total != 100) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Validasi Gagal");
            alert.setHeaderText(null);
            alert.setContentText("⚠️ Gagal menyimpan! Total akumulasi seluruh alokasi wajib berjumlah 100%.\nSaat ini jumlahnya: " + total + "%");
            alert.getDialogPane().setStyle("-fx-background-color: #1A1D36;");
            alert.showAndWait();
            return;
        }

        DataDompet.PERSEN_KEBUTUHAN.set(keb);
        DataDompet.PERSEN_KEINGINAN.set(kei);
        DataDompet.PERSEN_TABUNGAN.set(tab);

        double nominalDanaDarurat = totalIncomeSaatIni * (tab / 100.0);
        DataDompet.DANA_DARURAT.set(nominalDanaDarurat);

        DataDompet.kalkulasiUlang();

        KoneksiJSON.simpanDataKeJSON();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sukses");
        alert.setHeaderText(null);
        alert.setContentText(
            "🎉 Alokasi berhasil disimpan!\n" +
            "Rasio: " + keb + "% / " + kei + "% / " + tab + "%\n" +
            "Dana Darurat otomatis diisi: Rp " + String.format("%,.0f", nominalDanaDarurat)
        );
        alert.getDialogPane().setStyle("-fx-background-color: #1A1D36;");
        alert.showAndWait();
    }
}
