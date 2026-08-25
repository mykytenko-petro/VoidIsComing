package com.voidiscoming.common.component.mana;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;

public interface ManaComponent extends AutoSyncedComponent{
    float getMana();
    void setMana(float mana);
    void addMana(double amount);
    void removeMana(double amount);

    double getMaxMana();
}