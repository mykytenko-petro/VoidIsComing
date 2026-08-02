package com.voidiscoming.common.mechanic;

import com.voidiscoming.common.mechanic.level.ArmorLevelRestriction;
import com.voidiscoming.common.mechanic.stat.PlayerStatApplier;

import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.server.network.ServerPlayerEntity;

public class ModMechanics {
    public static void registerMechanics() {
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

        // Every tick
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
                ArmorLevelRestriction.enforceArmorRestrictions(player);
            }
        });
    }
}