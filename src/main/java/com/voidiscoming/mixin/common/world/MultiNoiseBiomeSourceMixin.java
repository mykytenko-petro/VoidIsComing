package com.voidiscoming.mixin.common.world;

import com.voidiscoming.common.world.VoidPlainsBiomeReplacer;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.MultiNoiseBiomeSource;
import net.minecraft.world.biome.source.util.MultiNoiseUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MultiNoiseBiomeSource.class)
public class MultiNoiseBiomeSourceMixin {

    @Inject(method = "getBiome", at = @At("RETURN"), cancellable = true)
    private void voidiscoming$replacePlains(int x, int y, int z, MultiNoiseUtil.MultiNoiseSampler noise, CallbackInfoReturnable<RegistryEntry<Biome>> cir) {
        RegistryEntry<Biome> original = cir.getReturnValue();
        RegistryEntry<Biome> replaced = VoidPlainsBiomeReplacer.replace(original, x, z);
        if (replaced != original) cir.setReturnValue(replaced);
    }
}