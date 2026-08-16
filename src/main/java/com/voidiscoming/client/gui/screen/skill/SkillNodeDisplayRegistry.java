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
        register(new SkillNodeDisplay(ModSkills.ARCHER_CLASS, 37, 42, "archer_class", VoidIsComing.id("textures/gui/skills/archer_class.png")));
        register(new SkillNodeDisplay(ModSkills.MAGE_CLASS, -37, 42, "mage_class", VoidIsComing.id("textures/gui/skills/mage_class.png")));
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