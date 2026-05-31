package com.savio.scenes.components;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class LogoSavio extends StackPane {

    public LogoSavio(double width, double height) {
            Image img = new Image(getClass().getResourceAsStream("/logo_savio.png"));
            ImageView imageView = new ImageView(img);
            
            imageView.setFitWidth(width);
            imageView.setFitHeight(height);
            imageView.setPreserveRatio(true);
            imageView.setSmooth(true);
            
            this.getChildren().add(imageView);
    }
}