package com.voidiscoming.common.block;

import com.voidiscoming.common.VoidIsComing;


import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

public class ModBlocks {

    public static final Block LITTLE_VOID_GRASS = register(
            "little_void_grass",
            new LittleVoidGrass(
                    FabricBlockSettings.create()
                            .noCollision()
                            .breakInstantly()
                            .sounds(BlockSoundGroup.GRASS)
            )
    );

    public static final Block VOID_GRASS = register(
            "void_grass",
            new VoidGrass(
                    FabricBlockSettings.copyOf(Blocks.GRASS_BLOCK)
            )
    );

    private static Block register(String name, Block block) {
        Registry.register(Registries.BLOCK, Identifier.of(VoidIsComing.MOD_ID, name), block);
        Registry.register(Registries.ITEM, Identifier.of(VoidIsComing.MOD_ID, name), new BlockItem(block, new Item.Settings()));
        return block;
    }

    public static void initialize() {
    }
}