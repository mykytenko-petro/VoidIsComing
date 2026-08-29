package com.voidiscoming.common.item;

import com.voidiscoming.client.gui.screen.ModBookScreen;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class ModBookItem extends Item {
    public ModBookItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);

        // Открываем графический экран книги на клиенте
        if (world.isClient) {
            MinecraftClient.getInstance().setScreen(new ModBookScreen());
        }

        return TypedActionResult.success(itemStack, world.isClient());
    }
}