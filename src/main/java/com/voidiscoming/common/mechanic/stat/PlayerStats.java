package com.voidiscoming.common.mechanic.stat;

import com.voidiscoming.common.component.ModComponents;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;

import java.util.function.ToDoubleFunction;

public enum PlayerStats {
    // bonuses
    MAX_HEALTH_BONUS("stat.voidiscoming.max_health_bonus", player -> (player.experienceLevel / 5) * 2.0),
    
    // stats
    // TODO: change vanila stats
    ATTACK_DAMAGE("stat.voidiscoming.attack_damage", player -> getAttribute(player, EntityAttributes.GENERIC_ATTACK_DAMAGE)),
    MOVEMENT_SPEED("stat.voidiscoming.speed", player -> getAttribute(player, EntityAttributes.GENERIC_MOVEMENT_SPEED)),
    ARMOR("stat.voidiscoming.armor", player -> getAttribute(player, EntityAttributes.GENERIC_ARMOR)),

    CURRENT_MANA("stat.voidiscoming.current_mana", player -> ModComponents.MANA.get(player).getMana()),
    MAX_MANA("stat.voidiscoming.max_mana", player -> 20.0 + (player.experienceLevel * 5.0)),
    MANA_REGEN("stat.voidiscoming.mana_regen", player -> 1.0 + (player.experienceLevel * 0.1)),
    
    MAX_HEALTH("stat.voidiscoming.max_health", player -> {
        double base = getAttribute(player, EntityAttributes.GENERIC_MAX_HEALTH);
        
        return base + MAX_HEALTH_BONUS.getValue(player);
    });

    private final String translationKey;
    private final ToDoubleFunction<PlayerEntity> valueGetter;

    PlayerStats(String translationKey, ToDoubleFunction<PlayerEntity> valueGetter) {
        this.translationKey = translationKey;
        this.valueGetter = valueGetter;
    }

    public double getValue(PlayerEntity player) {
        if (player == null) return 0.0;
        return this.valueGetter.applyAsDouble(player);
    }

    public String getTranslationKey() {
        return translationKey;
    }

    private static double getAttribute(PlayerEntity player, net.minecraft.entity.attribute.EntityAttribute attribute) {
        var instance = player.getAttributeInstance(attribute);
        return instance != null ? instance.getValue() : 0.0;
    }
}