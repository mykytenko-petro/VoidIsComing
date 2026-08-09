package com.voidiscoming.client.renderer;

import com.voidiscoming.common.VoidIsComing;
import com.voidiscoming.common.entity.VoidCowEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.CowEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.util.Identifier;

public class VoidCowRenderer extends MobEntityRenderer<VoidCowEntity, CowEntityModel<VoidCowEntity>> {
    private static final Identifier TEXTURE = new Identifier(VoidIsComing.MOD_ID, "textures/entity/cow.png");

    public VoidCowRenderer(EntityRendererFactory.Context context) {
        super(context, new CowEntityModel<>(context.getPart(EntityModelLayers.COW)), 0.7F);
    }

    @Override
    public Identifier getTexture(VoidCowEntity entity) {
        return TEXTURE;
    }
}