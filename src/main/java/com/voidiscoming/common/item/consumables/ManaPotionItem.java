package com.voidiscoming.common.item.consumables;

import com.voidiscoming.common.VoidIsComing;
import com.voidiscoming.common.component.ModComponents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
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

        // Просто уменьшаем предмет в руке, пустая бутылка не падает
        if (user instanceof PlayerEntity player && !player.isCreative()) {
            stack.decrement(1);
        }

        return stack.isEmpty() ? ItemStack.EMPTY : stack;
    }
}