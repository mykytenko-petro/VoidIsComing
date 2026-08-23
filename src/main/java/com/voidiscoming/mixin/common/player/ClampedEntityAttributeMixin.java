package com.voidiscoming.mixin.common.player;

import net.minecraft.entity.attribute.ClampedEntityAttribute;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClampedEntityAttribute.class)
public abstract class ClampedEntityAttributeMixin {
    @Mutable
    @Shadow
    private double maxValue;

    @Inject(method = "<init>", at = @At("RETURN"))
    private void onInit(String translationKey, double fallback, double min, double max, CallbackInfo ci) {
        if (this.maxValue < 2048.0) {
            this.maxValue = 2048.0;
        }
    }
}