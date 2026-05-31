package com.savio.scenes;

import com.savio.model.DataDompet;
import com.savio.model.DataSesi;
import com.savio.utils.ColorPalette;
import com.savio.utils.KoneksiJSON;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;

public class ProfilView extends VBox {
    private final Label lblInisial;
    private final Label lblNamaUser;
    private final Label lblValNama;

    public ProfilView() {
        this.setSpacing(25);
        this.setPadding(new Insets(30));
        this.setStyle("-fx-background-color: " + ColorPalette.BG_PRIMARY + ";");

        VBox headerBox = new VBox(5);
        Label lblTitle = new Label("Profil Pengguna 👤");
        lblTitle.setStyle("-fx-text-fill: white; -fx-font-size: 26px; -fx-font-weight: bold;");
        
        Label lblSub = new Label("Kelola informasi data akun, perbarui kata sandi, dan pantau ringkasan aset finansial SAVIO Anda.");
        lblSub.setStyle("-fx-text-fill: #A0A4B8; -fx-font-size: 14px;");
        headerBox.getChildren().addAll(lblTitle, lblSub);
        this.getChildren().add(headerBox);

        HBox mainContentRow = new HBox(30);
        mainContentRow.setAlignment(Pos.TOP_LEFT);

        VBox avatarCard = new VBox(15);
        avatarCard.setAlignment(Pos.CENTER);
        avatarCard.setPadding(new Insets(35, 30, 35, 30));
        avatarCard.setPrefWidth(260);
        avatarCard.setStyle("-fx-background-color: " + ColorPalette.BG_CARD + "; -fx-background-radius: 16;");

        StackPane avatarCircle = new StackPane();
        Circle circle = new Circle(50);
        circle.setFill(javafx.scene.paint.Color.web("#241468")); 
        circle.setStroke(javafx.scene.paint.Color.web(ColorPalette.ACCENT_KEBUTUHAN));
        circle.setStrokeWidth(2);

        String namaSaatIni = DataSesi.getNamaPengguna();
        String inisialHuruf = (!namaSaatIni.isEmpty()) ? namaSaatIni.substring(0, 1).toUpperCase() : "S";

        lblInisial = new Label(inisialHuruf);
        lblInisial.setStyle("-fx-text-fill: white; -fx-font-size: 36px; -fx-font-weight: bold;");
        avatarCircle.getChildren().addAll(circle, lblInisial);

        lblNamaUser = new Label(namaSaatIni);
        lblNamaUser.setStyle("-fx-text-fill: white; -fx-font-size: 18px; -fx-font-weight: bold;");

        avatarCard.getChildren().addAll(avatarCircle, lblNamaUser);

        VBox infoCard = new VBox(15);
        infoCard.setPadding(new Insets(30));
        HBox.setHgrow(infoCard, Priority.ALWAYS);
        infoCard.setStyle("-fx-background-color: " + ColorPalette.BG_CARD + "; -fx-background-radius: 16;");

        Label lblInfoTitle = new Label("Informasi Akun");
        lblInfoTitle.setStyle("-fx-text-fill: " + ColorPalette.ACCENT_KEBUTUHAN + "; -fx-font-size: 18px; -fx-font-weight: bold; -fx-padding: 0 0 5 0;");
        infoCard.getChildren().add(lblInfoTitle);

        VBox namaRow = new VBox(6);
        namaRow.setStyle("-fx-border-color: #1e124a transparent transparent transparent; -fx-border-width: 1px; -fx-padding: 10 0 0 0;");
        Label lblJdlNama = new Label("Nama Pengguna");
        lblJdlNama.setStyle("-fx-text-fill: " + ColorPalette.TEXT_MUTED + "; -fx-font-size: 12px; -fx-font-weight: bold;");
        lblValNama = new Label(namaSaatIni);
        lblValNama.setStyle("-fx-text-fill: white; -fx-font-size: 15px;");
        namaRow.getChildren().addAll(lblJdlNama, lblValNama);
        
        infoCard.getChildren().add(namaRow);
        infoCard.getChildren().add(buatBarisInfo("Alamat Email Akun", DataSesi.getUsernameAktif()));
        
        double totalSaldoLive = DataDompet.SALDO_AKTIF.get() + DataDompet.DANA_DARURAT.get();        infoCard.getChildren().add(buatBarisInfo("Total Aset Terpantau", "Rp " + String.format("%,.0f", totalSaldoLive)));

        HBox actionButtonRow = new HBox(15);
        actionButtonRow.setPadding(new Insets(15, 0, 0, 0));

        Button btnUbahProfil = new Button("📝 Ubah Profil");
        btnUbahProfil.setPadding(new Insets(10, 20, 10, 20));
        btnUbahProfil.setStyle("-fx-background-color: #241468; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 8; -fx-cursor: hand;");

        Button btnUbahPassword = new Button("🔒 Ubah Password");
        btnUbahPassword.setPadding(new Insets(10, 20, 10, 20));
        btnUbahPassword.setStyle("-fx-background-color: " + "linear-gradient(to right, #9B5CF6, #F72BB0)" + "; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 8; -fx-cursor: hand;");

        actionButtonRow.getChildren().addAll(btnUbahProfil, btnUbahPassword);
        infoCard.getChildren().add(actionButtonRow);

        mainContentRow.getChildren().addAll(avatarCard, infoCard);
        this.getChildren().add(mainContentRow);

        btnUbahProfil.setOnAction(e -> aksiPopUpUbahProfil());
        btnUbahPassword.setOnAction(e -> aksiPopUpUbahPassword());
    }

