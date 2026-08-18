package com.voidiscoming.common.mechanic.skill;

import java.util.HashMap;
import java.util.Map;

import com.voidiscoming.common.VoidIsComing;
import com.voidiscoming.common.component.ModComponents;
import com.voidiscoming.common.mechanic.level.PlayerLevelUpCallback;
import com.voidiscoming.common.mechanic.spell.ModSpells;

import net.minecraft.util.Identifier;

public class ModSkills {
    private static final Map<Identifier, SkillNode> SKILLS = new HashMap<>();

    // root spells
    public static final Identifier HEAL_SPELL = VoidIsComing.id("heal_spell_skill");
    
    // warrior
    public static final Identifier WARRIOR_CLASS = VoidIsComing.id("warrior_class_skill");
    
    // archer
    public static final Identifier ARCHER_CLASS = VoidIsComing.id("archer_class_skill");
    
    // mage
    public static final Identifier MAGE_CLASS = VoidIsComing.id("mage_class_skill");
    public static final Identifier FROST_AURA_SPELL = VoidIsComing.id("frost_aura_spell_skill");
    public static final Identifier TELEPORTATION_SPELL = VoidIsComing.id("teleportation_spell_skill");

    public static void registerSkills() {
        registerSkill(new SkillNode(HEAL_SPELL, 1, ModSpells.HEAL));

        registerSkill(new SkillNode(WARRIOR_CLASS, HEAL_SPELL, 4, new Identifier[] {ARCHER_CLASS, MAGE_CLASS}));

        registerSkill(new SkillNode(ARCHER_CLASS, HEAL_SPELL, 4, new Identifier[] {WARRIOR_CLASS, MAGE_CLASS}));
        
        registerSkill(new SkillNode(MAGE_CLASS, HEAL_SPELL, 4, new Identifier[] {ARCHER_CLASS, WARRIOR_CLASS}));
        registerSkill(new SkillNode(FROST_AURA_SPELL, MAGE_CLASS, 3, ModSpells.FROST_AURA));
        registerSkill(new SkillNode(TELEPORTATION_SPELL, MAGE_CLASS, 3, ModSpells.TELEPORT));
    }

    private static void registerSkill(SkillNode node) {
        SKILLS.put(node.id(), node);
    }

    public static SkillNode get(Identifier id) {
        return SKILLS.get(id);
    }

    public static void registerEvents() {
        PlayerLevelUpCallback.EVENT.register((player, oldLevel, newLevel) -> {
            int levelsGained = newLevel - oldLevel;
            
            ModComponents.SKILLS.maybeGet(player).ifPresent(skills -> {
                skills.addSkillPoints(levelsGained);
            });
        });
    }
}