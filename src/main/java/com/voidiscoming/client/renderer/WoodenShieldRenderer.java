package com.voidiscoming.client.renderer;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.ShieldEntityModel;

public class WoodenShieldRenderer {

    public static void render(ItemStack stack,
        ModelTransformationMode mode,
        MatrixStack matrices,
        VertexConsumerProvider vertexConsumers,
        int light,
        int overlay) {

        MinecraftClient client = MinecraftClient.getInstance();

        ShieldEntityModel model = new ShieldEntityModel(
        client.getEntityModelLoader().getModelPart(EntityModelLayers.SHIELD)
        );

        System.out.println("Модель щита получена!");
    }
}