package com.voidiscoming.common.mechanic;

import com.voidiscoming.common.mechanic.level.LevelMechanic;
import com.voidiscoming.common.mechanic.skill.ModSkills;
import com.voidiscoming.common.mechanic.spell.ModSpells;
import com.voidiscoming.common.mechanic.stat.PlayerStatApplier;

public class ModMechanics {
    public static void registerMechanics() {
        PlayerStatApplier.registerEvents();
        LevelMechanic.registerEvents();
        ModSpells.registerEvents();
        ModSpells.registerSpells();
        ModSkills.registerSkills();
    }
}