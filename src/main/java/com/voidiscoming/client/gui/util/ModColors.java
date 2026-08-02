package com.voidiscoming.client.gui.util;

public class ModColors {

    public static final int Eminence90 = rgba(108, 48, 130, 0.9);
    public static final int AttractivePurple90 = rgba(71, 25, 103, 0.9);

    public static int rgba(int red, int green, int blue, double alpha) {
        return ((int)(alpha * 100) << 24) | (red << 16) | (green << 8) | blue;
    }
}