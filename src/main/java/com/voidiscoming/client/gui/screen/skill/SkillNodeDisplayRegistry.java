package com.voidiscoming.client.gui.screen.skill;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.voidiscoming.client.gui.screen.skill.widget.SkillNodeDisplay;
import com.voidiscoming.common.VoidIsComing;
import com.voidiscoming.common.mechanic.skill.ModSkills;

import net.minecraft.util.Identifier;

public class SkillNodeDisplayRegistry {
    public static Map<Identifier, SkillNodeDisplay> skillNodes = new HashMap<>();

    public static void registerNodes() {
        register(new SkillNodeDisplay(ModSkills.HEAL_SPELL, 0, 0, "heal_spell", VoidIsComing.id("textures/gui/skills/heal.png")));

        register(new SkillNodeDisplay(ModSkills.WARRIOR_CLASS, 0, -50, "warrior_class", VoidIsComing.id("textures/gui/skills/warrior_class.png")));
        register(new SkillNodeDisplay(ModSkills.ARMOR_BONUS, 5, -100, "armor_bonus", VoidIsComing.id("textures/gui/skills/armor.png")));
        register(new SkillNodeDisplay(ModSkills.ARMOR_BONUS_2, 10, -150, "armor_bonus_2", VoidIsComing.id("textures/gui/skills/armor.png")));
        register(new SkillNodeDisplay(ModSkills.STONE_BASTION, -10, -180, "stone_bastion", VoidIsComing.id("textures/gui/skills/stone_bastion.png")));
        register(new SkillNodeDisplay(ModSkills.RESILIENCE, -40, -100, "resilience", VoidIsComing.id("textures/gui/skills/resilience.png")));
        register(new SkillNodeDisplay(ModSkills.SWORD_POWER, 55, -75, "sword_power", VoidIsComing.id("textures/gui/skills/warrior_class.png")));
        register(new SkillNodeDisplay(ModSkills.RAGE_SPELL, 40, -10, "rage_spell", VoidIsComing.id("textures/gui/skills/rage.png")));
        register(new SkillNodeDisplay(ModSkills.VAMPIRISM_SPELL, 80, -40, "vampirism_spell", VoidIsComing.id("textures/gui/skills/vampirism.png")));

        register(new SkillNodeDisplay(ModSkills.ARCHER_CLASS, 37, 42, "archer_class", VoidIsComing.id("textures/gui/skills/archer_class.png")));
        register(new SkillNodeDisplay(ModSkills.HEALTH_BONUS, 60, 80, "health_bonus", VoidIsComing.id("textures/gui/skills/heart.png")));
        register(new SkillNodeDisplay(ModSkills.HEALTH_BONUS_2, 70, 125, "health_bonus_2", VoidIsComing.id("textures/gui/skills/heart.png")));
        register(new SkillNodeDisplay(ModSkills.WILD_BLOOM, 100, 165, "wild_bloom", VoidIsComing.id("textures/gui/skills/wild_bloom.png")));
        register(new SkillNodeDisplay(ModSkills.SWIFTSTEP, 100, 70, "swiftstep", VoidIsComing.id("textures/gui/skills/speed.png")));
        register(new SkillNodeDisplay(ModSkills.BOW_POWER, 10, 100, "bow_power", VoidIsComing.id("textures/gui/skills/archer_class.png")));
        register(new SkillNodeDisplay(ModSkills.HUNTER_SENSE_SPELL, 0, 50, "hunter_sense_spell", VoidIsComing.id("textures/gui/skills/hunter_sense.png")));
        register(new SkillNodeDisplay(ModSkills.INVISIBILITY_SPELL, -10, 150, "invisibility_spell", VoidIsComing.id("textures/gui/skills/invisibility.png")));

        register(new SkillNodeDisplay(ModSkills.MAGE_CLASS, -37, 42, "mage_class", VoidIsComing.id("textures/gui/skills/mage_class.png")));
        register(new SkillNodeDisplay(ModSkills.MANA_BONUS, -80, 80, "mana_bonus", VoidIsComing.id("textures/gui/skills/mana.png")));
        register(new SkillNodeDisplay(ModSkills.MANA_BONUS_2, -110, 105, "mana_bonus_2", VoidIsComing.id("textures/gui/skills/mana.png")));
        register(new SkillNodeDisplay(ModSkills.ABYSSAL_RESERVOIR, -130, 135, "abyssal_reservoir", VoidIsComing.id("textures/gui/skills/abyssal_reservoir.png")));
        register(new SkillNodeDisplay(ModSkills.AFFINITY, -50, 100, "affinity", VoidIsComing.id("textures/gui/skills/affinity.png")));
        register(new SkillNodeDisplay(ModSkills.WAND_POWER, -105, 30, "wand_power", VoidIsComing.id("textures/gui/skills/mage_class.png")));
        register(new SkillNodeDisplay(ModSkills.FROST_AURA_SPELL, -40, -15, "frost_aura_spell", VoidIsComing.id("textures/gui/skills/frost_aura.png")));
        register(new SkillNodeDisplay(ModSkills.TELEPORTATION_SPELL, -120, -30, "teleportation_spell", VoidIsComing.id("textures/gui/skills/teleportation.png")));
    }

    private static void register(SkillNodeDisplay display) {
        skillNodes.put(display.skillId(), display);
    }

    public static SkillNodeDisplay get(Identifier id) {
        return skillNodes.get(id);
    }

    public static Collection<SkillNodeDisplay> getAll() {
        return skillNodes.values();
    }
}