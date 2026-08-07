package com.voidiscoming.common.component.spell;

import dev.onyxstudios.cca.api.v3.component.Component;
import java.util.List;

public interface SpellComponent extends Component {
    
    List<String> getUnlockedSpells();
    void unlockSpell(String spellId);
    boolean hasSpell(String spellId);

    String[] getEquippedSpells();
    void equipSpell(int slot, String spellId);
    void unequipSpell(int slot);

    boolean isOnCooldown(String spellId);
    long getCooldownEnd(String spellId);
    int getTotalCooldownTicks(String spellId);
    void setCooldown(String spellId, int ticks);
}