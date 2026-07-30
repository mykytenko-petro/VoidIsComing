package com.voidiscoming.common.component;

import dev.onyxstudios.cca.api.v3.component.Component;
import java.util.List;

public interface SkillComponent extends Component {
   
    List<String> getUnlockedSkills();
    void unlockSkill(String skillId);
    boolean hasSkill(String skillId);


    String[] getEquippedSkills();
    void equipSkill(int slot, String skillId);
    void unequipSkill(int slot);
}