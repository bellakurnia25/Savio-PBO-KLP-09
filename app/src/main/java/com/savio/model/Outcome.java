package com.savio.model;

import java.time.LocalDate;

public class Outcome extends KomponenKeuangan {
    private String kategori;

    public Outcome(String deskripsi, double jumlah, LocalDate tanggal, String kategori) {
        super(deskripsi, jumlah, tanggal);
        this.kategori = kategori;
    }

    public String getKategori() {
        return kategori;
    }

    @Override
    public String getTipeKomponen() {
        return "OUTCOME";
    }

    @Override
    public double hitungNilaiBersih() {
        return -getJumlah(); 
    }
}