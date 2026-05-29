package com.savio.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DataDompet {
    // 1. Variabel Utama (Sudah di-upgrade jadi Property agar Reaktif)
    public static final DoubleProperty TOTAL_INCOME = new SimpleDoubleProperty(0.0);
    public static final DoubleProperty TOTAL_OUTCOME = new SimpleDoubleProperty(0.0);
    public static final DoubleProperty SALDO_AKTIF = new SimpleDoubleProperty(0.0);
    public static final DoubleProperty DANA_DARURAT = new SimpleDoubleProperty(0.0);

    // 2. Variabel Alokasi Persentase
    public static final DoubleProperty PERSEN_KEBUTUHAN = new SimpleDoubleProperty(50.0);
    public static final DoubleProperty PERSEN_KEINGINAN = new SimpleDoubleProperty(30.0);
    public static final DoubleProperty PERSEN_TABUNGAN = new SimpleDoubleProperty(20.0);

    // 3. Variabel Nominal (Ini yang dicari oleh DashboardView & AlokasiView agar tidak error!)
    public static final DoubleProperty NOMINAL_KEBUTUHAN = new SimpleDoubleProperty(0.0);
    public static final DoubleProperty NOMINAL_KEINGINAN = new SimpleDoubleProperty(0.0);
    public static final DoubleProperty NOMINAL_TABUNGAN = new SimpleDoubleProperty(0.0);

    // 4. List Transaksi Global
    public static final ObservableList<ModelTransaksi> LIST_TRANSAKSI = FXCollections.observableArrayList();

    /**
     * Mesin Back-End: Menghitung total income/outcome dan membagi nominal pos anggaran.
     * SALDO_AKTIF = income - outcome - DANA_DARURAT (dana darurat sudah "dikunci", bukan bagian saldo bebas).
     */
    public static void kalkulasiUlang() {
        double income = 0;
        double outcome = 0;

        for (ModelTransaksi t : LIST_TRANSAKSI) {
            if (t.getKategori().equalsIgnoreCase("Income")) {
                income += t.getNominal();
            } else if (t.getKategori().equalsIgnoreCase("Outcome")) {
                outcome += t.getNominal();
            }
        }

        TOTAL_INCOME.set(income);
        TOTAL_OUTCOME.set(outcome);

        double nomKeb = income * (PERSEN_KEBUTUHAN.get() / 100.0);
        double nomKei = income * (PERSEN_KEINGINAN.get() / 100.0);
        double nomTab = income * (PERSEN_TABUNGAN.get() / 100.0);

        NOMINAL_KEBUTUHAN.set(nomKeb);
        NOMINAL_KEINGINAN.set(nomKei);
        NOMINAL_TABUNGAN.set(nomTab);

        // SALDO_AKTIF tidak termasuk dana darurat yang sudah dikunci
        double sisaJatah = income - outcome - DANA_DARURAT.get();
        SALDO_AKTIF.set(Math.max(0, sisaJatah));
    }

    public static void resetData() {
        TOTAL_INCOME.set(0.0);
        TOTAL_OUTCOME.set(0.0);
        SALDO_AKTIF.set(0.0);
        DANA_DARURAT.set(0.0);
        PERSEN_KEBUTUHAN.set(50.0);
        PERSEN_KEINGINAN.set(30.0);
        PERSEN_TABUNGAN.set(20.0);
        NOMINAL_KEBUTUHAN.set(0.0);
        NOMINAL_KEINGINAN.set(0.0);
        NOMINAL_TABUNGAN.set(0.0);
        LIST_TRANSAKSI.clear();
    }
}