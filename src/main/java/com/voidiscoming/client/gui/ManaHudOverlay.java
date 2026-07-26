package com.voidiscoming.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.Identifier;

public class ManaHudOverlay {
    private static final Identifier MANA_FULL = new Identifier("voidiscoming", "textures/gui/mana_full.png");
    private static final Identifier MANA_HALF = new Identifier("voidiscoming", "textures/gui/mana_half.png");
    private static final Identifier MANA_EMPTY = new Identifier("voidiscoming", "textures/gui/mana_empty.png");

    // Static test values for now
    private static final float TEST_CURRENT_MANA = 35.0f;
    private static final float TEST_MAX_MANA = 60.0f;

    public static void init() {
        HudRenderCallback.EVENT.register((DrawContext drawContext, float tickDelta) -> {
            MinecraftClient client = MinecraftClient.getInstance();

            if (client.player == null || client.options.hudHidden) return;
            if (!client.interactionManager.hasStatusBars()) return;

            // TODO: Replace with live component values when ready
            float currentMana = TEST_CURRENT_MANA;
            float maxMana = TEST_MAX_MANA;

            int width = client.getWindow().getScaledWidth();
            int height = client.getWindow().getScaledHeight();

            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

            int baseX = (width / 2) + 91 - 9;
            int baseY = height - 49;

            int totalIcons = (int) Math.ceil(maxMana / 2.0f);
            int iconsPerRow = 10;

            for (int i = 0; i < totalIcons; i++) {
                int column = i % iconsPerRow;
                int row = i / iconsPerRow;

                int x = baseX - (column * 8);
                int y = baseY - (row * 10);

                Identifier textureToDraw;
                float iconValue = (i + 1) * 2.0f;

                if (currentMana >= iconValue) {
                    textureToDraw = MANA_FULL;
                } else if (currentMana >= iconValue - 1.0f) {
                    textureToDraw = MANA_HALF;
                } else {
                    textureToDraw = MANA_EMPTY;
                }

                drawContext.drawTexture(
                    textureToDraw,
                    x, y,
                    0, 0,
                    9, 9,
                    9, 9
                );
            }
        });
    }
}