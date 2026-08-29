package com.voidiscoming.client.gui.screen.skill.widget;

import com.voidiscoming.client.network.skill.SkillUpgradeSender;
import com.voidiscoming.client.network.spell.SpellEquipUpdateSender;
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

import java.util.Arrays;
import java.util.function.Consumer;

public class SkillDescriptionPanel {

    private final int width = 240;
    private final int height = 60;

    private final ClientPlayerEntity player;

    private SkillNodeDisplay selectedNode = null;

    private final ButtonWidget upgradeButton;
    private final ButtonWidget equipButton;

    private int panelX;
    private int panelY;

    private double scale = 1.0D;

    private static final Identifier SKILL_POINT_TEXTURE =
            VoidIsComing.id("textures/gui/skills/skill_point.png");

    public SkillDescriptionPanel(ClientPlayerEntity player) {
        this.player = player;

        this.upgradeButton = ButtonWidget.builder(
                Text.translatable("gui.voidiscoming.upgrade"),
                button -> onUpgradePressed()
        ).dimensions(
                width - 80,
                height - 25,
                75,
                20
        ).build();

        this.equipButton = ButtonWidget.builder(
                Text.translatable("gui.voidiscoming.equip"),
                button -> onEquipPressed()
        ).dimensions(
                width - 160,
                height - 25,
                75,
                20
        ).build();
    }

    public void init(
            Screen screen,
            Consumer<ButtonWidget> addWidgetConsumer
    ) {
        updateButtons();
    }

    public void setSelectedNode(SkillNodeDisplay node) {
        this.selectedNode = node;
        updateButtons();
    }

    private boolean isSpellEquipped(Identifier spellId) {
        if (player == null || spellId == null) {
            return false;
        }

        Identifier[] equipped =
                ModComponents.SPELLS
                        .get(player)
                        .getEquippedSpells();

        return Arrays.stream(equipped)
                .anyMatch(id -> spellId.equals(id));
    }

    public void updateButtons() {
        if (this.selectedNode == null) {
            this.upgradeButton.visible = false;
            this.equipButton.visible = false;
            return;
        }

        this.upgradeButton.visible = true;

        var skillData =
                ModComponents.SKILLS.get(this.player);

        this.upgradeButton.active =
                skillData.canUnlock(
                        selectedNode.skillId()
                );

        boolean isSpell =
                this.selectedNode.getSkillType()
                        == SkillType.SPELL;

        this.equipButton.visible =
                isSpell;

        if (isSpell &&
                this.selectedNode.getSkill()
                        .spellId()
                        .isPresent()) {

            Identifier spellId =
                    this.selectedNode.getSkill()
                            .spellId()
                            .get();

            boolean equipped =
                    isSpellEquipped(spellId);

            this.equipButton.setMessage(
                    Text.translatable(
                            equipped
                                    ? "gui.voidiscoming.unequip"
                                    : "gui.voidiscoming.equip"
                    )
            );

            boolean hasSpace =
                    Arrays.stream(
                            ModComponents.SPELLS
                                    .get(player)
                                    .getEquippedSpells()
                    ).anyMatch(s -> s == null);

            this.equipButton.active =
                    skillData.hasUnlocked(
                            selectedNode.skillId()
                    ) && (equipped || hasSpace);

        } else {
            this.equipButton.active = false;
        }
    }

    private void onUpgradePressed() {
        if (selectedNode != null && player != null) {
            SkillUpgradeSender.send(
                    selectedNode.skillId()
            );

            updateButtons();
        }
    }

    private void onEquipPressed() {
        if (selectedNode != null &&
                player != null &&
                selectedNode.getSkill()
                        .spellId()
                        .isPresent()) {

            Identifier spellId =
                    selectedNode.getSkill()
                            .spellId()
                            .get();

            SpellEquipUpdateSender.send(spellId);

            updateButtons();
        }
    }