    private void aksiPopUpUbahProfil() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Ubah Profil");
        dialog.getDialogPane().setStyle("-fx-background-color: " + ColorPalette.BG_CARD + ";");
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        VBox pane = new VBox(10);
        pane.setPadding(new Insets(15));
        pane.setPrefWidth(300);

        Label lblInfo = new Label("Masukkan nama pengguna baru Anda:");
        lblInfo.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");
        
        TextField txtNamaBaru = new TextField();
        txtNamaBaru.setText(lblValNama.getText());
        txtNamaBaru.setStyle("-fx-background-color: " + ColorPalette.BG_PRIMARY + "; -fx-text-fill: white;");

        pane.getChildren().addAll(lblInfo, txtNamaBaru);
        dialog.getDialogPane().setContent(pane);

        dialog.showAndWait().ifPresent(res -> {
            if (res == ButtonType.OK) {
                String namaInput = txtNamaBaru.getText().trim();
                if (!namaInput.isEmpty()) {
                    DataSesi.setNamaPengguna(namaInput.toUpperCase());

                    lblValNama.setText(namaInput.toUpperCase());
                    lblNamaUser.setText(namaInput.toUpperCase());
                    lblInisial.setText(namaInput.substring(0, 1).toUpperCase());

                    KoneksiJSON.simpanDataKeJSON();
                }
            }
        });
    }

    private void aksiPopUpUbahPassword() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Ubah Password");
        dialog.getDialogPane().setStyle("-fx-background-color: " + ColorPalette.BG_CARD + ";");
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        VBox pane = new VBox(10);
        pane.setPadding(new Insets(15));
        pane.setPrefWidth(320);

        PasswordField txtPassLama = new PasswordField(); txtPassLama.setPromptText("Password saat ini...");
        PasswordField txtPassBaru = new PasswordField(); txtPassBaru.setPromptText("Password baru (min 6 karakter)...");

        pane.getChildren().addAll(
            new Label("Password Lama:"), txtPassLama,
            new Label("Password Baru:"), txtPassBaru
        );

        pane.getChildren().forEach(node -> {
            if (node instanceof Label) node.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");
        });

        dialog.getDialogPane().setContent(pane);

        dialog.showAndWait().ifPresent(res -> {
            if (res == ButtonType.OK) {
                String passLamaInput = txtPassLama.getText();
                String passBaruInput = txtPassBaru.getText();

                if (passLamaInput.equals(DataSesi.getPasswordAktif())) {
                    if (passBaruInput.trim().length() >= 6) {
                        DataSesi.setPasswordAktif(passBaruInput);
                        
                        KoneksiJSON.simpanDataKeJSON();
                        
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Sukses");
                        alert.setHeaderText(null);
                        alert.setContentText("Kata sandi keamanan Anda berhasil diperbarui!");
                        alert.getDialogPane().setStyle("-fx-background-color: " + ColorPalette.BG_CARD + ";");
                        alert.showAndWait();
                    } else {
                        tampilkanAlertError("Gagal! Kata sandi baru minimal wajib 6 karakter.");
                    }
                } else {
                    tampilkanAlertError("Gagal! Kata sandi lama yang Anda masukkan salah.");
                }
            }
        });
    }

    private void tampilkanAlertError(String pesan) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Kesalahan");
        alert.setHeaderText(null);
        alert.setContentText(pesan);
        alert.getDialogPane().setStyle("-fx-background-color: " + ColorPalette.BG_CARD + ";");
        alert.showAndWait();
    }

    private VBox buatBarisInfo(String labelJudul, String isiNilai) {
        VBox box = new VBox(6);
        box.setStyle("-fx-border-color: #1e124a transparent transparent transparent; -fx-border-width: 1px; -fx-padding: 10 0 0 0;");

        Label lblJdl = new Label(labelJudul);
        lblJdl.setStyle("-fx-text-fill: " + ColorPalette.TEXT_MUTED + "; -fx-font-size: 12px; -fx-font-weight: bold;");

        Label lblVal = new Label(isiNilai);
        lblVal.setStyle("-fx-text-fill: white; -fx-font-size: 15px;");

        box.getChildren().addAll(lblJdl, lblVal);
        return box;
    }
}