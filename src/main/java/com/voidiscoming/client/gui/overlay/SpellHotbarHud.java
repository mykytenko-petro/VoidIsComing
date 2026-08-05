package com.voidiscoming.client.gui.overlay;

import com.mojang.blaze3d.systems.RenderSystem;
import com.voidiscoming.common.VoidIsComing;
import com.voidiscoming.common.component.ModComponents;
import com.voidiscoming.common.component.spell.SpellComponent;
import com.voidiscoming.common.mechanic.spell.ModSpells;
import com.voidiscoming.common.mechanic.spell.Spell;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.Identifier;

public class SpellHotbarHud implements HudRenderCallback {

    private static final Identifier SLOT_TEXTURE = VoidIsComing.id("textures/gui/spellbar_cell.png");

    @Override
    public void onHudRender(DrawContext context, float tickDelta) {
        MinecraftClient client = MinecraftClient.getInstance();

        if (client.player == null || client.options.hudHidden) return;

        SpellComponent spellComponent = ModComponents.SPELLS.get(client.player);
        String[] equipped = spellComponent.getEquippedSpells();

        int slotSize = 18;
        int spacing = 2;
        int startX = 5;
        
        int totalHeight = (4 * slotSize) + (3 * spacing);
        int startY = (client.getWindow().getScaledHeight() - totalHeight) / 2;

        for (int i = 0; i < 4; i++) {
            int cellY = startY + i * (slotSize + spacing);

            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

            context.drawTexture(SLOT_TEXTURE, startX, cellY, 0, 0, slotSize, slotSize, slotSize, slotSize);

            String spellId = equipped[i];

            if (spellId != null && !spellId.isEmpty()) {
                Spell spell = ModSpells.getById(spellId);
                
                if (spell != null) {
                    if (spell.getIcon() != null) {
                        context.drawTexture(
                            spell.getIcon(),
                            startX + 1, cellY + 1,
                            0, 0,
                            16, 16,
                            16, 16
                        );
                    }

                    if (spell.getCost() > 0 && spell.getCostType() != Spell.ResourceCostType.NONE) {
                        String costText = String.valueOf(spell.getCost());
                        
                        int textWidth = client.textRenderer.getWidth(costText);
                        int textX = startX + slotSize - textWidth - 1;
                        int textY = cellY + slotSize - 8;

                        context.drawText(
                            client.textRenderer, 
                            costText, 
                            textX, 
                            textY, 
                            spell.getCostType().getColor(), 
                            true
                        );
                    }
                }
            }
        }
    }
}