package com.voidiscoming.common.world;

import com.voidiscoming.common.block.ModBlocks;
import com.voidiscoming.common.world.biome.ModBiomes;

import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Heightmap;

public class VoidLittleGrassSpawn {

    private static final int AREA_SIZE = 128;
    private static final int MIN_CLUSTERS_PER_AREA = 4;
    private static final int MAX_CLUSTERS_PER_AREA = 7;
    private static final int MIN_GRASS_PER_CLUSTER = 5;
    private static final int MAX_GRASS_PER_CLUSTER = 10;
    private static final int CLUSTER_RADIUS = 5;
    private static final int SEARCH_ATTEMPTS = 100;

    public static void spawnInitialGrass(ServerWorld world) {
        int centerX = world.getSpawnPos().getX();
        int centerZ = world.getSpawnPos().getZ();

        int centerAreaX = Math.floorDiv(centerX, AREA_SIZE);
        int centerAreaZ = Math.floorDiv(centerZ, AREA_SIZE);

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

                int clustersToSpawn = MIN_CLUSTERS_PER_AREA
                        + areaRandom.nextInt(MAX_CLUSTERS_PER_AREA - MIN_CLUSTERS_PER_AREA + 1);

                int spawnedClusters = 0;

                int minX = areaX * AREA_SIZE;
                int minZ = areaZ * AREA_SIZE;

                for (int clusterAttempt = 0; clusterAttempt < SEARCH_ATTEMPTS && spawnedClusters < clustersToSpawn; clusterAttempt++) {
                    int centerGrassX = minX + areaRandom.nextInt(AREA_SIZE);
                    int centerGrassZ = minZ + areaRandom.nextInt(AREA_SIZE);

                    BlockPos centerCheck = new BlockPos(
                            centerGrassX,
                            world.getSeaLevel(),
                            centerGrassZ
                    );

                    if (!world.getBiome(centerCheck).matchesKey(ModBiomes.VOID_PLAINS_KEY)) {
                        continue;
                    }

                    world.getChunk(centerGrassX >> 4, centerGrassZ >> 4);

                    BlockPos centerGround = world.getTopPosition(
                            Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                            centerCheck
                    );

                    if (!world.getBiome(centerGround).matchesKey(ModBiomes.VOID_PLAINS_KEY)) {
                        continue;
                    }

                    if (!world.getBlockState(centerGround.down()).isOf(ModBlocks.VOID_GRASS)) {
                        continue;
                    }

                    int grassToSpawn = MIN_GRASS_PER_CLUSTER
                            + areaRandom.nextInt(MAX_GRASS_PER_CLUSTER - MIN_GRASS_PER_CLUSTER + 1);

                    int spawnedGrass = 0;

                    for (int grassAttempt = 0; grassAttempt < grassToSpawn * 4 && spawnedGrass < grassToSpawn; grassAttempt++) {
                        int x = centerGrassX + areaRandom.nextInt(CLUSTER_RADIUS * 2 + 1) - CLUSTER_RADIUS;
                        int z = centerGrassZ + areaRandom.nextInt(CLUSTER_RADIUS * 2 + 1) - CLUSTER_RADIUS;

                        if (x < minX || x >= minX + AREA_SIZE || z < minZ || z >= minZ + AREA_SIZE) {
                            continue;
                        }

                        BlockPos checkPos = new BlockPos(
                                x,
                                world.getSeaLevel(),
                                z
                        );

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

                        spawnedGrass++;
                    }

                    if (spawnedGrass > 0) {
                        spawnedClusters++;

                        System.out.println(
                                "[VoidIsComing] Little Void Grass cluster spawned in area "
                                        + areaX + ", " + areaZ
                                        + " (" + spawnedGrass + " grass)"
                        );
                    }
                }
            }
        }
    }

    private static long getAreaKey(int areaX, int areaZ) {
        return ((long) areaX << 32) ^ (areaZ & 0xffffffffL);
    }
}