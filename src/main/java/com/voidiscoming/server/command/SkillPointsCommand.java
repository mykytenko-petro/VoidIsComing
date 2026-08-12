package com.voidiscoming.server.command;

import com.mojang.brigadier.CommandDispatcher;
import com.voidiscoming.common.component.ModComponents;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

public class SkillPointsCommand {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(
            CommandManager.literal("skillpoints")
                .executes(context -> {
                    var player = context.getSource().getPlayer();
                    if (player != null) {
                        ModComponents.SKILLS.maybeGet(player).ifPresent(skills -> {
                            int points = skills.getSkillPoints();
                            
                            // Виводимо у консоль сервера
                            System.out.println("[DEBUG] Гравець " + player.getName().getString() + " має очок навичок: " + points);
                            
                            // Виводимо повідомлення гравцю в чат
                            player.sendMessage(Text.literal("§6[VoidIsComing] Твої очка навичок: §e" + points), false);
                        });
                    }
                    return 1;
                })
        );
    }
}