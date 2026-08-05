package com.voidiscoming.common.mechanic.spell.impl;

import com.voidiscoming.common.VoidIsComing;
import com.voidiscoming.common.mechanic.spell.Spell;

import net.minecraft.entity.Entity;
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
    public void onAttack(PlayerEntity attacker, Entity target) {
        // Перевіряємо, чи ціль — це жива істота і чи ми на сервері
        if (!attacker.getWorld().isClient() && target instanceof LivingEntity) {
            // Приклад логіки: відновлюємо гравцю 1 ХП (0.5 сердечка) за кожен успішний удар
            attacker.heal(1.0F);
            
        
        }
    }
}