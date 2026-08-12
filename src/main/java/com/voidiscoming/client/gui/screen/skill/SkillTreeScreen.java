package com.voidiscoming.client.gui.screen.skill;

import com.voidiscoming.common.VoidIsComing;
import com.voidiscoming.common.mechanic.skill.ModSkills;
import com.voidiscoming.common.mechanic.skill.SkillType;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class SkillTreeScreen extends Screen {
    private int centerX;
    private int centerY;
    private int originDeltaX = 0;
    private int originDeltaY = 0;

    private final int panelWidth = 240;
    private final int panelHeight = 60;

    private ButtonWidget upgradeButton;
    private ButtonWidget equipButton;

    private static final Identifier BACKGROUND_TEXTURE =
        VoidIsComing.id("textures/gui/skills/skill_background.png");
    private static final Identifier SKILL_POINT_TEXTURE =
        VoidIsComing.id("textures/gui/skills/skill_point.png");

    private SkillNodeDisplay selectedNode = null;

    public SkillTreeScreen() {
        super(Text.translatable("gui.voidiscoming.skill_tree_title"));
    }

    @Override
    protected void init() {
        super.init();

        centerX = this.width / 2;
        centerY = this.height / 2;
        
        int panelX = (this.width - panelWidth) / 2;
        int panelY = this.height - panelHeight - 10;

        // Кнопка купівлі/розблокування скілла
        this.upgradeButton = ButtonWidget.builder(
            Text.translatable("gui.voidiscoming.upgrade"),
            button -> {
                if (selectedNode != null && this.client != null && this.client.player != null) {
                    PacketByteBuf buf = PacketByteBufs.create();
                    buf.writeIdentifier(selectedNode.getSkillId());

                    // Відправляємо пакет на сервер для розблокування вузла
                    ClientPlayNetworking.send(VoidIsComing.id("unlock_skill_packet"), buf);
                }
            }
        )
        .dimensions(panelX + panelWidth - 80, panelY + panelHeight - 25, 75, 20)
        .build();

        // Кнопка екіпірування спелла (якщо вузол типу SPELL)
        this.equipButton = ButtonWidget.builder(
            Text.translatable("gui.voidiscoming.equip"),
            button -> {
                if (selectedNode != null && this.client != null && this.client.player != null) {
                    var nodeData = ModSkills.get(selectedNode.getSkillId());
                    if (nodeData != null && nodeData.type() == SkillType.SPELL) {
                        nodeData.spellId().ifPresent(spellId -> {
                            PacketByteBuf buf = PacketByteBufs.create();
                            buf.writeInt(0); // Слот 0 за замовчуванням (або можна розширити вибір)
                            buf.writeIdentifier(spellId);

                            // Відправляємо пакет екіпірування на сервер
                            ClientPlayNetworking.send(VoidIsComing.id("spell_equip_packet"), buf);
                        });
                    }
                }
            }
        )
        .dimensions(panelX + panelWidth - 160, panelY + panelHeight - 25, 75, 20)
        .build();

        this.addDrawableChild(this.upgradeButton);
        this.addDrawableChild(this.equipButton);

        updateButtonVisibility();
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

        if (selectedNode != null) {
            renderDescriptionPanel(context, selectedNode);
        }

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
                            updateButtonVisibility();
                            return true;
                        }

                        selectedNode = node;
                        updateButtonVisibility();
                        return true;
                    }
                }
            }

            if (super.mouseClicked(mouseX, mouseY, button)) {
                return true;
            }

            selectedNode = null;
            updateButtonVisibility();
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

    private void renderDescriptionPanel(DrawContext context, SkillNodeDisplay node) {
        int panelX = (this.width - panelWidth) / 2;
        int panelY = this.height - panelHeight - 10;

        context.fill(panelX, panelY, panelX + panelWidth, panelY + panelHeight, 0xD0101010);
        context.drawBorder(panelX, panelY, panelWidth, panelHeight, 0xFF4A4A4A);

        Text titleText = Text.translatable("skill_name.voidiscoming." + node.translationKeyName);
        context.drawText(
            this.textRenderer,
            titleText,
            panelX + 5, panelY + 5,
            0xFFFFAA00,
            true
        );

        Text descText = Text.translatable("skill_description.voidiscoming." + node.translationKeyName);
        int maxTextWidth = panelWidth - 46;

        var wrappedLines = this.textRenderer.wrapLines(descText, maxTextWidth);
        
        int lineY = panelY + 15;
        int maxLines = 3;
        
        for (int i = 0; i < Math.min(wrappedLines.size(), maxLines); i++) {
            context.drawText(
                this.textRenderer,
                wrappedLines.get(i),
                panelX + 5,
                lineY,
                0xFFAAAAAA,
                true
            );
            lineY += this.textRenderer.fontHeight + 1;
        }

        // cost
        Text costText = Text.literal(String.valueOf(node.getCost()));

        context.drawText(
            this.textRenderer,
            costText,
            panelX + panelWidth - 18 - textRenderer.getWidth(costText), panelY + 8,
            0xFFFFAA00,
            true
        );

        context.drawTexture(
            SKILL_POINT_TEXTURE,
            panelX + panelWidth - 17, panelY + 5,
            0, 0, 
            12, 12,
            12, 12
        );
    }

    private void updateButtonVisibility() {
        boolean show = (selectedNode != null);
        
        this.upgradeButton.visible = show;
        this.equipButton.visible = show;
    }
}