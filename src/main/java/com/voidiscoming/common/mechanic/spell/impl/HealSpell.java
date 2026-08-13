package com.voidiscoming.common.mechanic.spell.impl;

import com.voidiscoming.common.VoidIsComing;
import com.voidiscoming.common.mechanic.spell.Spell;
import net.minecraft.entity.player.PlayerEntity;

public class HealSpell extends Spell {

    public HealSpell() {
        super(
            VoidIsComing.id("textures/gui/spells/heal.png"), 
            4, 
            ResourceCostType.MANA,
            80 
        );
    }

    @Override
    public void castBehaviour(PlayerEntity player) {
        player.heal(2.0F);
    }
}