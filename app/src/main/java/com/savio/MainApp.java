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

        // Hilangkan bingkai putih bawaan Windows (Undecorated)
        primaryStage.initStyle(StageStyle.UNDECORATED);

        // Inisialisasi database JSON (hanya cek/buat file, bukan load data)
        // Data user baru akan dimuat di LoginView setelah user berhasil login
        com.savio.utils.KoneksiJSON.inisialisasiDatabase();

        // Jalankan halaman Welcome/Splash Screen pertama kali
        navigateToWelcome();

        primaryStage.setTitle("SAVIO - Smart Finance, Better Life");
        primaryStage.setMinWidth(1150);
        primaryStage.setMinHeight(730);
        primaryStage.show();
    }

    /**
     * Jalur Splash Screen / Welcome awal aplikasi
     */
    public void navigateToWelcome() {
        WelcomeView welcomeView = new WelcomeView(this);
        Scene scene = new Scene(welcomeView, 1200, 750);
        primaryStage.setScene(scene);
    }

    /**
     * Navigasi melemparkan user ke halaman Login
     */
    public void navigateToLogin() {
        LoginView loginView = new LoginView(this);
        Scene scene = new Scene(loginView, 1200, 750);
        primaryStage.setScene(scene);
    }

    /**
     * Navigasi masuk ke dalam Dashboard Utama setelah sukses login
     */
    public void navigateToDashboard() {
        MainLayout mainLayout = new MainLayout(this);
        Scene scene = new Scene(mainLayout, 1200, 750);
        primaryStage.setScene(scene);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
