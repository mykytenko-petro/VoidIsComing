package com.voidiscoming.client;

import com.voidiscoming.client.gui.SpellHotbarHud;
import com.voidiscoming.client.gui.overlay.ManaHudOverlay;
import com.voidiscoming.common.mechanic.spell.ClientMechanics;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;

public class VoidIsComingClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
    
        ModKeyBinds.register();

        ClientMechanics.register();

        ManaHudOverlay.init();

        HudRenderCallback.EVENT.register(new SpellHotbarHud());
    }
}