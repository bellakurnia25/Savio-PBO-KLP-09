package com.savio.dao;

import com.savio.models.KomponenKeuangan;
import java.util.ArrayList;
import java.util.List;

public class TransaksiRepository {
    // Polimorfisme Koleksi: Menyimpan objek Income dan Outcome dalam satu List yang sama
    private final List<KomponenKeuangan> daftarTransaksi = new ArrayList<>();

    public void tambahTransaksi(KomponenKeuangan transaksi) {
        daftarTransaksi.add(transaksi);
    }

    public void hapusTransaksi(KomponenKeuangan transaksi) {
        daftarTransaksi.remove(transaksi);
    }

    /**
     * Menggunakan POLIMORFISME RUNTIME.
     * Sistem otomatis memanggil hitungNilaiBersih() milik Income (+Rp) atau Outcome (-Rp)
     */
    public double hitungTotalSaldoAktif() {
        double total = 0;
        for (KomponenKeuangan k : daftarTransaksi) {
            total += k.hitungNilaiBersih();
        }
        return total;
    }

    public List<KomponenKeuangan> getDaftarTransaksi() {
        return daftarTransaksi;
    }
}
