package com.voidiscoming.common.mechanic;

import com.voidiscoming.common.mechanic.level.Armor;
import com.voidiscoming.common.mechanic.level.Buff;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.network.ServerPlayerEntity;

public class ModMechanics {
    public static void init() {
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
                Armor.enforceArmorRestrictions(player);
                Buff.applyStatBonuses(player, player.experienceLevel);
            }
        });
    }
}