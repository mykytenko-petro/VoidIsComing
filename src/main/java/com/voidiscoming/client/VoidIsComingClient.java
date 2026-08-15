package com.voidiscoming.client;

import com.voidiscoming.client.event.ClientModEvents;
import com.voidiscoming.client.gui.ModGUI;
import com.voidiscoming.client.keybind.ModKeyBindings;
import com.voidiscoming.client.renderer.ModRenderers;
import com.voidiscoming.common.block.ModBlocks;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;

public class VoidIsComingClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        System.out.println("Client initialized");

        ModRenderers.registerRenderers();
        ModBlockColors.register();
        ModKeyBindings.registerBindings();
        ClientModEvents.registerEvents();
        ModGUI.init();

        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.VOID_SAPLING, RenderLayer.getCutout());
    }
}