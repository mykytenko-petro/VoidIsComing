package com.voidiscoming.common.component;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;

public class PlayerManaComponent implements ManaComponent, AutoSyncedComponent {
    private int mana = 20;
    private int maxMana = 20;
    private final PlayerEntity player;

    public PlayerManaComponent(PlayerEntity player) {
        this.player = player;
    }

    @Override
    public int getMana() {
        return this.mana;
    }

    @Override
    public int getMaxMana() {
        return this.maxMana;
    }

    @Override
    public void setMana(int mana) {
        this.mana = Math.max(0, Math.min(mana, this.maxMana));
        
        ModComponents.MANA.sync(this.player);
    }

    @Override
    public void addMana(int amount) {
        setMana(this.mana + amount);
    }

    @Override
    public void removeMana(int amount) {
        setMana(this.mana - amount);
    }

    @Override
    public void readFromNbt(NbtCompound tag) {
        if (tag.contains("Mana")) {
            this.mana = tag.getInt("Mana");
        }
        if (tag.contains("MaxMana")) {
            this.maxMana = tag.getInt("MaxMana");
        }
    }

    @Override
    public void writeToNbt(NbtCompound tag) {
        tag.putInt("Mana", this.mana);
        tag.putInt("MaxMana", this.maxMana);
    }
}