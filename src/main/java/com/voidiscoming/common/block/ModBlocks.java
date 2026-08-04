package com.voidiscoming.common.block;

import com.voidiscoming.common.VoidIsComing;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlocks {

    public static final Block VOID_GRASS = register(
            "void_grass",
            new VoidGrass(Block.Settings.create().strength(1.0f))
    );

    private static Block register(String name, Block block) {
        Registry.register(Registries.BLOCK, Identifier.of(VoidIsComing.MOD_ID, name), block);

        Registry.register(
                Registries.ITEM,
                Identifier.of(VoidIsComing.MOD_ID, name),
                new BlockItem(block, new Item.Settings())
        );

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.BUILDING_BLOCKS)
                .register(entries -> entries.add(block));

        return block;
    }

    public static void initialize() {}
}