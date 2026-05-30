package com.savio.scenes.components;

import com.savio.scenes.MainLayout;
import com.savio.utils.ColorPalette;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import java.util.ArrayList;
import java.util.List;

public class Sidebar extends VBox {
    private final MainLayout mainLayout;
    
    private final List<Button> allMenuButtons = new ArrayList<>();
    
    private Button activeButton;

    public Sidebar(MainLayout mainLayout) {
        this.mainLayout = mainLayout;
        this.setPrefWidth(240);

        this.setMinWidth(240);
        this.setMaxWidth(240);

        this.setSpacing(10);
        this.setPadding(new Insets(30, 15, 30, 15));
        this.setStyle("-fx-background-color: " + ColorPalette.BG_SIDEBAR + ";");
        
        VBox brandBox = new VBox(5);
        brandBox.setAlignment(Pos.CENTER);
        brandBox.setPadding(new Insets(0, 0, 10, 0));

        LogoSavio brandLogo = new LogoSavio(80, 80);
        
        Label brandName = new Label("SAVIO");
        brandName.setStyle("-fx-font-size: 40px; -fx-font-weight: bold; -fx-text-fill: white;");
        
        brandBox.getChildren().addAll(brandLogo, brandName);
        this.getChildren().add(brandBox);

        String[] menus = {"🏠 Dashboard", "🔁 Transaksi", "📊 Alokasi Dana", "🔒 Dana Darurat", "📈 Laporan", "👤 Profil", "Keluar"};
        for (String menu : menus) {
            Button btn = createMenuButton(menu);
            allMenuButtons.add(btn);
            this.getChildren().add(btn);

            if (menu.equals("Dashboard")) {
                setButtonToActiveStyle(btn);
                activeButton = btn;
            }
        }
    }

    private Button createMenuButton(String name) {
        Button btn = new Button(name);
        btn.setMaxWidth(Double.MAX_VALUE);
        btn.setAlignment(Pos.BASELINE_LEFT);
        btn.setPadding(new Insets(12, 15, 12, 15));

        setButtonToDefaultStyle(btn, name);

        btn.setOnAction(e -> {
            if (name.equals("Keluar")) {
                mainLayout.showView(name);
                return;
            }

            for (Button otherBtn : allMenuButtons) {
                setButtonToDefaultStyle(otherBtn, otherBtn.getText());
            }

            setButtonToActiveStyle(btn);
            activeButton = btn;

            mainLayout.showView(name);
        });

        btn.setOnMouseEntered(e -> {
            if (btn == activeButton) return;

            if (name.equals("Keluar")) {
                btn.setStyle("-fx-background-color: #3d0c11; -fx-text-fill: #FF6B6B; -fx-font-size: 14px; -fx-background-radius: 8; -fx-cursor: hand;");
            } else {
                btn.setStyle("-fx-background-color: #241468; -fx-text-fill: white; -fx-font-size: 14px; -fx-background-radius: 8; -fx-cursor: hand;");
            }
        });

        btn.setOnMouseExited(e -> {
            if (btn == activeButton) return;

            setButtonToDefaultStyle(btn, name);
        });
        return btn;
    }

    private void setButtonToActiveStyle(Button btn) {
        btn.setStyle(
            "-fx-background-color: #241468; " + // Latar belakang ungu indigo menyala seperti mockup-mu
            "-fx-text-fill: white; " +
            "-fx-font-weight: bold; " +
            "-fx-font-size: 14px; " +
            "-fx-background-radius: 8;"
        );
    }

    private void setButtonToDefaultStyle(Button btn, String name) {
        if (name.equals("Keluar")) {
            btn.setStyle("-fx-background-color: transparent; -fx-text-fill: " + ColorPalette.TEXT_DANGER + "; -fx-font-size: 14px; -fx-cursor: hand;");
        } else {
            btn.setStyle("-fx-background-color: transparent; -fx-text-fill: " + ColorPalette.TEXT_MUTED + "; -fx-font-size: 14px; -fx-cursor: hand;");
        }
    }
}