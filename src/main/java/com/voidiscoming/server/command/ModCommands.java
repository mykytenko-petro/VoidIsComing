package com.voidiscoming.server.command;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;

public class ModCommands {
    public static void registerCommands() {
        CommandRegistrationCallback.EVENT.register(
            (dispatcher, registryAccess, environment) -> {
                // Реєструємо тестові команди та інші майбутні команди сервера
                TestCommand.register(dispatcher);
                SpellDebugCommand.register(dispatcher, registryAccess, environment);
            }
        );
    }
}