package com.voidiscoming.mixin.common.world;

import com.voidiscoming.common.block.ModBlocks;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ChunkRegion;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.NoiseChunkGenerator;
import net.minecraft.world.gen.noise.NoiseConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(NoiseChunkGenerator.class)
public abstract class NoiseChunkGeneratorMixin {

    private static final RegistryKey<Biome> VOID_PLAINS = RegistryKey.of(RegistryKeys.BIOME, Identifier.of("voidiscoming", "void_plains"));

    @Inject(method = "generateFeatures", at = @At("TAIL"))
    private void voidIsComing$replacePlainsSurface(ChunkRegion region, StructureAccessor structures, NoiseConfig noiseConfig, Chunk chunk, CallbackInfo ci) {
        for (int x = chunk.getPos().getStartX(); x < chunk.getPos().getEndX(); x++) {
            for (int z = chunk.getPos().getStartZ(); z < chunk.getPos().getEndZ(); z++) {
                int topY = chunk.getHeightmap(net.minecraft.world.Heightmap.Type.WORLD_SURFACE_WG).get(x & 15, z & 15);

                if (!region.getBiome(new BlockPos(x, topY, z)).matchesKey(VOID_PLAINS)) {
                    continue;
                }

                if (topY <= region.getBottomY()) {
                    continue;
                }

                chunk.setBlockState(new BlockPos(x, topY - 1, z), ModBlocks.VOID_GRASS.getDefaultState(), false);
            }
        }
    }
}