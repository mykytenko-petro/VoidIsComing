package com.voidiscoming.common.mechanic.stat;

import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.UUID;

import com.voidiscoming.common.VoidIsComing;
import com.voidiscoming.common.component.ModComponents;
import com.voidiscoming.common.mechanic.skill.ModSkills;

public class PlayerStatApplier {

    private static final UUID HEALTH_BONUS_UUID = UUID.fromString("f482d8a0-2b1c-4f81-a7b2-9388bf379b32");
    private static final UUID ARMOR_BONUS_UUID = UUID.fromString("e721a9c1-5d3e-4f1a-b892-123456789abc");
    private static final UUID SPEED_BONUS_UUID = UUID.fromString("a1b2c3d4-e5f6-4a5b-8c9d-0123456789ab");
    private static final UUID ATTACK_BONUS_UUID = UUID.fromString("b2c3d4e5-f6a7-4b8c-9d0e-123456789abc");

    public static void syncPlayerStats(ServerPlayerEntity player) {
        applyHealthBonus(player);
        applyArmorBonus(player);
        applySpeedBonus(player);
        applyAttackBonus(player);
        updateMana(player);
    }

    private static void applyHealthBonus(ServerPlayerEntity player) {
        var healthInstance = player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);
        if (healthInstance == null) return;

        healthInstance.removeModifier(HEALTH_BONUS_UUID);

        double targetMaxHealth = PlayerStats.MAX_HEALTH.getValue(player);
        
        double baseHealth = healthInstance.getBaseValue();
        double bonusValue = targetMaxHealth - baseHealth;

        if (player.getHealth() > (float) (baseHealth + bonusValue)) {
            player.setHealth((float) (baseHealth + bonusValue));
        }

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

    private static void updateMana(ServerPlayerEntity player) {
        var component = ModComponents.MANA.get(player);

        if (component.getMana() > PlayerStats.MAX_MANA.getValue(player)) {
            component.setMana((float) PlayerStats.MAX_MANA.getValue(player));
        }
    }

    private static void applyArmorBonus(ServerPlayerEntity player) {
        var armorInstance = player.getAttributeInstance(EntityAttributes.GENERIC_ARMOR);
        if (armorInstance == null) return;

        armorInstance.removeModifier(ARMOR_BONUS_UUID);
        
        double currentTotalWithoutModifier = armorInstance.getValue();
        double bonusValue = currentTotalWithoutModifier + PlayerStats.getArmorBonus(player);

        VoidIsComing.LOGGER.info("base: {} bonus: {}", currentTotalWithoutModifier, PlayerStats.getArmorBonus(player));

        if (bonusValue > 0) {
            EntityAttributeModifier modifier = new EntityAttributeModifier(
                ARMOR_BONUS_UUID,
                "VoidIsComing Armor Bonus",
                bonusValue,
                EntityAttributeModifier.Operation.ADDITION
            );
            armorInstance.addPersistentModifier(modifier);
        }
    }

    private static void applySpeedBonus(ServerPlayerEntity player) {
        var speedInstance = player.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED);
        if (speedInstance == null) return;

        speedInstance.removeModifier(SPEED_BONUS_UUID);

        double speedPercentage = PlayerStats.MOVEMENT_SPEED.getValue(player);

        if (speedPercentage > 0) {
            EntityAttributeModifier modifier = new EntityAttributeModifier(
                SPEED_BONUS_UUID,
                "VoidIsComing Speed Bonus",
                speedPercentage,
                EntityAttributeModifier.Operation.MULTIPLY_BASE
            );
            speedInstance.addPersistentModifier(modifier);
        }
    }

    private static void applyAttackBonus(ServerPlayerEntity player) {
        var attackInstance = player.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE);
        if (attackInstance == null) return;

        attackInstance.removeModifier(ATTACK_BONUS_UUID);

        var skills = ModComponents.SKILLS.get(player);
        var mainHandItem = player.getMainHandStack().getItem();

        boolean holdingSword = mainHandItem instanceof net.minecraft.item.SwordItem;
        boolean holdingBow = mainHandItem instanceof net.minecraft.item.BowItem;

        double bonusPercentage = 0.0;

        if (holdingSword && skills.hasUnlocked(ModSkills.SWORD_POWER)) {
            bonusPercentage = 0.10;
        } else if (holdingBow && skills.hasUnlocked(ModSkills.BOW_POWER)) {
            bonusPercentage = 0.15;
        }

        if (bonusPercentage > 0.0) {
            EntityAttributeModifier modifier = new EntityAttributeModifier(
                ATTACK_BONUS_UUID,
                "VoidIsComing Attack Bonus",
                bonusPercentage,
                EntityAttributeModifier.Operation.MULTIPLY_BASE
            );
            attackInstance.addPersistentModifier(modifier);
        }
    }

    public static void onSpawn(ServerPlayerEntity player) {
        syncPlayerStats(player);

        player.setHealth(player.getMaxHealth());
    }

    public static void registerEvents() {
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
                if ((player.getId() + player.getWorld().getTime()) % 10 != 0) continue;

                syncPlayerStats(player);
            }
        });

        ServerPlayerEvents.AFTER_RESPAWN.register((oldPlayer, newPlayer, alive) -> {
            onSpawn(newPlayer);
        });
    }
}