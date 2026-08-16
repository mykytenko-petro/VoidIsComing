package com.voidiscoming.server.command;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;

public class ModCommands {
    public static void registerCommands() {
        CommandRegistrationCallback.EVENT.register(
            (dispatcher, registryAccess, environment) -> {
            
                TestCommand.register(dispatcher);
                // SpellDebugCommand.register(dispatcher, registryAccess, environment);
                SkillPointsCommand.register(dispatcher);
            }
        );
    }
}