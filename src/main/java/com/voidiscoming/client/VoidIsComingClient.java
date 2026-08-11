package com.voidiscoming.client;

import com.voidiscoming.client.event.ClientModEvents;
import com.voidiscoming.client.gui.ModGUI;
import com.voidiscoming.client.keybind.ModKeyBindings;
import com.voidiscoming.client.renderer.ModRenderers;

import net.fabricmc.api.ClientModInitializer;

public class VoidIsComingClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        System.out.println("Client initialized");

        ModRenderers.registerRenderers();
        ModKeyBindings.registerBindings();
        ClientModEvents.registerEvents();
        ModGUI.init();
    }
}
