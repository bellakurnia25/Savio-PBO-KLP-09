package com.savio.model;

public class ModelTransaksi {
    private String id;
    private String deskripsi;
    private String kategori;
    private double nominal;
    private String tanggal;

    public ModelTransaksi(String id, String deskripsi, String kategori, double nominal, String tanggal) {
        this.id = id;
        this.deskripsi = deskripsi;
        this.kategori = kategori;
        this.nominal = nominal;
        this.tanggal = tanggal;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getDeskripsi() { return deskripsi; }
    public void setDeskripsi(String deskripsi) { this.deskripsi = deskripsi; }
    
    public String getKategori() { return kategori; }
    public void setKategori(String kategori) { this.kategori = kategori; }
    
    public double getNominal() { return nominal; }
    public void setNominal(double nominal) { this.nominal = nominal; }
    
    public String getTanggal() { return tanggal; }
    public void setTanggal(String tanggal) { this.tanggal = tanggal; }
}