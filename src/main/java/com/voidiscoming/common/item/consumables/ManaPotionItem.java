package com.voidiscoming.common.item.consumables;

import com.voidiscoming.common.component.ModComponents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.PotionItem;
import net.minecraft.world.World;

public class ManaPotionItem extends PotionItem {
    private final double manaAmount;

    public ManaPotionItem(Settings settings, double manaAmount) {
        super(settings);
        this.manaAmount = manaAmount;
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        if (!world.isClient) {
            if (user instanceof PlayerEntity player) {
                var manaComponent = ModComponents.MANA.get(player);
                manaComponent.addMana(this.manaAmount);
                ModComponents.MANA.sync(player);
            }
        }

        if (user instanceof PlayerEntity player) {
            if (!player.isCreative()) {
                ItemStack glassBottle = new ItemStack(Items.GLASS_BOTTLE);
                
                stack.decrement(1);

                if (stack.isEmpty()) {
                    return glassBottle;
                }

                if (!player.getInventory().insertStack(glassBottle)) {
                    player.dropItem(glassBottle, false);
                }
            }
        }

        return stack.isEmpty() ? ItemStack.EMPTY : stack;
    }
}