package com.voidiscoming.common.component;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;

public class PlayerManaComponent implements ManaComponent {
    private float mana = 20.0f;
    private double maxMana = 20.0;
    private double manaRegen = 1.0;
    private final int manaRegenTickRequired = 50;

    private final PlayerEntity player;
    
    private int ticks = 0;

    public PlayerManaComponent(PlayerEntity player) {
        this.player = player;
    }

    @Override
    public float getMana() { return this.mana; }
    @Override
    public void setMana(float mana) {
        this.mana = (float) Math.max(0.0, Math.min(mana, this.maxMana));
        ModComponents.MANA.sync(this.player);
    }
    @Override
    public void addMana(double amount) { setMana((float) (this.mana + amount)); }
    @Override
    public void removeMana(double amount) { setMana((float) (this.mana - amount)); }

    @Override
    public double getMaxMana() { return this.maxMana; }
    @Override
    public void setMaxMana(double maxMana) {
        this.maxMana = maxMana;
        if (this.mana > this.maxMana) this.mana = (float) this.maxMana;
        ModComponents.MANA.sync(this.player);
    }

    @Override
    public double getManaRegen() { return this.manaRegen; }
    @Override
    public void setManaRegen(double manaRegen) {
        this.manaRegen = manaRegen;
        ModComponents.MANA.sync(this.player);
    }
    
    @Override
    public void tick() {
        if (this.player.getWorld().isClient()) return;

        if (this.mana > maxMana) {
            setMana((float) maxMana);
        }

        this.ticks++;
        if (this.ticks >= manaRegenTickRequired) {
            this.ticks = 0;
            if (this.mana < this.maxMana) {
                addMana(this.manaRegen);
            }
        }
    }

    @Override
    public void readFromNbt(NbtCompound tag) {
        if (tag.contains("Mana")) this.mana = tag.getFloat("Mana");
    }

    @Override
    public void writeToNbt(NbtCompound tag) {
        tag.putFloat("Mana", this.mana);
    }
}