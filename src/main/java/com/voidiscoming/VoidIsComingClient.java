package com.voidiscoming;

import com.voidiscoming.entity.VoidCowEntity;
import com.voidiscoming.entity.VoidPigEntity;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.CowEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.PigEntityModel;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class VoidIsComingClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(VoidIsComing.VOID_PIG, VoidPigRenderer::new);
        EntityRendererRegistry.register(VoidIsComing.VOID_COW, VoidCowRenderer::new);
    }

    public static class VoidPigRenderer extends MobEntityRenderer<VoidPigEntity, PigEntityModel<VoidPigEntity>> {

        private static final Identifier TEXTURE = new Identifier(VoidIsComing.MOD_ID, "textures/entity/pig.png");

        public VoidPigRenderer(EntityRendererFactory.Context context) {
            super(context, new PigEntityModel<>(context.getPart(EntityModelLayers.PIG)), 0.7F);
        }

        @Override
        public Identifier getTexture(VoidPigEntity entity) {
            return TEXTURE;
        }
    }

    public static class VoidCowRenderer extends MobEntityRenderer<VoidCowEntity, CowEntityModel<VoidCowEntity>> {

        private static final Identifier TEXTURE = new Identifier(VoidIsComing.MOD_ID, "textures/entity/cow.png");

        public VoidCowRenderer(EntityRendererFactory.Context context) {
            super(context, new CowEntityModel<>(context.getPart(EntityModelLayers.COW)), 0.7F);
        }

        @Override
        public Identifier getTexture(VoidCowEntity entity) {
            return TEXTURE;
        }
    }
}