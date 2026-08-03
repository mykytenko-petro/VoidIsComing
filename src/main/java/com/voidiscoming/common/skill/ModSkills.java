package com.voidiscoming.common.skill;

import com.voidiscoming.common.VoidIsComing;
import java.util.ArrayList;
import java.util.List;

public class ModSkills {
    public static final List<Skill> ALL_SKILLS = new ArrayList<>();

    // Пасивка
    public static final Skill VAMPIRISM = register(new Skill(
        "vampirism", 
        "Вампіризм", 
        VoidIsComing.id("textures/gui/skills/vampire.png")
    ));
    public static final Skill HEAL = register(new Skill(
        "heal", 
        "Лікування", 
        VoidIsComing.id("textures/gui/skills/heal.png"),
        10,
        Skill.ResourceCostType.MANA
    ));

    private static Skill register(Skill skill) {
        ALL_SKILLS.add(skill);
        return skill;
    }

    public static Skill getById(String id) {
        if (id == null || id.isEmpty()) return null;
        for (Skill skill : ALL_SKILLS) {
            if (skill.getId().equals(id)) {
                return skill;
            }
        }
        return null;
    }
}