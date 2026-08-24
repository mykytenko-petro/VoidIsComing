package com.voidiscoming.common.mechanic.spell.impl;

import com.voidiscoming.common.VoidIsComing;
import com.voidiscoming.common.mechanic.spell.Spell;

public class ConcentrationSpell extends Spell {

    public ConcentrationSpell() {
        super(VoidIsComing.id("textures/gui/spells/concentration.png"));
    }

    @Override
    public boolean providesCooldownReduction() {
        return true;
    }
}