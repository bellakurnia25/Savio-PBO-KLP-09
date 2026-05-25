package com.savio.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DataDompet {
    //tambah transaksi kas
    public static final ObservableList<ModelTransaksi> LIST_TRANSAKSI = FXCollections.observableArrayList();

    // Saldo murni
    public static final DoubleProperty SALDO_AKTIF = new SimpleDoubleProperty(0.0);
    
    // Kantong alokasi
    public static final DoubleProperty NOMINAL_KEBUTUHAN = new SimpleDoubleProperty(0.0);
    public static final DoubleProperty NOMINAL_KEINGINAN = new SimpleDoubleProperty(0.0);
    
    // Dana darurat awal
    public static double DANA_DARURAT = 0.0;

    //rasio alokasi persentase (50/30/20)
    public static final DoubleProperty PERSEN_KEBUTUHAN = new SimpleDoubleProperty(50.0);
    public static final DoubleProperty PERSEN_KEINGINAN = new SimpleDoubleProperty(30.0);
    public static final DoubleProperty PERSEN_TABUNGAN = new SimpleDoubleProperty(20.0);

    //OTOMATISASI SAVIO:

    public static void alokasikanPemasukanOtomatis(double jumlahIncome) {
        double pKeb = PERSEN_KEBUTUHAN.get() / 100.0;
        double pKei = PERSEN_KEINGINAN.get() / 100.0;
        double pTab = PERSEN_TABUNGAN.get() / 100.0;

        double jatahKebutuhan = jumlahIncome * pKeb;
        double jatahKeinginan = jumlahIncome * pKei;
        double jatahTabungan = jumlahIncome * pTab;

        // Distribusikan ke kantong pos reaktif
        NOMINAL_KEBUTUHAN.set(NOMINAL_KEBUTUHAN.get() + jatahKebutuhan);
        NOMINAL_KEINGINAN.set(NOMINAL_KEINGINAN.get() + jatahKeinginan);
        
      
        DANA_DARURAT += jatahTabungan;

        // Saldo Aktif adalah gabungan nominal Kebutuhan + Keinginan yang bisa dibelanjakan
        SALDO_AKTIF.set(SALDO_AKTIF.get() + (jatahKebutuhan + jatahKeinginan));
    }

    //SINKRONISASI KALKULASI PUSAT 
    public static void kalkulasiPusat() {
        System.out.println("🔄 [DataDompet] Kalkulasi pusat dijalankan secara aman.");
    }

    public static void refreshDataKalkulasiPusat() {
        kalkulasiPusat();
    }
}