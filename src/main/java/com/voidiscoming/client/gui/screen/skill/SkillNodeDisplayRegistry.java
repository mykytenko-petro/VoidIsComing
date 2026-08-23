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
        register(new SkillNodeDisplay(ModSkills.ARMOR_BONUS, -55, -25, "armor_bonus", VoidIsComing.id("textures/gui/skills/warrior_class.png")));
        register(new SkillNodeDisplay(ModSkills.ARMOR_BONUS_2, -100, -10, "armor_bonus_2", VoidIsComing.id("textures/gui/skills/warrior_class.png")));
        register(new SkillNodeDisplay(ModSkills.STONE_BASTION, -130, -60, "stone_bastion", VoidIsComing.id("textures/gui/skills/warrior_class.png")));
        register(new SkillNodeDisplay(ModSkills.RESILIENCE, -160, -100, "resilience", VoidIsComing.id("textures/gui/skills/warrior_class.png")));
        register(new SkillNodeDisplay(ModSkills.SWORD_POWER, 55, -25, "sword_power", VoidIsComing.id("textures/gui/skills/warrior_class.png")));
        register(new SkillNodeDisplay(ModSkills.RAGE_SPELL, 50, -50, "rage_spell", VoidIsComing.id("textures/gui/skills/rage.png")));
        register(new SkillNodeDisplay(ModSkills.VAMPIRISM_SPELL, 0, -100, "vampirism_spell", VoidIsComing.id("textures/gui/skills/vampirism.png")));

        register(new SkillNodeDisplay(ModSkills.ARCHER_CLASS, 37, 42, "archer_class", VoidIsComing.id("textures/gui/skills/archer_class.png")));
        register(new SkillNodeDisplay(ModSkills.HEALTH_BONUS, 10, 80, "health_bonus", VoidIsComing.id("textures/gui/skills/archer_class.png")));
        register(new SkillNodeDisplay(ModSkills.HEALTH_BONUS_2, 5, 125, "health_bonus_2", VoidIsComing.id("textures/gui/skills/archer_class.png")));
        register(new SkillNodeDisplay(ModSkills.WILD_BLOOM, 20, 165, "wild_bloom", VoidIsComing.id("textures/gui/skills/archer_class.png")));
        register(new SkillNodeDisplay(ModSkills.SWIFTSTEP, 30, 205, "swiftstep", VoidIsComing.id("textures/gui/skills/archer_class.png")));
        register(new SkillNodeDisplay(ModSkills.BOW_POWER, 80, 75, "bow_power", VoidIsComing.id("textures/gui/skills/archer_class.png")));
        register(new SkillNodeDisplay(ModSkills.INVISIBILITY_SPELL, 80, 38, "invisibility_spell", VoidIsComing.id("textures/gui/skills/invisibility.png")));
        register(new SkillNodeDisplay(ModSkills.HUNTER_SENSE_SPELL, 77, 77, "hunter_sense_spell", VoidIsComing.id("textures/gui/skills/hunter_sense.png")));

        register(new SkillNodeDisplay(ModSkills.MAGE_CLASS, -37, 42, "mage_class", VoidIsComing.id("textures/gui/skills/mage_class.png")));
        register(new SkillNodeDisplay(ModSkills.MANA_BONUS, -80, 80, "mana_bonus", VoidIsComing.id("textures/gui/skills/mana.png")));
        register(new SkillNodeDisplay(ModSkills.MANA_BONUS_2, -125, 115, "mana_bonus_2", VoidIsComing.id("textures/gui/skills/mana.png")));
        register(new SkillNodeDisplay(ModSkills.ABYSSAL_RESERVOIR, -150, 155, "abyssal_reservoir", VoidIsComing.id("textures/gui/skills/mana.png")));
        register(new SkillNodeDisplay(ModSkills.AFFINITY, -180, 195, "affinity", VoidIsComing.id("textures/gui/skills/mana.png")));
        register(new SkillNodeDisplay(ModSkills.WAND_POWER, -105, 30, "wand_power", VoidIsComing.id("textures/gui/skills/mage_class.png")));
        register(new SkillNodeDisplay(ModSkills.FROST_AURA_SPELL, -70, 25, "frost_aura_spell", VoidIsComing.id("textures/gui/skills/frost_aura.png")));
        register(new SkillNodeDisplay(ModSkills.TELEPORTATION_SPELL, -30, 80, "teleportation_spell", VoidIsComing.id("textures/gui/skills/teleportation.png")));
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