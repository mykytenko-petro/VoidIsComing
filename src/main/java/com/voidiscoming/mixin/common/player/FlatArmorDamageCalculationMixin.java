package com.voidiscoming.mixin.common.player;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
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

        return (float) Math.max(Math.max(amount - totalArmor, amount * 0.1), 1);
    }
}
