package com.voidiscoming.client.Model;

import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;
import net.minecraft.client.util.math.MatrixStack;

public class StoneGolemModel<T extends Entity> extends EntityModel<T> {
    public static final EntityModelLayer LAYER_LOCATION = new EntityModelLayer(new Identifier("voidiscoming", "stone_golem"), "main");

    private final ModelPart face;
    private final ModelPart body;
    private final ModelPart rightLegs;
    private final ModelPart leftLegs;
    private final ModelPart leftHands;
    private final ModelPart rightHands;

    public StoneGolemModel(ModelPart root) {
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

        ModelPartData face = modelPartData.addChild("face", ModelPartBuilder.create().uv(0, 36).cuboid(0.5F, -4.5F, 0.0F, 4.0F, 5.0F, 5.0F, new Dilation(0.0F)), ModelTransform.pivot(-3.0F, 12.0F, 2.0F));

        ModelPartData body = modelPartData.addChild("Body", ModelPartBuilder.create().uv(0, 0).cuboid(-8.5F, -13.5F, 0.5F, 8.0F, 10.0F, 8.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

        ModelPartData rightLegs = modelPartData.addChild("Right Legs", ModelPartBuilder.create().uv(60, 50).cuboid(-5.5F, -4.0F, 1.0F, 2.0F, 4.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

        ModelPartData leftLegs = modelPartData.addChild("Left Legs", ModelPartBuilder.create().uv(60, 36).cuboid(-5.5F, -4.0F, 5.0F, 2.0F, 4.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

        ModelPartData leftHands = modelPartData.addChild("Left Hands", ModelPartBuilder.create().uv(0, 56).cuboid(-6.5F, -13.0F, 8.5F, 3.0F, 10.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

        ModelPartData rightHands = modelPartData.addChild("Right Hands", ModelPartBuilder.create().uv(36, 36).cuboid(-6.5F, -13.0F, -2.5F, 3.0F, 10.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

        return TexturedModelData.of(modelData, 128, 128);
    }

    @Override
    public void setAngles(Entity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {

    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
        face.render(matrices, vertices, light, overlay, red, green, blue, alpha);
        body.render(matrices, vertices, light, overlay, red, green, blue, alpha);
        rightLegs.render(matrices, vertices, light, overlay, red, green, blue, alpha);
        leftLegs.render(matrices, vertices, light, overlay, red, green, blue, alpha);
        leftHands.render(matrices, vertices, light, overlay, red, green, blue, alpha);
        rightHands.render(matrices, vertices, light, overlay, red, green, blue, alpha);
    }
}