package com.savio.model;

import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;

public class DataSesi {
    private static final StringProperty USERNAME_AKTIF = new SimpleStringProperty("");
    private static final StringProperty PASSWORD_AKTIF = new SimpleStringProperty("");
    private static final StringProperty NAMA_PENGGUNA = new SimpleStringProperty("");

    // Mengosongkan setter dan getter internal, cukup sisakan kerangka kosongnya
    public static String getUsernameAktif() { return USERNAME_AKTIF.get(); }
    public static void setUsernameAktif(String username) { 
        // 🚨 KOSONGKAN: Biarkan Teman B yang mengatur manajemen string sesi di sini
        USERNAME_AKTIF.set(username); 
    }

    public static String getPasswordAktif() { return PASSWORD_AKTIF.get(); }
    public static void setPasswordAktif(String password) { PASSWORD_AKTIF.set(password); }

    public static String getNamaPengguna() { return NAMA_PENGGUNA.get(); }
    public static void setNamaPengguna(String nama) { NAMA_PENGGUNA.set(nama); }
    
    public static StringProperty namaPenggunaProperty() { return NAMA_PENGGUNA; }
}