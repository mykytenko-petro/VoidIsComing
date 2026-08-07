package com.voidiscoming.client.gui.overlay;

import com.voidiscoming.client.gui.util.ModColors;
import com.voidiscoming.client.mixin.gui.HandledScreenAccessor;
import com.voidiscoming.common.mechanic.stat.PlayerStats;

import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class InventoryStatOverlay {

    public static void init() {
        ScreenEvents.AFTER_INIT.register((client, screen, scaledWidth, scaledHeight) -> {
            if (screen instanceof InventoryScreen inventoryScreen) {
                ScreenEvents.afterRender(inventoryScreen).register((scr, context, mouseX, mouseY, delta) -> {
                    renderStats(context, inventoryScreen);
                });
            }
        });
    }

    private static void renderStats(DrawContext context, InventoryScreen screen) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null) return;

        HandledScreenAccessor accessor = (HandledScreenAccessor) screen;
        int inventoryX = accessor.getX();
        int inventoryY = accessor.getY();

        int width = 110;
        int height = accessor.getBackgroundHeight();

        int x = inventoryX - width - 5;
        int y = inventoryY;

        context.fill(x, y, x + width, y + height, ModColors.Eminence90);
        context.drawBorder(x, y, width, height, ModColors.AttractivePurple90);

        context
            .drawText(client.textRenderer, Text.literal("PLAYER STATS")
            .formatted(Formatting.DARK_PURPLE, Formatting.BOLD), x + 8, y + 8, 0xFFFFFF, true);
        context.fill(x + 6, y + 20, x + width - 6, y + 21, ModColors.AttractivePurple90);

        int currentY = y + 26;
        int lineSpacing = 12;

        for (PlayerStats stat : PlayerStats.values()) {
            double rawValue = stat.getValue(client.player);
            String formattedValue = formatStatValue(stat, rawValue);
            
            Text text = Text.translatable(stat.getTranslationKey()).formatted(Formatting.GRAY)
                    .append(Text.literal(": ").formatted(Formatting.GRAY))
                    .append(Text.literal(formattedValue).formatted(Formatting.GOLD));

            context.drawText(client.textRenderer, text, x + 8, currentY, 0xFFFFFF, false);

            currentY += lineSpacing;
        }
    }

    private static String formatStatValue(PlayerStats stat, double val) {
        if (stat == PlayerStats.MOVEMENT_SPEED) {
            return String.format("%.0f%%", val * 1000);
        }
        if (val % 1 == 0) {
            return String.format("%.0f", val);
        }
        return String.format("%.1f", val);
    }
}