package com.voidiscoming.client.gui.screen.skill;

import com.voidiscoming.client.gui.screen.skill.widget.SkillDescriptionPanel;
import com.voidiscoming.client.gui.screen.skill.widget.SkillNodeDisplay;
import com.voidiscoming.client.network.skill.SkillResetSender;
import com.voidiscoming.common.VoidIsComing;
import com.voidiscoming.common.component.ModComponents;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class SkillTreeScreen extends Screen {

    private static final int BASE_GUI_SCALE = 2;

    private int centerX;
    private int centerY;

    private int originDeltaX = 0;
    private int originDeltaY = 0;

    private SkillDescriptionPanel skillDescriptionPanel;
    private SkillNodeDisplay selectedNode = null;

    private final int backgroundWidth = 640;
    private final int backgroundHeight = 360;

    private ButtonWidget resetButton;

    private static final Identifier BACKGROUND_TEXTURE =
            VoidIsComing.id("textures/gui/skills/skill_background.png");

    private static final Identifier SKILL_POINT_TEXTURE =
            VoidIsComing.id("textures/gui/skills/skill_point.png");

    public SkillTreeScreen() {
        super(Text.translatable("gui.voidiscoming.skill_tree_title"));
    }

    private double getGuiScale() {
        if (this.client == null) {
            return BASE_GUI_SCALE;
        }

        return this.client.getWindow().getScaleFactor();
    }

    private double getContentScale() {
        return BASE_GUI_SCALE / getGuiScale();
    }

    @Override
    protected void init() {
        super.init();

        this.centerX = this.width / 2;
        this.centerY = this.height / 2;

        int bgLeft =
                centerX - backgroundWidth / 2;

        int bgTop =
                centerY - backgroundHeight / 2;

        this.resetButton = ButtonWidget.builder(
                Text.translatable("gui.voidiscoming.reset"),
                button -> resetSkills()
        ).dimensions(
                bgLeft + backgroundWidth - 80,
                bgTop + 8,
                75,
                20
        ).build();

        this.skillDescriptionPanel =
                new SkillDescriptionPanel(
                        this.client.player
                );

        this.skillDescriptionPanel.init(
                this,
                this::addDrawableChild
        );

        this.setSelectedNode(null);
    }

    private void resetSkills() {
        if (this.client != null &&
                this.client.player != null) {

            SkillResetSender.send();

            if (this.skillDescriptionPanel != null) {
                this.skillDescriptionPanel.updateButtons();
            }
        }
    }

    public void setSelectedNode(
            SkillNodeDisplay node
    ) {
        this.selectedNode = node;

        if (this.skillDescriptionPanel != null) {
            this.skillDescriptionPanel.setSelectedNode(node);
        }
    }

    @Override
    public void render(
            DrawContext context,
            int mouseX,
            int mouseY,
            float delta
    ) {
        this.renderBackground(context);

        double scale =
                getContentScale();

        int scaledBackgroundWidth =
                (int) Math.round(
                        backgroundWidth * scale
                );

        int scaledBackgroundHeight =
                (int) Math.round(
                        backgroundHeight * scale
                );

        int bgLeft =
                centerX
                        - scaledBackgroundWidth / 2;

        int bgTop =
                centerY
                        - scaledBackgroundHeight / 2;

        int guiMouseX =
                (int) Math.round(
                        centerX
                                + (mouseX - centerX)
                                / scale
                );

        int guiMouseY =
                (int) Math.round(
                        centerY
                                + (mouseY - centerY)
                                / scale
                );

        context.getMatrices().push();

        context.getMatrices().translate(
                centerX,
                centerY,
                0
        );

        context.getMatrices().scale(
                (float) scale,
                (float) scale,
                1.0F
        );

        context.getMatrices().translate(
                -centerX,
                -centerY,
                0
        );

        int baseBgLeft =
                centerX - backgroundWidth / 2;

        int baseBgTop =
                centerY - backgroundHeight / 2;

        context.drawTexture(
                BACKGROUND_TEXTURE,
                baseBgLeft - 8,
                baseBgTop - 4,
                0,
                0,
                backgroundWidth + 16,
                backgroundHeight + 9,
                backgroundWidth + 16,
                backgroundHeight + 9
        );

        int originX =
                centerX + originDeltaX;

        int originY =
                centerY + originDeltaY;

        context.enableScissor(
                bgLeft,
                bgTop,
                bgLeft + scaledBackgroundWidth,
                bgTop + scaledBackgroundHeight
        );

        SkillNodeConnectionRenderer.renderConnections(
                context,
                originX,
                originY
        );

        for (SkillNodeDisplay node :
                SkillNodeDisplayRegistry.getAll()) {

            node.render(
                    context,
                    this.client.player,
                    guiMouseX,
                    guiMouseY,
                    originX,
                    originY
            );
        }

        context.disableScissor();

        context.drawTexture(
                SKILL_POINT_TEXTURE,
                baseBgLeft + 10,
                baseBgTop + 10,
                0,
                0,
                12,
                12,
                12,
                12
        );

        if (this.client != null &&
                this.client.player != null) {

            int points =
                    ModComponents.SKILLS
                            .get(this.client.player)
                            .getSkillPoints();

            context.drawText(
                    this.textRenderer,
                    Text.literal(
                            String.valueOf(points)
                    ),
                    baseBgLeft + 25,
                    baseBgTop + 13,
                    0xFFFFAA00,
                    true
            );
        }

        /*
         * RESET остаётся как был.
         */
        resetButton.render(
                context,
                guiMouseX,
                guiMouseY,
                delta
        );

        context.getMatrices().pop();

        /*
         * DESCRIPTION PANEL
         *
         * Панель сама создаёт отдельный MatrixStack
         * и масштабирует ВСЁ своё содержимое.
         */
        if (this.skillDescriptionPanel != null) {
            this.skillDescriptionPanel.renderScaled(
                    context,
                    this.textRenderer,
                    this.width,
                    this.height,
                    scale,
                    mouseX,
                    mouseY,
                    delta
            );
        }
    }

    @Override
    public boolean mouseClicked(
            double mouseX,
            double mouseY,
            int button
    ) {
        double scale =
                getContentScale();

        double guiMouseX =
                centerX
                        + (mouseX - centerX)
                        / scale;

        double guiMouseY =
                centerY
                        + (mouseY - centerY)
                        / scale;

        if (button == 0) {

            if (resetButton.mouseClicked(
                    guiMouseX,
                    guiMouseY,
                    button
            )) {
                return true;
            }

            /*
             * Equip / Upgrade сами знают,
             * где находится масштабированная панель.
             */
            if (this.skillDescriptionPanel != null &&
                    this.skillDescriptionPanel.mouseClicked(
                            mouseX,
                            mouseY,
                            button
                    )) {
                return true;
            }

            int originX =
                    centerX + originDeltaX;

            int originY =
                    centerY + originDeltaY;

            int bgLeft =
                    centerX - backgroundWidth / 2;

            int bgTop =
                    centerY - backgroundHeight / 2;

            if (guiMouseX >= bgLeft &&
                    guiMouseX <= bgLeft + backgroundWidth &&
                    guiMouseY >= bgTop &&
                    guiMouseY <= bgTop + backgroundHeight) {

                for (SkillNodeDisplay node :
                        SkillNodeDisplayRegistry.getAll()) {

                    if (node.isMouseOver(
                            guiMouseX,
                            guiMouseY,
                            originX,
                            originY
                    )) {

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

        return super.mouseClicked(
                mouseX,
                mouseY,
                button
        );
    }

    @Override
    public boolean mouseDragged(
            double mouseX,
            double mouseY,
            int button,
            double deltaX,
            double deltaY
    ) {
        if (button == 0) {

            double scale =
                    getContentScale();

            this.originDeltaX +=
                    (int) Math.round(
                            deltaX / scale
                    );

            this.originDeltaY +=
                    (int) Math.round(
                            deltaY / scale
                    );

            return true;
        }

        return super.mouseDragged(
                mouseX,
                mouseY,
                button,
                deltaX,
                deltaY
        );
    }
}