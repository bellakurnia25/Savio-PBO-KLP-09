package com.savio.scenes.components;

import com.savio.utils.ColorPalette;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class CustomCard extends VBox {
    private final Label lblAmount;

    public CustomCard(String title, String amount, String accentColor) {
        this.setSpacing(10);
        this.setPadding(new Insets(20));
        this.setPrefWidth(260);
        
        this.setStyle(
            "-fx-background-color: " + ColorPalette.BG_CARD + "; " +
            "-fx-background-radius: 14; " +
            "-fx-border-color: #241468; " +
            "-fx-border-width: 1px; " +
            "-fx-border-radius: 14;"
        );

        Label lblTitle = new Label(title);
        lblTitle.setStyle("-fx-text-fill: " + ColorPalette.TEXT_MUTED + "; -fx-font-size: 13px; -fx-font-weight: bold;");

        lblAmount = new Label(amount);
        lblAmount.setStyle("-fx-text-fill: " + accentColor + "; -fx-font-size: 24px; -fx-font-weight: bold;");

        this.getChildren().addAll(lblTitle, lblAmount);
    }

    public void setAmount(String text) {
        this.lblAmount.setText(text);
    }
}