package com.voidiscoming.common.item;

import com.voidiscoming.common.VoidIsComing;
import com.voidiscoming.common.block.ModBlocks;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroups {

    public static final ItemGroup VOID_IS_COMING = Registry.register(
            Registries.ITEM_GROUP,
            Identifier.of(VoidIsComing.MOD_ID, "void_is_coming"),
            FabricItemGroup.builder()
                    .displayName(Text.translatable("itemGroup.voidiscoming.void_is_coming"))
                    .icon(() -> new ItemStack(ModBlocks.VOID_GRASS))
                    .entries((context, entries) -> {
                        entries.add(ModBlocks.VOID_GRASS);
                        entries.add(ModBlocks.LITTLE_VOID_GRASS);
                    })
                    .build()
    );

    public static void initialize() {
    }
}