package com.voidiscoming.client;

import com.voidiscoming.client.description.ModDescriptions;
import com.voidiscoming.client.gui.ModGUI;
import com.voidiscoming.client.renderer.ModRenderers;
import com.voidiscoming.common.block.ModBlocks;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.client.color.world.BiomeColors;

@Environment(EnvType.CLIENT)
public class VoidIsComingClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        System.out.println("Client initialized");
        ModRenderers.registerRenderers();
        ModGUI.init();
        ModDescriptions.init();

        ColorProviderRegistry.BLOCK.register(
            (state, world, pos, tintIndex) ->
                world != null && pos != null
                    ? BiomeColors.getGrassColor(world, pos)
                    : 0x91BD59,
            ModBlocks.VOID_GRASS
        );
    }
}