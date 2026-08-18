package com.voidiscoming.client;

import com.voidiscoming.client.blockcolor.ModBlockColors;
import com.voidiscoming.client.event.ClientModEvents;
import com.voidiscoming.client.gui.ModGUI;
import com.voidiscoming.client.keybind.ModKeyBindings;
import com.voidiscoming.client.renderer.ModRenderers;

import net.fabricmc.api.ClientModInitializer;

public class VoidIsComingClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ModRenderers.registerRenderers();
        ModBlockColors.register();
        ModKeyBindings.registerBindings();
        ClientModEvents.registerEvents();
        ModGUI.init();
    }
}