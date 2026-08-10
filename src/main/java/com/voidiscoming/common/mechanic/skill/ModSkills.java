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

    public static void register() {
        registerSkill(new SkillNode(HEAL_SPELL, Optional.empty(), 1));
    }

    private static void registerSkill(SkillNode node) {
        SKILLS.put(node.id(), node);
    }

    public static Optional<SkillNode> get(Identifier id) {
        return Optional.ofNullable(SKILLS.get(id));
    }

    public static Collection<SkillNode> getAll() {
        return SKILLS.values();
    }

    public static void registerEvents() {
        
    }
}