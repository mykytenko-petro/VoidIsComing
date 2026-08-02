package com.voidiscoming.client.renderer;

import com.voidiscoming.common.ModItems;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;

public class ModShieldRenderer {

    public static void register() {
        System.out.println("Registering shield renderer");
        BuiltinItemRendererRegistry.INSTANCE.register(
                ModItems.WOODEN_SHIELD,
                WoodenShieldRenderer::render
        );
    }
}