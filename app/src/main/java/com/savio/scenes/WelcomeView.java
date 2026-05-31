package com.savio.scenes;

import com.savio.MainApp;
import com.savio.scenes.components.LogoSavio;
import com.savio.utils.ColorPalette;

import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.TextAlignment;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;

public class WelcomeView extends StackPane {
    private final MainApp mainApp;
    private double xOffset = 0;
    private double yOffset = 0;
    private boolean isTransitioning = false;
    
    private double timeOffset = 0;
    private final LinearGradient bgGrad = new LinearGradient(0, 0, 1, 1, true, CycleMethod.NO_CYCLE, new Stop(0, Color.web("#14092E")), new Stop(1, Color.web("#110B24")));
    private final LinearGradient wave1Grad = new LinearGradient(0, 0, 0.5, 0.5, true, CycleMethod.NO_CYCLE, new Stop(0, Color.web("#D81B60")), new Stop(1, Color.web("#6A1B9A", 0.7)));
    private final LinearGradient wave2Grad = new LinearGradient(0, 0, 0.5, 0.5, true, CycleMethod.NO_CYCLE, new Stop(0, Color.web("#3C1361")), new Stop(1, Color.web("#1B0A3A", 0.8)));
    private final LinearGradient wave3Grad = new LinearGradient(0, 1, 1, 0.5, true, CycleMethod.NO_CYCLE, new Stop(0, Color.web("#501166")), new Stop(1, Color.web("#1F0A3B", 0.8)));
    private final LinearGradient wave4Grad = new LinearGradient(0, 1, 1, 0.6, true, CycleMethod.NO_CYCLE, new Stop(0, Color.web("#AD165B")), new Stop(1, Color.web("#681263", 0.9)));
    private final LinearGradient wave5Grad = new LinearGradient(0.5, 1, 1, 0.7, true, CycleMethod.NO_CYCLE, new Stop(0, Color.web("#FF402B")), new Stop(1, Color.web("#FF9F1C")));

