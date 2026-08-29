package com.voidiscoming.client.gui.screen;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModBookScreen extends Screen {
    // Используем стандартную ванильную текстуру книги
    private static final Identifier BOOK_TEXTURE = new Identifier("minecraft", "textures/gui/book.png");
    
    private final int imageWidth = 192;
    private final int imageHeight = 192;

    public ModBookScreen() {
        super(Text.literal("Void Is Coming Guide"));
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context);

        int x = (this.width - this.imageWidth) / 2;
        int y = (this.height - this.imageHeight) / 2;

        // Рисуем саму книгу
        context.drawTexture(BOOK_TEXTURE, x, y, 0, 0, this.imageWidth, this.imageHeight);
        context.drawText(this.textRenderer, Text.literal("Void Is Coming"), x + 42, y + 20, 0x000000, false);

        // Твои категории 
        context.drawText(this.textRenderer, Text.literal("1. Введение"), x + 30, y + 45, 0x000000, false);
        context.drawText(this.textRenderer, Text.literal("2. Система Маны"), x + 30, y + 60, 0x000000, false);
        context.drawText(this.textRenderer, Text.literal("3. Палочки и Посохи"), x + 30, y + 75, 0x000000, false);
        context.drawText(this.textRenderer, Text.literal("4. Алхимия"), x + 30, y + 90, 0x000000, false);
        context.drawText(this.textRenderer, Text.literal("5. Материалы"), x + 30, y + 105, 0x000000, false);
        context.drawText(this.textRenderer, Text.literal("6. Бестиарий"), x + 30, y + 120, 0x000000, false);

        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean shouldPause() {
        return false; // Игра не ставится на паузу при открытии книги
    }
}