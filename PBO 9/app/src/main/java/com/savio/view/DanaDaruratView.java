package com.savio.view;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class DanaDaruratView extends VBox {
    public final Label lblTotalDarurat = new Label("Rp 0");
    public final Label lblTargetInfo = new Label("");
    public final Button btnTarik = new Button("📥 Tarik Ke Dana Darurat");
    public final Button btnCairkan = new Button("📤 Cairkan Dana Darurat");

    public DanaDaruratView() {
        // 🚨 KOSONGKAN: Desain kartu utama, pewarnaan tombol Tarik/Cairkan, 
        // font teks status darurat, dan jarak padding/margin estetikanya.
        
        this.getChildren().addAll(lblTotalDarurat, lblTargetInfo, btnTarik, btnCairkan);
    }
}