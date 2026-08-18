package com.voidiscoming.mixin.common.entity;

import com.voidiscoming.common.effects.ModEffects;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityDamageMixin {

    @Shadow public abstract StatusEffectInstance getStatusEffect(net.minecraft.entity.effect.StatusEffect effect);

    @ModifyVariable(method = "damage", at = @At("HEAD"), argsOnly = true)
    private float modifyDamageAmount(float amount, DamageSource source, float originalAmount) {
        LivingEntity entity = (LivingEntity) (Object) this;

        StatusEffectInstance effectInstance = entity.getStatusEffect(ModEffects.VULNERABILITY);
        if (effectInstance != null) {
            int amplifier = effectInstance.getAmplifier(); 
            

            float multiplier = 1.0f + 0.25f * (amplifier + 1);
            return amount * multiplier;
        }

        return amount;
    }
}