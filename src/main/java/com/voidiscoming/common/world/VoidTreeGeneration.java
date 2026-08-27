package com.voidiscoming.common.world;

import com.voidiscoming.common.world.biome.ModBiomes;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;

import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.PlacedFeature;

public class VoidTreeGeneration {

    private static final RegistryKey<PlacedFeature> VOID_TREE_PLACED = RegistryKey.of(
            RegistryKeys.PLACED_FEATURE,
            Identifier.of("voidiscoming", "void_tree")
    );

    public static void register() {
        BiomeModifications.addFeature(
                BiomeSelectors.includeByKey(ModBiomes.VOID_PLAINS_KEY),
                GenerationStep.Feature.VEGETAL_DECORATION,
                VOID_TREE_PLACED
        );

        System.out.println("[VoidIsComing] Void Tree natural generation registered!");
    }
}