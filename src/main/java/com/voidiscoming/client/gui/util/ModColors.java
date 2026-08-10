package com.voidiscoming.client.gui.util;

public class ModColors {

    public static final int EMINENCE_90          = rgba(108, 48, 130, 0.9);
    public static final int ATTRACTIVE_PURPLE_90 = rgba(71, 25, 103, 0.9);

    public static final int NORD_DARK_SLATE      = 0xFF2E3440;
    public static final int NORD_LIGHT_SLATE     = 0xFF4C566A;
    public static final int DARK_VOID_BLACK      = 0xFF191C24;

    public static final int LIME_GREEN           = 0xFF00FF00;
    public static final int SOFT_GREEN_GLOW      = 0x5500FF00;
    public static final int HOVER_WHITE          = 0x33FFFFFF;

    public static final int NODE_FRAME_UNLOCKED  = NORD_DARK_SLATE;
    public static final int NODE_FRAME_AVAILABLE = NORD_LIGHT_SLATE;
    public static final int NODE_FRAME_LOCKED    = DARK_VOID_BLACK;

    public static final int NODE_EQUIPPED_GLOW   = SOFT_GREEN_GLOW;
    public static final int NODE_EQUIPPED_BORDER = LIME_GREEN;
    public static final int NODE_HOVER_TINT      = HOVER_WHITE;

    public static int rgba(int red, int green, int blue, double alpha) {
        return ((int)(alpha * 255) << 24) | (red << 16) | (green << 8) | blue;
    }
}