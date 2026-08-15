package com.voidiscoming.common.component.skill;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import net.minecraft.util.Identifier;

public interface SkillComponent extends AutoSyncedComponent {
    int getSkillPoints();
    void addSkillPoints(int points);

    boolean hasUnlocked(Identifier skillId);
    boolean hasUnlockedSpell(Identifier spellId);
    boolean canUnlock(Identifier skillId);
    boolean unlockSkill(Identifier skillId);
    void resetSkills();
}