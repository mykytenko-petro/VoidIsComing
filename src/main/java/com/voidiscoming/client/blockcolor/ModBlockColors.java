package com.voidiscoming.client.blockcolor;

import com.voidiscoming.common.block.ModBlocks;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.client.color.world.GrassColors;

@Environment(EnvType.CLIENT)
public class ModBlockColors {

    public static void register() {
        ColorProviderRegistry.BLOCK.register(
                (state, world, pos, tintIndex) -> world != null && pos != null
                        ? BiomeColors.getGrassColor(world, pos)
                        : GrassColors.getColor(0.5D, 1.0D),
                ModBlocks.VOID_GRASS
        );

        ColorProviderRegistry.ITEM.register(
                (stack, tintIndex) -> GrassColors.getColor(0.5D, 1.0D),
                ModBlocks.VOID_GRASS
        );
    }
}