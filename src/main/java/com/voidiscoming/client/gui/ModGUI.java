package com.voidiscoming.client.gui;

import net.fabricmc.api.Environment;

import com.voidiscoming.client.gui.overlay.InventoryStatOverlay;
import com.voidiscoming.client.gui.overlay.ManaHudOverlay;

import net.fabricmc.api.EnvType;

@Environment(EnvType.CLIENT)
public class ModGUI {
    public static void init() {
        InventoryStatOverlay.init();
        ManaHudOverlay.init();
    }
}
