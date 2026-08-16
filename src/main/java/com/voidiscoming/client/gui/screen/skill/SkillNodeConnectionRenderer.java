package com.voidiscoming.client.gui.screen.skill;

import java.util.Optional;

import com.voidiscoming.client.gui.screen.skill.widget.SkillNodeDisplay;
import com.voidiscoming.client.gui.util.ModColors;
import com.voidiscoming.common.mechanic.skill.ModSkills;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.RotationAxis;

public class SkillNodeConnectionRenderer {
    private static final int LINE_THICKNESS = 4;
    private static final int LINE_COLOR = ModColors.NORD_DARK_SLATE;

    public static void renderConnections(DrawContext context, int originX, int originY) {
        for (SkillNodeDisplay childDisplay : SkillNodeDisplayRegistry.getAll()) {
            Optional<SkillNodeDisplay> parentDisplayOpt = getParentDisplay(childDisplay);
            if (parentDisplayOpt.isEmpty()) {
                continue;
            }

            SkillNodeDisplay parentDisplay = parentDisplayOpt.get();

            int x1 = originX + parentDisplay.x();
            int y1 = originY + parentDisplay.y();
            int x2 = originX + childDisplay.x();
            int y2 = originY + childDisplay.y();

            drawDiagonalLine(context, x1, y1, x2, y2);
        }
    }

    private static void drawDiagonalLine(DrawContext context, int x1, int y1, int x2, int y2) {
        double dx = x2 - x1;
        double dy = y2 - y1;
        double length = Math.sqrt(dx * dx + dy * dy);

        if (length == 0) return;

        float angle = (float) Math.toDegrees(Math.atan2(dy, dx));

        MatrixStack matrices = context.getMatrices();
        matrices.push();

        matrices.translate(x1, y1, 0);

        matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(angle));

        int halfThick = LINE_THICKNESS / 2;
        context.fill(0, -halfThick, (int) length, halfThick, LINE_COLOR);

        matrices.pop();
    }

    private static Optional<SkillNodeDisplay> getParentDisplay(SkillNodeDisplay displayNode) {
        return ModSkills.get(displayNode.skillId())
            .parentId()
            .map(SkillNodeDisplayRegistry::get);
    }
}