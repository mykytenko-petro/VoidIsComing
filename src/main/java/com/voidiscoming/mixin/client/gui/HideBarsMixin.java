package com.voidiscoming.mixin.client.gui;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class HideBarsMixin {

    @Inject(method = "renderStatusBars", at = @At("HEAD"), cancellable = true)
    private void hideAllStatusBars(DrawContext context, CallbackInfo ci) {
        ci.cancel();
    }
}