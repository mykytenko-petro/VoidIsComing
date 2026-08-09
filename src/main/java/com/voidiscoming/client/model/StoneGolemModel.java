package com.voidiscoming.client.model;

import com.voidiscoming.client.animation.stoneGolem.StoneGolemMoveAnimation;
import com.voidiscoming.client.animation.stoneGolem.StoneGolemSlamAnimation;
import com.voidiscoming.client.animation.stoneGolem.StoneGolemTrowAnimation;
import com.voidiscoming.common.entity.stonegolem.StoneGolemEntity;

import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class StoneGolemModel<T extends StoneGolemEntity> extends SinglePartEntityModel<T> {

    public static final EntityModelLayer LAYER_LOCATION = new EntityModelLayer(new Identifier("voidiscoming", "stone_golem"), "main");

    private final ModelPart root;
    private final ModelPart face;
    private final ModelPart body;
    private final ModelPart rightLegs;
    private final ModelPart leftLegs;
    private final ModelPart leftHands;
    private final ModelPart rightHands;

    public StoneGolemModel(ModelPart root) {
        this.root = root;
        this.face = root.getChild("face");
        this.body = root.getChild("Body");
        this.rightLegs = root.getChild("Right Legs");
        this.leftLegs = root.getChild("Left Legs");
        this.leftHands = root.getChild("Left Hands");
        this.rightHands = root.getChild("Right Hands");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        modelPartData.addChild("face", ModelPartBuilder.create().uv(2, 38).cuboid(-5.0F, -21.5F, -2.0F, 8.0F, 9.0F, 8.0F, new Dilation(0.0F)), ModelTransform.pivot(-3.0F, 12.0F, 2.0F));
        modelPartData.addChild("Body", ModelPartBuilder.create().uv(11, 66).cuboid(-16.5F, -30.0F, -6.5F, 14.0F, 20.0F, 21.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));
        modelPartData.addChild("Right Legs", ModelPartBuilder.create().uv(66, 47).cuboid(-14.0F, -12.0F, -4.5F, 7.0F, 12.0F, 8.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));
        modelPartData.addChild("Left Legs", ModelPartBuilder.create().uv(35, 38).cuboid(-14.0F, -12.0F, 4.5F, 7.0F, 12.0F, 8.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));
        modelPartData.addChild("Left Hands", ModelPartBuilder.create().uv(8, 1).cuboid(-15.5F, -28.0F, 14.5F, 9.0F, 21.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));
        modelPartData.addChild("Right Hands", ModelPartBuilder.create().uv(37, 16).cuboid(-14.5F, -28.0F, -10.0F, 9.0F, 21.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

        return TexturedModelData.of(modelData, 128, 128);
    }

    @Override
    public ModelPart getPart() {
        return this.root;
    }

    @Override
    public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        this.getPart().traverse().forEach(ModelPart::resetTransform);

        this.root.yaw = -1.5707963F;

//        this.face.yaw = headYaw * 0.017453292F;
//        this.face.pitch = headPitch * 0.017453292F;

        this.updateAnimation(entity.slamAnimationState, StoneGolemSlamAnimation.GROUND_SLAM, animationProgress);
        this.updateAnimation(entity.throwAnimationState, StoneGolemTrowAnimation.ATTACK_THROW, animationProgress);
        this.updateAnimation(entity.moveAnimationState, StoneGolemMoveAnimation.MOVE, animationProgress);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
        this.root.render(matrices, vertices, light, overlay, red, green, blue, alpha);
    }
}