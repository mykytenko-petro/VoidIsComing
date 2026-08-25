package com.voidiscoming.common.mechanic.spell.impl;

import com.voidiscoming.common.VoidIsComing;
import com.voidiscoming.common.mechanic.spell.Spell;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;

public class LastStandSpell extends Spell {

    public LastStandSpell() {
        super(
            VoidIsComing.id("textures/gui/spells/last_stand.png")
        );
    }

    @Override
    public void onTick(PlayerEntity player) {
        if (player.getWorld().isClient()) return;

        float maxHealth = player.getMaxHealth();
        float currentHealth = player.getHealth();
        if (currentHealth <= maxHealth / 2.0f) {
            player.addStatusEffect(new StatusEffectInstance(
                StatusEffects.RESISTANCE, 
                40, 
                1, 
                true, 
                false 
            ));
        }
    }
}