package com.voidiscoming.common.component.spell;

import dev.onyxstudios.cca.api.v3.component.Component;
import net.minecraft.util.Identifier;

public interface SpellComponent extends Component {
    Identifier[] getEquippedSpells();
    void toggleSpell(Identifier spellId);
    void unequipAll();
    
    boolean isOnCooldown(Identifier identifier);
    long getCooldownEnd(Identifier identifier);
    int getTotalCooldownTicks(Identifier identifier);
    void setCooldown(Identifier identifier, int ticks);
}