package com.voidiscoming.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.voidiscoming.common.entity.ModEntities;
import com.voidiscoming.common.event.ModEvents;
import com.voidiscoming.common.mechanic.ModMechanics;
import com.voidiscoming.server.command.ModCommands;

import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;

public class VoidIsComing implements ModInitializer {
    public static final String MOD_ID = "voidiscoming";

    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("Initializing Void Is Coming...");

        ModMechanics.init();
        ModCommands.registerCommands();
        ModEntities.registerModEntities();
        ModEvents.registerEvents();
    }

    public static Identifier id(String path) {
        return new Identifier(MOD_ID, path);
    }
}