package com.savio.view;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.VBox;

public class AlokasiView extends VBox {
    public final Slider sliderKebutuhan = new Slider(0, 100, 50);
    public final Slider sliderKeinginan = new Slider(0, 100, 30);
    public final Slider sliderTabungan = new Slider(0, 100, 20);
    public final PieChart pieChart = new PieChart();
    public final Button btnSimpan = new Button("Simpan Rasio Alokasi");

    public AlokasiView() {
        // 🚨 KOSONGKAN: Pengaturan CSS Slider kustom, layouting Horizontal (HBox) 
        // teks persentase, dan desain visual pratinjau diagram lingkaran di sisi kanan.
        
        this.getChildren().addAll(
            new Label("Alokasi Kebutuhan"), sliderKebutuhan,
            new Label("Alokasi Keinginan"), sliderKeinginan,
            new Label("Alokasi Tabungan"), sliderTabungan,
            pieChart, btnSimpan
        );
        
        btnSimpan.setOnAction(e -> {
            // Tombol simpan kosong siap diisi logika hitung matematika oleh Teman B
        });
    }
}