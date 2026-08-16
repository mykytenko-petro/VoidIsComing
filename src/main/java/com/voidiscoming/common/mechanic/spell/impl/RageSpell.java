package com.voidiscoming.common.mechanic.spell.impl;

import com.voidiscoming.common.VoidIsComing;
import com.voidiscoming.common.mechanic.spell.Spell;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;

public class RageSpell extends Spell {

    public RageSpell() {
        super(
            VoidIsComing.id("textures/gui/spells/rage_spell.png"), 
            4,                                                     // 4 одиниці здоров'я (2 серця) для відображення в інтерфейсі
            ResourceCostType.HP,                                   // Тип витрат — здоров'я
            300                                                    // Кулдаун у тіках (15 секунд)
        );
    }

    @Override
    public void castBehaviour(PlayerEntity player) {
        if (!(player.getWorld() instanceof ServerWorld serverWorld)) return;

        // Забираємо 4 одиниці здоров'я (2 серця) у гравця за активацію люті
        player.damage(serverWorld.getDamageSources().magic(), 4.0F);

        Vec3d pos = player.getPos();

        // Менша кількість частинок (50 штук) для гарного і ненав'язливого ефекту
        int particleCount = 50;
        for (int i = 0; i < particleCount; i++) {
            double offsetX = (serverWorld.random.nextDouble() - 0.5) * 1.5;
            double offsetY = serverWorld.random.nextDouble() * 2.0;
            double offsetZ = (serverWorld.random.nextDouble() - 0.5) * 1.5;

            double x = pos.getX() + offsetX;
            double y = pos.getY() + offsetY;
            double z = pos.getZ() + offsetZ;

            serverWorld.spawnParticles(
                ParticleTypes.ANGRY_VILLAGER, 
                x, y, z, 
                1, 0.0, 0.05, 0.0, 0.02
            );
            serverWorld.spawnParticles(
                ParticleTypes.SOUL_FIRE_FLAME, 
                x, y, z, 
                1, 0.0, 0.02, 0.0, 0.01
            );
        }

        // Накладаємо ефекти тільки на юзера на 10 секунд (200 тіків):
        // Сила III (амплітуда 3 у конструкторі дає Силу III у грі)
        player.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 200, 3, false, false));

        // Швидкість II
        player.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 200, 1, false, false));

        // Стрибучість II (Jump Boost II)
        player.addStatusEffect(new StatusEffectInstance(StatusEffects.JUMP_BOOST, 200, 1, false, false));
    }
}