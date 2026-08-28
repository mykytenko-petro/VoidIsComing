package com.voidiscoming.common.world;

import com.voidiscoming.common.block.ModBlocks;
import com.voidiscoming.common.world.biome.ModBiomes;

import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Heightmap;

public class VoidLittleGrassSpawn {

    private static final int AREA_SIZE = 128;
    private static final int MIN_GRASS_PER_AREA = 8;
    private static final int MAX_GRASS_PER_AREA = 14;
    private static final int SEARCH_ATTEMPTS = 100;

    public static void spawnInitialGrass(ServerWorld world) {
        int centerX = world.getSpawnPos().getX();
        int centerZ = world.getSpawnPos().getZ();

        int centerAreaX = Math.floorDiv(centerX, AREA_SIZE);
        int centerAreaZ = Math.floorDiv(centerZ, AREA_SIZE);

        Random random = Random.create(world.getSeed());

        for (int offsetX = -2; offsetX <= 2; offsetX++) {
            for (int offsetZ = -2; offsetZ <= 2; offsetZ++) {
                int areaX = centerAreaX + offsetX;
                int areaZ = centerAreaZ + offsetZ;

                BlockPos areaCenter = new BlockPos(
                        areaX * AREA_SIZE + AREA_SIZE / 2,
                        world.getSeaLevel(),
                        areaZ * AREA_SIZE + AREA_SIZE / 2
                );

                if (!world.getBiome(areaCenter).matchesKey(ModBiomes.VOID_PLAINS_KEY)) {
                    continue;
                }

                Random areaRandom = Random.create(
                        getAreaKey(areaX, areaZ) ^ world.getSeed()
                );

                int grassToSpawn = MIN_GRASS_PER_AREA
                        + areaRandom.nextInt(MAX_GRASS_PER_AREA - MIN_GRASS_PER_AREA + 1);

                int spawned = 0;

                int minX = areaX * AREA_SIZE;
                int minZ = areaZ * AREA_SIZE;

                for (int attempt = 0; attempt < SEARCH_ATTEMPTS && spawned < grassToSpawn; attempt++) {
                    int x = minX + areaRandom.nextInt(AREA_SIZE);
                    int z = minZ + areaRandom.nextInt(AREA_SIZE);

                    BlockPos checkPos = new BlockPos(x, world.getSeaLevel(), z);

                    if (!world.getBiome(checkPos).matchesKey(ModBiomes.VOID_PLAINS_KEY)) {
                        continue;
                    }

                    world.getChunk(x >> 4, z >> 4);

                    BlockPos ground = world.getTopPosition(
                            Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                            checkPos
                    );

                    if (!world.getBiome(ground).matchesKey(ModBiomes.VOID_PLAINS_KEY)) {
                        continue;
                    }

                    BlockPos grassPos = ground;

                    if (!world.getBlockState(grassPos.down()).isOf(ModBlocks.VOID_GRASS)) {
                        continue;
                    }

                    if (!world.getBlockState(grassPos).isAir()) {
                        continue;
                    }

                    if (!world.getBlockState(grassPos.down()).isSolidBlock(world, grassPos.down())) {
                        continue;
                    }

                    if (!world.getBlockState(grassPos).canPlaceAt(world, grassPos)) {
                        continue;
                    }

                    world.setBlockState(
                            grassPos,
                            ModBlocks.LITTLE_VOID_GRASS.getDefaultState(),
                            3
                    );

                    spawned++;
                }

                if (spawned > 0) {
                    System.out.println(
                            "[VoidIsComing] Little Void Grass spawned in area "
                                    + areaX + ", " + areaZ
                                    + " (" + spawned + "/" + grassToSpawn + ")"
                    );
                }
            }
        }
    }

    private static long getAreaKey(int areaX, int areaZ) {
        return ((long) areaX << 32) ^ (areaZ & 0xffffffffL);
    }
}