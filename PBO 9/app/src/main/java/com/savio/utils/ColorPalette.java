package com.savio.utils;

public class ColorPalette {
    public static final String BG_PRIMARY = "#0B0426";      
    public static final String BG_SIDEBAR = "#0A0818";      
    public static final String BG_CARD = "#140B36";         
    public static final String GRAD_BTN   = "linear-gradient(to right, #9B5CF6, #F72BB0)";
    public static final String ACCENT_KEBUTUHAN = "#0d7765"; 
    public static final String ACCENT_KEINGINAN = "#d36f14";  
    public static final String ACCENT_DARURAT = "#531864";   
    public static final String TEXT_LIGHT = "#F5F6FA";
    public static final String TEXT_MUTED = "#7D74A6";
    public static final String TEXT_DANGER = "#E74C3C";

    public static javafx.scene.paint.Color hexToColor(String hex) {
        return javafx.scene.paint.Color.web(hex);
    }
}