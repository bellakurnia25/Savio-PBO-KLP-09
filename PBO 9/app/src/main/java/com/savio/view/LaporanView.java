package com.savio.view;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class LaporanView extends VBox {
    // Variabel komponen penampung teks status keuangan wajib ada
    public final Label lblKeb = new Label("Rp 0");
    public final Label lblKei = new Label("Rp 0");
    public final Label lblStatusKeb = new Label("");
    public final Label lblStatusKei = new Label("");
    public final Label lblDetailBulananKeb = new Label("");
    public final Label lblDetailBulananKei = new Label("");
    public final Label lblTotalIncome = new Label("Rp 0");
    public final Label lblTotalOutcome = new Label("Rp 0");
    
    public final Button btnFilterHarian = new Button("Harian");
    public final Button btnFilterMingguan = new Button("Mingguan");
    public final Button btnFilterBulanan = new Button("Bulanan");

    public LaporanView() {
        Label lblTitle = new Label("Laporan Keuangan");

        // 🚨 KOSONGKAN: Struktur susunan grid box laporan, pewarnaan teks (merah/hijau),
        // desain visual card total pemasukan/pengeluaran, dan style tombol filter.
        // Biarkan Front-End yang menata estetikanya di sini.

        this.getChildren().addAll(
            lblTitle, 
            btnFilterHarian, btnFilterMingguan, btnFilterBulanan,
            lblTotalIncome, lblTotalOutcome,
            lblKeb, lblStatusKeb, lblDetailBulananKeb,
            lblKei, lblStatusKei, lblDetailBulananKei
        );

        // Kerangka aksi tombol filter kosong untuk jatah Logic (Teman B)
        btnFilterHarian.setOnAction(e -> ubahModeHari(1));
        btnFilterMingguan.setOnAction(e -> ubahModeHari(7));
        btnFilterBulanan.setOnAction(e -> ubahModeHari(30));
    }

    private void ubahModeHari(int hari) {
        // 🕹️ JATAH LOGIC: Pembagian matematika alokasi anggaran berdasarkan filter hari
    }
}