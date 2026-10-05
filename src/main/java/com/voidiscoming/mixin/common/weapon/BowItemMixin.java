package com.voidiscoming.mixin.common.weapon;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.BowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(BowItem.class)
public class BowItemMixin {

    @Inject(
        method = "onStoppedUsing",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/World;spawnEntity(Lnet/minecraft/entity/Entity;)Z"
        ),
        locals = LocalCapture.CAPTURE_FAILHARD
    )
    private void setCustomArrowDamage(
        ItemStack stack, 
        World world, 
        LivingEntity user, 
        int remainingUseTicks, 
        CallbackInfo ci,
        PlayerEntity playerEntity,
        boolean bl,
        ItemStack itemStack,
        int i,
        float pullProgress,
        boolean bl2,
        ArrowItem arrowItem, 
        PersistentProjectileEntity arrowEntity
    ) {
        if (!world.isClient()) {
            double playerAttackDamage = playerEntity.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE);

            double pullMultiplier = pullProgress * 2.0;

            double finalArrowDamage = playerAttackDamage * pullMultiplier;

            arrowEntity.setDamage(finalArrowDamage);
        }
    }
}