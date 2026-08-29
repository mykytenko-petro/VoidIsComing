package com.voidiscoming.common.item;

import com.voidiscoming.common.VoidIsComing;
import com.voidiscoming.common.item.consumables.HealBottleItem;
import com.voidiscoming.common.item.consumables.ManaPotionItem;
import com.voidiscoming.common.item.consumables.WandItem;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {

    public static final Item SMALL_MANA_BOTTLE = register("small_mana_potion",
            new ManaPotionItem(new Item.Settings().maxCount(1), 4.0));

    public static final Item MEDIUM_MANA_BOTTLE = register("mana_potion",
            new ManaPotionItem(new Item.Settings().maxCount(1), 8.0));

    public static final Item LARGE_MANA_BOTTLE = register("big_mana_potion",
            new ManaPotionItem(new Item.Settings().maxCount(1), 12.0));

    public static final Item VOID_ESSENCE = register("void_essence",
            new Item(new Item.Settings()));

    public static final Item EMPTY_SMALL_BOTTLE = register("empty_small_bottle",
            new Item(new Item.Settings().maxCount(16)));

    public static final Item BIG_EMPTY_BOTTLE = register("big_empty_bottle",
            new Item(new Item.Settings().maxCount(16)));

    public static final Item VOID_PIG_TAIL = register("void_pig_tail", new Item(new Item.Settings()));
    public static final Item VOID_COW_HORN = register("void_cow_horn", new Item(new Item.Settings()));

    public static final Item HEAL_BOTTLE = register("heal_potion",
            new HealBottleItem(new FabricItemSettings().maxCount(16)));

    public static final Item WOODEN_STAFF = register("wooden_wand",
        new WandItem(new FabricItemSettings().maxDamage(60), 6.0F));

    public static final Item IRON_STAFF = register("iron_wand",
        new WandItem(new FabricItemSettings().maxDamage(150), 8.0F));

    public static final Item DIAMOND_STAFF = register("diamond_wand",
        new WandItem(new FabricItemSettings().maxDamage(400), 10.0F));

    public static final Item NETHERITE_STAFF = register("netherite_wand",
        new WandItem(new FabricItemSettings().maxDamage(750), 12.0F));

    // Используем ModBookItem, чтобы книга открывала интерфейс при клике ПКМ
    public static final Item MOD_BOOK = register("mod_book",
            new ModBookItem(new FabricItemSettings().maxCount(1)));

    private static Item register(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(VoidIsComing.MOD_ID, name), item);
    }

    public static void initialize() {
    }
}