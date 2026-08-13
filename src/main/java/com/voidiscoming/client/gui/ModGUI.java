package com.voidiscoming.client.gui;

import com.voidiscoming.client.gui.overlay.BarsOverlay;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;

import com.voidiscoming.client.gui.description.ModDescriptions;
import com.voidiscoming.client.gui.overlay.InventoryStatOverlay;
import com.voidiscoming.client.gui.overlay.SpellHotbarHud;
import com.voidiscoming.client.gui.screen.skill.SkillNodeDisplayRegistry;

public class ModGUI {
    public static void init() {
        InventoryStatOverlay.init();
        HudRenderCallback.EVENT.register(new SpellHotbarHud());
        ModDescriptions.init();
        SkillNodeDisplayRegistry.registerNodes();
        HudRenderCallback.EVENT.register(new BarsOverlay());
    }
}
