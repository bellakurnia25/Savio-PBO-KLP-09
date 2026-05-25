package com.savio.view;

import com.savio.MainApp;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class MainLayout extends HBox {
    private final StackPane contentArea;
    private final MainApp mainApp;

    public MainLayout(MainApp mainApp) {
        this.mainApp = mainApp;
        
        VBox rightSideContainer = new VBox();
        contentArea = new StackPane();
        
        // 🚨 KOSONGKAN: Biarkan Front-End yang membuat sidebar kustom, 
        // menghias tombol navigasi samping, serta menyusun layouting utamanya.
        
        rightSideContainer.getChildren().add(contentArea);
        this.getChildren().add(rightSideContainer);
    }

    public void showView(String viewName) {
        // Logika switch case navigasi tetap dibiarkan utuh agar halaman bisa berpindah
        switch (viewName) {
            case "Dashboard": contentArea.getChildren().setAll(new DashboardView(this)); break;
            case "Transaksi": contentArea.getChildren().setAll(new TransaksiView()); break;
            case "Alokasi Dana": contentArea.getChildren().setAll(new AlokasiView()); break;
            case "Dana Darurat": contentArea.getChildren().setAll(new DanaDaruratView()); break;
            case "Laporan": contentArea.getChildren().setAll(new LaporanView()); break;
            case "Profil": contentArea.getChildren().setAll(new ProfilView()); break;
        }
    }
}