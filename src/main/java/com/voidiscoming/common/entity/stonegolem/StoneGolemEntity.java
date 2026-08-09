package com.voidiscoming.common.entity.stonegolem;

import net.minecraft.entity.AnimationState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.boss.BossBar;
import net.minecraft.entity.boss.ServerBossBar;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class StoneGolemEntity extends HostileEntity {

    private static final TrackedData<Boolean> THROWING = DataTracker.registerData(StoneGolemEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Boolean> SLAMMING = DataTracker.registerData(StoneGolemEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    public final AnimationState moveAnimationState = new AnimationState();
    public final AnimationState throwAnimationState = new AnimationState();
    public final AnimationState slamAnimationState = new AnimationState();

    public int throwCooldown = 0;
    public StoneProjectileEntity activeProjectile;

    private final ServerBossBar bossBar = new ServerBossBar(
            Text.literal("Stone Golem"),
            BossBar.Color.WHITE,
            BossBar.Style.PROGRESS
    );

    public StoneGolemEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(THROWING, false);
        this.dataTracker.startTracking(SLAMMING, false);
    }

    public boolean isThrowing() {
        return this.dataTracker.get(THROWING);
    }

    public void setThrowing(boolean throwing) {
        this.dataTracker.set(THROWING, throwing);
    }

    public boolean isSlamming() {
        return this.dataTracker.get(SLAMMING);
    }

    public void setSlamming(boolean slamming) {
        this.dataTracker.set(SLAMMING, slamming);
    }

    @Override
    protected void initGoals() {
        this.targetSelector.add(1, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
        this.goalSelector.add(1, new StoneGolemGroundSlamMechanic(this));
        this.goalSelector.add(2, new StoneGolemTrowMechanic(this));
        this.goalSelector.add(3, new MeleeAttackGoal(this, 1.2D, false));
        this.goalSelector.add(4, new WanderAroundFarGoal(this, 0.8D));
        this.goalSelector.add(5, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.add(6, new LookAroundGoal(this));
    }

    public static DefaultAttributeContainer.Builder createStoneGolemAttributes() {
        return HostileEntity.createHostileAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 200.0D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.22D)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 15.0D)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0D)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 32.0D);
    }

    @Override
    public void setHeadYaw(float headYaw) {
        float bodyYaw = this.getBodyYaw();
        float delta = MathHelper.wrapDegrees(headYaw - bodyYaw);
        float clamped = MathHelper.clamp(delta, -85.0F, 85.0F);
        super.setHeadYaw(bodyYaw + clamped);
    }

    @Override
    public void tick() {
        super.tick();

        if (!this.getWorld().isClient()) {
            if (this.throwCooldown > 0) {
                this.throwCooldown--;
            }

            if (this.activeProjectile != null) {
                if (this.activeProjectile.isAlive()) {
                    ((ServerWorld) this.getWorld()).spawnParticles(ParticleTypes.SMOKE,
                            this.activeProjectile.getX(), this.activeProjectile.getY(), this.activeProjectile.getZ(),
                            2, 0.05D, 0.05D, 0.05D, 0.0D);
                } else {
                    this.activeProjectile = null;
                }
            }
        }

        if (this.getWorld().isClient()) {
            if (this.isSlamming()) {
                this.moveAnimationState.stop();
                this.throwAnimationState.stop();
                this.slamAnimationState.startIfNotRunning(this.age);
            } else {
                this.slamAnimationState.stop();

                if (this.isThrowing()) {
                    this.moveAnimationState.stop();
                    this.throwAnimationState.startIfNotRunning(this.age);
                } else {
                    this.throwAnimationState.stop();

                    if (this.getVelocity().horizontalLengthSquared() > 0.001D) {
                        this.moveAnimationState.startIfNotRunning(this.age);
                    } else {
                        this.moveAnimationState.stop();
                    }
                }
            }
        }
    }

    @Override
    protected void mobTick() {
        super.mobTick();
        this.bossBar.setPercent(this.getHealth() / this.getMaxHealth());
    }

    @Override
    public void onStartedTrackingBy(ServerPlayerEntity player) {
        super.onStartedTrackingBy(player);
        this.bossBar.addPlayer(player);
    }

    @Override
    public void onStoppedTrackingBy(ServerPlayerEntity player) {
        super.onStoppedTrackingBy(player);
        this.bossBar.removePlayer(player);
    }

    @Override
    public void setCustomName(@Nullable Text name) {
        super.setCustomName(name);
        this.bossBar.setName(this.getDisplayName());
    }

    @Override
    public boolean canImmediatelyDespawn(double distanceSquared) {
        return false;
    }

    @Override
    public boolean isPersistent() {
        return true;
    }
}