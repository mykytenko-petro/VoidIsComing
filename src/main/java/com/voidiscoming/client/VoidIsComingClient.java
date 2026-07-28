package com.voidiscoming.client;

import com.voidiscoming.client.gui.ModGUI;
import com.voidiscoming.client.renderer.ModRenderers;
import com.voidiscoming.common.mechanic.level.ArmorDescriptionRequirements;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class VoidIsComingClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ModRenderers.registerRenderers();
        ModGUI.init();
        ArmorDescriptionRequirements.register();
    }
} 