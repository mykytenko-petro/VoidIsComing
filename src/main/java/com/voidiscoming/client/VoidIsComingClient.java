package com.voidiscoming.client;

import com.voidiscoming.client.description.ModDescriptions;
import com.voidiscoming.client.gui.ModGUI;

import net.fabricmc.api.ClientModInitializer;

public class VoidIsComingClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ModKeyBinds.register();
        ClientModEvents.register();
        ModGUI.init();
        ModDescriptions.init();
    }
}