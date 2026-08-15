package com.voidiscoming.common.world;

import com.voidiscoming.common.world.biome.ModBiomes;

import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;

public class VoidPlainsBiomeReplacer {

    private static final RegistryKey<Biome> PLAINS_KEY = RegistryKey.of(RegistryKeys.BIOME, Identifier.of("minecraft", "plains"));

    public static RegistryEntry<Biome> replace(RegistryEntry<Biome> original, int x, int z) {
        RegistryEntry<Biome> voidPlains = ModBiomes.VOID_PLAINS;

        if (voidPlains == null) {
            return original;
        }

        if (!original.matchesKey(PLAINS_KEY)) {
            return original;
        }

        return voidPlains;
    }
}