package com.savio.view;

import com.savio.config.ColorPalette;
import com.savio.config.KoneksiJSON;
import com.savio.MainApp;
import com.savio.model.DataSesi;
import com.savio.view.components.LogoSavio;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
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

    public LoginView(MainApp mainApp) {
        this.mainApp = mainApp;
        this.setStyle("-fx-background-color: #111424;"); // Warna dasar navy gelap

        HBox mainRow = new HBox();
        mainRow.setAlignment(Pos.CENTER);

// ================= KOLOM KIRI (Background Ombak Persis Gambar) =================
        VBox leftColumn = new VBox(20); 
        leftColumn.setAlignment(Pos.CENTER); 
        leftColumn.setPadding(new Insets(60));
        HBox.setHgrow(leftColumn, Priority.ALWAYS);
        leftColumn.setPrefWidth(450); 
        
        // Memuat gambar ombak dari folder resources sebagai Background
        java.net.URL bgUrl = getClass().getResource("/bg_ombak.png");
        if (bgUrl != null) {
            String bgImage = bgUrl.toExternalForm();
            leftColumn.setStyle(
                "-fx-background-image: url('" + bgImage + "'); " +
                "-fx-background-size: cover; " +     // Mengisi seluruh area kolom
                "-fx-background-position: center; " + // Posisi tengah
                "-fx-background-repeat: no-repeat;"
            );
        } else {
            System.err.println("Peringatan: Gambar bg_ombak.png belum ada di folder resources!");
            leftColumn.setStyle("-fx-background-color: #1A0D3B;"); // Warna cadangan jika gambar gagal dimuat
        }

        // --- Logo dan Teks (Ditaruh di atas gambar ombak) ---
        LogoSavio brandLogoLeft = new LogoSavio(120, 120); 
        
        Label lblBrandLeft = new Label("SAVIO");
        lblBrandLeft.setStyle("-fx-text-fill: white; -fx-font-size: 46px; -fx-font-weight: bold; -fx-font-family: 'Segoe UI', Arial;");
        
        Label lblSloganLeft = new Label("Smart Finance,\nBetter Life");
        lblSloganLeft.setAlignment(Pos.CENTER);
        lblSloganLeft.setStyle("-fx-text-fill: #D1D5DB; -fx-font-size: 16px; -fx-text-alignment: center; -fx-line-spacing: 5px;");
        
        // Memasukkan logo dan teks ke dalam kolom
        leftColumn.getChildren().addAll(brandLogoLeft, lblBrandLeft, lblSloganLeft);

        // ================= KOLOM KANAN =================
        VBox rightColumn = new VBox();
        rightColumn.setAlignment(Pos.CENTER);
        rightColumn.setPadding(new Insets(40, 60, 40, 60)); // Padding disesuaikan agar pas di tengah
        rightColumn.setStyle("-fx-background-color: #141726;");
        HBox.setHgrow(rightColumn, Priority.ALWAYS);
        rightColumn.setPrefWidth(550);

        VBox formContainer = new VBox(20);
        formContainer.setAlignment(Pos.CENTER_LEFT);
        formContainer.setMaxWidth(400); 

        // --- Header ---
        VBox headerBox = new VBox(8);
        lblFormTitle = new Label("Selamat Datang Kembali! 👋");
        lblFormTitle.setStyle("-fx-text-fill: white; -fx-font-size: 26px; -fx-font-weight: bold;");
        lblSubtitle = new Label("Masuk untuk melanjutkan kelola keuanganmu");
        lblSubtitle.setStyle("-fx-text-fill: #A0A4B8; -fx-font-size: 13px;");
        headerBox.getChildren().addAll(lblFormTitle, lblSubtitle);

        // --- Input Fields (Custom Layout) ---
        txtUser = new TextField();
        HBox boxUser = createCustomInput("👤", "Email atau Username", "contoh@email.com", txtUser, false);
        
        txtPass = new PasswordField();
        HBox boxPass = createCustomInput("🔒", "Password", "Masukkan password", txtPass, true);

        lblError = new Label("");
        lblError.setStyle("-fx-text-fill: #E74C3C; -fx-font-size: 12px;");
        lblError.setWrapText(true);
        lblError.setManaged(false); // Sembunyikan space jika kosong

        // --- Tombol Utama (Gradient Pink-Purple) ---
        btnAksiUtama = new Button("Masuk");
        btnAksiUtama.setMaxWidth(Double.MAX_VALUE);
        btnAksiUtama.setPrefHeight(48);
        // Menambahkan margin atas sedikit agar tidak terlalu mepet dengan input password
        VBox.setMargin(btnAksiUtama, new Insets(10, 0, 0, 0));
        btnAksiUtama.setStyle("-fx-background-color: linear-gradient(to right, #D81B60, #6A1B9A); -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14px; -fx-background-radius: 8; -fx-cursor: hand;");

        // --- Footer ---
        HBox footerBox = new HBox(5);
        footerBox.setAlignment(Pos.CENTER);
        footerBox.setPadding(new Insets(20, 0, 0, 0));
        lblPertanyaan = new Label("Belum punya akun?");
        lblPertanyaan.setStyle("-fx-text-fill: #A0A4B8; -fx-font-size: 12px;");
        linkTukarForm = new Hyperlink("Daftar sekarang");
        linkTukarForm.setStyle("-fx-text-fill: #D81B60; -fx-font-size: 12px; -fx-font-weight: bold; -fx-border-color: transparent; -fx-padding: 0;");
        footerBox.getChildren().addAll(lblPertanyaan, linkTukarForm);

        // --- Assembly ---
        // Menghapus optionsRow, dividerRow, dan btnGoogle dari formContainer
        formContainer.getChildren().addAll(headerBox, boxUser, boxPass, lblError, btnAksiUtama, footerBox);
        rightColumn.getChildren().add(formContainer);
        mainRow.getChildren().addAll(leftColumn, rightColumn);

        HBox windowControls = buatTombolKontrolJendela();
        StackPane.setAlignment(windowControls, Pos.TOP_RIGHT);
        this.getChildren().addAll(mainRow, windowControls);

        // Window dragging logic
        windowControls.setOnMousePressed(e -> { xOffset = e.getSceneX(); yOffset = e.getSceneY(); });
        windowControls.setOnMouseDragged(e -> {
            Stage stage = (Stage) this.getScene().getWindow();
            stage.setX(e.getScreenX() - xOffset); stage.setY(e.getScreenY() - yOffset);
        });

        // --- Logic Buttons ---
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

    // Method Helper Khusus untuk membuat Input Box mirip gambar
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
        // Hapus background dan border asli dari text field
        inputField.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-prompt-text-fill: #5B5F75; -fx-padding: 0; -fx-focus-color: transparent; -fx-faint-focus-color: transparent;");
        
        textContainer.getChildren().addAll(lblTitle, inputField);
        container.getChildren().addAll(icon, textContainer);

        if (showEye) {
            Label eyeIcon = new Label("");
            eyeIcon.setStyle("-fx-text-fill: #A0A4B8; -fx-font-size: 18px; -fx-cursor: hand;");
            container.getChildren().add(eyeIcon);
        }

        // Efek hover & focus pada border
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

            // Reset Data untuk User Baru
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

    private HBox buatTombolKontrolJendela() {
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