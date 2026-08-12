package com.voidiscoming.client.gui.screen.skill;

import java.util.ArrayList;
import java.util.List;

import com.voidiscoming.common.VoidIsComing;
import com.voidiscoming.common.mechanic.skill.ModSkills;

public class SkillNodeDisplayRegistry {
    public static List<SkillNodeDisplay> skillNodes = new ArrayList<>();

    public static void registerNodes() {
        skillNodes.add(new SkillNodeDisplay(ModSkills.HEAL_SPELL, 0, 0, "heal_spell", VoidIsComing.id("textures/gui/skills/heal.png")));

        skillNodes.add(new SkillNodeDisplay(ModSkills.WARRIOR_CLASS, 0, -50, "warrior_class", VoidIsComing.id("textures/gui/skills/warrior_class.png")));
        skillNodes.add(new SkillNodeDisplay(ModSkills.ARCHER_CLASS, 37, 42, "archer_class", VoidIsComing.id("textures/gui/skills/archer_class.png")));
        skillNodes.add(new SkillNodeDisplay(ModSkills.MAGE_CLASS, -37, 42, "mage_class", VoidIsComing.id("textures/gui/skills/mage_class.png")));
    }
}