    public WelcomeView(MainApp mainApp) {
        this.mainApp = mainApp;

        this.setStyle("-fx-background-color: " + ColorPalette.BG_PRIMARY + ";");

        Canvas waveCanvas = new Canvas(800, 600);
        GraphicsContext gc = waveCanvas.getGraphicsContext2D();

        waveCanvas.widthProperty().bind(this.widthProperty());
        waveCanvas.heightProperty().bind(this.heightProperty());
        
        AnimationTimer waveTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                timeOffset += 0.005;
                drawWaveBackground(gc, waveCanvas.getWidth(), waveCanvas.getHeight(), timeOffset);
            }
        };
        waveTimer.start();

        StackPane backgroundLayer = createConcentricCircles();
        backgroundLayer.setOpacity(0);
        backgroundLayer.setScaleX(0.05);
        backgroundLayer.setScaleY(0.05);

        VBox kontenAsli = new VBox(20); 
        kontenAsli.setAlignment(Pos.CENTER);
        kontenAsli.setPickOnBounds(false); 
        
        Rotate rotateX = new Rotate(0, Rotate.X_AXIS);
        Rotate rotateY = new Rotate(0, Rotate.Y_AXIS);
        kontenAsli.getTransforms().addAll(rotateX, rotateY);
        
        LogoSavio brandLogo = new LogoSavio(200, 200); 
        
        Label brandName = new Label("SAVIO");
        brandName.setStyle("-fx-font-family: 'Segoe UI', Arial; -fx-font-size: 50px; -fx-font-weight: bold; -fx-text-fill: white; -fx-letter-spacing: 5px;");
        DropShadow glowName = new DropShadow(20, Color.web("#9B5CF6", 0.6));
        brandName.setEffect(glowName);
        VBox.setMargin(brandName, new Insets(-20, 0, -10, 0));

        Label brandSlogan = new Label("SMART FINANCE, BETTER LIFE");
        brandSlogan.setStyle("-fx-font-family: 'Arial', sans-serif; -fx-font-size: 13px; -fx-text-fill: #888899; -fx-font-weight: bold; -fx-letter-spacing: 2px;");

        Rectangle divider = new Rectangle(40, 1, Color.web("#b800dd"));
        DropShadow glowDivider = new DropShadow(10, Color.web("#b800dd", 0.8));
        divider.setEffect(glowDivider);
        VBox.setMargin(divider, new Insets(0, 0, 0, 0));

        Label tagline = new Label(" ");
        tagline.setTextAlignment(TextAlignment.CENTER);

        Label lblTapAnywhere = new Label("TEKAN DI MANA SAJA UNTUK MELANJUTKAN");
        lblTapAnywhere.setTextAlignment(TextAlignment.CENTER);
        lblTapAnywhere.setStyle("-fx-font-size: 10px; -fx-text-fill: #9000ad75; -fx-font-weight: bold; -fx-letter-spacing: 1.5px;");
        StackPane.setAlignment(lblTapAnywhere, Pos.BOTTOM_CENTER);
        StackPane.setMargin(lblTapAnywhere, new Insets(0, 0, 120, 0));

        brandLogo.setOpacity(0); brandLogo.setTranslateY(25);
        brandName.setOpacity(0); brandName.setTranslateY(25);
        brandSlogan.setOpacity(0); brandSlogan.setTranslateY(25);
        tagline.setOpacity(0); tagline.setTranslateY(0);
        lblTapAnywhere.setOpacity(0);
        divider.setOpacity(0); divider.setScaleX(0);

        FadeTransition blinkTransition = new FadeTransition(Duration.seconds(1.2), lblTapAnywhere);
        blinkTransition.setFromValue(1.0);
        blinkTransition.setToValue(0.2);
        blinkTransition.setCycleCount(Animation.INDEFINITE);
        blinkTransition.setAutoReverse(true);

        kontenAsli.getChildren().addAll(brandLogo, brandName, brandSlogan, divider, tagline);

        HBox windowControls = buatTombolKontrolJendela();
        StackPane.setAlignment(windowControls, Pos.TOP_RIGHT);

        this.getChildren().addAll(waveCanvas, backgroundLayer, kontenAsli, lblTapAnywhere, windowControls);

        ParallelTransition masterAnim = new ParallelTransition();

        FadeTransition ftCirc = new FadeTransition(Duration.seconds(2.5), backgroundLayer);
        ftCirc.setToValue(1);
        ScaleTransition stCirc = new ScaleTransition(Duration.seconds(2.5), backgroundLayer);
        stCirc.setToX(1); stCirc.setToY(1);
        
        stCirc.setOnFinished(e -> {
            ScaleTransition pulse = new ScaleTransition(Duration.seconds(4), backgroundLayer);
            pulse.setByX(0.04); 
            pulse.setByY(0.04);
            pulse.setAutoReverse(true);
            pulse.setCycleCount(Animation.INDEFINITE);
            pulse.play();
        });

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
        ftTap.setOnFinished(e -> blinkTransition.play()); 
        masterAnim.getChildren().add(ftTap);

        masterAnim.play();

        this.setOnMouseMoved(e -> {
            double width = this.getWidth() == 0 ? 800 : this.getWidth();
            double height = this.getHeight() == 0 ? 600 : this.getHeight();
            
            double xShift = (e.getX() - (width / 2));
            double yShift = (e.getY() - (height / 2));

            backgroundLayer.setTranslateX(-xShift * 0.015);
            backgroundLayer.setTranslateY(-yShift * 0.015);

            rotateX.setPivotX(width / 2);
            rotateX.setPivotY(height / 2);
            rotateY.setPivotX(width / 2);
            rotateY.setPivotY(height / 2);

            rotateX.setAngle(-(yShift / (height / 2)) * 8); 
            rotateY.setAngle((xShift / (width / 2)) * 8);
        });

        this.setOnMouseClicked(e -> {
            if (e.getY() > 40 && !isTransitioning) {
                isTransitioning = true;
                masterAnim.stop();
                blinkTransition.stop(); 
                waveTimer.stop();

                Circle ripple = new Circle(10);
                ripple.setFill(Color.TRANSPARENT);
                ripple.setStroke(Color.web("#9B5CF6"));
                ripple.setStrokeWidth(5);
                
                ripple.setTranslateX(e.getX() - (this.getWidth() / 2));
                ripple.setTranslateY(e.getY() - (this.getHeight() / 2));
                
                this.getChildren().add(ripple);

                Timeline rippleAnim = new Timeline(
                    new KeyFrame(Duration.ZERO,
                        new KeyValue(ripple.radiusProperty(), 10),
                        new KeyValue(ripple.opacityProperty(), 1)
                    ),
                    new KeyFrame(Duration.seconds(0.6),
                        new KeyValue(ripple.radiusProperty(), 1200),
                        new KeyValue(ripple.opacityProperty(), 0),
                        new KeyValue(ripple.strokeWidthProperty(), 25)
                    )
                );
                
                rippleAnim.setOnFinished(event -> mainApp.navigateToLogin());
                rippleAnim.play();
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

    private void drawWaveBackground(GraphicsContext gc, double w, double h, double time) {
        if (w <= 0 || h <= 0) return;
        gc.clearRect(0, 0, w, h);

        gc.setFill(bgGrad);
        gc.fillRect(0, 0, w, h);

        // Ombak 1
        gc.setFill(wave1Grad);
        gc.beginPath();
        gc.moveTo(0, 0);
        gc.lineTo(w * 0.7, 0);
        gc.bezierCurveTo(
            w * 0.3, h * 0.05 + Math.sin(time) * 15, 
            w * 0.15, h * 0.2 + Math.cos(time * 0.8) * 20, 
            0, h * 0.35 + Math.sin(time * 1.2) * 10
        );
        gc.closePath();
        gc.fill();

        // Ombak 2
        gc.setFill(wave2Grad);
        gc.beginPath();
        gc.moveTo(0, 0);
        gc.lineTo(w * 0.4, 0);
        gc.bezierCurveTo(
            w * 0.15, h * 0.1 + Math.sin(time + 2) * 20, 
            w * 0.05, h * 0.3 + Math.cos(time * 0.9) * 15, 
            0, h * 0.5 + Math.sin(time * 1.1 + 1) * 10
        );
        gc.closePath();
        gc.fill();

        // Ombak 3
        gc.setFill(wave3Grad);
        gc.beginPath();
        gc.moveTo(w * 0.55, h); 
        gc.bezierCurveTo(
            w * 0.7, h * 1.05 + Math.cos(time * 0.7) * 15, 
            w * 0.85, h * 0.6 + Math.sin(time * 0.8) * 15, 
            w, h * 0.45 + Math.cos(time) * 10 
        );
        gc.lineTo(w, h);
        gc.closePath();
        gc.fill();

        // Ombak 4
        gc.setFill(wave4Grad);
        gc.beginPath();
        gc.moveTo(w * 0.7, h); 
        gc.bezierCurveTo(
            w * 0.8, h * 0.95 + Math.sin(time * 1.3) * 10, 
            w * 0.9, h * 0.7 + Math.cos(time * 1.1) * 10, 
            w, h * 0.55 + Math.sin(time * 0.9) * 8 
        );
        gc.lineTo(w, h);
        gc.closePath();
        gc.fill();

        // Ombak 5
        gc.setFill(wave5Grad);
        gc.beginPath();
        gc.moveTo(w * 0.85, h); 
        gc.bezierCurveTo(
            w * 0.9, h * 0.98 + Math.sin(time * 1.5 + 4) * 8, 
            w * 0.95, h * 0.8 + Math.cos(time * 1.2 + 5) * 8, 
            w, h * 0.65 + Math.cos(time * 1.4) * 5 
        );
        gc.lineTo(w, h);
        gc.closePath();
        gc.fill();
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
        DropShadow circleGlow = new DropShadow(20, Color.web("#9B5CF6", 0.25));
        
        Circle c1 = new Circle(180);
        c1.setFill(Color.TRANSPARENT);
        c1.setStroke(Color.web("#ffffff", 0.08));
        c1.setEffect(circleGlow);
        
        Circle c2 = new Circle(280);
        c2.setFill(Color.TRANSPARENT);
        c2.setStroke(Color.web("#ffffff", 0.06));
        c2.setEffect(circleGlow);
        
        Circle c3 = new Circle(400);
        c3.setFill(Color.TRANSPARENT);
        c3.setStroke(Color.web("#ffffff", 0.04));

        // circlesPane.getChildren().addAll(c3, c2, c1);
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