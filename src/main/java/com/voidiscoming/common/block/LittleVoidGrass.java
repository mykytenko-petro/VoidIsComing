package com.voidiscoming.common.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.PlantBlock;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldView;

public class LittleVoidGrass extends PlantBlock {

    public LittleVoidGrass(Settings settings) {
        super(settings);
    }

    @Override
    protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        return floor.isOpaqueFullCube(world, pos);
    }

    @Override
    public boolean canReplace(BlockState state, ItemPlacementContext context) {
        return false;
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockPos pos = ctx.getBlockPos();

        if (ctx.getWorld().getBlockState(pos.down()).isOf(this)) {
            return null;
        }

        return super.getPlacementState(ctx);
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockState below = world.getBlockState(pos.down());

        return !below.isOf(this)
                && super.canPlaceAt(state, world, pos);
    }
}