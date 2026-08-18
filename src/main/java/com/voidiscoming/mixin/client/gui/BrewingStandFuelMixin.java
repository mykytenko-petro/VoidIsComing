package com.voidiscoming.mixin.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.voidiscoming.common.item.consumables.manaBottel.ModItems;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.BrewingStandScreen;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;
import java.util.Map;

@Mixin(BrewingStandScreen.class)
public class BrewingStandFuelMixin {
    // Общий список, который запоминает состояние для каждой зельеварки по её уникальному идентификатору окна
    private static final Map<BrewingStandScreen, Boolean> VOID_FUEL_MAP = new HashMap<>();

    @Inject(method = "drawBackground", at = @At("HEAD"))
    private void updateFuelType(DrawContext context, float delta, int mouseX, int mouseY, CallbackInfo ci) {
        BrewingStandScreen screen = (BrewingStandScreen) (Object) this;
        int fuel = screen.getScreenHandler().getFuel();
        ItemStack fuelStack = screen.getScreenHandler().getSlot(4).getStack();

        // Если топливо полностью кончилось — убираем из памяти
        if (fuel <= 0) {
            VOID_FUEL_MAP.remove(screen);
            return;
        }

        // Если закинули эссенцию — записываем true именно для этого окна
        if (fuelStack.isOf(ModItems.VOID_ESSENCE)) {
            VOID_FUEL_MAP.put(screen, true);
        }
        // Если закинули порошок — записываем false
        else if (fuelStack.isOf(Items.BLAZE_POWDER)) {
            VOID_FUEL_MAP.put(screen, false);
        }
    }

    @Inject(method = "drawBackground", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawTexture(Lnet/minecraft/util/Identifier;IIIIII)V", ordinal = 1))
    private void changeFlameColor(DrawContext context, float delta, int mouseX, int mouseY, CallbackInfo ci) {
        BrewingStandScreen screen = (BrewingStandScreen) (Object) this;
        boolean isVoid = VOID_FUEL_MAP.getOrDefault(screen, false);

        // Если топливо горит и это эссенция — красим в фиолетовый
        if (screen.getScreenHandler().getFuel() > 0 && isVoid) {
            RenderSystem.setShaderColor(0.7F, 0.3F, 0.9F, 1.0F);
        }
    }

    @Inject(method = "drawBackground", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawTexture(Lnet/minecraft/util/Identifier;IIIIII)V", ordinal = 1, shift = At.Shift.AFTER))
    private void resetFlameColor(DrawContext context, float delta, int mouseX, int mouseY, CallbackInfo ci) {
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
    }
}