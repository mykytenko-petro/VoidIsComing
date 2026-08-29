package com.voidiscoming.common.event;

import com.voidiscoming.common.item.ModItems;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;

public class BookGiveEvent {
    public static void register() {
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            ServerPlayerEntity player = handler.getPlayer();
            String tag = "voidiscoming_received_book";

            if (!player.getCommandTags().contains(tag)) {
                ItemStack book = new ItemStack(ModItems.MOD_BOOK);
                
                boolean added = player.getInventory().insertStack(book);
                if (!added) {
                    player.dropItem(book, false);
                }
                
                player.getCommandTags().add(tag);
            }
        });
    }
}