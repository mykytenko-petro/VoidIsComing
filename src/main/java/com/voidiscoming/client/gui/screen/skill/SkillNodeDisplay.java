package com.voidiscoming.client.gui.screen.skill;

import com.voidiscoming.common.VoidIsComing;
import com.voidiscoming.common.mechanic.skill.ModSkills;
import com.voidiscoming.common.mechanic.skill.SkillNode;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.Identifier;

public class SkillNodeDisplay {

    // dimension
    private final int x;
    private final int y;
    private final int size = 24;

    // description info
    public final String translationKeyName;

    // skill
    private final Identifier skillId;
    private final SkillNode skill;

    // visual
    private final Identifier iconTexture;
    private final Identifier backgroundTexture;
    private static final Identifier REGULAR_BACKGROUND = 
        VoidIsComing.id("textures/gui/skills/skill_cell.png");
    private static final Identifier SPELL_BACKGROUND = 
        VoidIsComing.id("textures/gui/skills/spell_skill_cell.png");
    private static final Identifier CLASS_BACKGROUND = 
        VoidIsComing.id("textures/gui/skills/class_skill_cell.png");

    // click
    private long lastClickTime = 0L;
    private static final long DOUBLE_CLICK_WINDOW_MS = 300L;

    public SkillNodeDisplay(
        Identifier skillId,
        int x, int y,
        String translationKeyName,
        Identifier iconTexture
    ) {
        this.x = x;
        this.y = y;
        
        this.translationKeyName = translationKeyName;

        this.skillId = skillId;
        skill = ModSkills.get(skillId);

        this.iconTexture = iconTexture;
        backgroundTexture = switch (skill.type()) {
            case REGULAR -> REGULAR_BACKGROUND;
            case SPELL -> SPELL_BACKGROUND;
            case CLASS -> CLASS_BACKGROUND;
        };
    }

    public void render(
        DrawContext context,
        int mouseX, int mouseY,
        int originX, int originY
    ) {
        context.drawTexture(
            backgroundTexture,
            x + originX, y + originY,
            0, 0, 
            size, size, 
            size, size
        );

        context.drawTexture(
            this.iconTexture,
            x + 4 + originX, y + 4 + originY,
            0, 0, 
            size - 8, size - 8, 
            size - 8, size - 8
        );
    }

    public boolean isMouseOver(double mouseX, double mouseY, int originX, int originY) {
        double renderX = this.x + originX;
        double renderY = this.y + originY;

        return mouseX >= renderX && mouseX < renderX + this.size &&
               mouseY >= renderY && mouseY < renderY + this.size;
    }

    public int getCost() {
        return skill.cost();
    }

    public Identifier getSkillId() {
        return this.skillId;
    }
}