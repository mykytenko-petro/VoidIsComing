package com.voidiscoming.common.mechanic.level;

import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.network.ServerPlayerEntity;

public class LevelMechanic {

    public static void registerEvents() {
        ServerPlayerEvents.COPY_FROM.register((oldPlayer, newPlayer, alive) -> {
            if (!alive) {
                newPlayer.experienceLevel = oldPlayer.experienceLevel;
                newPlayer.experienceProgress = oldPlayer.experienceProgress;
                newPlayer.totalExperience = oldPlayer.totalExperience;
            }
        });

        ServerTickEvents.END_SERVER_TICK.register(server -> {
            for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
                ArmorLevelRestriction.enforceArmorRestrictions(player);
            }
        });

        PlayerLevelManager.registerEvents();
    }
}
