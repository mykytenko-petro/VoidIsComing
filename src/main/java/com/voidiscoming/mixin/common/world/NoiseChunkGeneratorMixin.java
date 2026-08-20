package com.voidiscoming.mixin.common.world;

import com.voidiscoming.common.block.ModBlocks;
import com.voidiscoming.common.world.biome.ModBiomes;
import net.minecraft.block.Blocks;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ChunkRegion;
import net.minecraft.world.Heightmap;
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

    @Inject(method = "buildSurface", at = @At("TAIL"))
    private void voidIsComing$replaceVoidPlainsSurface(ChunkRegion region, StructureAccessor structures, NoiseConfig noiseConfig, Chunk chunk, CallbackInfo ci) {
        int startX = chunk.getPos().getStartX();
        int startZ = chunk.getPos().getStartZ();

        for (int x = startX; x < startX + 16; x++) {
            for (int z = startZ; z < startZ + 16; z++) {
                int topY = chunk.getHeightmap(Heightmap.Type.WORLD_SURFACE_WG).get(x & 15, z & 15);

                if (topY <= region.getBottomY()) {
                    continue;
                }

                BlockPos biomePos = new BlockPos(x, topY - 1, z);
                RegistryEntry<Biome> biome = region.getBiome(biomePos);

                if (!biome.matchesKey(ModBiomes.VOID_PLAINS_KEY)) {
                    continue;
                }

                BlockPos surfacePos = new BlockPos(x, topY - 1, z);

                if (chunk.getBlockState(surfacePos).isOf(Blocks.GRASS_BLOCK)) {
                    chunk.setBlockState(surfacePos, ModBlocks.VOID_GRASS.getDefaultState(), false);
                }
            }
        }
    }
}