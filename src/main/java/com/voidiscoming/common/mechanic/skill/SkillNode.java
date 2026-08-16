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
    public SkillNode(Identifier id, Identifier parentId, int cost, SkillType type) {
        this(id, Optional.of(parentId), cost, type, Optional.empty());
    }

    public SkillNode(Identifier id, int cost, SkillType type) {
        this(id, Optional.empty(), cost, type, Optional.empty());
    }

    public SkillNode(Identifier id, int cost, Identifier spellId) {
        this(id, Optional.empty(), cost, SkillType.SPELL, Optional.of(spellId));
    }

    public SkillNode(Identifier id, Identifier parentId, int cost, Identifier spellId) {
        this(id, Optional.of(parentId), cost, SkillType.SPELL, Optional.of(spellId));
    }

    public boolean isRoot() {
        return parentId.isEmpty();
    }
}