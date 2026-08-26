package com.voidiscoming.mixin.common.player;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import com.voidiscoming.common.mechanic.stat.PlayerStats;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.tag.DamageTypeTags;

@Mixin(LivingEntity.class)
public abstract class FlatArmorDamageCalculationMixin {
    @Overwrite
    public float applyArmorToDamage(DamageSource source, float amount) {
        var entity = (LivingEntity) (Object) this;

        if (source.isIn(DamageTypeTags.BYPASSES_ARMOR)) {
            return amount;
        }

        double totalArmor = entity.getAttributeValue(EntityAttributes.GENERIC_ARMOR);
        double resilience = 0.0;

        if (entity instanceof PlayerEntity player) {
            resilience = PlayerStats.RESILIENCE.getValue(player);
        }

        double damageAfterArmor = Math.max(amount - totalArmor, 0.0);
        double finalDamage = damageAfterArmor * (1.0 - resilience);

        return (float) Math.max(finalDamage, amount * 0.1);
    }
}