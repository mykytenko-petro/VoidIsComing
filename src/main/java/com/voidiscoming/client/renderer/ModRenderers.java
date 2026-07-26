package com.voidiscoming.client.renderer;

import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import com.voidiscoming.common.entity.ModEntities;
import net.fabricmc.api.EnvType;

@Environment(EnvType.CLIENT)
public class ModRenderers {
    public static void registerRenderers() {
        EntityRendererRegistry.register(ModEntities.VOID_PIG, VoidPigRenderer::new);
        EntityRendererRegistry.register(ModEntities.VOID_COW, VoidCowRenderer::new);
    }
}
