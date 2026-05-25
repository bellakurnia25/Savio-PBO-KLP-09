package com.savio.view;

import com.savio.MainApp;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class LoginView extends StackPane {
    private final MainApp mainApp;
    
    // Variabel komponen wajib ditinggalkan agar tidak memicu error di kodingan lain
    public final TextField txtUser = new TextField();
    public final PasswordField txtPass = new PasswordField();
    public final Label lblError = new Label("");
    public final Label lblFormTitle = new Label("Sign In");
    public final Button btnAksiUtama = new Button("Sign In");
    public final Label lblPertanyaan = new Label("Belum memiliki akun?");
    public final Hyperlink linkTukarForm = new Hyperlink("Daftar");

    public LoginView(MainApp mainApp) {
        this.mainApp = mainApp;

        HBox mainRow = new HBox();
        mainRow.setAlignment(Pos.CENTER);

        // 🚨 KOSONGKAN: Kerangka dasar kolom kiri dan kanan.
        // Penataan grid layout dua kolom, warna gelap, font, dan tombol kontrol jendela
        // diserahkan sepenuhnya kepada Front-End untuk ditata ulang di sini.
        VBox formContainer = new VBox(15);
        formContainer.getChildren().addAll(lblFormTitle, txtUser, txtPass, lblError, btnAksiUtama, lblPertanyaan, linkTukarForm);
        
        mainRow.getChildren().add(formContainer);
        this.getChildren().add(mainRow);

        // Logika tombol aksi tetap aman (Bisa diisi Teman B / Logic nantinya)
        btnAksiUtama.setOnAction(e -> {
            // Logika login & registrasi terisolasi aman
        });
    }
}