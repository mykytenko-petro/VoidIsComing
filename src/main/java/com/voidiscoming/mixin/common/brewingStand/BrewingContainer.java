package com.voidiscoming.mixin.common.brewingStand;

import com.voidiscoming.common.item.consumables.manaBottel.ModItems;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.BrewingStandScreenHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(BrewingStandScreenHandler.class)
public class BrewingContainer {

    // Разрешаем слоту топлива принимать как порошок ифрита, так и нашу эссенцию
    @Redirect(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isOf(Lnet/minecraft/item/Item;)Z", ordinal = 0))
    private boolean allowCustomFuel(ItemStack stack, net.minecraft.item.Item item) {
        return stack.isOf(Items.BLAZE_POWDER) || stack.isOf(ModItems.VOID_ESSENCE);
    }
}