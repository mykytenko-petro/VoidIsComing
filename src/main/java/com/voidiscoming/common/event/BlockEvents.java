package com.voidiscoming.common.event;

import com.voidiscoming.client.gui.screen.ComingSoonScreen;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.block.AnvilBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.EnchantingTableBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.ActionResult;

public class BlockEvents {
    public static void register() {
        UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
            BlockState state = world.getBlockState(hitResult.getBlockPos());

            if (state.getBlock() instanceof AnvilBlock || state.getBlock() instanceof EnchantingTableBlock) {
                // Открываем кастомный GUI только на клиенте
                if (world.isClient) {
                    MinecraftClient.getInstance().setScreen(new ComingSoonScreen());
                }
                // Возвращаем SUCCESS, чтобы отменить открытие стандартной наковальни/стола
                return ActionResult.SUCCESS;
            }

            return ActionResult.PASS;
        });
    }
}