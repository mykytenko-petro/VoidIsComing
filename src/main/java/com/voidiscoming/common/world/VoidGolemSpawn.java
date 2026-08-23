package com.voidiscoming.common.world;

import com.voidiscoming.common.entity.ModEntities;
import com.voidiscoming.common.entity.stonegolem.StoneGolemEntity;
import com.voidiscoming.common.world.biome.ModBiomes;

import net.minecraft.entity.Entity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;

public class VoidGolemSpawn {

    private static final int AREA_SIZE = 128;
    private static final int MIN_DISTANCE = 32;
    private static final int MAX_DISTANCE = 56;

    public static void spawnNearPlayer(ServerWorld world, BlockPos playerPos) {
        int areaX = Math.floorDiv(playerPos.getX(), AREA_SIZE);
        int areaZ = Math.floorDiv(playerPos.getZ(), AREA_SIZE);

        if (hasGolemInArea(world, areaX, areaZ)) {
            System.out.println("[VoidIsComing] Void Golem already exists in area " + areaX + ", " + areaZ);
            return;
        }

        BlockPos spawnPos = findSpawnPosition(world, playerPos, areaX, areaZ);

        if (spawnPos == null) {
            System.out.println("[VoidIsComing] Could not find Stone Golem spawn position.");
            return;
        }

        spawnGolem(world, spawnPos);
    }

    private static BlockPos findSpawnPosition(ServerWorld world, BlockPos playerPos, int areaX, int areaZ) {
        int minX = areaX * AREA_SIZE;
        int minZ = areaZ * AREA_SIZE;
        int maxX = minX + AREA_SIZE - 1;
        int maxZ = minZ + AREA_SIZE - 1;

        for (int attempt = 0; attempt < 64; attempt++) {
            double angle = world.random.nextDouble() * Math.PI * 2.0;
            int distance = MIN_DISTANCE + world.random.nextInt(MAX_DISTANCE - MIN_DISTANCE + 1);

            int x = playerPos.getX() + (int) Math.round(Math.cos(angle) * distance);
            int z = playerPos.getZ() + (int) Math.round(Math.sin(angle) * distance);

            if (x < minX || x > maxX || z < minZ || z > maxZ) {
                continue;
            }

            world.getChunk(x >> 4, z >> 4);

            BlockPos ground = world.getTopPosition(
                    Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                    new BlockPos(x, world.getTopY(), z)
            );

            if (!world.getBiome(ground).matchesKey(ModBiomes.VOID_PLAINS_KEY)) {
                continue;
            }

            if (!world.getBlockState(ground.down()).isSolidBlock(world, ground.down())) {
                continue;
            }

            BlockPos feet = ground;

            if (!world.getBlockState(feet).isAir()) {
                feet = feet.up();
            }

            if (!world.getBlockState(feet).isAir()) {
                continue;
            }

            if (!world.getBlockState(feet.up()).isAir()) {
                continue;
            }

            if (!world.getBlockState(feet.up(2)).isAir()) {
                continue;
            }

            return feet;
        }

        return null;
    }

    private static boolean hasGolemInArea(ServerWorld world, int areaX, int areaZ) {
        int minX = areaX * AREA_SIZE;
        int minZ = areaZ * AREA_SIZE;
        int maxX = minX + AREA_SIZE;
        int maxZ = minZ + AREA_SIZE;

        for (Entity entity : world.iterateEntities()) {
            if (!(entity instanceof StoneGolemEntity golem)) {
                continue;
            }

            if (!golem.isAlive()) {
                continue;
            }

            if (golem.getX() >= minX && golem.getX() < maxX && golem.getZ() >= minZ && golem.getZ() < maxZ) {
                return true;
            }
        }

        return false;
    }

    private static void spawnGolem(ServerWorld world, BlockPos spawnPos) {
        StoneGolemEntity golem = ModEntities.STONE_GOLEM.create(world);

        if (golem == null) {
            System.out.println("[VoidIsComing] Failed to create Stone Golem.");
            return;
        }

        golem.refreshPositionAndAngles(
                spawnPos.getX() + 0.5,
                spawnPos.getY(),
                spawnPos.getZ() + 0.5,
                world.random.nextFloat() * 360.0F,
                0.0F
        );

        if (!world.spawnEntity(golem)) {
            System.out.println("[VoidIsComing] Failed to spawn Stone Golem at " + spawnPos);
            return;
        }

        System.out.println("[VoidIsComing] Stone Golem spawned at " + spawnPos);
    }
}