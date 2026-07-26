package com.voidiscoming.server.command;

import com.mojang.brigadier.CommandDispatcher;

import net.minecraft.command.argument.EntityArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.voidiscoming.common.component.ManaComponent;
import com.voidiscoming.common.component.ModComponents;

import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public class TestCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("mana")
            .requires(source -> source.hasPermissionLevel(2))
            .then(CommandManager.argument("target", EntityArgumentType.player())
            .then(CommandManager.argument("amount", IntegerArgumentType.integer(0))
            .executes(ctx -> {
                ServerPlayerEntity player = EntityArgumentType.getPlayer(ctx, "target");
                int amount = IntegerArgumentType.getInteger(ctx, "amount");

                ManaComponent mana = ModComponents.MANA.get(player);
                mana.setMana(amount);

                ctx.getSource().sendFeedback(
                    () -> Text.literal("Set " + player.getName().getString() + "'s mana to " + amount),
                    true
                );

                return 1;
            }))));
    }
}
