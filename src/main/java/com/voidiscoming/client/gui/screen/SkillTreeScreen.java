package com.voidiscoming.client.gui.screen;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class SkillTreeScreen extends Screen {

    public SkillTreeScreen() {
        super(Text.translatable("gui.voidiscoming.skill_tree_title"));
    }

    @Override
    protected void init() {
        super.init();

        int buttonWidth = 100;
        int buttonHeight = 20;
        int x = (this.width - buttonWidth) / 2;
        int y = (this.height - buttonHeight) / 2;

        this.addDrawableChild(
            ButtonWidget.builder(Text.literal("Select Warrior"), button -> {
                
            })
            .dimensions(x, y, buttonWidth, buttonHeight)
            .build()
        );
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context);

        super.render(context, mouseX, mouseY, delta);

        // context.drawCenteredTextWithShadow(
        //     this.textRenderer, 
        //     this.title, 
        //     this.width / 2, 
        //     20, 
        //     0xFFFFFF
        // );
    }
}