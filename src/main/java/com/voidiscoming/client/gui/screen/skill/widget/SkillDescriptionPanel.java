package com.voidiscoming.client.gui.screen.skill.widget;

import com.voidiscoming.common.VoidIsComing;
import com.voidiscoming.common.component.ModComponents;
import com.voidiscoming.common.mechanic.skill.SkillType;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.function.Consumer;

public class SkillDescriptionPanel {
    private final int width = 240;
    private final int height = 60;

    private final ClientPlayerEntity player;
    private SkillNodeDisplay selectedNode = null;

    private ButtonWidget upgradeButton;
    private ButtonWidget equipButton;

    private static final Identifier SKILL_POINT_TEXTURE =
        VoidIsComing.id("textures/gui/skills/skill_point.png");

    public SkillDescriptionPanel(ClientPlayerEntity player) {
        this.player = player;

        this.upgradeButton = ButtonWidget.builder(
            Text.translatable("gui.voidiscoming.upgrade"),
            button -> onUpgradePressed()
        ).dimensions(0, 0, 75, 20).build();

        this.equipButton = ButtonWidget.builder(
            Text.translatable("gui.voidiscoming.equip"),
            button -> onEquipPressed()
        ).dimensions(0, 0, 75, 20).build();
    }

    public void init(Screen screen, Consumer<ButtonWidget> addWidgetConsumer) {
        int panelX = (screen.width - width) / 2;
        int panelY = screen.height - height - 10;

        this.upgradeButton.setX(panelX + width - 80);
        this.upgradeButton.setY(panelY + height - 25);

        this.equipButton.setX(panelX + width - 160);
        this.equipButton.setY(panelY + height - 25);

        addWidgetConsumer.accept(this.upgradeButton);
        addWidgetConsumer.accept(this.equipButton);

        updateButtons();
    }

    public void setSelectedNode(SkillNodeDisplay node) {
        this.selectedNode = node;
        updateButtons();
    }

    public void updateButtons() {
        if (this.selectedNode == null) {
            this.upgradeButton.visible = false;
            this.equipButton.visible = false;
            return;
        }

        this.upgradeButton.visible = true;

        if (this.player != null) {
            var skillData = ModComponents.SKILLS.get(this.player);
            boolean alreadyUnlocked = skillData.hasUnlocked(this.selectedNode.skillId());
            boolean canAfford = skillData.getSkillPoints() >= this.selectedNode.getCost();

            this.upgradeButton.active = !alreadyUnlocked && canAfford;

            boolean isSpell = (this.selectedNode.getSkillType() == SkillType.SPELL);
            this.equipButton.visible = isSpell;
            this.equipButton.active = isSpell && alreadyUnlocked;
        }
    }

    private void onUpgradePressed() {
        if (selectedNode != null && player != null) {
            ModComponents.SKILLS.get(player).unlockSkill(selectedNode.skillId());
        }
    }

    private void onEquipPressed() {
        if (selectedNode != null && player != null) {
            // TODO: Send C2S Packet to equip selectedNode spell
        }
    }

    public void render(DrawContext context, TextRenderer textRenderer, int screenWidth, int screenHeight) {
        if (this.selectedNode == null) return;

        int panelX = (screenWidth - width) / 2;
        int panelY = screenHeight - height - 10;

        // Background & Border
        context.fill(panelX, panelY, panelX + width, panelY + height, 0xD0101010);
        context.drawBorder(panelX, panelY, width, height, 0xFF4A4A4A);

        // Name
        Text titleText = Text.translatable("skill_name.voidiscoming." + selectedNode.translationKeyName());
        context.drawText(textRenderer, titleText, panelX + 5, panelY + 5, 0xFFFFAA00, true);

        // Description lines
        Text descText = Text.translatable("skill_description.voidiscoming." + selectedNode.translationKeyName());
        var wrappedLines = textRenderer.wrapLines(descText, width - 46);
        
        int lineY = panelY + 15;
        for (int i = 0; i < Math.min(wrappedLines.size(), 3); i++) {
            context.drawText(textRenderer, wrappedLines.get(i), panelX + 5, lineY, 0xFFAAAAAA, true);
            lineY += textRenderer.fontHeight + 1;
        }

        // Cost & Icon
        Text costText = Text.literal(String.valueOf(selectedNode.getCost()));
        context.drawText(textRenderer, costText, panelX + width - 18 - textRenderer.getWidth(costText), panelY + 8, 0xFFFFAA00, true);
        context.drawTexture(SKILL_POINT_TEXTURE, panelX + width - 17, panelY + 5, 0, 0, 12, 12, 12, 12);
    }
}