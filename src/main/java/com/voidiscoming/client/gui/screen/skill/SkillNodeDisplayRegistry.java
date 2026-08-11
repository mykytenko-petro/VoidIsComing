package com.voidiscoming.client.gui.screen.skill;

import java.util.ArrayList;
import java.util.List;

import com.voidiscoming.common.VoidIsComing;
import com.voidiscoming.common.mechanic.skill.ModSkills;

public class SkillNodeDisplayRegistry {
    public static List<SkillNodeDisplay> skillNodes;

    public static void registerNodes() {
        skillNodes = new ArrayList<>();

        skillNodes.add(new SkillNodeDisplay(ModSkills.HEAL_SPELL, 0, 0, "heal_spell", VoidIsComing.id("textures/gui/skills/heal.png")));
    }
}
