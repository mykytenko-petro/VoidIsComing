package com.voidiscoming.client.renderer;

import com.voidiscoming.common.VoidIsComing;
import com.voidiscoming.common.entity.VoidPigEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.PigEntityModel;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class VoidPigRenderer extends MobEntityRenderer<VoidPigEntity, PigEntityModel<VoidPigEntity>> {

    private static final Identifier TEXTURE = new Identifier(VoidIsComing.MOD_ID, "textures/entity/pig.png");

    public VoidPigRenderer(EntityRendererFactory.Context context) {
        super(context, new PigEntityModel<>(context.getPart(EntityModelLayers.PIG)), 0.7F);
    }

    @Override
    public Identifier getTexture(VoidPigEntity entity) {
        return TEXTURE;
    }
}