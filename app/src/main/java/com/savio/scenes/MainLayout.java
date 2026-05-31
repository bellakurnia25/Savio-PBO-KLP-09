package com.savio.scenes;

import com.savio.MainApp;
import com.savio.scenes.components.Sidebar;
import com.savio.utils.ColorPalette;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainLayout extends HBox {
    private final StackPane contentArea;
    private final DashboardView dashboardView;
    private final MainApp mainApp;

    private double xOffset = 0;
    private double yOffset = 0;

    public MainLayout(MainApp mainApp) {
        this.mainApp = mainApp;
        this.setStyle("-fx-background-color: " + ColorPalette.BG_PRIMARY + ";");

        VBox rightSideContainer = new VBox();
        HBox.setHgrow(rightSideContainer, Priority.ALWAYS);

        //Tombol Kontrol
        HBox windowControls = buatTombolKontrolJendela();
        
        contentArea = new StackPane();
        contentArea.setPadding(new Insets(10));
        VBox.setVgrow(contentArea, Priority.ALWAYS);

        this.dashboardView = new DashboardView(this);        contentArea.getChildren().setAll(dashboardView);

        rightSideContainer.getChildren().addAll(windowControls, contentArea);

        Sidebar sidebar = new Sidebar(this);

        this.getChildren().addAll(sidebar, rightSideContainer);

        windowControls.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        windowControls.setOnMouseDragged(event -> {
            Stage stage = (Stage) this.getScene().getWindow();
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        });
    }

    public void showView(String viewName) {
        switch (viewName) {
            case "🏠 Dashboard":
            case "Dasbor":
                dashboardView.refreshDataKalkulasiPusat();                setHalamanTengah(dashboardView);
                break;
            case "🔁 Transaksi":
                setHalamanTengah(new TransaksiView());
                break;
            case "📊 Alokasi Dana":
                setHalamanTengah(new AlokasiView());
                break;
            case "🔒 Dana Darurat":
                setHalamanTengah(new DanaDaruratView());
                break;
            case "📈 Laporan":
                setHalamanTengah(new LaporanView());
                break;
            case "👤 Profil":
            case "👤 Profile":
                setHalamanTengah(new ProfilView());
                break;
            case "Keluar":
                tampilkanDialogKeluar();
                break;
        }
    }

    private void setHalamanTengah(Node viewBaru) {
        contentArea.getChildren().setAll(viewBaru);
    }

    private HBox buatTombolKontrolJendela() {
        HBox topBar = new HBox(5);
        topBar.setAlignment(Pos.CENTER_RIGHT);
        topBar.setPadding(new Insets(5, 10, 0, 0));
        topBar.setStyle("-fx-background-color: " + ColorPalette.BG_PRIMARY + ";");

        Button btnMinimize = new Button("—");
        styleTombolWindow(btnMinimize, "#333333");
        btnMinimize.setOnAction(e -> {
            Stage stage = (Stage) this.getScene().getWindow();
            stage.setIconified(true);
        });

        Button btnMaximize = new Button("❑");
        styleTombolWindow(btnMaximize, "#333333");
        btnMaximize.setOnAction(e -> {
            Stage stage = (Stage) this.getScene().getWindow();
            stage.setMaximized(!stage.isMaximized());
        });

        Button btnClose = new Button("✕");
        styleTombolWindow(btnClose, "#E74C3C");
        btnClose.setOnAction(e -> {
            Stage stage = (Stage) this.getScene().getWindow();
            stage.close();
            System.exit(0);
        });

        topBar.getChildren().addAll(btnMinimize, btnMaximize, btnClose);
        return topBar;
    }

    private void styleTombolWindow(Button btn, String hoverColor) {
        btn.setPrefSize(40, 30);
        btn.setStyle("-fx-background-color: transparent; -fx-text-fill: #888888; -fx-font-size: 12px; -fx-background-radius: 0; -fx-cursor: hand;");
        
        btn.setOnMouseEntered(e -> btn.setStyle("-fx-background-color: " + hoverColor + "; -fx-text-fill: white; -fx-font-size: 12px; -fx-background-radius: 0; -fx-cursor: hand;"));
        btn.setOnMouseExited(e -> btn.setStyle("-fx-background-color: transparent; -fx-text-fill: #888888; -fx-font-size: 12px; -fx-background-radius: 0; -fx-cursor: hand;"));
    }

    private void tampilkanDialogKeluar() {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.CONFIRMATION);
        alert.setTitle("Konfirmasi Keluar");
        alert.setHeaderText("Apakah Anda yakin ingin keluar dari akun?");
        alert.setContentText("Anda harus memasukkan kembali kredensial untuk masuk.");

        javafx.scene.control.ButtonType btnYa = new javafx.scene.control.ButtonType("Ya, Keluar");
        javafx.scene.control.ButtonType btnTidak = new javafx.scene.control.ButtonType("Tidak", javafx.scene.control.ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(btnYa, btnTidak);

        javafx.scene.control.DialogPane dialogPane = alert.getDialogPane();
        dialogPane.setStyle("-fx-background-color: " + ColorPalette.BG_CARD + "; -fx-border-color: " + "#d36f14" + "; -fx-border-width: 2px; -fx-border-radius: 10px; -fx-background-radius: 10px;");
        dialogPane.lookup(".content.label").setStyle("-fx-text-fill: white; -fx-font-size: 13px;");
        
        if (dialogPane.lookup(".header-panel .label") != null) {
            dialogPane.lookup(".header-panel .label").setStyle("-fx-text-fill: white; -fx-font-size: 15px; -fx-font-weight: bold;");
        }

        alert.showAndWait().ifPresent(response -> {
            if (response == btnYa) {
                mainApp.navigateToLogin();
            }
        });
    }
}