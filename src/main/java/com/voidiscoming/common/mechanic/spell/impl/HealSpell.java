package com.voidiscoming.common.mechanic.spell.impl;

import com.voidiscoming.common.VoidIsComing;
import com.voidiscoming.common.component.mana.ManaComponent;
import com.voidiscoming.common.component.ModComponents;
import com.voidiscoming.common.component.spell.PlayerSpellComponent;
import com.voidiscoming.common.mechanic.spell.Spell;
import net.minecraft.entity.player.PlayerEntity;

public class HealSpell extends Spell {

    public HealSpell() {
        super(
            "heal", 
            "Healing", 
            VoidIsComing.id("textures/gui/spells/heal.png"), 
            4, 
            ResourceCostType.MANA,
            80 
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
                player.heal(2.0F);
            }
        });
    }
}