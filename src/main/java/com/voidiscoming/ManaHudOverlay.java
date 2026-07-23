package com.voidiscoming;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.Identifier;
import com.mojang.blaze3d.systems.RenderSystem;

public class ManaHudOverlay {
    public static void init() {
    // Внимание на (DrawContext drawContext, float tickDelta)
    HudRenderCallback.EVENT.register((DrawContext drawContext, float tickDelta) -> {
        
        MinecraftClient client = MinecraftClient.getInstance();

        if (client.player == null || client.options.hudHidden) return;
        if (!client.interactionManager.hasStatusBars()) return;

        float currentMana = 13.0f;//тут мана щас
        float maxMana = 20.0f;//тут макс мана

        int width = client.getWindow().getScaledWidth();
        int height = client.getWindow().getScaledHeight();

        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

        int x = (width / 2) + 91 - 9;
        int y = height - 49;

        int totalIcons = (int) Math.ceil(maxMana / 2.0f);

        for (int i = 0; i < totalIcons; i++) {
            Identifier textureToDraw;
            float iconValue = (i + 1) * 2.0f;

            if (currentMana >= iconValue) {
                textureToDraw = new Identifier("voidiscoming", "textures/gui/mana_full.png");
            } else if (currentMana >= iconValue - 1.0f) {
                textureToDraw = new Identifier("voidiscoming", "textures/gui/mana_half.png");
            } else {
                textureToDraw = new Identifier("voidiscoming", "textures/gui/mana_empty.png");
            }

            // Вот здесь используется drawContext!
            drawContext.drawTexture(
                textureToDraw,
                x - (i * 8),
                y,
                0, 0,
                9, 9,
                9, 9
            );
        }
    });
    }
}