package com.savio.scenes;

import com.savio.MainApp;
import com.savio.model.DataSesi;
import com.savio.scenes.components.LogoSavio;
import com.savio.utils.KoneksiJSON;

import javafx.animation.AnimationTimer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.stage.Stage;

public class LoginView extends StackPane {
    private final MainApp mainApp;
    private final TextField txtUser;
    private final PasswordField txtPass;
    private final Label lblError;
    private final Label lblFormTitle;
    private final Label lblSubtitle;
    private final Button btnAksiUtama;
    private final Label lblPertanyaan;
    private final Hyperlink linkTukarForm;
    
    private boolean isLoginMode = true;
    private double xOffset = 0;
    private double yOffset = 0;
    
    private double timeOffset = 0;
    
    // 1. Deklarasi di tingkat kelas agar bisa dihentikan di method lain
    private AnimationTimer waveTimer;

    public LoginView(MainApp mainApp) {
        this.mainApp = mainApp;
        this.setStyle("-fx-background-color: #111424;"); 
        
        HBox mainRow = new HBox();
        mainRow.setAlignment(Pos.CENTER);

        // Kiri
        StackPane leftStack = new StackPane();
        HBox.setHgrow(leftStack, Priority.ALWAYS);
        leftStack.setPrefWidth(280);
        leftStack.setStyle("-fx-background-color: #120B29;");
        javafx.scene.canvas.Canvas waveCanvas = new javafx.scene.canvas.Canvas(450, 700);
        javafx.scene.canvas.GraphicsContext gc = waveCanvas.getGraphicsContext2D();

        waveCanvas.widthProperty().bind(leftStack.widthProperty());
        waveCanvas.heightProperty().bind(leftStack.heightProperty());
        
        // 2. Inisialisasi menggunakan instance variable kelas
        waveTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                timeOffset += 0.005;
                drawWaveBackground(gc, waveCanvas.getWidth(), waveCanvas.getHeight(), timeOffset);
            }
        };
        waveTimer.start();

        VBox leftContent = new VBox(0);
        leftContent.setAlignment(Pos.CENTER);
        leftContent.setPadding(new Insets(60));
        leftContent.setStyle("-fx-background-color: transparent;"); 

        LogoSavio brandLogoLeft = new LogoSavio(170, 170);
        
        Label lblBrandLeft = new Label("SAVIO");
        lblBrandLeft.setStyle("-fx-text-fill: white; -fx-font-size: 60px; -fx-font-weight: bold; -fx-font-family: 'Segoe UI', Arial;");
        
        Label lblSloganLeft = new Label("Smart Finance, Better Life");
        lblSloganLeft.setAlignment(Pos.CENTER);
        lblSloganLeft.setStyle("-fx-text-fill: #A0A4B8; -fx-font-size: 16px; -fx-text-alignment: center; -fx-line-spacing: 0px;");
        
        leftContent.getChildren().addAll(brandLogoLeft, lblBrandLeft, lblSloganLeft);
        leftStack.getChildren().addAll(waveCanvas, leftContent);

        // Kanan
        VBox rightColumn = new VBox();
        rightColumn.setAlignment(Pos.CENTER);
        rightColumn.setPadding(new Insets(40, 60, 40, 60)); 
        rightColumn.setStyle("-fx-background-color: #141726;");
        HBox.setHgrow(rightColumn, Priority.ALWAYS);
        rightColumn.setPrefWidth(700);

        VBox formContainer = new VBox(20);
        formContainer.setAlignment(Pos.CENTER_LEFT);
        formContainer.setMaxWidth(400); 

        VBox headerBox = new VBox(8);
        lblFormTitle = new Label("Selamat Datang Kembali! 👋");
        lblFormTitle.setStyle("-fx-text-fill: white; -fx-font-size: 26px; -fx-font-weight: bold;");
        lblSubtitle = new Label("Masuk untuk melanjutkan kelola keuanganmu");
        lblSubtitle.setStyle("-fx-text-fill: #A0A4B8; -fx-font-size: 13px;");
        headerBox.getChildren().addAll(lblFormTitle, lblSubtitle);

        txtUser = new TextField();
        HBox boxUser = createCustomInput("👤", "Email atau Username", "contoh@email.com", txtUser, false);
        
        txtPass = new PasswordField();
        HBox boxPass = createCustomInput("🔒", "Password", "Masukkan password", txtPass, true);

        lblError = new Label("");
        lblError.setStyle("-fx-text-fill: #E74C3C; -fx-font-size: 12px;");
        lblError.setWrapText(true);
        lblError.setManaged(false);

        btnAksiUtama = new Button("Masuk");
        btnAksiUtama.setMaxWidth(Double.MAX_VALUE);
        btnAksiUtama.setPrefHeight(48);
        VBox.setMargin(btnAksiUtama, new Insets(10, 0, 0, 0));
        btnAksiUtama.setStyle("-fx-background-color: linear-gradient(to right, #D81B60, #6A1B9A); -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14px; -fx-background-radius: 8; -fx-cursor: hand;");

        HBox footerBox = new HBox(5);
        footerBox.setAlignment(Pos.CENTER);
        footerBox.setPadding(new Insets(20, 0, 0, 0));
        lblPertanyaan = new Label("Belum punya akun?");
        lblPertanyaan.setStyle("-fx-text-fill: #A0A4B8; -fx-font-size: 12px;");
        linkTukarForm = new Hyperlink("Daftar sekarang");
        linkTukarForm.setStyle("-fx-text-fill: #D81B60; -fx-font-size: 12px; -fx-font-weight: bold; -fx-border-color: transparent; -fx-padding: 0;");
        footerBox.getChildren().addAll(lblPertanyaan, linkTukarForm);

        formContainer.getChildren().addAll(headerBox, boxUser, boxPass, lblError, btnAksiUtama, footerBox);
        rightColumn.getChildren().add(formContainer);
        
        mainRow.getChildren().addAll(leftStack, rightColumn); 

        HBox windowControls = buatTomrolKontrolJendela();
        StackPane.setAlignment(windowControls, Pos.TOP_RIGHT);
        this.getChildren().addAll(mainRow, windowControls);

        windowControls.setOnMousePressed(e -> { xOffset = e.getSceneX(); yOffset = e.getSceneY(); });
        windowControls.setOnMouseDragged(e -> {
            Stage stage = (Stage) this.getScene().getWindow();
            stage.setX(e.getScreenX() - xOffset); stage.setY(e.getScreenY() - yOffset);
        });

        linkTukarForm.setOnAction(e -> {
            isLoginMode = !isLoginMode; 
            lblError.setText(""); lblError.setManaged(false);
            txtUser.clear(); txtPass.clear();
            
            if (isLoginMode) {
                lblFormTitle.setText("Selamat Datang Kembali! 👋");
                lblSubtitle.setText("Masuk untuk melanjutkan kelola keuanganmu");
                btnAksiUtama.setText("Masuk");
                lblPertanyaan.setText("Belum punya akun?"); 
                linkTukarForm.setText("Daftar sekarang");
            } else {
                lblFormTitle.setText("Buat Akun Baru 🚀");
                lblSubtitle.setText("Daftar sekarang untuk memulai perjalananmu");
                btnAksiUtama.setText("Daftar");
                lblPertanyaan.setText("Sudah punya akun?"); 
                linkTukarForm.setText("Masuk sekarang");
            }
        });

        btnAksiUtama.setOnAction(e -> handleLoginRegister());
    }

    private void drawWaveBackground(javafx.scene.canvas.GraphicsContext gc, double w, double h, double time) {
        if (w <= 0 || h <= 0) return;
        gc.clearRect(0, 0, w, h);

        LinearGradient bgGrad = new LinearGradient(0, 0, 1, 1, true, CycleMethod.NO_CYCLE,
            new Stop(0, Color.web("#14092E")), new Stop(1, Color.web("#110B24"))
        );
        gc.setFill(bgGrad);
        gc.fillRect(0, 0, w, h);

        // Ombak 1
        gc.setFill(new LinearGradient(0, 0, 0.5, 0.5, true, CycleMethod.NO_CYCLE,
            new Stop(0, Color.web("#D81B60")), new Stop(1, Color.web("#6A1B9A", 0.7))
        ));
        gc.beginPath();
        gc.moveTo(0, 0);
        gc.lineTo(w * 0.7, 0);
        gc.bezierCurveTo(
            w * 0.3, h * 0.05 + Math.sin(time) * 15, 
            w * 0.15, h * 0.2 + Math.cos(time * 0.8) * 20, 
            0, h * 0.35 + Math.sin(time * 1.2) * 10
        );
        gc.closePath();
        gc.fill();

        // Ombak 2
        gc.setFill(new LinearGradient(0, 0, 0.5, 0.5, true, CycleMethod.NO_CYCLE,
            new Stop(0, Color.web("#3C1361")), new Stop(1, Color.web("#1B0A3A", 0.8))
        ));
        gc.beginPath();
        gc.moveTo(0, 0);
        gc.lineTo(w * 0.4, 0);
        gc.bezierCurveTo(
            w * 0.15, h * 0.1 + Math.sin(time + 2) * 20, 
            w * 0.05, h * 0.3 + Math.cos(time * 0.9) * 15, 
            0, h * 0.5 + Math.sin(time * 1.1 + 1) * 10
        );
        gc.closePath();
        gc.fill();

        // Ombak 3
        gc.setFill(new LinearGradient(0, 1, 1, 0.5, true, CycleMethod.NO_CYCLE,
            new Stop(0, Color.web("#501166")), new Stop(1, Color.web("#1F0A3B", 0.8))
        ));
        gc.beginPath();
        gc.moveTo(0, h);
        gc.lineTo(0, h);
        gc.bezierCurveTo(
            w * 0.3, h * 1.2 + Math.cos(time * 0.7) * 25, 
            w * 0.6, h * 0.6 + Math.sin(time * 0.8) * 25, 
            w, h * 0.45 + Math.cos(time) * 15
        );
        gc.lineTo(w, h);
        gc.closePath();
        gc.fill();

        // Ombak 4
        gc.setFill(new LinearGradient(0, 1, 1, 0.6, true, CycleMethod.NO_CYCLE,
            new Stop(0, Color.web("#AD165B")), new Stop(1, Color.web("#681263", 0.9))
        ));
        gc.beginPath();
        gc.moveTo(w * 0.15, h);
        gc.bezierCurveTo(
            w * 0.4, h * 0.9 + Math.sin(time * 1.3) * 20, 
            w * 0.7, h * 0.7 + Math.cos(time * 1.1) * 15, 
            w, h * 0.6 + Math.sin(time * 0.9) * 10
        );
        gc.lineTo(w, h);
        gc.closePath();
        gc.fill();

        // Ombak 5
        gc.setFill(new LinearGradient(0.5, 1, 1, 0.7, true, CycleMethod.NO_CYCLE,
            new Stop(0, Color.web("#FF402B")), new Stop(1, Color.web("#FF9F1C"))
        ));
        gc.beginPath();
        gc.moveTo(w * 0.45, h);
        gc.bezierCurveTo(
            w * 0.6, h * 0.95 + Math.sin(time * 1.5 + 4) * 15, 
            w * 0.8, h * 0.85 + Math.cos(time * 1.2 + 5) * 15, 
            w, h * 0.7 + Math.cos(time * 1.4) * 10
        );
        gc.lineTo(w, h);
        gc.closePath();
        gc.fill();
    }

    private HBox createCustomInput(String iconSymbol, String title, String prompt, TextField inputField, boolean showEye) {
        HBox container = new HBox(12);
        container.setAlignment(Pos.CENTER_LEFT);
        container.setStyle("-fx-border-color: #2D314A; -fx-border-radius: 8; -fx-padding: 8 15; -fx-background-color: transparent;");

        Label icon = new Label(iconSymbol);
        icon.setStyle("-fx-text-fill: #A0A4B8; -fx-font-size: 18px;");

        VBox textContainer = new VBox(2);
        HBox.setHgrow(textContainer, Priority.ALWAYS);
        Label lblTitle = new Label(title);
        lblTitle.setStyle("-fx-text-fill: white; -fx-font-size: 11px; -fx-font-weight: bold;");
        
        inputField.setPromptText(prompt);
        inputField.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-prompt-text-fill: #5B5F75; -fx-padding: 0; -fx-focus-color: transparent; -fx-faint-focus-color: transparent;");
        
        textContainer.getChildren().addAll(lblTitle, inputField);
        container.getChildren().addAll(icon, textContainer);

        if (showEye) {
            Label eyeIcon = new Label("");
            eyeIcon.setStyle("-fx-text-fill: #A0A4B8; -fx-font-size: 18px; -fx-cursor: hand;");
            container.getChildren().add(eyeIcon);
        }

        inputField.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) container.setStyle("-fx-border-color: #D81B60; -fx-border-radius: 8; -fx-padding: 8 15; -fx-background-color: transparent;");
            else container.setStyle("-fx-border-color: #2D314A; -fx-border-radius: 8; -fx-padding: 8 15; -fx-background-color: transparent;");
        });

        return container;
    }

    private void handleLoginRegister() {
        String usernameInput = txtUser.getText().trim(); 
        String passwordInput = txtPass.getText();

        if (usernameInput.isEmpty() || passwordInput.trim().isEmpty()) {
            tampilkanError("⚠️ Input tidak boleh kosong!"); return;
        }
        if (!usernameInput.contains("@") || !usernameInput.contains(".")) {
            tampilkanError("⚠️ Format username harus berupa email valid."); return;
        }
        if (passwordInput.length() < 6) {
            tampilkanError("⚠️ Password minimal harus 6 karakter."); return;
        }

        lblError.setManaged(false); lblError.setText("");

        if (isLoginMode) {
            if (KoneksiJSON.verifikasiLoginSistem(usernameInput, passwordInput)) {
                DataSesi.setUsernameAktif(usernameInput.toLowerCase());
                DataSesi.setPasswordAktif(passwordInput);
                KoneksiJSON.muatDataDariJSON();
                
                // 3. Menghentikan timer animasi gelombang agar hemat sumber daya CPU
                if (waveTimer != null) {
                    waveTimer.stop();
                }
                
                mainApp.navigateToDashboard();
            } else {
                tampilkanError("⚠️ Username atau password salah / belum terdaftar!");
            }
        } else {
            if (KoneksiJSON.cekEmailSudahTerdaftar(usernameInput)) {
                tampilkanError("⚠️ Email ini sudah terdaftar! Silakan login.");
                return;
            }

            DataSesi.setUsernameAktif(usernameInput.toLowerCase());
            DataSesi.setPasswordAktif(passwordInput);
            int indexAt = usernameInput.indexOf("@");
            DataSesi.setNamaPengguna((indexAt != -1) ? usernameInput.substring(0, indexAt).toUpperCase() : "USER");

            com.savio.model.DataDompet.SALDO_AKTIF.set(0.0);
            com.savio.model.DataDompet.NOMINAL_KEBUTUHAN.set(0.0);
            com.savio.model.DataDompet.NOMINAL_KEINGINAN.set(0.0);
            com.savio.model.DataDompet.DANA_DARURAT.set(0.0);
            com.savio.model.DataDompet.PERSEN_KEBUTUHAN.set(50.0);
            com.savio.model.DataDompet.PERSEN_KEINGINAN.set(30.0);
            com.savio.model.DataDompet.PERSEN_TABUNGAN.set(20.0);
            com.savio.model.DataDompet.LIST_TRANSAKSI.clear();

            KoneksiJSON.simpanDataKeJSON();
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Sukses");
            alert.setContentText("Akun berhasil terdaftar! Silakan login.");
            alert.showAndWait();
            linkTukarForm.fire(); 
        }
    }

    private void tampilkanError(String pesan) {
        lblError.setText(pesan);
        lblError.setManaged(true);
    }

    private HBox buatTomrolKontrolJendela() {
        HBox topBar = new HBox(5); topBar.setAlignment(Pos.CENTER_RIGHT); topBar.setPadding(new Insets(5, 10, 0, 0)); topBar.setMaxHeight(35);
        Button btnMinimize = new Button("—"); styleTombolWindow(btnMinimize, "#333333");
        btnMinimize.setOnAction(e -> ((Stage) this.getScene().getWindow()).setIconified(true));
        Button btnClose = new Button("✕"); styleTombolWindow(btnClose, "#E74C3C");
        btnClose.setOnAction(e -> { ((Stage) this.getScene().getWindow()).close(); System.exit(0); });
        topBar.getChildren().addAll(btnMinimize, btnClose);
        return topBar;
    }

    private void styleTombolWindow(Button btn, String hoverColor) {
        btn.setPrefSize(40, 30);
        btn.setStyle("-fx-background-color: transparent; -fx-text-fill: #888888; -fx-font-size: 12px; -fx-cursor: hand;");
        btn.setOnMouseEntered(e -> btn.setStyle("-fx-background-color: " + hoverColor + "; -fx-text-fill: white; -fx-font-size: 12px; -fx-cursor: hand;"));
        btn.setOnMouseExited(e -> btn.setStyle("-fx-background-color: transparent; -fx-text-fill: #888888; -fx-font-size: 12px; -fx-cursor: hand;"));
    }
}