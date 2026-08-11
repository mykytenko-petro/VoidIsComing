package com.voidiscoming.server.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.voidiscoming.common.component.ModComponents;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.IdentifierArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class SpellDebugCommand {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess registryAccess, CommandManager.RegistrationEnvironment environment) {
        dispatcher.register(
            CommandManager.literal("spelltest")
                .then(CommandManager.literal("add")
                    .then(CommandManager.argument("spellId", IdentifierArgumentType.identifier())
                        .executes(SpellDebugCommand::executeAdd)
                    )
                )
                .then(CommandManager.literal("remove")
                    .then(CommandManager.argument("spellId", IdentifierArgumentType.identifier())
                        .executes(SpellDebugCommand::executeRemove)
                    )
                )
                .then(CommandManager.literal("equip")
                    .then(CommandManager.argument("slot", IntegerArgumentType.integer(0, 3))
                        .then(CommandManager.argument("spellId", IdentifierArgumentType.identifier())
                            .executes(SpellDebugCommand::executeEquip)
                        )
                    )
                )               
                .then(CommandManager.literal("unequip")
                    .then(CommandManager.argument("slot", IntegerArgumentType.integer(0, 3))
                        .executes(SpellDebugCommand::executeUnequip)
                    )
                )
        );
    }

    private static int executeAdd(CommandContext<ServerCommandSource> context) {
        try {
            ServerPlayerEntity player = context.getSource().getPlayer();
            if (player == null) return 0;

            Identifier spellId = IdentifierArgumentType.getIdentifier(context, "spellId");

            ModComponents.SPELLS.maybeGet(player).ifPresent(comp -> {
                comp.addSpell(spellId);
                context.getSource().sendFeedback(() -> Text.literal("§aУспішно додано скілл: " + spellId), false);
            });

        } catch (Exception e) {
            context.getSource().sendFeedback(() -> Text.literal("§cПомилка: " + e.getMessage()), false);
        }
        return 1;
    }

    private static int executeRemove(CommandContext<ServerCommandSource> context) {
        try {
            ServerPlayerEntity player = context.getSource().getPlayer();
            if (player == null) return 0;

            Identifier spellId = IdentifierArgumentType.getIdentifier(context, "spellId");

            ModComponents.SPELLS.maybeGet(player).ifPresent(comp -> {
                comp.removeSpell(spellId);
                context.getSource().sendFeedback(() -> Text.literal("§eУспішно забрано скілл: " + spellId), false);
            });

        } catch (Exception e) {
            context.getSource().sendFeedback(() -> Text.literal("§cПомилка: " + e.getMessage()), false);
        }
        return 1;
    }

    private static int executeEquip(CommandContext<ServerCommandSource> context) {
        try {
            ServerPlayerEntity player = context.getSource().getPlayer();
            if (player == null) return 0;

            int slot = IntegerArgumentType.getInteger(context, "slot");
            Identifier spellId = IdentifierArgumentType.getIdentifier(context, "spellId");

            ModComponents.SPELLS.maybeGet(player).ifPresent(comp -> {
                if (!comp.hasSpell(spellId)) {
                    context.getSource().sendFeedback(() -> Text.literal("§cПомилка: Спочатку вивчіть цей скілл (/spelltest add)!"), false);
                    return;
                }

                comp.equipSpell(slot, spellId);
                context.getSource().sendFeedback(() -> Text.literal("§bСкілл " + spellId + " успішно екіпіровано в слот " + slot), false);
            });

        } catch (Exception e) {
            context.getSource().sendFeedback(() -> Text.literal("§cПомилка: " + e.getMessage()), false);
        }
        return 1;
    }

    private static int executeUnequip(CommandContext<ServerCommandSource> context) {
        try {
            ServerPlayerEntity player = context.getSource().getPlayer();
            if (player == null) return 0;

            int slot = IntegerArgumentType.getInteger(context, "slot");

            ModComponents.SPELLS.maybeGet(player).ifPresent(comp -> {
                comp.unequipSpell(slot);
                context.getSource().sendFeedback(() -> Text.literal("§eСлот " + slot + " успішно очищено (скілл знято)"), false);
            });

        } catch (Exception e) {
            context.getSource().sendFeedback(() -> Text.literal("§cПомилка: " + e.getMessage()), false);
        }
        return 1;
    }
}