package com.savio.view;

import com.savio.config.ColorPalette;
import com.savio.MainApp;
import com.savio.view.components.LogoSavio;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class WelcomeView extends StackPane {
    private final MainApp mainApp;
    private double xOffset = 0;
    private double yOffset = 0;

    public WelcomeView(MainApp mainApp) {
        this.mainApp = mainApp;
        this.setStyle("-fx-background-color: " + ColorPalette.BG_PRIMARY + ";");

        // --- 1. KONTEN UTAMA ---
        VBox kontenAsli = new VBox(25); 
        kontenAsli.setAlignment(Pos.CENTER);
        
        LogoSavio brandLogo = new LogoSavio(45, 55); 
        
        Label brandName = new Label("SAVIO");
        brandName.setStyle("-fx-font-size: 36px; -fx-font-weight: bold; -fx-text-fill: white; -fx-letter-spacing: 2px;");
        
        Label brandSlogan = new Label("Smart Finance, Better Life");
        brandSlogan.setStyle("-fx-font-size: 15px; -fx-text-fill: " + ColorPalette.TEXT_MUTED + ";");
        
        // FITUR BARU: Tulisan petunjuk interaktif kustom pengganti tombol
        Label lblTapAnywhere = new Label("Tap di mana saja untuk melanjutkan");
        lblTapAnywhere.setStyle("-fx-font-size: 14px; -fx-text-fill: " + ColorPalette.ACCENT_KEBUTUHAN + "; -fx-font-weight: bold; -fx-padding: 40 0 0 0;");

        // Memberikan efek animasi berkedip (Blinking) yang halus dan estetik ala game premium
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1.2), lblTapAnywhere);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.2);
        fadeTransition.setCycleCount(Animation.INDEFINITE);
        fadeTransition.setAutoReverse(true);
        fadeTransition.play();
        
        kontenAsli.getChildren().addAll(brandLogo, brandName, brandSlogan, lblTapAnywhere);

        // --- 2. BILAH TOMBOL WINDOWS KUSTOM (TETAP AMAN DI POJOK ATAS) ---
        HBox windowControls = buatTombolKontrolJendela();
        StackPane.setAlignment(windowControls, Pos.TOP_RIGHT);

        // Satukan ke layout dasar
        this.getChildren().addAll(kontenAsli, windowControls);

        // ==================== LOGIKA FITUR UTAMA: KLIK DI MANA SAJA ====================
        this.setOnMouseClicked(e -> {
            // Cegah perpindahan halaman jika yang diklik sengaja tombol Minimize/Maximize/Close atas
            if (e.getY() > 40) {
                fadeTransition.stop(); // Matikan animasi biar hemat memori
                mainApp.navigateToLogin(); // Lempar langsung ke halaman Login dua kolom kita
            }
        });
        
        // Memberikan efek visual kursor tangan di seluruh area agar user tahu layar ini bisa diklik
        this.setStyle(this.getStyle() + " -fx-cursor: hand;");

        // Logika drag jendela lewat bilah atas kustom
        windowControls.setOnMousePressed(e -> {
            xOffset = e.getSceneX();
            yOffset = e.getSceneY();
        });
        windowControls.setOnMouseDragged(e -> {
            Stage stage = (Stage) this.getScene().getWindow();
            stage.setX(e.getScreenX() - xOffset);
            stage.setY(e.getScreenY() - yOffset);
        });
    }

    private HBox buatTombolKontrolJendela() {
        HBox topBar = new HBox(5);
        topBar.setAlignment(Pos.CENTER_RIGHT);
        topBar.setPadding(new Insets(5, 10, 0, 0));
        topBar.setMaxHeight(35);
        topBar.setStyle("-fx-cursor: default;"); // Kembalikan kursor panah biasa khusus di area tombol atas

        Button btnMinimize = new Button("—");
        styleTombolWindow(btnMinimize, "#333333");
        btnMinimize.setOnAction(e -> ((Stage) this.getScene().getWindow()).setIconified(true));

        Button btnMaximize = new Button("❑");
        styleTombolWindow(btnMaximize, "#333333");
        btnMaximize.setOnAction(e -> {
            Stage stage = (Stage) this.getScene().getWindow();
            stage.setMaximized(!stage.isMaximized());
        });

        Button btnClose = new Button("✕");
        styleTombolWindow(btnClose, "#E74C3C");
        btnClose.setOnAction(e -> {
            ((Stage) this.getScene().getWindow()).close();
            System.exit(0);
        });

        topBar.getChildren().addAll(btnMinimize, btnMaximize, btnClose);
        return topBar;
    }

    private void styleTombolWindow(Button btn, String hoverColor) {
        btn.setPrefSize(40, 30);
        btn.setStyle("-fx-background-color: transparent; -fx-text-fill: #888888; -fx-font-size: 12px; -fx-background-radius: 0;");
        btn.setOnMouseEntered(e -> btn.setStyle("-fx-background-color: " + hoverColor + "; -fx-text-fill: white; -fx-font-size: 12px; -fx-background-radius: 0; -fx-cursor: hand;"));
        btn.setOnMouseExited(e -> btn.setStyle("-fx-background-color: transparent; -fx-text-fill: #888888; -fx-font-size: 12px; -fx-background-radius: 0;"));
    }
}