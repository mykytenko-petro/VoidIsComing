package com.voidiscoming.common.mechanic.skill;

import net.minecraft.util.Identifier;
import java.util.Optional;

public record SkillNode(
    Identifier id,
    Optional<Identifier> parentId,
    int cost,
    SkillType type
) {
    public boolean isRoot() {
        return parentId.isEmpty();
    }
}