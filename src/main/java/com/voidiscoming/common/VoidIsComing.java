package com.voidiscoming.common;

import com.voidiscoming.common.entity.ModEntities;
import com.voidiscoming.server.command.ModCommands;

import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;

public class VoidIsComing implements ModInitializer {
    public static final String MOD_ID = "voidiscoming";

    @Override
    public void onInitialize() {
        ModCommands.registerCommands();
        ModEntities.registerModEntities();
    }

    public static Identifier id(String path) {
        return new Identifier(MOD_ID, path);
    }
}