package com.voidiscoming.common.mechanic.spell.impl;

import com.voidiscoming.common.VoidIsComing;
import com.voidiscoming.common.mechanic.spell.Spell;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;

public class PurificationSpell extends Spell {

    public PurificationSpell() {
        super(
            VoidIsComing.id("textures/gui/spells/purification.png"), 
            8,                  
            ResourceCostType.MANA, 
            200                 
        );
    }

    @Override
    public void castBehaviour(PlayerEntity player) {
        if (!(player.getWorld() instanceof ServerWorld serverWorld)) return;

        player.clearStatusEffects();

        Vec3d pos = player.getPos();
        serverWorld.spawnParticles(
            ParticleTypes.INSTANT_EFFECT,
            pos.getX(), pos.getY() + 1.0, pos.getZ(), 
            30, 0.5, 0.8, 0.5, 0.1
        );
    }
}