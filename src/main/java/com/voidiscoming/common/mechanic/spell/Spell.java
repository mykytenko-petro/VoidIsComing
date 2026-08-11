package com.voidiscoming.common.mechanic.spell;

import net.minecraft.entity.LivingEntity;
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

    private final Identifier id;
    private final Identifier icon;
    private final int cost;
    private final ResourceCostType costType;
    private final boolean isPassive;
    private final int cooldownTicks; 

    public Spell(Identifier id, Identifier icon, int cost, ResourceCostType costType, int cooldownTicks) {
        this.id = id;
        this.icon = icon;
        this.cost = cost;
        this.costType = costType;
        this.isPassive = false;
        this.cooldownTicks = cooldownTicks;
    }
 
    public Spell(Identifier id, Identifier icon, int cost, ResourceCostType costType) {
        this(id, icon, cost, costType, 0);
    }
 
    public Spell(Identifier id, Identifier icon) {
        this.id = id;
        this.icon = icon;
        this.cost = 0;
        this.costType = ResourceCostType.NONE;
        this.isPassive = true;
        this.cooldownTicks = 0;
    }

    public Identifier getId() { return id; }

    public String getTranslationKey() {
        return "spell." + id.getNamespace() + "." + id.getPath();
    }

    public Identifier getIcon() { return icon; }
    public int getCost() { return cost; }
    public ResourceCostType getCostType() { return costType; }
    public boolean isPassive() { return isPassive; }
    public int getCooldownTicks() { return cooldownTicks; } 

    public void cast(PlayerEntity player) {}
    public void onKill(PlayerEntity attacker, LivingEntity target) {}
}