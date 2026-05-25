package com.savio.model;

public class User {
    private String nama;
    private String password;
    private double saldoAktif;

    public User(String nama, String password, double saldoAwal) {
        this.nama = nama;
        this.password = password;
        this.saldoAktif = saldoAwal;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        if (nama != null && !nama.trim().isEmpty()) {
            this.nama = nama;
        }
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if (password != null && password.length() >= 6) {
            this.password = password;
        }
    }

    public double getSaldoAktif() {
        return saldoAktif;
    }

    public void sesuaikanSaldo(double jumlah) {
        this.saldoAktif += jumlah;
    }
}