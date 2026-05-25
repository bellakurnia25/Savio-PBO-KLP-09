package com.savio.view;

import com.savio.MainApp;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class WelcomeView extends StackPane {
    private final MainApp mainApp;

    public WelcomeView(MainApp mainApp) {
        this.mainApp = mainApp;
        
        VBox kontenAsli = new VBox(25);
        
        // 🚨 KOSONGKAN: Biarkan Front-End membuat desain logo, 
        // teks SAVIO, slogan, dan animasi berkedip di sini.

        this.getChildren().add(kontenAsli);

        // Fitur klik untuk pindah halaman tetap dipertahankan agar tidak putus sistemnya
        this.setOnMouseClicked(e -> {
            mainApp.navigateToLogin();
        });
    }
}