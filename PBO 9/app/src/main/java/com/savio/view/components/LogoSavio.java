package com.savio.view.components;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class LogoSavio extends StackPane {

    public LogoSavio(double width, double height) {
        try {
            // Mengambil gambar resmi dari folder resources proyek
            Image img = new Image(getClass().getResourceAsStream("/logo_savio.png"));
            ImageView imageView = new ImageView(img);
            
            // Mengatur ukuran proporsional gambar tanpa merusak aspek rasio
            imageView.setFitWidth(width);
            imageView.setFitHeight(height);
            imageView.setPreserveRatio(true);
            imageView.setSmooth(true);
            
            this.getChildren().add(imageView);
        } catch (Exception e) {
            // Sistem pengaman cadangan jika gambar lupa dimasukkan ke folder resources
            javafx.scene.control.Label fallback = new javafx.scene.control.Label("S");
            fallback.setStyle("-fx-font-size: " + (height * 0.8) + "px; -fx-font-weight: bold; -fx-text-fill: #D41A8B;");
            this.getChildren().add(fallback);
            System.out.println("Peringatan: File logo.png belum ditaruh di folder resources!");
        }
    }
}