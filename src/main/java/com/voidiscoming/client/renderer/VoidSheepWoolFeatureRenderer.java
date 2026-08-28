package com.voidiscoming.client.renderer;

import com.voidiscoming.common.VoidIsComing;
import com.voidiscoming.common.entity.VoidSheepEntity;
import com.voidiscoming.mixin.common.entity.QuadrupedEntityModelAccessor;

import net.minecraft.client.model.ModelPart;
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

        this.model.animateModel(
                sheep,
                limbAngle,
                limbDistance,
                tickDelta
        );

        this.getContextModel().copyStateTo(this.model);

        QuadrupedEntityModelAccessor woolAccessor =
                (QuadrupedEntityModelAccessor) this.model;

        QuadrupedEntityModelAccessor mainAccessor =
                (QuadrupedEntityModelAccessor) this.getContextModel();

        ModelPart woolHead = woolAccessor.voidiscoming$getHead();
        ModelPart mainHead = mainAccessor.voidiscoming$getHead();

        woolHead.yaw = mainHead.yaw;
        woolHead.pitch = mainHead.pitch;
        woolHead.roll = mainHead.roll;

        ModelPart woolLeftFrontLeg =
                woolAccessor.voidiscoming$getLeftFrontLeg();

        ModelPart mainLeftFrontLeg =
                mainAccessor.voidiscoming$getLeftFrontLeg();

        ModelPart woolRightFrontLeg =
                woolAccessor.voidiscoming$getRightFrontLeg();

        ModelPart mainRightFrontLeg =
                mainAccessor.voidiscoming$getRightFrontLeg();

        ModelPart woolLeftHindLeg =
                woolAccessor.voidiscoming$getLeftHindLeg();

        ModelPart mainLeftHindLeg =
                mainAccessor.voidiscoming$getLeftHindLeg();

        ModelPart woolRightHindLeg =
                woolAccessor.voidiscoming$getRightHindLeg();

        ModelPart mainRightHindLeg =
                mainAccessor.voidiscoming$getRightHindLeg();

        woolLeftFrontLeg.pitch = mainLeftFrontLeg.pitch;
        woolLeftFrontLeg.yaw = mainLeftFrontLeg.yaw;
        woolLeftFrontLeg.roll = mainLeftFrontLeg.roll;

        woolRightFrontLeg.pitch = mainRightFrontLeg.pitch;
        woolRightFrontLeg.yaw = mainRightFrontLeg.yaw;
        woolRightFrontLeg.roll = mainRightFrontLeg.roll;

        woolLeftHindLeg.pitch = mainLeftHindLeg.pitch;
        woolLeftHindLeg.yaw = mainLeftHindLeg.yaw;
        woolLeftHindLeg.roll = mainLeftHindLeg.roll;

        woolRightHindLeg.pitch = mainRightHindLeg.pitch;
        woolRightHindLeg.yaw = mainRightHindLeg.yaw;
        woolRightHindLeg.roll = mainRightHindLeg.roll;

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