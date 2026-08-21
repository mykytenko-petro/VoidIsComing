package com.voidiscoming.common.mechanic.stat;

import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;

import java.util.function.ToDoubleFunction;

import com.voidiscoming.common.component.ModComponents;
import com.voidiscoming.common.mechanic.skill.ModSkills;

public enum PlayerStats {
    MAX_HEALTH("stat.voidiscoming.max_health", player -> {
        double base = 20;
        
        double perLevelBonus = 
            ModComponents.SKILLS.get(player).hasUnlocked(ModSkills.ARCHER_CLASS)
            ? (player.experienceLevel / 10) * 2.0
            : 0;

        double bonus = (player.experienceLevel / 10) * 2.0;

        return base + bonus;
    }),
    
    MAX_MANA("stat.voidiscoming.max_mana", player -> {
        double base = 20.0;

        double perLevelBonus = 
            ModComponents.SKILLS.get(player).hasUnlocked(ModSkills.MAGE_CLASS)
            ? (player.experienceLevel / 10) * 2.0
            : 0;

        double bonus = perLevelBonus;

        return base + bonus;
    }),
    
    ATTACK_DAMAGE("stat.voidiscoming.attack_damage", player -> getAttribute(player, EntityAttributes.GENERIC_ATTACK_DAMAGE)),
    MOVEMENT_SPEED("stat.voidiscoming.speed", player -> getAttribute(player, EntityAttributes.GENERIC_MOVEMENT_SPEED)),
    ARMOR("stat.voidiscoming.armor", player -> getAttribute(player, EntityAttributes.GENERIC_ARMOR));

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

    private static double getAttribute(PlayerEntity player, EntityAttribute attribute) {
        var instance = player.getAttributeInstance(attribute);
        return instance != null ? instance.getValue() : 0.0;
    }


}