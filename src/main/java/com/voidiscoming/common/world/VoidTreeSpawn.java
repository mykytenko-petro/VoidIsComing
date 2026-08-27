package com.voidiscoming.common.world;

import com.voidiscoming.common.block.ModBlocks;
import com.voidiscoming.common.world.biome.ModBiomes;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Heightmap;
import net.minecraft.world.PersistentState;
import net.minecraft.world.PersistentStateManager;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.ConfiguredFeature;

import java.util.HashSet;
import java.util.Set;

public class VoidTreeSpawn {

    private static final int AREA_SIZE = 128;
    private static final int TREE_CHANCE = 35;

    private static final String STATE_KEY = "voidiscoming_tree_spawn_v2";

    public static void register() {
        ServerTickEvents.END_WORLD_TICK.register(world -> {
            if (world.getRegistryKey() != World.OVERWORLD) {
                return;
            }

            spawnTrees(world);
        });
    }

    private static void spawnTrees(ServerWorld world) {
        TreeSpawnState state = TreeSpawnState.get(world);

        int centerX = world.getSpawnPos().getX();
        int centerZ = world.getSpawnPos().getZ();

        int areaX = Math.floorDiv(centerX, AREA_SIZE);
        int areaZ = Math.floorDiv(centerZ, AREA_SIZE);

        for (int offsetX = -2; offsetX <= 2; offsetX++) {
            for (int offsetZ = -2; offsetZ <= 2; offsetZ++) {
                int currentAreaX = areaX + offsetX;
                int currentAreaZ = areaZ + offsetZ;

                long areaKey = getAreaKey(currentAreaX, currentAreaZ);

                if (state.processedAreas.contains(areaKey)) {
                    continue;
                }

                int minX = currentAreaX * AREA_SIZE;
                int minZ = currentAreaZ * AREA_SIZE;

                Random random = Random.create(areaKey);

                int x = minX + random.nextInt(AREA_SIZE);
                int z = minZ + random.nextInt(AREA_SIZE);

                BlockPos checkPos = new BlockPos(x, world.getSeaLevel(), z);

                world.getChunk(x >> 4, z >> 4);

                if (!world.getBiome(checkPos).matchesKey(ModBiomes.VOID_PLAINS_KEY)) {
                    continue;
                }

                BlockPos ground = world.getTopPosition(
                        Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                        checkPos
                );

                if (!world.getBiome(ground).matchesKey(ModBiomes.VOID_PLAINS_KEY)) {
                    continue;
                }

                BlockPos treePos = ground;

                if (!world.getBlockState(treePos.down()).isOf(ModBlocks.VOID_GRASS)) {
                    state.processedAreas.add(areaKey);
                    state.markDirty();
                    continue;
                }

                if (random.nextInt(100) >= TREE_CHANCE) {
                    state.processedAreas.add(areaKey);
                    state.markDirty();
                    continue;
                }

                ConfiguredFeature<?, ?> tree = world.getRegistryManager()
                        .get(RegistryKeys.CONFIGURED_FEATURE)
                        .get(Identifier.of("voidiscoming", "void_tree"));

                if (tree == null) {
                    System.out.println("[VoidIsComing] Void Tree configured feature not found!");
                    continue;
                }

                boolean generated = tree.generate(
                        world,
                        world.getChunkManager().getChunkGenerator(),
                        random,
                        treePos
                );

                if (!generated) {
                    continue;
                }

                System.out.println("[VoidIsComing] Void Tree spawned at " + treePos);
                state.processedAreas.add(areaKey);
                state.markDirty();
            }
        }
    }

    private static long getAreaKey(int areaX, int areaZ) {
        return ((long) areaX << 32) ^ (areaZ & 0xffffffffL);
    }

    private static class TreeSpawnState extends PersistentState {

        private final Set<Long> processedAreas = new HashSet<>();

        public static TreeSpawnState get(ServerWorld world) {
            PersistentStateManager manager = world.getPersistentStateManager();

            return manager.getOrCreate(
                    TreeSpawnState::fromNbt,
                    TreeSpawnState::new,
                    STATE_KEY
            );
        }

        private static TreeSpawnState fromNbt(NbtCompound nbt) {
            TreeSpawnState state = new TreeSpawnState();

            long[] areas = nbt.getLongArray("processed_areas");

            for (long area : areas) {
                state.processedAreas.add(area);
            }

            return state;
        }

        @Override
        public NbtCompound writeNbt(NbtCompound nbt) {
            long[] areas = new long[processedAreas.size()];

            int index = 0;

            for (long area : processedAreas) {
                areas[index++] = area;
            }

            nbt.putLongArray("processed_areas", areas);

            return nbt;
        }
    }
}