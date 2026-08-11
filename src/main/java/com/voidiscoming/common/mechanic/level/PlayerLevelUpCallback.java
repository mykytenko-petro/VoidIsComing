package com.voidiscoming.common.mechanic.level;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.server.network.ServerPlayerEntity;

public interface PlayerLevelUpCallback {
    Event<PlayerLevelUpCallback> EVENT = EventFactory.createArrayBacked(PlayerLevelUpCallback.class,
        listeners -> (player, oldLevel, newLevel) -> {
            for (PlayerLevelUpCallback listener : listeners) {
                listener.onLevelUp(player, oldLevel, newLevel);
            }
        }
    );

    void onLevelUp(ServerPlayerEntity player, int oldLevel, int newLevel);
}