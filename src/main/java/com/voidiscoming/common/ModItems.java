package com.voidiscoming.common;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.item.ShieldItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ModItems {

    public static final Item WOODEN_SHIELD = Registry.register(
            Registries.ITEM,
            VoidIsComing.id("wooden_shield"),
            new ShieldItem(new FabricItemSettings().maxDamage(120))
    );

    public static final Item GOLDEN_SHIELD = Registry.register(
            Registries.ITEM,
            VoidIsComing.id("golden_shield"),
            new ShieldItem(new FabricItemSettings().maxDamage(240))
    );

    public static final Item DIAMOND_SHIELD = Registry.register(
            Registries.ITEM,
            VoidIsComing.id("diamond_shield"),
            new ShieldItem(new FabricItemSettings().maxDamage(600))
    );

    public static final Item NETHERITE_SHIELD = Registry.register(
            Registries.ITEM,
            VoidIsComing.id("netherite_shield"),
            new ShieldItem(new FabricItemSettings().maxDamage(900).fireproof())
    );

    public static void registerModItems() {
        // Метод нужен только для вызова регистрации
    }
}