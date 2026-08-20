package com.voidiscoming.common.mechanic.stat;

import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.entity.attribute.ClampedEntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.UUID;

import com.voidiscoming.mixin.common.player.ClampedEntityAttributeAccessor;

public class PlayerStatApplier {

    private static final UUID HEALTH_BONUS_UUID = UUID.fromString("f482d8a0-2b1c-4f81-a7b2-9388bf379b32");

    public static void syncPlayerStats(ServerPlayerEntity player) {
        applyHealthBonus(player);
    }

    private static void applyHealthBonus(ServerPlayerEntity player) {
        var healthInstance = player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);
        if (healthInstance == null) return;

        healthInstance.removeModifier(HEALTH_BONUS_UUID);

        double targetMaxHealth = PlayerStats.MAX_HEALTH.getValue(player);
        
        double baseHealth = healthInstance.getBaseValue();
        double bonusValue = targetMaxHealth - baseHealth;

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

    public static void init() {
        if (EntityAttributes.GENERIC_ARMOR instanceof ClampedEntityAttribute clamped) {
            ((ClampedEntityAttributeAccessor) clamped).setMaxValue(2048.0);
        }
    }

    public static void registerEvents() {
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            syncPlayerStats(handler.getPlayer());
        });

        ServerPlayerEvents.AFTER_RESPAWN.register((oldPlayer, newPlayer, alive) -> {
            onSpawn(newPlayer);
        });
    }
}