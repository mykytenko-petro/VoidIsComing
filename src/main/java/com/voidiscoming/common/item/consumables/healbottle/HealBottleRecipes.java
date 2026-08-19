package com.voidiscoming.common.item.consumables.healbottle;

import com.voidiscoming.common.item.ModItems;

import net.fabricmc.fabric.api.registry.FabricBrewingRecipeRegistry;
import net.minecraft.item.Items;
import net.minecraft.item.PotionItem;
import net.minecraft.recipe.Ingredient;

public class HealBottleRecipes {

    public static void registerRecipes() {
        FabricBrewingRecipeRegistry.registerItemRecipe(
                (PotionItem) Items.POTION,
                Ingredient.ofItems(ModItems.VOID_COW_HORN),
                (PotionItem) ModItems.HEAL_BOTTLE
        );

    }
}