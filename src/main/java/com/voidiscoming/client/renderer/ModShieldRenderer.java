package com.voidiscoming.client.renderer;

import com.voidiscoming.common.ModItems;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;

public class ModShieldRenderer {
    public static void register() {
        BuiltinItemRendererRegistry.INSTANCE.register(ModItems.WOODEN_SHIELD, (stack, mode, matrices, vertexConsumers, light, overlay) -> {
        });
        BuiltinItemRendererRegistry.INSTANCE.register(ModItems.GOLDEN_SHIELD, (stack, mode, matrices, vertexConsumers, light, overlay) -> {
        });
        BuiltinItemRendererRegistry.INSTANCE.register(ModItems.DIAMOND_SHIELD, (stack, mode, matrices, vertexConsumers, light, overlay) -> {
        });
        BuiltinItemRendererRegistry.INSTANCE.register(ModItems.NETHERITE_SHIELD, (stack, mode, matrices, vertexConsumers, light, overlay) -> {
        });
    }
}