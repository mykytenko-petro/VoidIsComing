package com.voidiscoming.common.mechanic.skill;

import net.minecraft.util.Identifier;
import java.util.Optional;

public record SkillNode(
    Identifier id,
    Optional<Identifier> parentId,
    int cost,
    SkillType type,
    Optional<Identifier> spellId 
) {
    public SkillNode(Identifier id, Optional<Identifier> parentId, int cost, SkillType type) {
        this(id, parentId, cost, type, Optional.empty());
    }

    public boolean isRoot() {
        return parentId.isEmpty();
    }
}