package com.voidiscoming.common.effects;

import com.voidiscoming.common.VoidIsComing;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ModEffects {
    public static final StatusEffect VULNERABILITY = Registry.register(
        Registries.STATUS_EFFECT,
        VoidIsComing.id("vulnerability"),
        new VulnerabilityEffect()
    );

    public static void registerEffects() {
    }
}