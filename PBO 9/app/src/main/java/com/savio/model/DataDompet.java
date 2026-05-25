package com.savio.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DataDompet {
    // Variabel global reaktif wajib dibiarkan utuh agar views tidak merah/error
    public static final DoubleProperty SALDO_AKTIF = new SimpleDoubleProperty(0.0);
    public static final DoubleProperty NOMINAL_KEBUTUHAN = new SimpleDoubleProperty(0.0);
    public static final DoubleProperty NOMINAL_KEINGINAN = new SimpleDoubleProperty(0.0);
    public static double DANA_DARURAT = 0.0;

    public static final DoubleProperty PERSEN_KEBUTUHAN = new SimpleDoubleProperty(50.0);
    public static final DoubleProperty PERSEN_KEINGINAN = new SimpleDoubleProperty(30.0);
    public static final DoubleProperty PERSEN_TABUNGAN = new SimpleDoubleProperty(20.0);

    public static final ObservableList<ModelTransaksi> LIST_TRANSAKSI = FXCollections.observableArrayList();

    public static void alokasikanPemasukanOtomatis(double nominalIncome) {
        // 🚨 PANGKAS / KOSONGKAN ISI METHOD INI:
        // Biarkan Teman B (Logic) yang mengetik rumus matematika pembagian nominal uang
        // berdasarkan persentase (50% Kebutuhan, 30% Keinginan, 20% Tabungan) di sini.
    }
}