    public void renderScaled(
            DrawContext context,
            TextRenderer textRenderer,
            int screenWidth,
            int screenHeight,
            double scale,
            int mouseX,
            int mouseY,
            float delta
    ) {
        updateButtons();

        if (this.selectedNode == null) {
            return;
        }

        this.scale = scale;

        /*
         * Сначала рассчитываем РЕАЛЬНЫЙ размер панели
         * на экране после масштабирования.
         */
        int scaledWidth =
                (int) Math.round(width * scale);

        int scaledHeight =
                (int) Math.round(height * scale);

        /*
         * Панель всегда остаётся снизу по центру.
         *
         * Важно:
         * здесь уже учитывается масштаб,
         * поэтому панель не улетает вверх.
         */
        this.panelX =
                (screenWidth - scaledWidth) / 2;

        this.panelY =
                screenHeight - scaledHeight - 10;

        /*
         * Переводим мышь из экранных координат
         * в локальные координаты панели.
         */
        int localMouseX =
                (int) Math.round(
                        (mouseX - panelX) / scale
                );

        int localMouseY =
                (int) Math.round(
                        (mouseY - panelY) / scale
                );

        context.getMatrices().push();

        /*
         * ВАЖНО:
         *
         * Масштабируем ВСЮ панель целиком.
         *
         * Поэтому текст, иконка и кнопки
         * увеличиваются/уменьшаются одинаково.
         */
        context.getMatrices().translate(
                panelX,
                panelY,
                0
        );

        context.getMatrices().scale(
                (float) scale,
                (float) scale,
                1.0F
        );

        /*
         * Панель теперь рисуется в своих обычных
         * 240x60 координатах.
         */
        context.fill(
                0,
                0,
                width,
                height,
                0xD0101010
        );

        context.drawBorder(
                0,
                0,
                width,
                height,
                0xFF4A4A4A
        );

        Text titleText =
                Text.translatable(
                        "skill_name.voidiscoming."
                                + selectedNode.translationKeyName()
                );

        context.drawText(
                textRenderer,
                titleText,
                5,
                5,
                0xFFFFAA00,
                true
        );

        Text descText =
                Text.translatable(
                        "skill_description.voidiscoming."
                                + selectedNode.translationKeyName()
                );

        var wrappedLines =
                textRenderer.wrapLines(
                        descText,
                        width - 46
                );

        int lineY = 15;

        for (int i = 0;
             i < Math.min(wrappedLines.size(), 3);
             i++) {

            context.drawText(
                    textRenderer,
                    wrappedLines.get(i),
                    5,
                    lineY,
                    0xFFAAAAAA,
                    true
            );

            lineY +=
                    textRenderer.fontHeight + 1;
        }

        Text costText =
                Text.literal(
                        String.valueOf(
                                selectedNode.getCost()
                        )
                );

        context.drawText(
                textRenderer,
                costText,
                width - 18
                        - textRenderer.getWidth(costText),
                8,
                0xFFFFAA00,
                true
        );

        context.drawTexture(
                SKILL_POINT_TEXTURE,
                width - 17,
                5,
                0,
                0,
                12,
                12,
                12,
                12
        );

        /*
         * Кнопки находятся ВНУТРИ того же scale.
         */
        if (this.equipButton.visible) {
            this.equipButton.render(
                    context,
                    localMouseX,
                    localMouseY,
                    delta
            );
        }

        if (this.upgradeButton.visible) {
            this.upgradeButton.render(
                    context,
                    localMouseX,
                    localMouseY,
                    delta
            );
        }

        context.getMatrices().pop();
    }

    public boolean mouseClicked(
            double mouseX,
            double mouseY,
            int button
    ) {
        if (button != 0 ||
                this.selectedNode == null) {
            return false;
        }

        /*
         * Переводим экранные координаты мыши
         * в координаты самой панели.
         */
        double localX =
                (mouseX - panelX) / scale;

        double localY =
                (mouseY - panelY) / scale;

        if (this.equipButton.visible &&
                this.equipButton.mouseClicked(
                        localX,
                        localY,
                        button
                )) {
            return true;
        }

        if (this.upgradeButton.visible &&
                this.upgradeButton.mouseClicked(
                        localX,
                        localY,
                        button
                )) {
            return true;
        }

        return false;
    }
}