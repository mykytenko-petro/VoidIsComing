package com.voidiscoming.common.mechanic.spell.impl;

import com.voidiscoming.common.VoidIsComing;
import com.voidiscoming.common.mechanic.spell.Spell;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;

public class VampirismSpell extends Spell {

    public VampirismSpell() {
        super(
            "vampirism",
            "Вампіризм",
            VoidIsComing.id("textures/gui/spells/vampire.png")
        );
    }

    @Override
    public void onKill(PlayerEntity player, LivingEntity killedEntity) {
        player.heal(1F);
    }
}