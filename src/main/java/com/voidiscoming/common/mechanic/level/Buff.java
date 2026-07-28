package com.voidiscoming.common.mechanic.level;

import java.util.UUID;

import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.server.network.ServerPlayerEntity;

public class Buff {
    private static final UUID ATTACK_ID = UUID.fromString("a5e1c2d0-1111-4a11-8a11-000000000001");
    private static final UUID HEALTH_ID = UUID.fromString("a5e1c2d0-1111-4a11-8a11-000000000002");


    public static void applyStatBonuses(ServerPlayerEntity player, int level) {
        
        EntityAttributeInstance attack = player.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE);
        if (attack != null) {
            attack.removeModifier(ATTACK_ID);
            
            if (level > 0) {
                attack.addPersistentModifier(new EntityAttributeModifier(
                        ATTACK_ID, 
                        "expMechanick.level_attack", 
                        level,
                        EntityAttributeModifier.Operation.ADDITION
                ));
            }
        }

        EntityAttributeInstance health = player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);
        if (health != null) {
            health.removeModifier(HEALTH_ID);
            
            int healthBonus = level / 5;
            if (healthBonus > 0) {
                health.addPersistentModifier(new EntityAttributeModifier(
                        HEALTH_ID, 
                        "expMechanick.level_health", 
                        healthBonus,
                        EntityAttributeModifier.Operation.ADDITION
                ));
            }
        }
    }
}