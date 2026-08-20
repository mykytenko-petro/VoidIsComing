package com.voidiscoming.mixin.common.food;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
public class FoodMixin {

    @Inject(
        method = "use(Lnet/minecraft/world/World;Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/util/Hand;)Lnet/minecraft/util/TypedActionResult;", 
        at = @At("HEAD"), 
        cancellable = true
    )
    private void allowEatingAnytime(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> cir) {
        ItemStack stack = user.getStackInHand(hand);
        if (stack.getItem().isFood()) {
            user.setCurrentHand(hand);
            cir.setReturnValue(TypedActionResult.consume(stack));
        }
    }

    @Inject(
        method = "finishUsing(Lnet/minecraft/item/ItemStack;Lnet/minecraft/world/World;Lnet/minecraft/entity/LivingEntity;)Lnet/minecraft/item/ItemStack;", 
        at = @At("HEAD"), 
        cancellable = true
    )
    private void makeFoodHealDirectly(ItemStack stack, World world, LivingEntity user, CallbackInfoReturnable<ItemStack> cir) {
        if (user instanceof PlayerEntity player && stack.getItem().isFood()) {
            var foodComponent = stack.getItem().getFoodComponent();
            if (foodComponent != null) {
                player.heal(1);

                if (!world.isClient) {
                    stack.decrement(1);
                }

                cir.setReturnValue(stack);
            }
        }
    }
}