package com.voidiscoming.common.mechanic.spell;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;

public abstract class Spell {

    public enum ResourceCostType {
        NONE(0xFFFFFF),
        MANA(0x3366FF),
        HP(0xFF3333);

        private final int color;

        ResourceCostType(int color) {
            this.color = color;
        }

        public int getColor() {
            return color;
        }
    }

    private final String id;
    private final String name;
    private final Identifier icon;
    private final int cost;
    private final ResourceCostType costType;
    private final boolean isPassive;

    public Spell(String id, String name, Identifier icon, int cost, ResourceCostType costType) {
        this.id = id;
        this.name = name;
        this.icon = icon;
        this.cost = cost;
        this.costType = costType;
        this.isPassive = false;
    }

    public Spell(String id, String name, Identifier icon) {
        this.id = id;
        this.name = name;
        this.icon = icon;
        this.cost = 0;
        this.costType = ResourceCostType.NONE;
        this.isPassive = true;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public Identifier getIcon() { return icon; }
    public int getCost() { return cost; }
    public ResourceCostType getCostType() { return costType; }
    public boolean isPassive() { return isPassive; }

    public void cast(PlayerEntity player) {}

    public void onAttack(PlayerEntity attacker, Entity target) {}
}