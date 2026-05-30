package com.savio.scenes;

import com.savio.model.DataDompet;
import com.savio.utils.KoneksiJSON;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class DanaDaruratView extends ScrollPane {
    private final Label lblTotalDarurat;
    private final Label lblTargetInfo;

    public DanaDaruratView() {
        this.setFitToWidth(true);
        this.setStyle("-fx-background: transparent; -fx-background-color: transparent;");

        VBox mainContainer = new VBox(25);
        mainContainer.setPadding(new Insets(30, 40, 30, 40));
        mainContainer.setStyle("-fx-background-color: #0F1123;");
        
        //Header
        VBox headerBox = new VBox(5);
        Label lblTitle = new Label("Dana Darurat");
        lblTitle.setStyle("-fx-text-fill: white; -fx-font-size: 26px; -fx-font-weight: bold;");
        Label lblSub = new Label("Amankan masa depan finansialmu dari pengeluaran tak terduga");
        lblSub.setStyle("-fx-text-fill: #A0A4B8; -fx-font-size: 14px;");
        headerBox.getChildren().addAll(lblTitle, lblSub);
        mainContainer.getChildren().add(headerBox);

        //Konten
        VBox contentStack = new VBox(20); 

        VBox cardContent = new VBox(15);
        cardContent.setPadding(new Insets(30));
        cardContent.setStyle("-fx-background-color: linear-gradient(to bottom right, #5A1A6B, #340D40); -fx-background-radius: 16;");

        HBox vaultHeader = new HBox(10);
        Label lblIconVault = new Label("🔒");
        lblIconVault.setStyle("-fx-font-size: 20px; -fx-text-fill: white;");
        Label lblSimpananTitle = new Label("Total Dana Darurat Terkunci");
        lblSimpananTitle.setStyle("-fx-text-fill: #ffffff; -fx-font-size: 14px; -fx-font-weight: bold;");
        vaultHeader.getChildren().addAll(lblIconVault, lblSimpananTitle);

        lblTotalDarurat = new Label("Rp " + String.format("%,.0f", DataDompet.DANA_DARURAT.get()));
        lblTotalDarurat.setStyle("-fx-text-fill: white; -fx-font-size: 36px; -fx-font-weight: bold;");

        HBox actionRow = new HBox(15);
        Button btnTarik = new Button("📥 Isi Dana");
        Button btnCairkan = new Button("📤 Cairkan");
        btnTarik.setStyle("-fx-background-color: white; -fx-text-fill: #73007e; -fx-font-weight: bold; -fx-background-radius: 8; -fx-cursor: hand; -fx-padding: 10 20;");
        btnCairkan.setStyle("-fx-background-color: transparent; -fx-border-color: white; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 8; -fx-border-radius: 8; -fx-cursor: hand; -fx-padding: 10 20;");
        
        HBox.setHgrow(btnTarik, Priority.ALWAYS); HBox.setHgrow(btnCairkan, Priority.ALWAYS);
        btnTarik.setMaxWidth(Double.MAX_VALUE); btnCairkan.setMaxWidth(Double.MAX_VALUE);
        actionRow.getChildren().addAll(btnTarik, btnCairkan);

        cardContent.getChildren().addAll(vaultHeader, lblTotalDarurat, actionRow);

        VBox infoBox = new VBox(15);
        infoBox.setPadding(new Insets(25));
        infoBox.setStyle("-fx-background-color: #1A1D36; -fx-background-radius: 16;");

        Label lblInfoT = new Label("💡 Mengapa Dana Darurat Penting?");
        lblInfoT.setStyle("-fx-text-fill: #9700a5; -fx-font-size: 16px; -fx-font-weight: bold;");
        lblTargetInfo = new Label();
        lblTargetInfo.setStyle("-fx-text-fill: #d2d4dd; -fx-font-size: 13px; -fx-line-spacing: 5px;");
        lblTargetInfo.setWrapText(true);
        
        infoBox.getChildren().addAll(lblInfoT, lblTargetInfo);

        contentStack.getChildren().addAll(cardContent, infoBox);
        mainContainer.getChildren().add(contentStack);
        
        this.setContent(mainContainer);

        //Logika
        refreshTeksTarget();
        DataDompet.PERSEN_TABUNGAN.addListener((o, ov, nv) -> refreshTeksTarget());

        btnTarik.setOnAction(e -> handleAksiDana("Isi Dana Darurat", true));
        btnCairkan.setOnAction(e -> handleAksiDana("Cairkan Dana Darurat", false));
    }

    private void handleAksiDana(String judul, boolean isTarik) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle(judul);
        dialog.setHeaderText(isTarik ? "Simpan uang ke brankas darurat" : "Ambil dana dari brankas darurat");
        dialog.setContentText("Masukkan Nominal (Rp):");

        dialog.showAndWait().ifPresent(input -> {
            try {
                double nominal = Double.parseDouble(input.trim());
                if (nominal <= 0) throw new NumberFormatException();

                if (isTarik) {
                    if (nominal > DataDompet.SALDO_AKTIF.get()) {
                        tampilkanAlert("Saldo Kurang", "Saldo Aktif tidak mencukupi untuk dialokasikan!", Alert.AlertType.ERROR);
                        return;
                    }
                    DataDompet.DANA_DARURAT.set(DataDompet.DANA_DARURAT.get() + nominal);
                    tampilkanAlert("Sukses", "Uang berhasil dikunci di brankas Dana Darurat!", Alert.AlertType.INFORMATION);
                } else {
                    if (nominal > DataDompet.DANA_DARURAT.get()) {
                        tampilkanAlert("Dana Kurang", "Nominal penarikan melebihi isi simpanan Dana Darurat Anda!", Alert.AlertType.ERROR);
                        return;
                    }
                    DataDompet.DANA_DARURAT.set(DataDompet.DANA_DARURAT.get() - nominal);
                    tampilkanAlert("Sukses", "Dana Darurat berhasil dicairkan ke Saldo Aktif!", Alert.AlertType.INFORMATION);
                }

                DataDompet.kalkulasiUlang();

                com.savio.utils.KoneksiJSON.simpanDataKeJSON();

                lblTotalDarurat.setText("Rp " + String.format("%,.0f", DataDompet.DANA_DARURAT.get()));

            } catch (Exception ex) {
                tampilkanAlert("Error", "Format nominal yang Anda masukkan tidak valid!", Alert.AlertType.ERROR);
            }
        });
    }

    private void refreshTeksTarget() {
        Platform.runLater(() -> {
            double p = DataDompet.PERSEN_TABUNGAN.get();
            lblTargetInfo.setText(String.format("Dana darurat adalah cadangan untuk keadaan mendesak. " +
                "Saat ini, sistem disetel untuk mengalokasikan %.0f%% dari pemasukan bulanan Anda ke pos ini.", p));
        });
    }

    private void tampilkanAlert(String j, String p, Alert.AlertType t) {
        Alert a = new Alert(t); a.setTitle(j); a.setContentText(p);
        a.getDialogPane().setStyle("-fx-background-color: #1A1D36;");
        a.showAndWait();
    }
}