package com.voidiscoming.client.renderer;

import com.voidiscoming.common.VoidIsComing;
import com.voidiscoming.common.entity.VoidSheepEntity;

import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.EntityModelLoader;
import net.minecraft.client.render.entity.model.SheepEntityModel;
import net.minecraft.client.render.entity.model.SheepWoolEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class VoidSheepWoolFeatureRenderer
        extends FeatureRenderer<VoidSheepEntity, SheepEntityModel<VoidSheepEntity>> {

    private static final Identifier WOOL_TEXTURE =
            new Identifier(
                    VoidIsComing.MOD_ID,
                    "textures/entity/sheep_wool.png"
            );

    private final SheepWoolEntityModel<VoidSheepEntity> model;

    public VoidSheepWoolFeatureRenderer(
            FeatureRendererContext<VoidSheepEntity, SheepEntityModel<VoidSheepEntity>> context,
            EntityModelLoader loader
    ) {
        super(context);

        this.model = new SheepWoolEntityModel<>(
                loader.getModelPart(EntityModelLayers.SHEEP_FUR)
        );
    }

    @Override
    public void render(
            MatrixStack matrices,
            VertexConsumerProvider vertexConsumers,
            int light,
            VoidSheepEntity sheep,
            float limbAngle,
            float limbDistance,
            float age,
            float headYaw,
            float headPitch,
            float tickDelta
    ) {
        if (sheep.isSheared()) {
            return;
        }

        // Анимация шерсти
        this.model.animateModel(
                sheep,
                limbAngle,
                limbDistance,
                tickDelta
        );

        this.model.setAngles(
                sheep,
                limbAngle,
                limbDistance,
                age,
                headYaw,
                headPitch
        );

        VertexConsumer vertexConsumer =
                vertexConsumers.getBuffer(
                        RenderLayer.getEntityCutoutNoCull(WOOL_TEXTURE)
                );

        this.model.render(
                matrices,
                vertexConsumer,
                light,
                OverlayTexture.DEFAULT_UV,
                1.0F,
                1.0F,
                1.0F,
                1.0F
        );
    }
}