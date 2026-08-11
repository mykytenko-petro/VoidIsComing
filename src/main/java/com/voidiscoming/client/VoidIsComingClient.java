package com.voidiscoming.client;

import com.voidiscoming.client.description.ModDescriptions;
import com.voidiscoming.client.gui.ModGUI;
import com.voidiscoming.client.renderer.ModRenderers;
import com.voidiscoming.common.block.ModBlocks;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;

@Environment(EnvType.CLIENT)
public class VoidIsComingClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        System.out.println("Client initialized");

        ModRenderers.registerRenderers();
        ModGUI.init();
        ModDescriptions.init();

        BlockRenderLayerMap.INSTANCE.putBlock(
                ModBlocks.LITTLE_VOID_GRASS,
                RenderLayer.getCutout()
        );
    }
}