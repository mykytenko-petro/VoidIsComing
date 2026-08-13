package com.voidiscoming.server.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.voidiscoming.common.component.ModComponents;
import com.voidiscoming.common.mechanic.spell.ModSpells;
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
                // 1. Екіпірувати скілл у слот (0-3): /spelltest equip <слот> <id>
                .then(CommandManager.literal("equip")
                    .then(CommandManager.argument("slot", IntegerArgumentType.integer(0, 3))
                        .then(CommandManager.argument("spellId", IdentifierArgumentType.identifier())
                            .executes(SpellDebugCommand::executeEquip)
                        )
                    )
                )            
                // 2. Зняти скілл зі слоту (0-3): /spelltest unequip <слот>
                .then(CommandManager.literal("unequip")
                    .then(CommandManager.argument("slot", IntegerArgumentType.integer(0, 3))
                        .executes(SpellDebugCommand::executeUnequip)
                    )
                )
        );
    }

    private static int executeEquip(CommandContext<ServerCommandSource> context) {
        try {
            ServerPlayerEntity player = context.getSource().getPlayer();
            if (player == null) return 0;

            int slot = IntegerArgumentType.getInteger(context, "slot");
            Identifier spellId = IdentifierArgumentType.getIdentifier(context, "spellId");

            // Перевіряємо, чи існує такий скілл у загальному реєстрі мода
            if (ModSpells.get(spellId) == null) {
                context.getSource().sendFeedback(() -> Text.literal("§cПомилка: Скілл з таким ID не знайдено в реєстрах мода!"), false);
                return 0;
            }

            ModComponents.SPELLS.maybeGet(player).ifPresent(comp -> {
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
                context.getSource().sendFeedback(() -> Text.literal("§eСлот " + slot + " успішно очищено"), false);
            });

        } catch (Exception e) {
            context.getSource().sendFeedback(() -> Text.literal("§cПомилка: " + e.getMessage()), false);
        }
        return 1;
    }
}