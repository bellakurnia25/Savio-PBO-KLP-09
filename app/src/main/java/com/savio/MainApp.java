package com.savio;

import com.savio.scenes.LoginView;
import com.savio.scenes.MainLayout;
import com.savio.scenes.WelcomeView;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainApp extends Application {
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;

        primaryStage.initStyle(StageStyle.UNDECORATED);

        com.savio.utils.KoneksiJSON.inisialisasiDatabase();

        navigateToWelcome();

        primaryStage.setTitle("SAVIO - Smart Finance, Better Life");
        primaryStage.setMinWidth(1150);
        primaryStage.setMinHeight(730);
        primaryStage.show();
    }

    public void navigateToWelcome() {
        WelcomeView welcomeView = new WelcomeView(this);
        Scene scene = new Scene(welcomeView, 1200, 750);
        primaryStage.setScene(scene);
    }

    
    public void navigateToLogin() {
        LoginView loginView = new LoginView(this);
        Scene scene = new Scene(loginView, 1200, 750);
        primaryStage.setScene(scene);
    }

    
    public void navigateToDashboard() {
        MainLayout mainLayout = new MainLayout(this);
        Scene scene = new Scene(mainLayout, 1200, 750);
        primaryStage.setScene(scene);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
