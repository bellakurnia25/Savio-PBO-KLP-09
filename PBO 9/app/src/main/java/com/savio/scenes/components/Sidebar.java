package com.savio.scenes.components;

import com.savio.utils.ColorPalette;
import com.savio.scenes.MainLayout;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import java.util.ArrayList;
import java.util.List;

public class Sidebar extends VBox {
    private final MainLayout mainLayout;
    
    // Tempat menyimpan semua daftar tombol menu untuk mempermudah reset warna
    private final List<Button> allMenuButtons = new ArrayList<>();
    
    // Variabel pelacak untuk mengunci tombol mana yang saat ini sedang aktif dibuka
    private Button activeButton;

    public Sidebar(MainLayout mainLayout) {
        this.mainLayout = mainLayout;
        this.setPrefWidth(240);
        this.setSpacing(10);
        this.setPadding(new Insets(30, 15, 30, 15));
        this.setStyle("-fx-background-color: " + ColorPalette.BG_SIDEBAR + ";");
        
        // Brand Header SAVIO
        VBox brandBox = new VBox(5);
        brandBox.setAlignment(Pos.CENTER);
        brandBox.setPadding(new Insets(0, 0, 30, 0));
        
        // Mengatur logo masuk ke dalam sidebar menu kiri
        LogoSavio brandLogo = new LogoSavio(45, 55);
        
        Label brandName = new Label("SAVIO");
        brandName.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: white;");
        
        brandBox.getChildren().addAll(brandLogo, brandName);
        this.getChildren().add(brandBox);
        
        // Daftar Tombol Menu Navigasi
        String[] menus = {"Dashboard", "Transaksi", "Alokasi Dana", "Dana Darurat", "Laporan", "Profil", "Keluar"};
        for (String menu : menus) {
            Button btn = createMenuButton(menu);
            allMenuButtons.add(btn);
            this.getChildren().add(btn);
            
            // DEFAULT AWAL: Kunci tombol "Dashboard" agar langsung menyala saat aplikasi pertama terbuka
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
        
        // Atur gaya dasar kosmetik awal (Redup/Muted bawaan pabrik)
        setButtonToDefaultStyle(btn, name);

        // ==================== LOGIKA INTERAKSI PENGUNCI WARNA AKTIF ====================
        btn.setOnAction(e -> {
            // Khusus tombol Keluar, tidak perlu mengunci status aktif sidebar karena dia memicu pop-up exit
            if (name.equals("Keluar")) {
                mainLayout.showView(name);
                return;
            }

            // 1. Kembalikan semua tombol menu lainnya ke warna redup semula
            for (Button otherBtn : allMenuButtons) {
                setButtonToDefaultStyle(otherBtn, otherBtn.getText());
            }

            // 2. Kunci dan paksa tombol yang baru saja diklik ini agar menyala terang benderang
            setButtonToActiveStyle(btn);
            activeButton = btn;

            // 3. Lemparkan perintah perpindahan halaman tengah ke MainLayout
            mainLayout.showView(name);
        });

        // ==================== LOGIKA HOVER MOUSE (DIPROTEKSI OLEH STATUS AKTIF) ====================
        btn.setOnMouseEntered(e -> {
            // Jika tombol ini adalah tombol yang sedang aktif dibuka, biarkan dia tetap menyala premium
            if (btn == activeButton) return;

            if (name.equals("Keluar")) {
                btn.setStyle("-fx-background-color: #3d0c11; -fx-text-fill: #FF6B6B; -fx-font-size: 14px; -fx-background-radius: 8; -fx-cursor: hand;");
            } else {
                btn.setStyle("-fx-background-color: #241468; -fx-text-fill: white; -fx-font-size: 14px; -fx-background-radius: 8; -fx-cursor: hand;");
            }
        });

        btn.setOnMouseExited(e -> {
            // KUNCI UTAMA: Begitu kursor mouse pergi, jangan biarkan dia meredup jika dia adalah menu aktif!
            if (btn == activeButton) return;

            setButtonToDefaultStyle(btn, name);
        });

        return btn;
    }

    /**
     * Helper khusus untuk memaksa tombol menyala terang menetap (Active State Highlight)
     */
    private void setButtonToActiveStyle(Button btn) {
        btn.setStyle(
            "-fx-background-color: #241468; " + // Latar belakang ungu indigo menyala seperti mockup-mu
            "-fx-text-fill: white; " +
            "-fx-font-weight: bold; " +
            "-fx-font-size: 14px; " +
            "-fx-background-radius: 8;"
        );
    }

    /**
     * Helper khusus untuk mengembalikan tombol ke warna redup normal
     */
    private void setButtonToDefaultStyle(Button btn, String name) {
        if (name.equals("Keluar")) {
            btn.setStyle("-fx-background-color: transparent; -fx-text-fill: " + ColorPalette.TEXT_DANGER + "; -fx-font-size: 14px; -fx-cursor: hand;");
        } else {
            btn.setStyle("-fx-background-color: transparent; -fx-text-fill: " + ColorPalette.TEXT_MUTED + "; -fx-font-size: 14px; -fx-cursor: hand;");
        }
    }
}
