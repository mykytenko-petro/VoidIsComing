package com.voidiscoming.common.world;

import com.voidiscoming.common.entity.ModEntities;
import com.voidiscoming.common.world.biome.ModBiomes;
import net.minecraft.entity.SpawnReason;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;

public class VoidGolemSpawn {

    private static boolean spawned = false;
    private static final int MIN_DISTANCE = 64;
    private static final int MAX_DISTANCE = 256;

    public static void spawn(ServerWorld world, BlockPos center) {
        if (spawned) {
            return;
        }

        BlockPos golemPos = findSpawnPosition(world, center);

        if (golemPos == null) {
            System.out.println("[VoidIsComing] Could not find Void Plains position for Stone Golem");
            return;
        }

        if (ModEntities.STONE_GOLEM.spawn(world, golemPos, SpawnReason.EVENT) == null) {
            System.out.println("[VoidIsComing] Failed to spawn Stone Golem");
            return;
        }

        spawned = true;

        System.out.println("[VoidIsComing] Stone Golem spawned at " + golemPos);
    }

    private static BlockPos findSpawnPosition(ServerWorld world, BlockPos center) {
        for (int distance = MIN_DISTANCE; distance <= MAX_DISTANCE; distance += 16) {
            for (int angle = 0; angle < 360; angle += 30) {
                double radians = Math.toRadians(angle);

                int x = center.getX() + (int) (Math.cos(radians) * distance);
                int z = center.getZ() + (int) (Math.sin(radians) * distance);

                BlockPos ground = world.getTopPosition(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, new BlockPos(x, 0, z));

                if (!world.getBiome(ground).matchesKey(ModBiomes.VOID_PLAINS_KEY)) {
                    continue;
                }

                BlockPos feet = ground;
                BlockPos head = ground.up();

                if (!world.getBlockState(feet.down()).isSolidBlock(world, feet.down())) {
                    continue;
                }

                if (!world.getBlockState(feet).isAir() || !world.getBlockState(head).isAir()) {
                    continue;
                }

                return feet;
            }
        }

        return null;
    }
}