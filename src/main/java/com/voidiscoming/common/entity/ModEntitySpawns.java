package com.voidiscoming.common.entity;

import com.voidiscoming.common.world.biome.ModBiomes;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.entity.SpawnGroup;

public class ModEntitySpawns {

    public static void register() {
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(ModBiomes.VOID_PLAINS_KEY), SpawnGroup.CREATURE, ModEntities.VOID_PIG, 5, 3, 3);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(ModBiomes.VOID_PLAINS_KEY), SpawnGroup.CREATURE, ModEntities.VOID_COW, 4, 2, 2);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(ModBiomes.VOID_PLAINS_KEY), SpawnGroup.CREATURE, ModEntities.VOID_SHEEP, 3, 1, 1);
    }
}