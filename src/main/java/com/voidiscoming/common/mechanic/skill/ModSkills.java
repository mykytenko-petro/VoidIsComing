package com.voidiscoming.common.mechanic.skill;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import net.minecraft.util.Identifier;

public class ModSkills {
    private static final Map<Identifier, SkillNode> SKILLS = new HashMap<>();

    // Sample Skill Identifiers
    public static final Identifier MAGE_ROOT = new Identifier("voidiscoming", "mage_root");
    public static final Identifier MANA_BOOST_1 = new Identifier("voidiscoming", "mana_boost_1");

    public static void register() {
        registerSkill(new SkillNode(MAGE_ROOT, Optional.empty(), 1));
        registerSkill(new SkillNode(MANA_BOOST_1, Optional.of(MAGE_ROOT), 1));
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