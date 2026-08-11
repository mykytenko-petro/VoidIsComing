package com.voidiscoming.client.renderer;

import com.voidiscoming.client.model.ModModelLayers;
import com.voidiscoming.client.model.StoneGolemModel;
import com.voidiscoming.common.block.ModBlocks;
import com.voidiscoming.common.entity.ModEntities;

import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.RenderLayer;

public class ModRenderers {

    public static void registerRenderers() {
        // entities
        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.STONE_GOLEM, StoneGolemModel::getTexturedModelData);
        EntityRendererRegistry.register(ModEntities.VOID_PIG, VoidPigRenderer::new);
        EntityRendererRegistry.register(ModEntities.VOID_COW, VoidCowRenderer::new);
        EntityRendererRegistry.register(ModEntities.STONE_GOLEM, StoneGolemRenderer::new);
        EntityRendererRegistry.register(ModEntities.VOID_SHEEP, VoidSheepRenderer::new);

        // block
        BlockRenderLayerMap.INSTANCE.putBlock(
            ModBlocks.LITTLE_VOID_GRASS,
            RenderLayer.getCutout()
        );
    }
}