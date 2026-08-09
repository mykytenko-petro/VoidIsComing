package com.voidiscoming.common.mechanic.spell.impl;

import com.voidiscoming.common.VoidIsComing;
import com.voidiscoming.common.component.mana.ManaComponent;
import com.voidiscoming.common.component.ModComponents;
import com.voidiscoming.common.component.spell.PlayerSpellComponent;
import com.voidiscoming.common.mechanic.spell.Spell;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;

public class InvisibilitySpell extends Spell {

    public InvisibilitySpell() {
        super(
            "invisibility", 
            "Невидимість", 
            VoidIsComing.id("textures/gui/spells/invisibility.png"), 
            15, 
            ResourceCostType.MANA,
            2400
        );
    }

    @Override
    public void cast(PlayerEntity player) {
        if (player.getWorld().isClient()) return;

        ModComponents.SPELLS.maybeGet(player).ifPresent(spellComp -> {
            if (spellComp instanceof PlayerSpellComponent playerSpellComp) {
                if (playerSpellComp.isOnCooldown(getId())) {
                    return;
                }
            }

            ManaComponent mana = ModComponents.MANA.get(player);

            if (mana.getMana() >= getCost()) {
                mana.removeMana(getCost());

                if (spellComp instanceof PlayerSpellComponent playerSpellComp) {
                    playerSpellComp.setCooldown(getId(), getCooldownTicks());
                }

               
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
        });
    }
}