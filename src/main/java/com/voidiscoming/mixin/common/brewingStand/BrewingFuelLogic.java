package com.voidiscoming.mixin.common.brewingstand;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BrewingStandBlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.voidiscoming.common.item.ModItems;

@Mixin(BrewingStandBlockEntity.class)
public class BrewingFuelLogic {

    @Inject(method = "tick", at = @At("HEAD"))
    private static void addCustomFuel(World world, BlockPos pos, BlockState state, BrewingStandBlockEntity blockEntity, CallbackInfo ci) {
        BrewingAccess accessor = (BrewingAccess) blockEntity;

        if (accessor.getFuel() <= 0) {
            ItemStack fuelStack = blockEntity.getStack(4);

            if (fuelStack.isOf(ModItems.VOID_ESSENCE)) {
                accessor.setFuel(20);
                fuelStack.decrement(1);
                blockEntity.markDirty();
            }
        }
    }
}