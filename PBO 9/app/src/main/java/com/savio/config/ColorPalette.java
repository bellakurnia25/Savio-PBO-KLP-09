package com.savio.config;

/**
 * Kelas konstanta untuk menyimpan palet warna premium SAVIO.
 * Diperbarui dengan sentuhan Deep Velvet Midnight dan Soft Neon Accent.
 */
public class ColorPalette {
    
    // Latar Belakang Utama (Deep Velvet Midnight - Gelap, Mewah, Tidak bikin mata lelah)
    public static final String BG_PRIMARY = "#0B0426";      
    
    // Latar Belakang Sidebar (Lebih solid dan gelap untuk ilusi kedalaman)
    public static final String BG_SIDEBAR = "#060218";      
    
    // Kontainer / Card Komponen Internal (Ungu Gelap Mewah dengan kesan transparan)
    public static final String BG_CARD = "#140B36";         
    
    // Aksen Kebutuhan / Saldo Aktif (Electric Soft Blue - Teduh tapi menyala)
    public static final String ACCENT_KEBUTUHAN = "#6EA5F7"; 
    
    // Aksen Keinginan / Outcome (Vibrant Orchid Magenta - Mewah dan tegas)
    public static final String ACCENT_KEINGINAN = "#D41A8B";  
    
    // Aksen Dana Darurat / Siaga (Warm Sunset Orange - Kontras tinggi yang elegan)
    public static final String ACCENT_DARURAT = "#E67E22";   
    
    // Warna Teks Utama (Putih Susu - Lebih ramah di mata daripada putih pekat)
    public static final String TEXT_LIGHT = "#F5F6FA";
    
    // Warna Teks Sekunder / Keterangan (Lavender Muted - Ungu abu-abu yang estetik)
    public static final String TEXT_MUTED = "#7D74A6";
    
    // Warna Bahaya / Tombol Keluar (Rose Red Crimson - Merah mahal, bukan merah terang jenuh)
    public static final String TEXT_DANGER = "#E74C3C";

    /**
     * Helper untuk mengubah string Hex menjadi objek Color bawaan JavaFX
     */
    public static javafx.scene.paint.Color hexToColor(String hex) {
        return javafx.scene.paint.Color.web(hex);
    }
}