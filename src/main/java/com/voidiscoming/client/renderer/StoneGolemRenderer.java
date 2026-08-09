package com.voidiscoming.client.renderer;

import com.voidiscoming.common.entity.stoneGolem.StoneGolemEntity;
import com.voidiscoming.client.model.StoneGolemModel;
import com.voidiscoming.client.model.ModModelLayers;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.RotationAxis;

public class StoneGolemRenderer extends MobEntityRenderer<StoneGolemEntity, StoneGolemModel<StoneGolemEntity>> {

    public StoneGolemRenderer(EntityRendererFactory.Context context) {
        super(context, new StoneGolemModel<>(context.getPart(ModModelLayers.STONE_GOLEM)), 0.7f);
    }

    @Override
    public Identifier getTexture(StoneGolemEntity entity) {
        return new Identifier("voidiscoming", "textures/entity/stone_golem.png");
    }

    @Override
    protected void scale(StoneGolemEntity entity, MatrixStack matrices, float amount) {
        matrices.scale(2.5f, 2.5f, 2.5f);
    }

    @Override
    protected void setupTransforms(StoneGolemEntity entity, MatrixStack matrices, float animationProgress, float bodyYaw, float tickDelta) {
        super.setupTransforms(entity, matrices, animationProgress, bodyYaw, tickDelta);

        matrices.translate(0.5f, 0.0f, -1.15f);

         matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180));
    }
}