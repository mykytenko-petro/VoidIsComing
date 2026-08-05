package com.voidiscoming.client.gui;

import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;

import com.voidiscoming.client.gui.overlay.InventoryStatOverlay;
import com.voidiscoming.client.gui.overlay.ManaHudOverlay;
import com.voidiscoming.client.gui.overlay.SpellHotbarHud;

import net.fabricmc.api.EnvType;

@Environment(EnvType.CLIENT)
public class ModGUI {
    public static void init() {
        InventoryStatOverlay.init();
        ManaHudOverlay.init();
        HudRenderCallback.EVENT.register(new SpellHotbarHud());
    }
}
