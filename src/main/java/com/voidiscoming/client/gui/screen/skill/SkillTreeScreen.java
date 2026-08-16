package com.voidiscoming.client.gui.screen.skill;

import com.voidiscoming.client.gui.screen.skill.widget.SkillDescriptionPanel;
import com.voidiscoming.client.gui.screen.skill.widget.SkillNodeDisplay;
import com.voidiscoming.common.VoidIsComing;
import com.voidiscoming.common.component.ModComponents;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class SkillTreeScreen extends Screen {
    private int centerX;
    private int centerY;
    private int originDeltaX = 0;
    private int originDeltaY = 0;

    private SkillDescriptionPanel skillDescriptionPanel;
    private SkillNodeDisplay selectedNode = null;

    private int backgroundWidth = 640;
    private int backgroundHeight = 360;

    private ButtonWidget resetButton;

    private static final Identifier BACKGROUND_TEXTURE =
        VoidIsComing.id("textures/gui/skills/skill_background.png");
    private static final Identifier SKILL_POINT_TEXTURE =
        VoidIsComing.id("textures/gui/skills/skill_point.png");

    public SkillTreeScreen() {
        super(Text.translatable("gui.voidiscoming.skill_tree_title"));
    }

    @Override
    protected void init() {
        super.init();

        this.centerX = this.width / 2;
        this.centerY = this.height / 2;

        int bgLeft = centerX - backgroundWidth / 2;
        int bgTop = centerY - backgroundHeight / 2;

        resetButton = ButtonWidget.builder(
            Text.translatable("gui.voidiscoming.reset"),
            button -> ModComponents.SKILLS.get(this.client.player).resetSkills()
        ).dimensions(
            bgLeft + backgroundWidth - 80, bgTop + 8, 
            75, 20
        ).build();

        this.skillDescriptionPanel = new SkillDescriptionPanel(this.client.player);
        this.skillDescriptionPanel.init(this, this::addDrawableChild);
        
        addDrawableChild(resetButton);

        this.setSelectedNode(null);
    }

    public void setSelectedNode(SkillNodeDisplay node) {
        this.selectedNode = node;
        if (this.skillDescriptionPanel != null) {
            this.skillDescriptionPanel.setSelectedNode(node);
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context);

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

        SkillNodeConnectionRenderer.renderConnections(context, originX, originY);

        for (SkillNodeDisplay node : SkillNodeDisplayRegistry.getAll()) {
            node.render(context, mouseX, mouseY, originX, originY);
        }
        context.disableScissor();

        this.skillDescriptionPanel.render(context, this.textRenderer, this.width, this.height);

        context.drawTexture(
            SKILL_POINT_TEXTURE,
            bgLeft + 10, bgTop + 10,
            0, 0, 
            12, 12,
            12, 12
        );

        if (this.client != null && this.client.player != null) {
            int points = ModComponents.SKILLS.get(this.client.player).getSkillPoints();
            context.drawText(
                this.textRenderer,
                Text.literal(String.valueOf(points)),
                bgLeft + 25, bgTop + 13,
                0xFFFFAA00,
                true
            );
        }

        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == 0) {
            if (super.mouseClicked(mouseX, mouseY, button)) {
                return true;
            }

            int originX = centerX + originDeltaX;
            int originY = centerY + originDeltaY;

            int bgLeft = centerX - backgroundWidth / 2;
            int bgTop = centerY - backgroundHeight / 2;

            if (mouseX >= bgLeft && mouseX <= bgLeft + backgroundWidth &&
                mouseY >= bgTop && mouseY <= bgTop + backgroundHeight) {

                for (SkillNodeDisplay node : SkillNodeDisplayRegistry.getAll()) {
                    if (node.isMouseOver(mouseX, mouseY, originX, originY)) {
                        if (node == this.selectedNode) {
                            setSelectedNode(null);
                        } else {
                            setSelectedNode(node);
                        }
                        return true;
                    }
                }
            }

            setSelectedNode(null);
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