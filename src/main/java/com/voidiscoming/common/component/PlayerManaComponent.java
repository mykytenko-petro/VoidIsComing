package com.voidiscoming.common.component;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;

public class PlayerManaComponent implements ManaComponent {
    private float mana = 20.0f;
    private float maxMana = 20.0f;

    private final PlayerEntity player;
    
    private int ticks = 0;

    public PlayerManaComponent(PlayerEntity player) {
        this.player = player;
    }

    @Override
    public void tick() { 
        if (this.player.getWorld().isClient()) return; 

        this.ticks++;
        if (this.ticks >= 20) { 
            this.ticks = 0;
            if (this.mana < this.maxMana) {
                addMana(1.0f); 
            }
        }
    }

    @Override
    public float getMana() {
        return this.mana;
    }

    @Override
    public float getMaxMana() {
        return this.maxMana;
    }

    @Override
    public void setMana(float mana) {
        this.mana = Math.max(0.0f, Math.min(mana, this.maxMana));
        ModComponents.MANA.sync(this.player);
    }

    @Override
    public void addMana(float amount) {
        setMana(this.mana + amount);
    }

    @Override
    public void removeMana(float amount) {
        setMana(this.mana - amount);
    }

    @Override
    public void readFromNbt(NbtCompound tag) {
        if (tag.contains("Mana")) this.mana = tag.getFloat("Mana");
        if (tag.contains("MaxMana")) this.maxMana = tag.getFloat("MaxMana");
    }

    @Override
    public void writeToNbt(NbtCompound tag) {
        tag.putFloat("Mana", this.mana);
        tag.putFloat("MaxMana", this.maxMana);
    }
}