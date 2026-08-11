package com.voidiscoming.client.renderer;

import com.voidiscoming.common.VoidIsComing;
import com.voidiscoming.common.entity.VoidSheepEntity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.SheepEntityModel;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class VoidSheepRenderer
        extends MobEntityRenderer<VoidSheepEntity, SheepEntityModel<VoidSheepEntity>> {

    private static final Identifier TEXTURE =
            new Identifier(
                    VoidIsComing.MOD_ID,
                    "textures/entity/sheep.png"
            );

    public VoidSheepRenderer(EntityRendererFactory.Context context) {

        super(
                context,
                new SheepEntityModel<>(
                        context.getPart(EntityModelLayers.SHEEP)
                ),
                0.7F
        );

        this.addFeature(
                new VoidSheepWoolFeatureRenderer(
                        this,
                        context.getModelLoader()
                )
        );
    }

    @Override
    public Identifier getTexture(VoidSheepEntity entity) {
        return TEXTURE;
    }
}