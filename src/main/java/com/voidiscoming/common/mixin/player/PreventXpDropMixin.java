package com.voidiscoming.common.mixin.player;

import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public class PreventXpDropMixin {

    @Inject(method = "getXpToDrop", at = @At("HEAD"), cancellable = true)
    private void preventXpDropOnDeath(CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(0);
    }
}