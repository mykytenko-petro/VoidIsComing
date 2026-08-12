package com.voidiscoming.common.mechanic.skill;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.voidiscoming.common.VoidIsComing;

import net.minecraft.util.Identifier;

public class ModSkills {
    private static final Map<Identifier, SkillNode> SKILLS = new HashMap<>();

    public static final Identifier HEAL_SPELL = VoidIsComing.id("heal_spell_skill");
    
    public static final Identifier WARRIOR_CLASS = VoidIsComing.id("warrior_class_skill");
    public static final Identifier ARCHER_CLASS = VoidIsComing.id("archer_class_skill");
    public static final Identifier MAGE_CLASS = VoidIsComing.id("mage_class_skill");

    public static void registerSkills() {
        registerSkill(new SkillNode(HEAL_SPELL, Optional.empty(), 1, SkillType.SPELL));

        registerSkill(new SkillNode(WARRIOR_CLASS, Optional.of(HEAL_SPELL), 4, SkillType.CLASS));
        registerSkill(new SkillNode(ARCHER_CLASS, Optional.of(HEAL_SPELL), 4, SkillType.CLASS));
        registerSkill(new SkillNode(MAGE_CLASS, Optional.of(HEAL_SPELL), 4, SkillType.CLASS));
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