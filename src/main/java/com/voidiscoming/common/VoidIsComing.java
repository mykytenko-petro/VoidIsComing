package com.voidiscoming.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.voidiscoming.common.entity.ModEntities;
import com.voidiscoming.common.mechanic.ModMechanics;
import com.voidiscoming.common.mechanic.spell.ModSpells;
import com.voidiscoming.server.command.ModCommands;

import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;

public class VoidIsComing implements ModInitializer {
    public static final String MOD_ID = "voidiscoming";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static final Identifier USE_SPELL_PACKET = id("use_spell");

    @Override
    public void onInitialize() {
        LOGGER.info("Initializing Void Is Coming...");

        ModMechanics.registerMechanics();
        ModCommands.registerCommands();
        ModEntities.registerModEntities();
        ModSpells.registerSpells();
    }

    public static Identifier id(String path) {
        return new Identifier(MOD_ID, path);
    }
}