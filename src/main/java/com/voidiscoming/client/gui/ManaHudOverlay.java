package com.voidiscoming.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.voidiscoming.common.VoidIsComing;
import com.voidiscoming.common.component.ManaComponent;
import com.voidiscoming.common.component.ModComponents;

import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.Identifier;
import net.fabricmc.api.EnvType;

@Environment(EnvType.CLIENT)
public class ManaHudOverlay {
    private static final Identifier MANA_FULL = VoidIsComing.id("textures/gui/mana_full.png");
    private static final Identifier MANA_HALF = VoidIsComing.id("textures/gui/mana_half.png");
    private static final Identifier MANA_EMPTY = VoidIsComing.id("textures/gui/mana_empty.png");

    public static void init() {
        HudRenderCallback.EVENT.register((DrawContext drawContext, float tickDelta) -> {
            MinecraftClient client = MinecraftClient.getInstance();

            if (client.player == null || client.options.hudHidden) return;
            if (!client.interactionManager.hasStatusBars()) return;

            ManaComponent mana = ModComponents.MANA.get(client.player);
            float currentMana = mana.getMana();
            double maxMana = mana.getMaxMana();

            int width = client.getWindow().getScaledWidth();
            int height = client.getWindow().getScaledHeight();

            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

            int baseX = (width / 2) + 91 - 9;
            int baseY = height - 49;

            int air = client.player.getAir();
            int maxAir = client.player.getMaxAir();

            if (air < maxAir) {
                baseY -= 10;
            }

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