package com.voidiscoming.common.mechanic.level;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerLevelManager {
    private static final Map<UUID, Integer> lastLevels = new HashMap<>();

    public static void registerEvents() {
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
                UUID uuid = player.getUuid();
                int currentLevel = player.experienceLevel;
                int oldLevel = lastLevels.getOrDefault(uuid, currentLevel);

                if (currentLevel > oldLevel) {
                    PlayerLevelUpCallback.EVENT.invoker().onLevelUp(player, oldLevel, currentLevel);
                }

                lastLevels.put(uuid, currentLevel);
            }
        });
    }
}