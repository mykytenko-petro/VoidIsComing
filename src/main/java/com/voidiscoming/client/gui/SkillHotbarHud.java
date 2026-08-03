package com.voidiscoming.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.voidiscoming.common.VoidIsComing;
import com.voidiscoming.common.component.ModComponents;
import com.voidiscoming.common.component.SkillComponent;
import com.voidiscoming.common.skill.ModSkills;
import com.voidiscoming.common.skill.Skill;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.Identifier;

public class SkillHotbarHud implements HudRenderCallback {

    private static final Identifier SLOT_TEXTURE = VoidIsComing.id("textures/gui/spellbar_cell.png");

    @Override
    public void onHudRender(DrawContext context, float tickDelta) {
        MinecraftClient client = MinecraftClient.getInstance();

        if (client.player == null || client.options.hudHidden) return;

        SkillComponent skillComponent = ModComponents.SKILLS.get(client.player);
        String[] equipped = skillComponent.getEquippedSkills();

        int slotSize = 18;
        int spacing = 2;
        int startX = 5;
        
        int totalHeight = (4 * slotSize) + (3 * spacing);
        int startY = (client.getWindow().getScaledHeight() - totalHeight) / 2;

        for (int i = 0; i < 4; i++) {
            int cellY = startY + i * (slotSize + spacing);

            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

        
            context.drawTexture(SLOT_TEXTURE, startX, cellY, 0, 0, slotSize, slotSize, slotSize, slotSize);

            // Хардкод 
            String skillId = equipped[i];
            if (i == 0 && skillId == null) skillId = "vampirism";
            if (i == 1 && skillId == null) skillId = "heal";

            
            if (skillId != null && !skillId.isEmpty()) {
                Skill skill = ModSkills.getById(skillId);
                
                if (skill != null) {
                    if (skill.getIcon() != null) {
                        context.drawTexture(
                            skill.getIcon(),
                            startX + 1, cellY + 1,
                            0, 0,
                            16, 16,
                            16, 16
                        );
                    }

                
                    if (skill.getCost() > 0 && skill.getCostType() != Skill.ResourceCostType.NONE) {
                        String costText = String.valueOf(skill.getCost());
                        
                        int textWidth = client.textRenderer.getWidth(costText);
                        int textX = startX + slotSize - textWidth - 1;
                        int textY = cellY + slotSize - 8;

                        context.drawText(
                            client.textRenderer, 
                            costText, 
                            textX, 
                            textY, 
                            skill.getCostType().getColor(), 
                            true
                        );
                    }
                }
            }
        }
    }
}