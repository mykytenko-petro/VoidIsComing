package com.voidiscoming.common.mechanic.entityMechanick.stoneGolem;

import com.voidiscoming.common.entity.stoneGolem.StoneGolemEntity;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

import java.util.EnumSet;
import java.util.List;

public class StoneGolemGroundSlamMechanic extends Goal {
    private final StoneGolemEntity golem;
    private int attackTimer = 0;
    private int cooldown = 0;

    public StoneGolemGroundSlamMechanic(StoneGolemEntity golem) {
        this.golem = golem;
        this.setControls(EnumSet.of(Control.MOVE, Control.LOOK));
    }

    @Override
    public boolean canStart() {
        if (this.cooldown > 0) {
            this.cooldown--;
        }

        if (golem.isThrowing()) return false;

        LivingEntity target = golem.getTarget();
        if (target == null || !target.isAlive()) return false;

        if (this.cooldown > 0) return false;

        return golem.squaredDistanceTo(target) <= 100.0D;
    }

    @Override
    public void start() {
        this.attackTimer = 20;
        this.cooldown = 160;
        this.golem.getNavigation().stop();
        this.golem.setSlamming(true);
    }

    @Override
    public void stop() {
        this.attackTimer = 0;
        this.golem.setSlamming(false);
    }

    @Override
    public boolean shouldContinue() {
        return this.attackTimer > 0 && this.golem.getTarget() != null && this.golem.getTarget().isAlive();
    }

    @Override
    public void tick() {
        LivingEntity target = this.golem.getTarget();
        if (target != null) {
            this.golem.getLookControl().lookAt(target, 30.0F, 30.0F);
        }

        this.attackTimer--;

        if (this.attackTimer == 10) {
            World world = this.golem.getWorld();
            if (!world.isClient()) {
                ServerWorld serverWorld = (ServerWorld) world;

                // Звук удара
                serverWorld.playSound(
                        null,
                        this.golem.getBlockPos(),
                        SoundEvents.ENTITY_GENERIC_EXPLODE,
                        SoundCategory.HOSTILE,
                        1.0F,
                        0.5F
                );

                // Частицы волны
                double[] radii = {3.0D, 6.0D, 10.0D};
                for (double radius : radii) {
                    int points = (int)(radius * 10);
                    for (int i = 0; i < points; i++) {
                        double angle = 2.0D * Math.PI * i / points;
                        double x = this.golem.getX() + radius * Math.cos(angle);
                        double z = this.golem.getZ() + radius * Math.sin(angle);

                        double surfaceY = this.golem.getY();
                        for (int dy = 3; dy >= -3; dy--) {
                            BlockPos testPos = new BlockPos((int)x, (int)(this.golem.getY() + dy), (int)z);
                            if (!serverWorld.getBlockState(testPos).isAir()) {
                                surfaceY = testPos.getY() + 1.0D;
                                break;
                            }
                        }

                        serverWorld.spawnParticles(
                                new BlockStateParticleEffect(ParticleTypes.BLOCK, Blocks.DIRT.getDefaultState()),
                                x, surfaceY, z, 3, 0.2D, 0.1D, 0.2D, 0.05D
                        );
                        serverWorld.spawnParticles(
                                new BlockStateParticleEffect(ParticleTypes.BLOCK, Blocks.STONE.getDefaultState()),
                                x, surfaceY, z, 2, 0.2D, 0.1D, 0.2D, 0.05D
                        );
                    }
                }

                // Проверяем сущности в радиусе 10 блоков
                Box box = this.golem.getBoundingBox().expand(10.0D, 3.0D, 10.0D);
                List<LivingEntity> entities = serverWorld.getEntitiesByClass(LivingEntity.class, box, entity -> entity != this.golem);

                for (LivingEntity entity : entities) {
                    if (!entity.isOnGround()) {
                        continue;
                    }

                    entity.damage(serverWorld.getDamageSources().mobAttack(this.golem), 14.0F);
                    entity.addVelocity(0.0D, 1.28D, 0.0D);
                    entity.velocityModified = typeCheck(entity);
                    entity.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 260, 1), this.golem);
                }
            }
        }
    }

    private boolean typeCheck(LivingEntity entity) {
        return true;
    }
}