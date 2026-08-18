package com.voidiscoming.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.voidiscoming.common.block.ModBlocks;
import com.voidiscoming.common.entity.ModEntities;
import com.voidiscoming.common.item.ModItemGroups;
import com.voidiscoming.common.item.consumables.manaBottel.ModItems;
import com.voidiscoming.common.item.consumables.manaBottel.ModBrewingRecipes;
import com.voidiscoming.common.mechanic.ModMechanics;
import com.voidiscoming.common.network.ModNetworking;
import com.voidiscoming.server.command.ModCommands;

import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;

public class VoidIsComing implements ModInitializer {
    public static final String MOD_ID = "voidiscoming";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {

        ModBlocks.initialize();
        ModItems.initialize();

        ModEntities.registerModEntities();
        ModMechanics.registerMechanics();
        ModCommands.registerCommands();
        ModNetworking.registerPackets();

        ModBrewingRecipes.registerRecipes();

        ModItemGroups.initialize();
    }

    public static Identifier id(String path) {
        return new Identifier(MOD_ID, path);
    }
}