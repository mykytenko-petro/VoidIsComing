package com.voidiscoming.common.block;

import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.gen.feature.ConfiguredFeature;

import org.jetbrains.annotations.Nullable;

public class VoidSaplingGenerator extends SaplingGenerator {

    private static final RegistryKey<ConfiguredFeature<?, ?>> VOID_TREE = RegistryKey.of(
            RegistryKeys.CONFIGURED_FEATURE,
            Identifier.of("voidiscoming", "void_tree")
    );

    @Override
    @Nullable
    protected RegistryKey<ConfiguredFeature<?, ?>> getTreeFeature(Random random, boolean bees) {
        return VOID_TREE;
    }
}