/**
*
*/
package com.voidiscoming.common.item.consumables.manaBottel;

import net.fabricmc.fabric.api.registry.FabricBrewingRecipeRegistry;
import net.minecraft.item.Items;
import net.minecraft.item.PotionItem;
import net.minecraft.recipe.Ingredient;

public class ModBrewingRecipes {

    public static void registerRecipes() {
        FabricBrewingRecipeRegistry.registerItemRecipe(
                (PotionItem) Items.POTION,
                Ingredient.ofItems(Items.STONE),
                (PotionItem) ModItems.MEDIUM_MANA_BOTTLE
        );

    }
}