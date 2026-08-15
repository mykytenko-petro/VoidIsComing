package com.voidiscoming.common.world.biome;

import net.fabricmc.fabric.api.event.registry.DynamicRegistrySetupCallback;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;

public class ModBiomes {

    public static final RegistryKey<Biome> VOID_PLAINS_KEY = RegistryKey.of(RegistryKeys.BIOME, Identifier.of("voidiscoming", "void_plains"));
    public static RegistryEntry<Biome> VOID_PLAINS;

    public static void register() {
        DynamicRegistrySetupCallback.EVENT.register(registryView -> {
            registryView.registerEntryAdded(RegistryKeys.BIOME, (rawId, id, biome) -> {
                if (id.equals(VOID_PLAINS_KEY.getValue())) {
                    Registry<Biome> registry = registryView.getOptional(RegistryKeys.BIOME).orElse(null);
                    if (registry != null) {
                        VOID_PLAINS = registry.getEntry(VOID_PLAINS_KEY).orElse(null);
                    }
                }
            });
        });
    }
}