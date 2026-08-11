package com.voidiscoming.common.component.spell;

import dev.onyxstudios.cca.api.v3.component.Component;
import net.minecraft.util.Identifier;

public interface SpellComponent extends Component {
    Identifier[] getEquippedSpells();
    void equipSpell(int slot, Identifier spellId);
    void unequipSpell(int slot);
    
    boolean isOnCooldown(Identifier spellId);
    long getCooldownEnd(Identifier spellId);
    int getTotalCooldownTicks(Identifier spellId);
    void setCooldown(Identifier spellId, int ticks);
}