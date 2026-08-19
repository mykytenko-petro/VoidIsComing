package com.voidiscoming.mixin.common.brewingstand;

import net.minecraft.block.entity.BrewingStandBlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.voidiscoming.common.item.ModItems;

@Mixin(Slot.class)
public class BrewingFuelSlotMixin {

    @Inject(method = "canInsert", at = @At("HEAD"), cancellable = true)
    private void allowVoidEssenceAsFuel(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        Slot slot = (Slot) (Object) this;

        if (slot.inventory instanceof BrewingStandBlockEntity && slot.getIndex() == 4) {
            if (stack.isOf(ModItems.VOID_ESSENCE)) {
                cir.setReturnValue(true);
            }
        }
    }
}