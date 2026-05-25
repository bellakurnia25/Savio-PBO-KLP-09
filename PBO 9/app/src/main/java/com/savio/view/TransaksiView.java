package com.savio.view;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import java.time.format.DateTimeFormatter;

public class TransaksiView extends VBox {
    // Variabel komponen utama wajib dipertahankan agar sistem tidak error
    public final VBox listContainer = new VBox(10);
    public final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy");
    public final Button btnTambahTransaksi = new Button("➕ Tambah Transaksi");

    public TransaksiView() {
        // Kerangka layout dasar
        Label lblTitle = new Label("Riwayat Arus Kas");
        
        // 🚨 KOSONGKAN: Pengaturan CSS, warna background gelap, padding, 
        // bentuk list kartu transaksi, dan pop-up dialog tambah/edit data
        // diserahkan sepenuhnya kepada Front-End untuk didesain.

        this.getChildren().addAll(lblTitle, btnTambahTransaksi, listContainer);

        btnTambahTransaksi.setOnAction(e -> {
            // 🕹️ JATAH LOGIC: Tempat Teman B memasukkan event handler pop-up form input
        });
    }

    public void refreshDaftarTransaksi() {
        // 🕹️ JATAH LOGIC: Tempat Teman B melakukan perulangan (looping) untuk menggambar data dari JSON
    }
}