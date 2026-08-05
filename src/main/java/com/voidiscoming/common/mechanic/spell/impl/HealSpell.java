package com.voidiscoming.common.mechanic.spell.impl;

import com.voidiscoming.common.VoidIsComing;
import com.voidiscoming.common.component.mana.ManaComponent;
import com.voidiscoming.common.mechanic.spell.Spell;
import com.voidiscoming.common.component.ModComponents;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;

public class HealSpell extends Spell {

    public HealSpell() {
        super(
            "heal", 
            "Healing", 
            VoidIsComing.id("textures/gui/spells/heal.png"), 
            5, 
            ResourceCostType.MANA
        );
    }

    @Override
    public void cast(PlayerEntity player) {
        VoidIsComing.LOGGER.info(">>> HealSpell.cast() ВЖЕ ВИКЛИКАНО для гравця: " + player.getName().getString());

        if (player.getWorld().isClient()) {
            VoidIsComing.LOGGER.info(">>> Це клієнт, виходимо.");
            return;
        }

        ManaComponent mana = ModComponents.MANA.get(player);
        VoidIsComing.LOGGER.info(">>> Поточна мана гравця: " + mana.getMana() + ", вартість спелу: " + getCost());

        if (mana.getMana() >= getCost()) {
            mana.removeMana(getCost());
            VoidIsComing.LOGGER.info(">>> Ману знято успішно! Накладаємо ефект хіла.");

            player.addStatusEffect(new StatusEffectInstance(
                StatusEffects.INSTANT_HEALTH, 
                1, 
                10,
                false, 
                true   
            ));
        } else {
            VoidIsComing.LOGGER.info(">>> НЕ ВИСТАЧАЄ МАНИ!");
        }
    }
}