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
            new Identifier(VoidIsComing.MOD_ID, "void_is_coming"),
            FabricItemGroup.builder()
                    .displayName(Text.translatable("itemGroup.voidiscoming.void_is_coming"))
                    .icon(() -> new ItemStack(ModBlocks.VOID_GRASS))
                    .entries((context, entries) -> {
                        entries.add(ModBlocks.VOID_GRASS);
                        entries.add(ModBlocks.LITTLE_VOID_GRASS);

                        entries.add(ModItems.SMALL_MANA_BOTTLE);
                        entries.add(ModItems.MEDIUM_MANA_BOTTLE);
                        entries.add(ModItems.LARGE_MANA_BOTTLE);
                        entries.add(ModItems.VOID_COW_HORN);
                        entries.add(ModItems.BIG_EMPTY_BOTTLE);
                        entries.add(ModItems.EMPTY_SMALL_BOTTLE);
                        entries.add(ModItems.VOID_ESSENCE);
                        entries.add(ModItems.VOID_PIG_TAIL);
                        entries.add(ModItems.HEAL_BOTTLE);
                    })
                    .build()
    );

    public static void initialize() {
        // Метод для вызова из главного класса
    }
}