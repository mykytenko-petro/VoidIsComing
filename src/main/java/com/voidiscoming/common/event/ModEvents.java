package com.voidiscoming.common.event;

import com.voidiscoming.common.mechanic.stat.PlayerStatApplier;

import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;

public class ModEvents {
    public static void registerEvents() {
        // Join
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            PlayerStatApplier.syncPlayerStats(handler.getPlayer());
        });

        // Respawn
        ServerPlayerEvents.AFTER_RESPAWN.register((oldPlayer, newPlayer, alive) -> {
            PlayerStatApplier.onSpawn(newPlayer);
        });

        // On death
        ServerPlayerEvents.COPY_FROM.register((oldPlayer, newPlayer, alive) -> {
            if (!alive) {
                newPlayer.experienceLevel = oldPlayer.experienceLevel;
                newPlayer.experienceProgress = oldPlayer.experienceProgress;
                newPlayer.totalExperience = oldPlayer.totalExperience;
            }
        });
    }
}
