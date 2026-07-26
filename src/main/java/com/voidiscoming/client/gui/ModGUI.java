package com.voidiscoming.client.gui;

import net.fabricmc.api.Environment;
import net.fabricmc.api.EnvType;

@Environment(EnvType.CLIENT)
public class ModGUI {
    public static void init() {
        ManaHudOverlay.init();
    }
}
