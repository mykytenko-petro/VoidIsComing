package com.voidiscoming.common.entity;

import com.voidiscoming.common.block.ModBlocks;
import com.voidiscoming.common.item.ModItems;

import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class VoidSheepEntity extends SheepEntity {

    private static final int CLONE_COOLDOWN = 2400;
    private static final int EAT_COOLDOWN = 2400;

    private int cloneCooldown = 0;
    private int eatCooldown = 0;

    public VoidSheepEntity(EntityType<? extends SheepEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createVoidSheepAttributes() {
        return SheepEntity.createSheepAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 27.0D)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 4.5D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25D);
    }

    @Override
    protected void initGoals() {
        super.initGoals();

        this.goalSelector.add(0, new VoidSheepEatGrassGoal(this));
        this.goalSelector.add(1, new MeleeAttackGoal(this, 1.0D, false));

        this.targetSelector.add(
                1,
                new ActiveTargetGoal<>(
                        this,
                        PlayerEntity.class,
                        true
                )
        );
    }

    @Override
    public void tick() {
        super.tick();

        if (this.cloneCooldown > 0) {
            this.cloneCooldown--;
        }

        if (this.eatCooldown > 0) {
            this.eatCooldown--;
        }

        if (!this.getWorld().isClient) {
            if (!this.isSheared()) {
                if (this.cloneCooldown <= 0) {
                    if (this.getTarget() != null) {
                        createClone();
                    }
                }
            }
        }
    }

    private void createClone() {
        if (!(this.getWorld() instanceof ServerWorld serverWorld)) {
            return;
        }

        VoidSheepEntity clone = new VoidSheepEntity(
                ModEntities.VOID_SHEEP,
                serverWorld
        );

        clone.refreshPositionAndAngles(
                this.getX() + 1.5D,
                this.getY(),
                this.getZ() + 1.5D,
                this.getYaw(),
                this.getPitch()
        );

        clone.setHealth(clone.getMaxHealth());
        clone.setSheared(true);
        clone.cloneCooldown = CLONE_COOLDOWN;
        clone.eatCooldown = EAT_COOLDOWN;

        serverWorld.spawnEntity(clone);

        this.setSheared(true);
        this.cloneCooldown = CLONE_COOLDOWN;
    }

    @Override
    protected void dropLoot(DamageSource source, boolean causedByPlayer) {
        super.dropLoot(source, causedByPlayer);

        if (this.random.nextFloat() < 0.05F) {
            this.dropStack(new ItemStack(ModItems.VOID_ESSENCE));
        }
    }

    private static class VoidSheepEatGrassGoal extends Goal {

        private final VoidSheepEntity sheep;
        private BlockPos grassPos;
        private int eatTimer;

        public VoidSheepEatGrassGoal(VoidSheepEntity sheep) {
            this.sheep = sheep;
        }

        @Override
        public boolean canStart() {
            if (this.sheep.isSheared()) {
                return false;
            }

            if (this.sheep.eatCooldown > 0) {
                return false;
            }

            this.grassPos = findGrass();

            return this.grassPos != null;
        }

        @Override
        public boolean shouldContinue() {
            return !this.sheep.isSheared()
                    && this.sheep.eatCooldown <= 0
                    && this.grassPos != null
                    && this.eatTimer < 40;
        }

        @Override
        public void start() {
            this.eatTimer = 0;

            this.sheep.getNavigation().startMovingTo(
                    this.grassPos.getX() + 0.5D,
                    this.grassPos.getY() + 1.0D,
                    this.grassPos.getZ() + 0.5D,
                    1.0D
            );
        }

        @Override
        public void tick() {
            if (this.grassPos == null) {
                return;
            }

            if (this.sheep.isSheared()) {
                this.stop();
                return;
            }

            double distance = this.sheep.squaredDistanceTo(
                    this.grassPos.getX() + 0.5D,
                    this.grassPos.getY() + 1.0D,
                    this.grassPos.getZ() + 0.5D
            );

            if (distance <= 4.0D) {
                this.sheep.getNavigation().stop();
                this.eatTimer++;

                if (this.eatTimer >= 40) {
                    eatGrass();
                }
            } else {
                this.sheep.getNavigation().startMovingTo(
                        this.grassPos.getX() + 0.5D,
                        this.grassPos.getY() + 1.0D,
                        this.grassPos.getZ() + 0.5D,
                        1.0D
                );
            }
        }

        @Override
        public void stop() {
            this.sheep.getNavigation().stop();
            this.grassPos = null;
            this.eatTimer = 0;
        }

        private BlockPos findGrass() {
            if (this.sheep.isSheared()) {
                return null;
            }

            BlockPos origin = this.sheep.getBlockPos();

            for (int x = -4; x <= 4; x++) {
                for (int z = -4; z <= 4; z++) {
                    for (int y = -1; y <= 1; y++) {
                        BlockPos pos = origin.add(x, y, z);

                        if (this.sheep.getWorld().getBlockState(pos).isOf(ModBlocks.VOID_GRASS)) {
                            return pos;
                        }

                        if (this.sheep.getWorld().getBlockState(pos).isOf(Blocks.GRASS_BLOCK)) {
                            return pos;
                        }
                    }
                }
            }

            return null;
        }

        private void eatGrass() {
            if (this.grassPos == null || this.sheep.isSheared()) {
                return;
            }

            World world = this.sheep.getWorld();

            if (world.getBlockState(this.grassPos).isOf(ModBlocks.VOID_GRASS)) {
                world.setBlockState(
                        this.grassPos,
                        Blocks.DIRT.getDefaultState()
                );

                this.sheep.onEatingGrass();
                this.sheep.setSheared(true);
            } else if (world.getBlockState(this.grassPos).isOf(Blocks.GRASS_BLOCK)) {
                this.sheep.onEatingGrass();
                this.sheep.setSheared(true);
            }

            this.sheep.eatCooldown = EAT_COOLDOWN;
            this.eatTimer = 40;
        }
    }
}