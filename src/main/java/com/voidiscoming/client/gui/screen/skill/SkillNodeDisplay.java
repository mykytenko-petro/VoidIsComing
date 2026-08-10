package com.voidiscoming.client.gui.screen.skill;

import com.voidiscoming.client.gui.util.ModColors;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class SkillNodeDisplay {

    private final Identifier skillId;
    private int x;
    private int y;
    private final int width = 24;
    private final int height = 24;

    private final Text title;
    private final MutableText description;
    private final Identifier iconTexture;

    private final boolean isEquipable;
    private boolean isUnlocked;
    private boolean isAvailable;
    private boolean isEquipped;

    private long lastClickTime = 0L;
    private static final long DOUBLE_CLICK_WINDOW_MS = 300L;

    public SkillNodeDisplay(Identifier skillId, int x, int y, MutableText title, MutableText description, Identifier iconTexture, boolean isEquipable) {
        this.skillId = skillId;
        this.x = x;
        this.y = y;
        this.title = title;
        this.description = description;
        this.iconTexture = iconTexture;
        this.isEquipable = isEquipable;
    }

    public void render(DrawContext context, int mouseX, int mouseY) {
        // 1. Draw Equipped Green Glow
        if (this.isEquipped) {
            context.fill(x - 2, y - 2, x + width + 2, y + height + 2, ModColors.NODE_EQUIPPED_GLOW);
            context.drawBorder(x - 1, y - 1, width + 2, height + 2, ModColors.NODE_EQUIPPED_BORDER);
        }

        // 2. Draw Node Frame Background
        int frameColor = getFrameColor();
        context.fill(x, y, x + width, y + height, frameColor);

        // 3. Draw Skill Icon
        context.drawTexture(this.iconTexture, x + 4, y + 4, 0, 0, 16, 16, 16, 16);

        // 4. Hover Highlight Tint
        if (isMouseOver(mouseX, mouseY)) {
            context.fill(x, y, x + width, y + height, ModColors.NODE_HOVER_TINT);
        }
    }

    private int getFrameColor() {
        if (this.isUnlocked) {
            return ModColors.NODE_FRAME_UNLOCKED;
        } else if (this.isAvailable) {
            return ModColors.NODE_FRAME_AVAILABLE;
        } else {
            return ModColors.NODE_FRAME_LOCKED;
        }
    }

    public boolean isMouseOver(double mouseX, double mouseY) {
        return mouseX >= this.x && mouseX < this.x + this.width &&
               mouseY >= this.y && mouseY < this.y + this.height;
    }

    public boolean handleClick() {
        long currentTime = System.currentTimeMillis();
        boolean isDoubleClick = (currentTime - lastClickTime) <= DOUBLE_CLICK_WINDOW_MS;
        this.lastClickTime = currentTime;

        if (isDoubleClick && this.isUnlocked && this.isEquipable) {
            this.isEquipped = !this.isEquipped;
            return true;
        }

        return false;
    }

    public Identifier getSkillId() { return skillId; }
    public Text getTitle() { return title; }
    public MutableText getDescription() { return description; }
    public boolean isEquipable() { return isEquipable; }
    public boolean isEquipped() { return isEquipped; }
    public void setEquipped(boolean equipped) { this.isEquipped = equipped; }
    public boolean isUnlocked() { return isUnlocked; }
    public void setUnlocked(boolean unlocked) { this.isUnlocked = unlocked; }
    public boolean isAvailable() { return isAvailable; }
    public void setAvailable(boolean available) { this.isAvailable = available; }
    public void setPosition(int x, int y) { this.x = x; this.y = y; }
}