package com.savio.model;

import java.time.LocalDate;

public class Income extends KomponenKeuangan {
    private String sumberPemasukan;

    public Income(String deskripsi, double jumlah, LocalDate tanggal, String sumberPemasukan) {
        super(deskripsi, jumlah, tanggal); 
        this.sumberPemasukan = sumberPemasukan;
    }

    public String getSumberPemasukan() {
        return sumberPemasukan;
    }

    @Override
    public String getTipeKomponen() {
        return "INCOME";
    }

    @Override
    public double hitungNilaiBersih() {
        return getJumlah(); 
    }
}