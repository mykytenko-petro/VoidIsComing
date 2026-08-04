package com.voidiscoming.client.renderer;

import com.voidiscoming.client.model.ModModelLayers;
import com.voidiscoming.client.model.StoneGolemModel;
import com.voidiscoming.common.entity.ModEntities;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

@Environment(EnvType.CLIENT)
public class ModRenderers {
    public static void registerRenderers() {
        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.STONE_GOLEM, StoneGolemModel::getTexturedModelData);
        EntityRendererRegistry.register(ModEntities.VOID_PIG, VoidPigRenderer::new);
        EntityRendererRegistry.register(ModEntities.VOID_COW, VoidCowRenderer::new);
        EntityRendererRegistry.register(ModEntities.STONE_GOLEM, StoneGolemRenderer::new);
    }
}