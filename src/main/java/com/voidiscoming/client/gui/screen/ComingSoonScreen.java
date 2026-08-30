package com.voidiscoming.client.gui.screen;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class ComingSoonScreen extends Screen {

    private final int imageWidth = 176;
    private final int imageHeight = 90;

    public ComingSoonScreen() {
        super(Text.literal("Coming Soon"));
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context);

        int x = (this.width - this.imageWidth) / 2;
        int y = (this.height - this.imageHeight) / 2;

        // --- Отрисовка чистой рамки в стиле Vanilla GUI ---
        // Внешняя черная обводка
        context.fill(x - 2, y - 2, x + this.imageWidth + 2, y + this.imageHeight + 2, 0xFF000000);

        // Серый фон плашки
        context.fill(x, y, x + this.imageWidth, y + this.imageHeight, 0xFFC6C6C6);

        // Белые блики (верхняя и левая граница)
        context.fill(x, y, x + this.imageWidth, y + 2, 0xFFFFFFFF);
        context.fill(x, y, x + 2, y + this.imageHeight, 0xFFFFFFFF);

        // Темные тени (нижняя и правая граница)
        context.fill(x, y + this.imageHeight - 2, x + this.imageWidth, y + this.imageHeight, 0xFF555555);
        context.fill(x + this.imageWidth - 2, y, x + this.imageWidth, y + this.imageHeight, 0xFF555555);

        // --- Текст по центру ---
        Text text = Text.literal("Coming Soon");
        int textWidth = this.textRenderer.getWidth(text);
        
        context.drawTextWithShadow(
            this.textRenderer, 
            text, 
            x + (this.imageWidth - textWidth) / 2, 
            y + (this.imageHeight - 8) / 2, 
            0xFFAA00
        );

        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}