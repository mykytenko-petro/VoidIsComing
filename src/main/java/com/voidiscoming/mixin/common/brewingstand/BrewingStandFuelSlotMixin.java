package com.voidiscoming.mixin.common.brewingstand;

import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.voidiscoming.common.item.ModItems;

@Mixin(targets = "net.minecraft.screen.BrewingStandScreenHandler$FuelSlot")
public class BrewingStandFuelSlotMixin {

    @Inject(method = "matches", at = @At("HEAD"), cancellable = true)
    private static void allowVoidEssenceInFuelSlot(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        if (stack.isOf(ModItems.VOID_ESSENCE)) {
            cir.setReturnValue(true);
        }
    }
}