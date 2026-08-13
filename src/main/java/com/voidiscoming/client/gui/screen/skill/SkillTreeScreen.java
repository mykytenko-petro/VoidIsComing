package com.voidiscoming.client.gui.screen.skill;

import com.voidiscoming.client.gui.screen.skill.widget.SkillDescriptionPanel;
import com.voidiscoming.client.gui.screen.skill.widget.SkillNodeDisplay;
import com.voidiscoming.common.VoidIsComing;
import com.voidiscoming.common.component.ModComponents;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class SkillTreeScreen extends Screen {
    private int centerX;
    private int centerY;
    private int originDeltaX = 0;
    private int originDeltaY = 0;

    public final SkillDescriptionPanel skillDescriptionPanel;

    private static final Identifier BACKGROUND_TEXTURE =
        VoidIsComing.id("textures/gui/skills/skill_background.png");
    private static final Identifier SKILL_POINT_TEXTURE =
        VoidIsComing.id("textures/gui/skills/skill_point.png");
    
    private SkillNodeDisplay selectedNode = null;

    public SkillTreeScreen() {
        super(Text.translatable("gui.voidiscoming.skill_tree_title"));

        skillDescriptionPanel = new SkillDescriptionPanel();
    }

    @Override
    protected void init() {
        super.init();

        centerX = this.width / 2;
        centerY = this.height / 2;

        skillDescriptionPanel.init(this, this::addDrawableChild);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context);

        int backgroundWidth = 640;
        int backgroundHeight = 360;

        int bgLeft = centerX - backgroundWidth / 2;
        int bgTop = centerY - backgroundHeight / 2;

        context.drawTexture(
            BACKGROUND_TEXTURE,
            bgLeft - 8, bgTop - 4,
            0, 0, 
            backgroundWidth + 16, backgroundHeight + 9,
            backgroundWidth + 16, backgroundHeight + 9
        );

        int originX = centerX + originDeltaX;
        int originY = centerY + originDeltaY;

        context.enableScissor(bgLeft, bgTop, bgLeft + backgroundWidth, bgTop + backgroundHeight);

        for (SkillNodeDisplay node : SkillNodeDisplayRegistry.skillNodes) {
            node.render(
                context,
                mouseX, mouseY,
                originX, originY
            );
        }

        context.disableScissor();

        skillDescriptionPanel.render(context, textRenderer, width, height, selectedNode);

        // skill points
        context.drawTexture(
            SKILL_POINT_TEXTURE,
            bgLeft + 10, bgTop + 10,
            0, 0, 
            12, 12,
            12, 12
        );

        context.drawText(
            this.textRenderer,
            Text.literal(String.valueOf(ModComponents.SKILLS.get(this.client.player).getSkillPoints())),
            bgLeft + 25, bgTop + 13,
            0xFFFFAA00,
            true
        );

        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == 0) {
            int originX = centerX + originDeltaX;
            int originY = centerY + originDeltaY;

            int backgroundWidth = 640;
            int backgroundHeight = 360;
            int bgLeft = centerX - backgroundWidth / 2;
            int bgTop = centerY - backgroundHeight / 2;

            if (mouseX >= bgLeft && mouseX <= bgLeft + backgroundWidth &&
                mouseY >= bgTop && mouseY <= bgTop + backgroundHeight) {

                for (SkillNodeDisplay node : SkillNodeDisplayRegistry.skillNodes) {
                    if (node.isMouseOver(mouseX, mouseY, originX, originY)) {                    
                        if (node == selectedNode) {
                            selectedNode = null;
                            return true;
                        }

                        selectedNode = node;
                        return true;
                    }
                }
            }

            if (super.mouseClicked(mouseX, mouseY, button)) {
                return true;
            }

            selectedNode = null;
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        if (button == 0) {
            this.originDeltaX += (int) deltaX;
            this.originDeltaY += (int) deltaY;

            return true;
        }
        return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
    }
}