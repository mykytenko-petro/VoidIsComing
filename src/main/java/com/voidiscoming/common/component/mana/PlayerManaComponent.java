package com.voidiscoming.common.component.mana;

import com.voidiscoming.common.component.ModComponents;
import com.voidiscoming.common.mechanic.stat.PlayerStats;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;

public class PlayerManaComponent implements ManaComponent {
    private float mana = 20.0f;
    private final PlayerEntity player;

    public PlayerManaComponent(PlayerEntity player) {
        this.player = player;
    }

    @Override
    public float getMana() { 
        return this.mana; 
    }

    @Override
    public void setMana(float mana) {
        this.mana = (float) Math.max(0.0, Math.min(mana, getMaxMana()));
        ModComponents.MANA.sync(this.player);
    }

    @Override
    public void addMana(double amount) { 
        setMana((float) (this.mana + amount)); 
    }

    @Override
    public void removeMana(double amount) { 
        setMana((float) (this.mana - amount)); 
    }

    @Override
    public double getMaxMana() { 
        return PlayerStats.MAX_MANA.getValue(this.player); 
    }

    @Override
    public double getManaRegen() { 
        return PlayerStats.MANA_REGEN.getValue(this.player); 
    }

    @Override
    public void readFromNbt(NbtCompound tag) {
        if (tag.contains("Mana")) {
            this.mana = tag.getFloat("Mana");
        }
    }

    @Override
    public void writeToNbt(NbtCompound tag) {
        tag.putFloat("Mana", this.mana);
    }
}