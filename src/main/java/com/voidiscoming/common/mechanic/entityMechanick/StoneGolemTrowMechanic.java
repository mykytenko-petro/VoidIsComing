package com.voidiscoming.common.mechanic.entityMechanick;

import com.voidiscoming.common.entity.StoneGolem.StoneGolemEntity;
import com.voidiscoming.common.entity.StoneGolem.StoneProjectileEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.EnumSet;

public class StoneGolemTrowMechanic extends Goal {
    private final StoneGolemEntity golem;
    private LivingEntity target;
    private int prepareTime = 0;

    // Длительность анимации броска в тиках (25 тиков = 1.25 секунды).
    // Подгони это число под реальную длину анимации в Blockbench, если нужно!
    private static final int THROW_DURATION = 25;

    public StoneGolemTrowMechanic(StoneGolemEntity golem) {
        this.golem = golem;
        this.setControls(EnumSet.of(Control.MOVE));
    }

    @Override
    public boolean canStart() {
        if (this.golem.throwCooldown > 0) {
            return false;
        }

        LivingEntity livingEntity = this.golem.getTarget();
        if (livingEntity == null || !livingEntity.isAlive()) {
            return false;
        }

        double distanceSq = this.golem.squaredDistanceTo(livingEntity);

        if (distanceSq < 16.0D) {
            return false;
        }
        if (distanceSq > 32.0D * 32.0D) {
            return false;
        }

        return true;
    }

    @Override
    public boolean shouldContinue() {
        return this.prepareTime > 0 && this.golem.getTarget() != null && this.golem.getTarget().isAlive();
    }

    @Override
    public void start() {
        this.target = this.golem.getTarget();
        this.prepareTime = THROW_DURATION;
        this.golem.getNavigation().stop();

        // Включаем анимацию броска
        this.golem.setThrowing(true);
    }

    @Override
    public void stop() {
        // Выключаем анимацию только когда весь процесс полностью завершился
        this.golem.setThrowing(false);
        this.target = null;
        this.golem.throwCooldown = 60; // Кулдаун до следующего броска
    }

    @Override
    public void tick() {
        if (this.target == null) return;

        this.golem.getLookControl().lookAt(this.target, 30.0F, 30.0F);
        this.prepareTime--;

        // Выпускаем снаряд примерно на середине анимации (например, когда руки делают замах/бросок)
        if (this.prepareTime == 13) {
            World world = this.golem.getWorld();

            if (!world.isClient() && world instanceof ServerWorld serverWorld) {
                BlockPos blockUnder = this.golem.getBlockPos().down();

                if (!world.getBlockState(blockUnder).isAir() && world.getBlockState(blockUnder).getHardness(world, blockUnder) != -1.0F) {
                    world.breakBlock(blockUnder, false);

                    serverWorld.spawnParticles(ParticleTypes.CLOUD,
                            this.golem.getX(), this.golem.getY(), this.golem.getZ(),
                            25, 0.6D, 0.2D, 0.6D, 0.05D);
                }

                StoneProjectileEntity projectile = new StoneProjectileEntity(world, this.golem);
                projectile.setItem(new ItemStack(Items.COBBLESTONE));

                double dx = this.target.getX() - this.golem.getX();
                double dy = this.target.getBodyY(0.5D) - projectile.getY();
                double dz = this.target.getZ() - this.golem.getZ();
                double dist = Math.sqrt(dx * dx + dz * dz);

                projectile.setVelocity(dx, dy + dist * 0.1D, dz, 1.8F, 0.0F);

                world.playSound(null, this.golem.getBlockPos(), SoundEvents.ENTITY_SNOWBALL_THROW, SoundCategory.HOSTILE, 1.0F, 0.4F);
                world.spawnEntity(projectile);

                this.golem.activeProjectile = projectile;
            }
        }
    }
}