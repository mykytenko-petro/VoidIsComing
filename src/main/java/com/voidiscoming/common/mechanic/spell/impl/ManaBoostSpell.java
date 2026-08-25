package com.voidiscoming.common.mechanic.spell.impl;

import com.voidiscoming.common.VoidIsComing;
import com.voidiscoming.common.component.ModComponents;
import com.voidiscoming.common.component.mana.ManaComponent;
import com.voidiscoming.common.mechanic.spell.Spell;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;

public class ManaBoostSpell extends Spell {

    public ManaBoostSpell() {
        super(
            VoidIsComing.id("textures/gui/spells/mana_boost.png")
        );
    }

    @Override
    public void onTick(PlayerEntity player) {
        if (player.getWorld().isClient()) return;

        
        ModComponents.MANA.maybeGet(player).ifPresent(mana -> {
            if (mana.getMana() >= mana.getMaxMana()) {
                player.addStatusEffect(new StatusEffectInstance(
                    StatusEffects.SPEED, 
                    40, 
                    1, 
                    true, 
                    false
                ));
            }
        });
    }
}