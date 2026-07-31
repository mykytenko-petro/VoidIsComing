package com.voidiscoming.common.mechanic.stat;

import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.UUID;

import com.voidiscoming.common.VoidIsComing;

public class PlayerStatApplier {

    private static final UUID HEALTH_BONUS_UUID = UUID.fromString("f482d8a0-2b1c-4f81-a7b2-9388bf379b32");

    public static void syncPlayerStats(ServerPlayerEntity player) {
        applyHealthBonus(player);
    }

    private static void applyHealthBonus(ServerPlayerEntity player) {
        var healthInstance = player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);
        if (healthInstance == null) return;

        healthInstance.removeModifier(HEALTH_BONUS_UUID);

        double bonusValue = PlayerStats.MAX_HEALTH_BONUS.getValue(player);

        VoidIsComing.LOGGER.info("bonusValue: {}", bonusValue);

        if (bonusValue > 0) {
            EntityAttributeModifier modifier = new EntityAttributeModifier(
                HEALTH_BONUS_UUID,
                "VoidIsComing Health Bonus",
                bonusValue,
                EntityAttributeModifier.Operation.ADDITION
            );
            healthInstance.addPersistentModifier(modifier);
        }
    }

    public static void onSpawn(ServerPlayerEntity player) {
        syncPlayerStats(player);

        player.setHealth(player.getMaxHealth());
    }
}