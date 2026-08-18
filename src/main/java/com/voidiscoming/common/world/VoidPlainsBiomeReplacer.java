package com.voidiscoming.common.world;

import com.voidiscoming.common.world.biome.ModBiomes;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;

public class VoidPlainsBiomeReplacer {

    private static final RegistryKey<Biome> PLAINS_KEY = RegistryKey.of(RegistryKeys.BIOME, Identifier.of("minecraft", "plains"));
    private static final int AREA_SIZE = 128;
    private static final double VOID_CHANCE = 0.40;

    public static RegistryEntry<Biome> replace(RegistryEntry<Biome> original, int x, int z) {
        RegistryEntry<Biome> voidPlains = ModBiomes.VOID_PLAINS;

        if (voidPlains == null || !original.matchesKey(PLAINS_KEY)) {
            return original;
        }

        int areaX = Math.floorDiv(x, AREA_SIZE);
        int areaZ = Math.floorDiv(z, AREA_SIZE);

        long hash = areaX * 341873128712L + areaZ * 132897987541L;
        hash ^= hash >>> 13;
        hash *= 1274126177L;
        hash ^= hash >>> 16;

        double random = (hash & Long.MAX_VALUE) / (double) Long.MAX_VALUE;

        return random < VOID_CHANCE ? voidPlains : original;
    }
}