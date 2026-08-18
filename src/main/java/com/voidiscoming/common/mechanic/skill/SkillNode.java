package com.voidiscoming.common.mechanic.skill;

import net.minecraft.util.Identifier;
import java.util.Optional;

public record SkillNode(
    Identifier id,
    Optional<Identifier> parentId,
    int cost,
    SkillType type,
    Optional<Identifier> spellId,
    Identifier[] mutuallyExclusiveNodes
) {
    public SkillNode(Identifier id, Identifier parentId, int cost, SkillType type) {
        this(id, Optional.of(parentId), cost, type, Optional.empty(), new Identifier[0]);
    }

    public SkillNode(Identifier id, int cost, SkillType type) {
        this(id, Optional.empty(), cost, type, Optional.empty(), new Identifier[0]);
    }

    public SkillNode(Identifier id, int cost, Identifier spellId) {
        this(id, Optional.empty(), cost, SkillType.SPELL, Optional.of(spellId), new Identifier[0]);
    }

    public SkillNode(Identifier id, Identifier parentId, int cost, Identifier spellId) {
        this(id, Optional.of(parentId), cost, SkillType.SPELL, Optional.of(spellId), new Identifier[0]);
    }

    public SkillNode(Identifier id, Identifier parentId, int cost, Identifier[] mutuallyExclusiveNodes) {
        this(id, Optional.of(parentId), cost, SkillType.CLASS, Optional.empty(), mutuallyExclusiveNodes);
    }

    public boolean isRoot() {
        return parentId.isEmpty();
    }
}