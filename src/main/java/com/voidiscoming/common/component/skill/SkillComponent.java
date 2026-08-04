package com.voidiscoming.common.component.skill;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;

import net.minecraft.util.Identifier;

import java.util.Set;

public interface SkillComponent extends AutoSyncedComponent {
    int getSkillPoints();
    void setSkillPoints(int points);
    void addSkillPoints(int points);

    Set<Identifier> getUnlockedSkills();
    boolean hasUnlocked(Identifier skillId);
    boolean canUnlock(Identifier skillId);
    boolean unlockSkill(Identifier skillId);
}