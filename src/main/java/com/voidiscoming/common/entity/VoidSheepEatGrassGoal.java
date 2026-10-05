package com.voidiscoming.common.entity;

import com.voidiscoming.common.block.ModBlocks;

import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class VoidSheepEatGrassGoal extends Goal {

    private final VoidSheepEntity sheep;
    private int timer;

    public VoidSheepEatGrassGoal(VoidSheepEntity sheep) {
        this.sheep = sheep;
    }

    @Override
    public boolean canStart() {
        if (this.sheep.isSheared()) {
            return false;
        }

        BlockPos pos = this.sheep.getBlockPos();

        return this.sheep.getWorld().getBlockState(pos.down()).isOf(ModBlocks.VOID_GRASS);
    }

    @Override
    public boolean shouldContinue() {
        return this.timer > 0;
    }

    @Override
    public void start() {
        this.timer = 40;
    }

    @Override
    public void tick() {
        this.timer--;

        if (this.timer == 4) {
            World world = this.sheep.getWorld();
            BlockPos pos = this.sheep.getBlockPos().down();

            if (world.getBlockState(pos).isOf(ModBlocks.VOID_GRASS)) {
                world.breakBlock(pos, false);
                this.sheep.onEatingGrass();
                this.sheep.setSheared(false);
            }
        }
    }

    @Override
    public void stop() {
        this.timer = 0;
    }
}