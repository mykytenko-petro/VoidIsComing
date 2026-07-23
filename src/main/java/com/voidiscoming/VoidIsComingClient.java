package com.voidiscoming;

import net.fabricmc.api.ClientModInitializer;

public class VoidIsComingClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // Запускаем наш HUD хук!
        ManaHudOverlay.init();
    }
}