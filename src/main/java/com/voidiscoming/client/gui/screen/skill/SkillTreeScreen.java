package com.voidiscoming.client.gui.screen.skill;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class SkillTreeScreen extends Screen {
    private SkillNodeDisplay hoveredNode = null;

    public SkillTreeScreen() {
        super(Text.translatable("gui.voidiscoming.skill_tree_title"));
    }

    @Override
    protected void init() {
        super.init();

        int centerX = this.width / 2 - 12;
        int centerY = this.height / 2 - 12;

        SkillNodeDisplayRegistry.registerNodes(centerX, centerY);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        hoveredNode = null;

        for (SkillNodeDisplay node : SkillNodeDisplayRegistry.skillNodes) {
            node.render(context, mouseX, mouseY);
            
            if (node.isMouseOver(mouseX, mouseY)) {
                hoveredNode = node;
            }
        }

        // Draw bottom description panel for the hovered node at the end
        // if (hoveredNode != null) {
        //     renderBottomDescriptionPanel(context, hoveredNode);
        // }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == 0) {
            for (SkillNodeDisplay node : SkillNodeDisplayRegistry.skillNodes) {
                if (node.isMouseOver(mouseX, mouseY)) {
                    boolean doubleClicked = node.handleClick();
                    
                    if (doubleClicked) {
                        
                    } else {
                        // Handle single click (e.g. select or purchase)
                    }
                    return true;
                }
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }
}