package com.voidiscoming.common.mechanic.spell.impl;

import com.voidiscoming.common.VoidIsComing;
import com.voidiscoming.common.component.mana.ManaComponent;
import com.voidiscoming.common.component.ModComponents;
import com.voidiscoming.common.component.spell.PlayerSpellComponent;
import com.voidiscoming.common.mechanic.spell.Spell;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import com.voidiscoming.common.mechanic.spell.ModSpells;
public class InvisibilitySpell extends Spell {

    public InvisibilitySpell() {
        super(
            VoidIsComing.id("textures/gui/spells/invisibility.png"), 
            15, 
            ResourceCostType.MANA,
            2400
        );
    }

    @Override
    public void castBehaviour(PlayerEntity player){
        player.addStatusEffect(new StatusEffectInstance(
            StatusEffects.INVISIBILITY, 
            1200, 
            0, 
            false, 
            false 
        ));

        player.addStatusEffect(new StatusEffectInstance(
            StatusEffects.SPEED, 
            120, 
            1, 
            false, 
            false
        )); 
    }
}