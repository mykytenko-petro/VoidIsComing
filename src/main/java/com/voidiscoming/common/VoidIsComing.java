package com.voidiscoming.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.voidiscoming.common.block.ModBlocks;
import com.voidiscoming.common.effects.ModEffects;
import com.voidiscoming.common.entity.ModEntities;
import com.voidiscoming.common.entity.ModEntitySpawns;
import com.voidiscoming.common.event.ModEvents;
import com.voidiscoming.common.item.ModItemGroups;
import com.voidiscoming.common.item.ModItems;
import com.voidiscoming.common.mechanic.ModMechanics;
import com.voidiscoming.common.network.ModNetworking;
import com.voidiscoming.common.recipe.brewing.ModBrewingRecipes;
import com.voidiscoming.common.world.VoidTreeGeneration;
import com.voidiscoming.common.world.VoidWorldSpawn;
import com.voidiscoming.common.world.biome.ModBiomes;
import com.voidiscoming.server.command.ModCommands;

import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;

public class VoidIsComing implements ModInitializer {
    public static final String MOD_ID = "voidiscoming";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        ModItems.initialize();
        ModEntities.registerModEntities();
        ModMechanics.registerMechanics();
        ModCommands.registerCommands();
        ModEntitySpawns.register();
        ModBlocks.initialize();
        ModItemGroups.initialize();
        ModNetworking.registerPackets();
        ModEffects.registerEffects();
        ModBiomes.register();
        VoidWorldSpawn.register();
        VoidTreeGeneration.register();
        ModBrewingRecipes.registerRecipes();
        ModEvents.registerEvents();
    }

    public static Identifier id(String path) {
        return new Identifier(MOD_ID, path);
    }
}
