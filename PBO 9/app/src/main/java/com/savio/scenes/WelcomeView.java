package com.savio.scenes;

import com.savio.MainApp;
import com.savio.scenes.components.LogoSavio;
import com.savio.utils.ColorPalette;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

public class WelcomeView extends StackPane {
    private final MainApp mainApp;
    private double xOffset = 0;
    private double yOffset = 0;

    public WelcomeView(MainApp mainApp) {
        this.mainApp = mainApp;

        this.setStyle("-fx-background-color: " + ColorPalette.BG_PRIMARY + ";");

        StackPane backgroundLayer = createConcentricCircles();
        backgroundLayer.setOpacity(0);
        backgroundLayer.setScaleX(0.85);
        backgroundLayer.setScaleY(0.85);

        // Utama
        VBox kontenAsli = new VBox(20); 
        kontenAsli.setAlignment(Pos.CENTER);
        
        LogoSavio brandLogo = new LogoSavio(100, 100); 
        
        Label brandName = new Label("S A V I O");
        brandName.setStyle("-fx-font-family: 'Times New Roman', serif; -fx-font-size: 42px; -fx-font-weight: bold; -fx-text-fill: white; -fx-letter-spacing: 5px;");
        VBox.setMargin(brandName, new Insets(10, 0, -10, 0)); 

        Label brandSlogan = new Label("SMART FINANCE, BETTER LIFE");
        brandSlogan.setStyle("-fx-font-family: 'Arial', sans-serif; -fx-font-size: 13px; -fx-text-fill: #888899; -fx-font-weight: bold; -fx-letter-spacing: 2px;");

        Rectangle divider = new Rectangle(40, 1, Color.web("#510180"));
        VBox.setMargin(divider, new Insets(0, 0, 0, 0));

        Label tagline = new Label("Atur keuanganmu dengan bijak. Raih kebebasan finansialmu hari ini.");
        tagline.setTextAlignment(TextAlignment.CENTER);
        tagline.setStyle("-fx-font-size: 14px; -fx-text-fill: #AAAAAA; -fx-line-spacing: 5px;");

        Label lblTapAnywhere = new Label("TEKAN DI MANA SAJA UNTUK\nMELANJUTKAN");
        lblTapAnywhere.setTextAlignment(TextAlignment.CENTER);
        lblTapAnywhere.setStyle("-fx-font-size: 18px; -fx-text-fill: #b800dd; -fx-font-weight: bold; -fx-letter-spacing: 1.5px;");
        VBox.setMargin(lblTapAnywhere, new Insets(0, 0, 40, 0));

        brandLogo.setOpacity(0); brandLogo.setTranslateY(25);
        brandName.setOpacity(0); brandName.setTranslateY(25);
        brandSlogan.setOpacity(0); brandSlogan.setTranslateY(25);
        tagline.setOpacity(0); tagline.setTranslateY(25);
        lblTapAnywhere.setOpacity(0);
        
        divider.setOpacity(0); divider.setScaleX(0);

        FadeTransition blinkTransition = new FadeTransition(Duration.seconds(1.2), lblTapAnywhere);
        blinkTransition.setFromValue(1.0);
        blinkTransition.setToValue(0.2);
        blinkTransition.setCycleCount(Animation.INDEFINITE);
        blinkTransition.setAutoReverse(true);

        kontenAsli.getChildren().addAll(brandLogo, brandName, brandSlogan, divider, tagline, lblTapAnywhere);

        HBox windowControls = buatTombolKontrolJendela();
        StackPane.setAlignment(windowControls, Pos.TOP_RIGHT);

        this.getChildren().addAll(backgroundLayer, kontenAsli, windowControls);

        ParallelTransition masterAnim = new ParallelTransition();

        FadeTransition ftCirc = new FadeTransition(Duration.seconds(2.5), backgroundLayer);
        ftCirc.setToValue(1);
        ScaleTransition stCirc = new ScaleTransition(Duration.seconds(2.5), backgroundLayer);
        stCirc.setToX(1); stCirc.setToY(1);
        masterAnim.getChildren().addAll(ftCirc, stCirc);

        tambahkanAnimasiFadeUp(masterAnim, brandLogo, 0.4);
        tambahkanAnimasiFadeUp(masterAnim, brandName, 0.7);
        tambahkanAnimasiFadeUp(masterAnim, brandSlogan, 1.0);

        FadeTransition ftDiv = new FadeTransition(Duration.seconds(1), divider);
        ftDiv.setToValue(1);
        ftDiv.setDelay(Duration.seconds(1.3));
        ScaleTransition stDiv = new ScaleTransition(Duration.seconds(1), divider);
        stDiv.setToX(1);
        stDiv.setDelay(Duration.seconds(1.3));
        masterAnim.getChildren().addAll(ftDiv, stDiv);

        tambahkanAnimasiFadeUp(masterAnim, tagline, 1.6);

        FadeTransition ftTap = new FadeTransition(Duration.seconds(1), lblTapAnywhere);
        ftTap.setToValue(1);
        ftTap.setDelay(Duration.seconds(2.2));
        ftTap.setOnFinished(e -> blinkTransition.play()); // Mulai berkedip setelah fade-in selesai
        masterAnim.getChildren().add(ftTap);

        masterAnim.play();

        this.setOnMouseClicked(e -> {
            if (e.getY() > 40) {
                masterAnim.stop();
                blinkTransition.stop(); 
                mainApp.navigateToLogin(); 
            }
        });

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

    private void tambahkanAnimasiFadeUp(ParallelTransition pt, Node node, double delayDetik) {
        FadeTransition ft = new FadeTransition(Duration.seconds(1.2), node);
        ft.setToValue(1);
        ft.setDelay(Duration.seconds(delayDetik));
        
        TranslateTransition tt = new TranslateTransition(Duration.seconds(1.2), node);
        tt.setToY(0);
        tt.setDelay(Duration.seconds(delayDetik));
        
        pt.getChildren().addAll(ft, tt);
    }

    private StackPane createConcentricCircles() {
        StackPane circlesPane = new StackPane();
        
        Circle c1 = new Circle(180);
        c1.setFill(Color.TRANSPARENT);
        c1.setStroke(Color.web("#ffffff", 0.08));
        
        Circle c2 = new Circle(280);
        c2.setFill(Color.TRANSPARENT);
        c2.setStroke(Color.web("#ffffff", 0.06));
        
        Circle c3 = new Circle(400);
        c3.setFill(Color.TRANSPARENT);
        c3.setStroke(Color.web("#ffffff", 0.04));

        circlesPane.getChildren().addAll(c3, c2, c1);
        return circlesPane;
    }

    private HBox buatTombolKontrolJendela() {
        HBox topBar = new HBox(5);
        topBar.setAlignment(Pos.CENTER_RIGHT);
        topBar.setPadding(new Insets(5, 10, 0, 0));
        topBar.setMaxHeight(35);
        topBar.setStyle("-fx-cursor: default;"); 

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
        btn.setStyle("-fx-background-color: transparent; -fx-text-fill: #555566; -fx-font-size: 12px; -fx-background-radius: 0;");
        btn.setOnMouseEntered(e -> btn.setStyle("-fx-background-color: " + hoverColor + "; -fx-text-fill: white; -fx-font-size: 12px; -fx-background-radius: 0; -fx-cursor: hand;"));
        btn.setOnMouseExited(e -> btn.setStyle("-fx-background-color: transparent; -fx-text-fill: #555566; -fx-font-size: 12px; -fx-background-radius: 0;"));
    }
}