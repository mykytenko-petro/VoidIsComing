package com.voidiscoming.common.mechanic.spell;

import com.voidiscoming.common.VoidIsComing;
import com.voidiscoming.common.component.ModComponents;
import com.voidiscoming.common.component.mana.ManaComponent;
import com.voidiscoming.common.component.spell.PlayerSpellComponent;

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

    private final Identifier icon;
    private final int cost;
    private final ResourceCostType costType;
    private final boolean isPassive;
    private final int cooldownTicks; 

    public Spell(Identifier icon, int cost, ResourceCostType costType, int cooldownTicks) {
        this.icon = icon;
        this.cost = cost;
        this.costType = costType;
        this.isPassive = false;
        this.cooldownTicks = cooldownTicks;
    }
 
    public Spell(Identifier icon, int cost, ResourceCostType costType) {
        this(icon, cost, costType, 0);
    }
 
    public Spell(Identifier icon) {
        this.icon = icon;
        this.cost = 0;
        this.costType = ResourceCostType.NONE;
        this.isPassive = true;
        this.cooldownTicks = 0;
    }

    public Identifier getIcon() { return icon; }
    public int getCost() { return cost; }
    public ResourceCostType getCostType() { return costType; }
    public boolean isPassive() { return isPassive; }
    public int getCooldownTicks() { return cooldownTicks; } 

    public void cast(PlayerEntity player, Identifier spellId) {
        // if (player.getWorld().isClient()) return;

        ModComponents.SPELLS.maybeGet(player).ifPresent(spellComp -> {
            VoidIsComing.LOGGER.info(spellComp.toString());

            if (spellComp instanceof PlayerSpellComponent playerSpellComp) {
                if (playerSpellComp.isOnCooldown(spellId)) {
                    return;
                }
            }

            ManaComponent mana = ModComponents.MANA.get(player);

            if (mana.getMana() >= getCost()) {
                mana.removeMana(getCost());

                if (spellComp instanceof PlayerSpellComponent playerSpellComp) {
                    playerSpellComp.setCooldown(spellId, getCooldownTicks());
                }
                
                castBehaviour(player);
            }
        });
    }
    public void castBehaviour(PlayerEntity player) {}
    public void onKill(PlayerEntity attacker, LivingEntity target) {}
}