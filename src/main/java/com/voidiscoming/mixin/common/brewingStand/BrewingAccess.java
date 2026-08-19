package com.voidiscoming.mixin.common.brewingstand;

import net.minecraft.block.entity.BrewingStandBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(BrewingStandBlockEntity.class)
public interface BrewingAccess {
    @Accessor("fuel")
    int getFuel();

    @Accessor("fuel")
    void setFuel(int fuel);
}