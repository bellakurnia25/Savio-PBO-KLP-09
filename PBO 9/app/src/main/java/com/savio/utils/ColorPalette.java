package com.savio.utils;

public class ColorPalette {
    public static final String BG_PRIMARY = "#0b0426";      
    public static final String BG_SIDEBAR = "#0A0818";      
    public static final String BG_CARD = "#140B36";          
    public static final String TEXT_MUTED = "#7D74A6";

    public static javafx.scene.paint.Color hexToColor(String hex) {
        return javafx.scene.paint.Color.web(hex);
    }
}