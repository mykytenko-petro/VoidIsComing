package com.voidiscoming.common.component;

import dev.onyxstudios.cca.api.v3.component.Component;

public interface ManaComponent extends Component {
    int getMana();
    int getMaxMana();
    
    void setMana(int mana);
    void addMana(int amount);
    void removeMana(int amount);
}
