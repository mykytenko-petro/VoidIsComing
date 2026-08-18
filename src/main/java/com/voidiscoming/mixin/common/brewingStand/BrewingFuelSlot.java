package com.voidiscoming.mixin.common.brewingStand;

import com.voidiscoming.common.item.consumables.manaBottel.ModItems;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// Обрати внимание: мы целимся во внутренний класс FuelSlot через знак доллара ($)
@Mixin(targets = "net.minecraft.screen.BrewingStandScreenHandler$FuelSlot")
public class BrewingFuelSlot {

    // Перехватываем метод matches, который решает, пускать предмет в слот или нет
    @Inject(method = "matches", at = @At("HEAD"), cancellable = true)
    private static void allowVoidEssence(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        // Если это наша эссенция — говорим игре "Да, это подходит!"
        if (stack.isOf(ModItems.VOID_ESSENCE)) {
            cir.setReturnValue(true);
        }
    }
}