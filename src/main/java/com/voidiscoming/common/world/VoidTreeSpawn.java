package com.voidiscoming.common.world;

import com.voidiscoming.common.block.ModBlocks;
import com.voidiscoming.common.world.biome.ModBiomes;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Heightmap;
import net.minecraft.world.PersistentState;
import net.minecraft.world.PersistentStateManager;
import net.minecraft.world.gen.feature.ConfiguredFeature;

import java.util.HashSet;
import java.util.Set;

public class VoidTreeSpawn {

    private static final int AREA_SIZE = 128;
    private static final int MIN_TREES_PER_AREA = 1;
    private static final int MAX_TREES_PER_AREA = 2;
    private static final int SEARCH_ATTEMPTS = 100;

    private static final String STATE_KEY = "voidiscoming_tree_spawn_v5";

    public static void spawnInitialTrees(ServerWorld world, BlockPos spawnPos) {
        System.out.println("[VoidIsComing] Generating guaranteed Void Plains trees...");

        ConfiguredFeature<?, ?> tree = world.getRegistryManager()
                .get(RegistryKeys.CONFIGURED_FEATURE)
                .get(Identifier.of("voidiscoming", "void_tree"));

        if (tree == null) {
            System.out.println("[VoidIsComing] Void Tree configured feature not found!");
            return;
        }

        TreeSpawnState state = TreeSpawnState.get(world);

        int centerAreaX = Math.floorDiv(spawnPos.getX(), AREA_SIZE);
        int centerAreaZ = Math.floorDiv(spawnPos.getZ(), AREA_SIZE);

        for (int offsetX = -2; offsetX <= 2; offsetX++) {
            for (int offsetZ = -2; offsetZ <= 2; offsetZ++) {
                int areaX = centerAreaX + offsetX;
                int areaZ = centerAreaZ + offsetZ;

                long areaKey = getAreaKey(areaX, areaZ);

                if (state.processedAreas.contains(areaKey)) {
                    continue;
                }

                generateTreesInArea(world, tree, state, areaX, areaZ);
            }
        }

        System.out.println("[VoidIsComing] Guaranteed Void Plains trees generated!");
    }

    private static void generateTreesInArea(ServerWorld world, ConfiguredFeature<?, ?> tree, TreeSpawnState state, int areaX, int areaZ) {
        int centerX = areaX * AREA_SIZE + AREA_SIZE / 2;
        int centerZ = areaZ * AREA_SIZE + AREA_SIZE / 2;

        BlockPos areaCenter = new BlockPos(centerX, world.getSeaLevel(), centerZ);

        if (!world.getBiome(areaCenter).matchesKey(ModBiomes.VOID_PLAINS_KEY)) {
            return;
        }

        Random random = Random.create(getAreaKey(areaX, areaZ));

        int treesToSpawn = MIN_TREES_PER_AREA + random.nextInt(MAX_TREES_PER_AREA - MIN_TREES_PER_AREA + 1);

        int spawnedTrees = 0;

        int minX = areaX * AREA_SIZE;
        int minZ = areaZ * AREA_SIZE;

        for (int attempt = 0; attempt < SEARCH_ATTEMPTS && spawnedTrees < treesToSpawn; attempt++) {
            int x = minX + random.nextInt(AREA_SIZE);
            int z = minZ + random.nextInt(AREA_SIZE);

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

            BlockPos treePos = ground;

            if (!world.getBlockState(treePos.down()).isOf(ModBlocks.VOID_GRASS)) {
                continue;
            }

            if (!world.getBlockState(treePos).isAir()) {
                continue;
            }

            if (!world.getBlockState(treePos.up()).isAir()) {
                continue;
            }

            if (isTooCloseToAnotherTree(world, treePos)) {
                continue;
            }

            if (!tree.generate(world, world.getChunkManager().getChunkGenerator(), random, treePos)) {
                continue;
            }

            spawnedTrees++;

            System.out.println("[VoidIsComing] Void Tree spawned at " + treePos + " (" + spawnedTrees + "/" + treesToSpawn + ")");
        }

        if (spawnedTrees > 0) {
            state.processedAreas.add(getAreaKey(areaX, areaZ));
            state.markDirty();
        } else {
            System.out.println("[VoidIsComing] Could not find tree position in Void Plains area " + areaX + ", " + areaZ);
        }
    }

    private static boolean isTooCloseToAnotherTree(ServerWorld world, BlockPos pos) {
        int radius = 12;

        for (int x = -radius; x <= radius; x += 4) {
            for (int z = -radius; z <= radius; z += 4) {
                BlockPos check = pos.add(x, 0, z);

                for (int y = -1; y <= 12; y++) {
                    if (world.getBlockState(check.up(y)).isOf(ModBlocks.VOID_LOG)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    private static long getAreaKey(int areaX, int areaZ) {
        return ((long) areaX << 32) ^ (areaZ & 0xffffffffL);
    }

    private static class TreeSpawnState extends PersistentState {

        private final Set<Long> processedAreas = new HashSet<>();

        public static TreeSpawnState get(ServerWorld world) {
            PersistentStateManager manager = world.getPersistentStateManager();
            return manager.getOrCreate(TreeSpawnState::fromNbt, TreeSpawnState::new, STATE_KEY);
        }

        private static TreeSpawnState fromNbt(NbtCompound nbt) {
            TreeSpawnState state = new TreeSpawnState();

            for (long area : nbt.getLongArray("processed_areas")) {
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