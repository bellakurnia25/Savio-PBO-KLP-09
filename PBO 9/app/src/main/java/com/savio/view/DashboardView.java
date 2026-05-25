package com.savio.view;

import javafx.scene.control.Label;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.VBox;

public class DashboardView extends VBox {
    private final MainLayout mainLayout;
    
    // Variabel komponen atas, samping, dan diagram lingkaran
    public final Label lblSaldoAtas = new Label("Rp 0");
    public final Label lblDaruratAtas = new Label("Rp 0");
    public final Label lblAlokasiAtas = new Label("Rp 0");
    public final PieChart pieChart = new PieChart();
    public final Label lblTextInsight = new Label("");

    public DashboardView(MainLayout mainLayout) {
        this.mainLayout = mainLayout;
        
        // 🚨 KOSONGKAN: Desain tata letak 3 kartu atas (Saldo/Darurat/Keinginan), 
        // pembuatan komponen Doughnut Chart, serta grid box legenda warna.
        // Serahkan ke Front-End untuk digambar dari awal.
        
        this.getChildren().addAll(lblSaldoAtas, lblDaruratAtas, lblAlokasiAtas, pieChart, lblTextInsight);
    }

    public void refreshDataKalkulasiPusat() {}
}