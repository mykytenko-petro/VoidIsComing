package com.voidiscoming.common.mechanic.skill;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.voidiscoming.common.VoidIsComing;

import net.minecraft.util.Identifier;

public class ModSkills {
    private static final Map<Identifier, SkillNode> SKILLS = new HashMap<>();

    public static final Identifier HEAL_SPELL = VoidIsComing.id("heal_spell");

    public static void registerSkills() {
        registerSkill(new SkillNode(HEAL_SPELL, Optional.empty(), 1, SkillType.SPELL));
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