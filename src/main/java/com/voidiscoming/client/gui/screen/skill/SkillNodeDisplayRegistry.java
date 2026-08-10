package com.voidiscoming.client.gui.screen.skill;

import java.util.ArrayList;
import java.util.List;

import com.voidiscoming.common.VoidIsComing;
import com.voidiscoming.common.mechanic.skill.ModSkills;

import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class SkillNodeDisplayRegistry {
    public static List<SkillNodeDisplay> skillNodes;

    private static int centerX;
    private static int centerY;

    public static void registerNodes(int x, int y) {
        centerX = x;
        centerY = y;

        skillNodes = new ArrayList<>();

        registerNode(ModSkills.HEAL_SPELL, 0, 0, "", "", VoidIsComing.id("textures/gui/skills/heal.png"), true);
    }

    private static void registerNode(
        Identifier skill,
        int x,
        int y,
        String titleTranslationKey,
        String descriptionTranslationKey,
        Identifier icon,
        boolean isEquipable
    ) {
        skillNodes.add(new SkillNodeDisplay(
            skill,
            x + centerX,
            y + centerY,
            Text.translatable(titleTranslationKey),
            Text.translatable(descriptionTranslationKey),
            icon,
            isEquipable
        ));
    }
}
