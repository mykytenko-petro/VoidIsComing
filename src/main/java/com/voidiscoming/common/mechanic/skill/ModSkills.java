package com.voidiscoming.common.mechanic.skill;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.voidiscoming.common.VoidIsComing;
import com.voidiscoming.common.component.ModComponents;
import com.voidiscoming.common.mechanic.level.PlayerLevelUpCallback;
import com.voidiscoming.common.mechanic.level.PlayerLevelManager;
import net.minecraft.util.Identifier;

public class ModSkills {
    private static final Map<Identifier, SkillNode> SKILLS = new HashMap<>();

    public static final Identifier HEAL_SKILL = VoidIsComing.id("heal_skill");

    public static void registerSkills() {
        registerSkill(new SkillNode(
            HEAL_SKILL, 
            Optional.empty(), 
            1, 
            SkillType.SPELL, 
            Optional.of(VoidIsComing.id("heal_spell"))
        ));

        PlayerLevelUpCallback.EVENT.register((player, oldLevel, newLevel) -> {
            int levelsGained = newLevel - oldLevel;
            
            ModComponents.SKILLS.maybeGet(player).ifPresent(skills -> {
                skills.addSkillPoints(levelsGained);
            });
        });

        PlayerLevelManager.init();
    }

    private static void registerSkill(SkillNode node) {
        SKILLS.put(node.id(), node);
    }

    public static SkillNode get(Identifier id) {
        return SKILLS.get(id);
    }

    public static Collection<SkillNode> getAll() {
        return SKILLS.values();
    }
}