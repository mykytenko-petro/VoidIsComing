package com.voidiscoming.common.component;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import dev.onyxstudios.cca.api.v3.component.tick.CommonTickingComponent;

public interface ManaComponent extends AutoSyncedComponent, CommonTickingComponent {
    float getMana();
    void setMana(float mana);
    void addMana(double amount);
    void removeMana(double amount);

    double getMaxMana();

    double getManaRegen();
}