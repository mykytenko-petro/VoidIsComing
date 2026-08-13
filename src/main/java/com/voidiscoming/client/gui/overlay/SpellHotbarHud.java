package com.voidiscoming.client.gui.overlay;

import com.voidiscoming.client.keybind.ModKeyBindings;
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
        Identifier[] equipped = spellComponent.getEquippedSpells();
        long currentTime = client.player.getWorld().getTime();

        int slotSize = 18;
        int spacing = 2;
        int startX = 5;
        
        int totalHeight = (4 * slotSize) + (3 * spacing);
        int startY = (client.getWindow().getScaledHeight() - totalHeight) / 2;

        for (int i = 0; i < 4; i++) {
            int cellY = startY + i * (slotSize + spacing);

            context.drawTexture(SLOT_TEXTURE, startX, cellY, 0, 0, slotSize, slotSize, slotSize, slotSize);

            String bindText = "";
            if (ModKeyBindings.SPELL_KEYS != null && ModKeyBindings.SPELL_KEYS[i] != null) {
                bindText = ModKeyBindings.SPELL_KEYS[i].getBoundKeyLocalizedText().getString();
            }

            context.drawText(
                client.textRenderer, 
                bindText, 
                startX + 2, 
                cellY + 2, 
                0xFF555555, 
                false
            );

            Identifier spellId = equipped[i];

            if (spellId != null) {
                Spell spell = ModSpells.get(spellId);
                
                if (spell != null) {
                    int iconX = startX + 1;
                    int iconY = cellY + 1;
                    int iconSize = 16;

                    if (spell.getIcon() != null) {
                        context.drawTexture(
                            spell.getIcon(),
                            iconX, iconY,
                            0, 0,
                            iconSize, iconSize,
                            iconSize, iconSize
                        );
                    }

                    if (spellComponent.isOnCooldown(spellId)) {
                        long cooldownEnd = spellComponent.getCooldownEnd(spellId);
                        long totalCooldown = spellComponent.getTotalCooldownTicks(spellId);

                        if (totalCooldown > 0) {
                            long ticksLeft = cooldownEnd - currentTime;
                            
                            float progress = (float) ticksLeft / totalCooldown;
                            if (progress > 1.0f) progress = 1.0f;
                            if (progress < 0.0f) progress = 0.0f;

                            int overlayHeight = (int) (iconSize * progress);

                            context.fill(
                                iconX,                    
                                iconY + iconSize - overlayHeight,                     
                                iconX + iconSize,       
                                iconY + iconSize,  
                                0x80FFFFFF              
                            );
                        }
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