package com.savio.scenes.components;

import com.savio.utils.ColorPalette;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class CustomCard extends VBox {
    // Variabel label angka rupiah agar bisa diakses dan diubah teksnya
    private final Label lblAmount;

    public CustomCard(String title, String amount, String accentColor) {
        this.setSpacing(10);
        this.setPadding(new Insets(20));
        this.setPrefWidth(260);
        
        // Desain kotak kartu gelap premium sesuai mockup
        this.setStyle(
            "-fx-background-color: " + ColorPalette.BG_CARD + "; " +
            "-fx-background-radius: 14; " +
            "-fx-border-color: #241468; " +
            "-fx-border-width: 1px; " +
            "-fx-border-radius: 14;"
        );

        // Label Judul Kartu
        Label lblTitle = new Label(title);
        lblTitle.setStyle("-fx-text-fill: " + ColorPalette.TEXT_MUTED + "; -fx-font-size: 13px; -fx-font-weight: bold;");

        // Label Nominal Rupiah (Menggunakan warna aksen neon kustom)
        lblAmount = new Label(amount);
        lblAmount.setStyle("-fx-text-fill: " + accentColor + "; -fx-font-size: 24px; -fx-font-weight: bold;");

        this.getChildren().addAll(lblTitle, lblAmount);
    }

    /**
     * UTAMA: Method pembuka fungsi reaktif agar halaman DashboardView 
     * bisa mengubah teks nominal uang secara real-time!
     */
    public void setAmount(String text) {
        this.lblAmount.setText(text);
    }
}
