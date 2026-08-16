package com.voidiscoming.common.mechanic.spell.impl;

import com.voidiscoming.common.VoidIsComing;
import com.voidiscoming.common.mechanic.spell.Spell;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

import java.util.List;

public class FrostAuraSpell extends Spell {

    public FrostAuraSpell() {
        super(
            VoidIsComing.id("textures/gui/spells/ice_storm.png"),
            15,                                     
            ResourceCostType.MANA, 
            300                                     
        );
    }

    @Override
    public void castBehaviour(PlayerEntity player) {
        if (!(player.getWorld() instanceof ServerWorld serverWorld)) return;

        double radius = 6.0;
        Vec3d pos = player.getPos();
        
        int particleCount = 300;
        for (int i = 0; i < particleCount; i++) {
            double angle = serverWorld.random.nextDouble() * 2 * Math.PI;
            double distance = serverWorld.random.nextDouble() * radius;
            double x = pos.getX() + Math.cos(angle) * distance;
            double y = pos.getY() + serverWorld.random.nextDouble() * 2.5;
            double z = pos.getZ() + Math.sin(angle) * distance;

            serverWorld.spawnParticles(
                ParticleTypes.SNOWFLAKE, 
                x, y, z, 
                2, 0.1, 0.1, 0.1, 0.05
            );
            serverWorld.spawnParticles(
                ParticleTypes.ITEM_SNOWBALL, 
                x, y, z, 
                1, 0.05, 0.05, 0.05, 0.03
            );
            serverWorld.spawnParticles(
                ParticleTypes.CAMPFIRE_COSY_SMOKE, 
                x, y, z, 
                1, 0.02, 0.05, 0.02, 0.01
            );
        }

        Box areaBox = player.getBoundingBox().expand(radius);
        List<LivingEntity> targets = serverWorld.getEntitiesByClass(
            LivingEntity.class, 
            areaBox, 
            entity -> entity != player && entity.isAlive()
        );

        for (LivingEntity target : targets) {
            if (player.distanceTo(target) <= radius) {
                
                target.setFrozenTicks(target.getFrozenTicks() + 600);
                target.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 300, 4, false, true));
                target.addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, 300, 1, false, true));
            }
        }
    }
}