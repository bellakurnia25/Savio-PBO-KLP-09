package com.savio.models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class DataSesi {
    private static String usernameAktif = "";
    private static String passwordAktif = "";
    
    // Properti koneksi nama pengguna
    private static final StringProperty namaPenggunaProperty = new SimpleStringProperty("SAVIO");

    public static String getUsernameAktif() {
        return usernameAktif;
    }

    public static void setUsernameAktif(String username) {
        usernameAktif = username;
        // Saat pertama kali login, set nama default diambil dari bagian depan email
        if (username != null && username.contains("@")) {
            setNamaPengguna(username.split("@")[0].toUpperCase());
        } else if (username != null) {
            setNamaPengguna(username.toUpperCase());
        }
    }

    public static String getPasswordAktif() {
        return passwordAktif;
    }

    public static void setPasswordAktif(String password) {
        passwordAktif = password;
    }

    // Getter untuk menyinkronkan nama di profil dan dashboard
    public static StringProperty namaPenggunaProperty() {
        return namaPenggunaProperty;
    }

    public static String getNamaPengguna() {
        return namaPenggunaProperty.get();
    }

    public static void setNamaPengguna(String nama) {
        namaPenggunaProperty.set(nama);
    }
}
