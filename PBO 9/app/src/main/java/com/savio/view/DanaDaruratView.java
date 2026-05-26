package com.savio.view;

import com.savio.config.KoneksiJSON;
import com.savio.model.DataDompet;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
        
        // ==================== 1. HEADER ====================
        VBox headerBox = new VBox(5);
        Label lblTitle = new Label("Dana Darurat");
        lblTitle.setStyle("-fx-text-fill: white; -fx-font-size: 26px; -fx-font-weight: bold;");
        Label lblSub = new Label("Amankan masa depan finansialmu dari pengeluaran tak terduga");
        lblSub.setStyle("-fx-text-fill: #A0A4B8; -fx-font-size: 14px;");
        headerBox.getChildren().addAll(lblTitle, lblSub);
        mainContainer.getChildren().add(headerBox);

        // ==================== 2. KONTEN (ATAS - BAWAH) ====================
        VBox contentStack = new VBox(20); 

        // --- Kartu Utama ---
        VBox cardContent = new VBox(15);
        cardContent.setPadding(new Insets(30));
        cardContent.setStyle("-fx-background-color: linear-gradient(to bottom right, #f28c00, #b0550B); -fx-background-radius: 16;");

        HBox vaultHeader = new HBox(10);
        Label lblIconVault = new Label("🔒");
        lblIconVault.setStyle("-fx-font-size: 20px; -fx-text-fill: white;");
        Label lblSimpananTitle = new Label("Total Simpanan Terkunci");
        lblSimpananTitle.setStyle("-fx-text-fill: #FFE5B4; -fx-font-size: 14px; -fx-font-weight: bold;");
        vaultHeader.getChildren().addAll(lblIconVault, lblSimpananTitle);

        lblTotalDarurat = new Label("Rp " + String.format("%,.0f", DataDompet.DANA_DARURAT));
        lblTotalDarurat.setStyle("-fx-text-fill: white; -fx-font-size: 36px; -fx-font-weight: bold;");

        HBox actionRow = new HBox(15);
        Button btnTarik = new Button("📥 Isi Dana");
        Button btnCairkan = new Button("📤 Cairkan");
        btnTarik.setStyle("-fx-background-color: white; -fx-text-fill: #D97316; -fx-font-weight: bold; -fx-background-radius: 8; -fx-cursor: hand; -fx-padding: 10 20;");
        btnCairkan.setStyle("-fx-background-color: transparent; -fx-border-color: white; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 8; -fx-border-radius: 8; -fx-cursor: hand; -fx-padding: 10 20;");
        
        HBox.setHgrow(btnTarik, Priority.ALWAYS); HBox.setHgrow(btnCairkan, Priority.ALWAYS);
        btnTarik.setMaxWidth(Double.MAX_VALUE); btnCairkan.setMaxWidth(Double.MAX_VALUE);
        actionRow.getChildren().addAll(btnTarik, btnCairkan);

        cardContent.getChildren().addAll(vaultHeader, lblTotalDarurat, actionRow);

        // --- Kartu Info ---
        VBox infoBox = new VBox(15);
        infoBox.setPadding(new Insets(25));
        infoBox.setStyle("-fx-background-color: #1A1D36; -fx-background-radius: 16;");

        Label lblInfoT = new Label("💡 Mengapa Dana Darurat Penting?");
        lblInfoT.setStyle("-fx-text-fill: #f28c00; -fx-font-size: 16px; -fx-font-weight: bold;");
        lblTargetInfo = new Label();
        lblTargetInfo.setStyle("-fx-text-fill: #A0A4B8; -fx-font-size: 13px; -fx-line-spacing: 5px;");
        lblTargetInfo.setWrapText(true);
        
        infoBox.getChildren().addAll(lblInfoT, lblTargetInfo);

        contentStack.getChildren().addAll(cardContent, infoBox);
        mainContainer.getChildren().add(contentStack);
        
        this.setContent(mainContainer);

        // ==================== 3. LOGIKA & SINKRONISASI ====================
        // Sinkronisasi Data Reaktif
        refreshTeksTarget();
        DataDompet.PERSEN_TABUNGAN.addListener((o, ov, nv) -> refreshTeksTarget());
        
        // Memastikan tampilan saldo selalu update jika ada perubahan model
        // (Jika Anda punya property di model, Anda bisa binding, atau gunakan listener ini)
        btnTarik.setOnAction(e -> handleAksiDana("Isi Dana Darurat", true));
        btnCairkan.setOnAction(e -> handleAksiDana("Cairkan Dana Darurat", false));
    }

    private void handleAksiDana(String judul, boolean isTarik) {
        TextInputDialog dialog = buatDialogKustom(judul, "Masukkan Nominal (Rp):");
        dialog.showAndWait().ifPresent(input -> {
            try {
                double nominal = Double.parseDouble(input.trim());
                if (nominal <= 0) return;

                if (isTarik) {
                    if (nominal > DataDompet.SALDO_AKTIF.get()) {
                        tampilkanAlert("Saldo Kurang", "Saldo Aktif tidak mencukupi!", Alert.AlertType.ERROR);
                        return;
                    }
                    DataDompet.SALDO_AKTIF.set(DataDompet.SALDO_AKTIF.get() - nominal);
                    DataDompet.DANA_DARURAT += nominal;
                } else {
                    if (nominal > DataDompet.DANA_DARURAT) {
                        tampilkanAlert("Dana Kurang", "Melebihi total Dana Darurat!", Alert.AlertType.ERROR);
                        return;
                    }
                    DataDompet.DANA_DARURAT -= nominal;
                    DataDompet.SALDO_AKTIF.set(DataDompet.SALDO_AKTIF.get() + nominal);
                }
                KoneksiJSON.simpanDataKeJSON(); // Simpan perubahan
                lblTotalDarurat.setText("Rp " + String.format("%,.0f", DataDompet.DANA_DARURAT));
            } catch (Exception ex) { tampilkanAlert("Error", "Nominal tidak valid!", Alert.AlertType.ERROR); }
        });
    }

    private void refreshTeksTarget() {
        Platform.runLater(() -> {
            double p = DataDompet.PERSEN_TABUNGAN.get();
            lblTargetInfo.setText(String.format("Dana darurat adalah cadangan untuk keadaan mendesak. " +
                "Saat ini, sistem disetel untuk mengalokasikan %.0f%% dari pemasukan bulanan Anda ke pos ini.", p));
        });
    }

    private TextInputDialog buatDialogKustom(String title, String content) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle(title); dialog.setContentText(content);
        dialog.getDialogPane().setStyle("-fx-background-color: #1A1D36;");
        dialog.getEditor().setStyle("-fx-background-color: #0F1123; -fx-text-fill: white;");
        return dialog;
    }

    private void tampilkanAlert(String j, String p, Alert.AlertType t) {
        Alert a = new Alert(t); a.setTitle(j); a.setContentText(p);
        a.getDialogPane().setStyle("-fx-background-color: #1A1D36;");
        a.showAndWait();
    }
}