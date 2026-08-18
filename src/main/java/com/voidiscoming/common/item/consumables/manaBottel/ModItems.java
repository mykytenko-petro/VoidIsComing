package com.voidiscoming.common.item.consumables.manaBottel;

import com.voidiscoming.common.VoidIsComing;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {

    public static final Item SMALL_MANA_BOTTLE = register("small_mana_bottle",
            new ManaBottleItem(new Item.Settings().maxCount(1), 4.0));

    public static final Item MEDIUM_MANA_BOTTLE = register("mana_bottle",
            new ManaBottleItem(new Item.Settings().maxCount(1), 8.0));

    public static final Item LARGE_MANA_BOTTLE = register("big_mana_bottle",
            new ManaBottleItem(new Item.Settings().maxCount(1), 12.0));

    public static final Item VOID_ESSENCE = register("void_essence",
            new Item(new Item.Settings()));

    public static final Item EMPTY_SMALL_BOTTLE = register("empty_small_bottle",
            new Item(new Item.Settings().maxCount(16)));

    public static final Item BIG_EMPTY_BOTTLE = register("big_empty_bottle",
            new Item(new Item.Settings().maxCount(16)));

    private static Item register(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(VoidIsComing.MOD_ID, name), item);
    }

    public static final Item VOID_PIG_TAIL = register("void_pig_tail", new Item(new Item.Settings()));
    public static final Item VOID_COW_HORN = register("void_cow_horn", new Item(new Item.Settings()));

    public static void initialize() {
    }
}