package com.savio.model;

import java.time.LocalDate;

public abstract class KomponenKeuangan {
    private String deskripsi;
    private double jumlah;
    private LocalDate tanggal;

    public KomponenKeuangan(String deskripsi, double jumlah, LocalDate tanggal) {
        this.deskripsi = deskripsi;
        this.jumlah = jumlah;
        this.tanggal = tanggal;
    }

    public abstract String getTipeKomponen();
    public abstract double hitungNilaiBersih();

    public String getDeskripsi() { 
        return deskripsi; 
    }
    
    public double getJumlah() { 
        return jumlah; 
    }
    
    public LocalDate getTanggal() { 
        return tanggal; 
    }
}