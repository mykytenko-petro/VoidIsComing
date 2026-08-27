package com.voidiscoming.common.mechanic.stat;

import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;

import java.util.function.ToDoubleFunction;

import com.voidiscoming.common.VoidIsComing;
import com.voidiscoming.common.component.ModComponents;
import com.voidiscoming.common.mechanic.skill.ModSkills;

public enum PlayerStats {
    MAX_HEALTH("stat.voidiscoming.max_health", player -> {
        double base = 20.0;
        var skills = ModComponents.SKILLS.get(player);
        
        double perLevelBonus = 
            skills.hasUnlocked(ModSkills.ARCHER_CLASS)
            ? (player.experienceLevel / 3)
            : 0;
        
        double health_bonus = 
            skills.hasUnlocked(ModSkills.HEALTH_BONUS)
            ? 1
            : 0;
        
        double health_bonus_2 = 
            skills.hasUnlocked(ModSkills.HEALTH_BONUS_2)
            ? 1
            : 0;
            
        double subtotal = base + perLevelBonus + health_bonus + health_bonus_2;
        
        double health_bonus_3 = 
            skills.hasUnlocked(ModSkills.WILD_BLOOM)
            ? subtotal * 0.10
            : 0;
           
        return (int) (subtotal + health_bonus_3);
    }),

    MAX_MANA("stat.voidiscoming.max_mana", player -> {
        double base = 20.0;
        var skills = ModComponents.SKILLS.get(player);
        
        double perLevelBonus = 
            skills.hasUnlocked(ModSkills.MAGE_CLASS)
            ? (player.experienceLevel / 3)
            : 0;
            
        double mana_bonus = 
            skills.hasUnlocked(ModSkills.MANA_BONUS)
            ? 1
            : 0;
            
        double mana_bonus_2 = 
            skills.hasUnlocked(ModSkills.MANA_BONUS_2)
            ? 1
            : 0;
            
        double subtotal = base + perLevelBonus + mana_bonus + mana_bonus_2;
        
        double mana_bonus_3 = 
            skills.hasUnlocked(ModSkills.ABYSSAL_RESERVOIR)
            ? subtotal * 0.10
            : 0;
            
        return (int) (subtotal + mana_bonus_3);
    }),

    ARMOR("stat.voidiscoming.armor", player -> {
        return getAttribute(player, EntityAttributes.GENERIC_ARMOR);
    }),
    
    ATTACK_DAMAGE("stat.voidiscoming.attack_damage", player -> getAttribute(player, EntityAttributes.GENERIC_ATTACK_DAMAGE)),

    MOVEMENT_SPEED("stat.voidiscoming.speed", player -> {
        var skills = ModComponents.SKILLS.get(player);

        return skills.hasUnlocked(ModSkills.SPEED_BONUS)
            ? 0.10
            : 0.0;
    }),
    
    AFFINITY("stat.voidiscoming.affinity", player -> {
        var skills = ModComponents.SKILLS.get(player);
        double base = 0.0;

        double bonus = skills.hasUnlocked(ModSkills.AFFINITY_BONUS)
            ? 10.0
            : 0;

        return base + bonus;
    }),
    
    RESILIENCE("stat.voidiscoming.resilience", player -> {
        var skills = ModComponents.SKILLS.get(player);
        double base = 0.0;

        double bonus = skills.hasUnlocked(ModSkills.RESILIENCE_BONUS)
            ? 10.0
            : 0;

        return base + bonus;
    }),;

    public static double getArmorBonus(PlayerEntity player) {
        if (player == null) return 0.0;

        var skills = ModComponents.SKILLS.get(player);

        double perLevelBonus = 
            skills.hasUnlocked(ModSkills.WARRIOR_CLASS)
            ? (player.experienceLevel / 3)
            : 0.0;

        double armor_bonus = 
            skills.hasUnlocked(ModSkills.ARMOR_BONUS)
            ? 1.0
            : 0.0;

        double armor_bonus_2 = 
            skills.hasUnlocked(ModSkills.ARMOR_BONUS_2)
            ? 1.0
            : 0.0;

        double subtotal = perLevelBonus + armor_bonus + armor_bonus_2;

        double armor_bonus_3 = 
            skills.hasUnlocked(ModSkills.STONE_BASTION)
            ? subtotal * 0.10
            : 0.0;

        return (int) (subtotal + armor_bonus_3);
    }

    public static double getSpellCostReduction(PlayerEntity entity, double stat) {
        if (AFFINITY.getValue(entity) != 0) {
            return stat * (1 - (AFFINITY.getValue(entity) / 100.0));
        } else {
            return stat;
        }
    }
    
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