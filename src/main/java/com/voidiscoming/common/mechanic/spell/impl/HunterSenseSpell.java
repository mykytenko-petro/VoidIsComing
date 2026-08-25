package com.voidiscoming.common.mechanic.spell.impl;

import com.voidiscoming.common.VoidIsComing;
import com.voidiscoming.common.effects.ModEffects;
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

public class HunterSenseSpell extends Spell {

    public HunterSenseSpell() {
        super(
            VoidIsComing.id("textures/gui/spells/hunter_sense.png"), 
            6,
            ResourceCostType.MANA,
            400
        );
    }

    @Override
    public void castBehaviour(PlayerEntity player) {
        if (!(player.getWorld() instanceof ServerWorld serverWorld)) return;

        double radius = 30.0; 
        Box area = player.getBoundingBox().expand(radius);

        List<LivingEntity> targets = serverWorld.getEntitiesByClass(
            LivingEntity.class, 
            area, 
            entity -> entity != player && entity.isAlive()
        );

        for (LivingEntity target : targets) {
            target.addStatusEffect(new StatusEffectInstance(
                ModEffects.VULNERABILITY, 
                1200, 
                1, 
                false, 
                true
            ));
            
            target.addStatusEffect(new StatusEffectInstance(
                StatusEffects.GLOWING, 
                1200, 
                0, 
                false, 
                false
            ));
        }

        Vec3d pos = player.getPos();
        serverWorld.spawnParticles(
            ParticleTypes.END_ROD, 
            pos.getX(), pos.getY() + 1.0, pos.getZ(), 
            25, 0.5, 0.5, 0.5, 0.05
        );
    }
}