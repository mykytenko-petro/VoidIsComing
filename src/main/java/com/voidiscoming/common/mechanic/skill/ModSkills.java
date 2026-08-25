package com.voidiscoming.common.mechanic.skill;

import java.util.HashMap;
import java.util.Map;

import com.voidiscoming.common.VoidIsComing;
import com.voidiscoming.common.component.ModComponents;
import com.voidiscoming.common.mechanic.level.PlayerLevelUpCallback;
import com.voidiscoming.common.mechanic.spell.ModSpells;

import net.minecraft.util.Identifier;

public class ModSkills {
    private static final Map<Identifier, SkillNode> SKILLS = new HashMap<>();

    // root spells
    public static final Identifier HEAL_SPELL = VoidIsComing.id("heal_spell_skill");
    public static final Identifier CONCENTRATION_SPELL = VoidIsComing.id("concentration_spell_skill");
    
    // warrior
    public static final Identifier WARRIOR_CLASS = VoidIsComing.id("warrior_class_skill");
    public static final Identifier ARMOR_BONUS = VoidIsComing.id("armor_bonus_skill");
    public static final Identifier ARMOR_BONUS_2 = VoidIsComing.id("armor_bonus_2_skill");
    public static final Identifier STONE_BASTION = VoidIsComing.id("stone_bastion_skill");
    public static final Identifier RESILIENCE = VoidIsComing.id("resilience_skill");
    public static final Identifier SWORD_POWER = VoidIsComing.id("sword_power_skill");
    public static final Identifier RAGE_SPELL = VoidIsComing.id("rage_spell_skill");
    public static final Identifier VAMPIRISM_SPELL = VoidIsComing.id("vampirism_spell_skill");

    // archer
    public static final Identifier ARCHER_CLASS = VoidIsComing.id("archer_class_skill");
    public static final Identifier HEALTH_BONUS = VoidIsComing.id("health_bonus_skill");
    public static final Identifier HEALTH_BONUS_2 = VoidIsComing.id("health_bonus_2_skill");
    public static final Identifier WILD_BLOOM = VoidIsComing.id("wild_bloom_skill");
    public static final Identifier SWIFTSTEP = VoidIsComing.id("swiftstep_skill");
    public static final Identifier BOW_POWER = VoidIsComing.id("bow_power_skill");
    public static final Identifier HUNTER_SENSE_SPELL = VoidIsComing.id("hunter_sense_spell_skill");
    public static final Identifier INVISIBILITY_SPELL = VoidIsComing.id("invisibility_spell_skill");

    // mage
    public static final Identifier MAGE_CLASS = VoidIsComing.id("mage_class_skill");
    public static final Identifier MANA_BONUS = VoidIsComing.id("mana_bonus_skill");
    public static final Identifier MANA_BONUS_2 = VoidIsComing.id("mana_bonus_2_skill");
    public static final Identifier ABYSSAL_RESERVOIR = VoidIsComing.id("abyssal_reservoir_skill");
    public static final Identifier AFFINITY = VoidIsComing.id("affinity_skill");
    public static final Identifier WAND_POWER = VoidIsComing.id("wand_power_skill");
    public static final Identifier FROST_AURA_SPELL = VoidIsComing.id("frost_aura_spell_skill");
    public static final Identifier TELEPORTATION_SPELL = VoidIsComing.id("teleportation_spell_skill");
    public static final Identifier LAST_STAND_SPELL = VoidIsComing.id("last_stand_spell_skill");
    public static final Identifier MANA_BOOST_SPELL = VoidIsComing.id("mana_boost_spell_skill");
    public static final Identifier PURIFICATION_SPELL = VoidIsComing.id("purification_spell_skill");

    public static void registerSkills() {
        registerSkill(new SkillNode(HEAL_SPELL, 1, ModSpells.HEAL));
        registerSkill(new SkillNode(CONCENTRATION_SPELL, 2, ModSpells.CONCENTRATION));

        registerSkill(new SkillNode(WARRIOR_CLASS, HEAL_SPELL, 4, new Identifier[] {ARCHER_CLASS, MAGE_CLASS}));
        registerSkill(new SkillNode(ARMOR_BONUS, WARRIOR_CLASS, 1));
        registerSkill(new SkillNode(ARMOR_BONUS_2, ARMOR_BONUS, 1));
        registerSkill(new SkillNode(STONE_BASTION, ARMOR_BONUS_2, 1));
        registerSkill(new SkillNode(RESILIENCE, ARMOR_BONUS, 1));
        registerSkill(new SkillNode(SWORD_POWER, ARMOR_BONUS_2, 1));
        registerSkill(new SkillNode(RAGE_SPELL, WARRIOR_CLASS, 3, ModSpells.RAGE));
        registerSkill(new SkillNode(VAMPIRISM_SPELL, SWORD_POWER, 3, ModSpells.VAMPIRISM));
        registerSkill(new SkillNode(LAST_STAND_SPELL, ARMOR_BONUS_2, 3, ModSpells.LAST_STAND));

        registerSkill(new SkillNode(ARCHER_CLASS, HEAL_SPELL, 4, new Identifier[] {WARRIOR_CLASS, MAGE_CLASS}));
        registerSkill(new SkillNode(HEALTH_BONUS, ARCHER_CLASS, 1));
        registerSkill(new SkillNode(HEALTH_BONUS_2, HEALTH_BONUS, 1));
        registerSkill(new SkillNode(WILD_BLOOM, HEALTH_BONUS_2, 1));
        registerSkill(new SkillNode(SWIFTSTEP, HEALTH_BONUS, 1));
        registerSkill(new SkillNode(BOW_POWER, HEALTH_BONUS_2, 1));
        registerSkill(new SkillNode(HUNTER_SENSE_SPELL, ARCHER_CLASS, 3, ModSpells.HUNTER_SENSE));
        registerSkill(new SkillNode(INVISIBILITY_SPELL, BOW_POWER, 3, ModSpells.INVISIBILITY));

        registerSkill(new SkillNode(MAGE_CLASS, HEAL_SPELL, 4, new Identifier[] {ARCHER_CLASS, WARRIOR_CLASS}));
        registerSkill(new SkillNode(MANA_BONUS, MAGE_CLASS, 1));
        registerSkill(new SkillNode(MANA_BONUS_2, MANA_BONUS, 1));
        registerSkill(new SkillNode(ABYSSAL_RESERVOIR, MANA_BONUS_2, 1));
        registerSkill(new SkillNode(AFFINITY, MANA_BONUS, 1));
        registerSkill(new SkillNode(WAND_POWER, MANA_BONUS_2, 1));
        registerSkill(new SkillNode(FROST_AURA_SPELL, MAGE_CLASS, 3, ModSpells.FROST_AURA));
        registerSkill(new SkillNode(TELEPORTATION_SPELL, WAND_POWER, 3, ModSpells.TELEPORT));
        registerSkill(new SkillNode(MANA_BOOST_SPELL, MANA_BONUS, 3, ModSpells.MANA_BOOST));
        registerSkill(new SkillNode(PURIFICATION_SPELL, WAND_POWER, 3, ModSpells.PURIFICATION));
    }

    private static void registerSkill(SkillNode node) {
        SKILLS.put(node.id(), node);
    }

    public static SkillNode get(Identifier id) {
        return SKILLS.get(id);
    }

    public static void registerEvents() {
        PlayerLevelUpCallback.EVENT.register((player, oldLevel, newLevel) -> {
            int levelsGained = newLevel - oldLevel;
            
            ModComponents.SKILLS.maybeGet(player).ifPresent(skills -> {
                skills.addSkillPoints(levelsGained);
            });
        });
    }
}