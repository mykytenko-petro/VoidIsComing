package com.voidiscoming.client.event;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

public class ClientModEvents {
    public static void registerEvents() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null) return;

            SkillHandler.handle(client);
            SpellHandler.handle();
        });
    }
}