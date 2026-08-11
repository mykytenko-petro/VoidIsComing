package com.voidiscoming.mixin.food;

import net.minecraft.entity.player.HungerManager;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HungerManager.class)
public abstract class HungerManagerMixin {
    @Shadow public abstract void setFoodLevel(int foodLevel);
    @Shadow public abstract void setSaturationLevel(float saturationLevel);

    @Inject(method = "update", at = @At("HEAD"), cancellable = true)
    private void disableHunger(PlayerEntity player, CallbackInfo ci) {
        this.setFoodLevel(20);
        this.setSaturationLevel(5.0F);
        ci.cancel();
    }
}