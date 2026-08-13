package com.voidiscoming.common.mechanic.spell.impl;

import com.voidiscoming.common.VoidIsComing;
import com.voidiscoming.common.mechanic.spell.Spell;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;

public class TeleportSpell extends Spell {

    public TeleportSpell() {
        super(
            VoidIsComing.id("teleport_spell"), 
            VoidIsComing.id("textures/spell/teleport_spell.png"), 
            10, 
            ResourceCostType.MANA, 
            40
        );
    }

    @Override
    public void cast(PlayerEntity player) {
        if (!(player instanceof ServerPlayerEntity serverPlayer)) return;

        double maxDistance = 30.0;
        Vec3d startPos = serverPlayer.getEyePos();
        Vec3d rotationVec = serverPlayer.getRotationVector();
        Vec3d endPos = startPos.add(
            rotationVec.x * maxDistance, 
            rotationVec.y * maxDistance, 
            rotationVec.z * maxDistance
        );

        // Безпечно приводимо світ до ServerWorld
        ServerWorld serverWorld = (ServerWorld) serverPlayer.getWorld();

        BlockHitResult hitResult = serverWorld.raycast(new RaycastContext(
            startPos, endPos,
            RaycastContext.ShapeType.COLLIDER,
            RaycastContext.FluidHandling.NONE,
            serverPlayer
        ));

        Vec3d targetPos;
        if (hitResult.getType() == HitResult.Type.MISS) {
            targetPos = endPos;
        } else {
            targetPos = hitResult.getPos().subtract(rotationVec.multiply(0.5));
        }

        serverPlayer.teleport(
            serverWorld,
            targetPos.x, targetPos.y, targetPos.z,
            serverPlayer.getYaw(), serverPlayer.getPitch()
        );

        serverWorld.spawnParticles(
            net.minecraft.particle.ParticleTypes.PORTAL,
            serverPlayer.getX(), serverPlayer.getY(), serverPlayer.getZ(),
            25, 0.5, 1.0, 0.5, 0.1
        );
    }
}