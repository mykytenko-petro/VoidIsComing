package com.voidiscoming.client.gui.screen.skill.widget;

import com.voidiscoming.common.VoidIsComing;
import com.voidiscoming.common.mechanic.skill.ModSkills;
import com.voidiscoming.common.mechanic.skill.SkillNode;
import com.voidiscoming.common.mechanic.skill.SkillType;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.Identifier;

public record SkillNodeDisplay(
    Identifier skillId,
    int x,
    int y,
    String translationKeyName,
    Identifier iconTexture
) {
    private static final int SIZE = 24;

    private static final Identifier REGULAR_BACKGROUND = 
        VoidIsComing.id("textures/gui/skills/skill_cell.png");
    private static final Identifier SPELL_BACKGROUND = 
        VoidIsComing.id("textures/gui/skills/spell_skill_cell.png");
    private static final Identifier CLASS_BACKGROUND = 
        VoidIsComing.id("textures/gui/skills/class_skill_cell.png");

    public void render(
        DrawContext context,
        int mouseX, int mouseY,
        int originX, int originY
    ) {
        int renderX = this.x + originX - SIZE / 2;
        int renderY = this.y + originY - SIZE / 2;

        Identifier backgroundTexture = getBackgroundTexture();

        context.drawTexture(
            backgroundTexture,
            renderX, renderY,
            0, 0, 
            SIZE, SIZE, 
            SIZE, SIZE
        );

        context.drawTexture(
            this.iconTexture,
            renderX + 4, renderY + 4,
            0, 0, 
            SIZE - 8, SIZE - 8, 
            SIZE - 8, SIZE - 8
        );
    }

    public boolean isMouseOver(double mouseX, double mouseY, int originX, int originY) {
        double renderX = this.x + originX - SIZE / 2;
        double renderY = this.y + originY - SIZE / 2;

        return mouseX >= renderX && mouseX < renderX + SIZE &&
               mouseY >= renderY && mouseY < renderY + SIZE;
    }

    private Identifier getBackgroundTexture() {
        SkillNode skill = getSkill();
        if (skill == null) return REGULAR_BACKGROUND;

        return switch (skill.type()) {
            case REGULAR -> REGULAR_BACKGROUND;
            case SPELL -> SPELL_BACKGROUND;
            case CLASS -> CLASS_BACKGROUND;
        };
    }

    public SkillNode getSkill() {
        return ModSkills.get(this.skillId);
    }

    public int getCost() {
        SkillNode skill = getSkill();
        return skill != null ? skill.cost() : 0;
    }

    public SkillType getSkillType() {
        SkillNode skill = getSkill();
        return skill != null ? skill.type() : SkillType.REGULAR;
    }
}