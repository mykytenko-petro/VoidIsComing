package com.voidiscoming.common.world;

import com.voidiscoming.common.world.biome.ModBiomes;

import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.PersistentState;
import net.minecraft.world.PersistentStateManager;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

public class VoidWorldSpawn {

    private static final int SEARCH_STEP = 128;
    private static final int SEARCH_RADIUS = 16;

    public static void register() {
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            ServerPlayerEntity player = handler.getPlayer();

            if (player.getWorld().getRegistryKey() != World.OVERWORLD) {
                return;
            }

            initializePlayer(player, server);
        });
    }

    private static void initializePlayer(ServerPlayerEntity player, MinecraftServer server) {
        ServerWorld world = server.getOverworld();
        WorldSpawnState state = WorldSpawnState.get(world);

        if (state.initialized) {
            return;
        }

        System.out.println("[VoidIsComing] Preparing Void Plains before player spawn...");

        BlockPos spawnPos = findVoidPlainsSpawn(world);

        if (spawnPos == null) {
            System.out.println("[VoidIsComing] Could not find Void Plains spawn.");
            return;
        }

        System.out.println("[VoidIsComing] Found Void Plains spawn at " + spawnPos);

        world.setSpawnPos(spawnPos, 0.0F);

        System.out.println("[VoidIsComing] Generating Void Plains trees...");

        VoidTreeSpawn.spawnInitialTrees(world,spawnPos);
        VoidLittleGrassSpawn.spawnInitialGrass(world);

        System.out.println("[VoidIsComing] Void Plains trees generated!");

        System.out.println("[VoidIsComing] Spawning Void Golem...");

        VoidGolemSpawn.spawnNearPlayer(world, spawnPos);

        player.teleport(
                world,
                spawnPos.getX() + 0.5,
                spawnPos.getY(),
                spawnPos.getZ() + 0.5,
                player.getYaw(),
                player.getPitch()
        );

        state.initialized = true;
        state.markDirty();

        System.out.println("[VoidIsComing] Player teleported to Void Plains at " + spawnPos);
        System.out.println("[VoidIsComing] World spawn set at " + spawnPos);
    }

    private static BlockPos findVoidPlainsSpawn(ServerWorld world) {
        RegistryKey<Biome> voidKey = ModBiomes.VOID_PLAINS_KEY;

        for (int areaX = -SEARCH_RADIUS; areaX <= SEARCH_RADIUS; areaX++) {
            for (int areaZ = -SEARCH_RADIUS; areaZ <= SEARCH_RADIUS; areaZ++) {
                int centerX = areaX * SEARCH_STEP + SEARCH_STEP / 2;
                int centerZ = areaZ * SEARCH_STEP + SEARCH_STEP / 2;

                BlockPos biomePos = new BlockPos(
                        centerX,
                        world.getSeaLevel(),
                        centerZ
                );

                if (!world.getBiome(biomePos).matchesKey(voidKey)) {
                    continue;
                }

                world.getChunk(centerX >> 4, centerZ >> 4);

                BlockPos ground = world.getTopPosition(
                        Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                        biomePos
                );

                if (!world.getBiome(ground).matchesKey(voidKey)) {
                    continue;
                }

                BlockPos safe = findSafeSpawnPosition(world, ground);

                if (safe != null) {
                    return safe;
                }
            }
        }

        return null;
    }

    private static BlockPos findSafeSpawnPosition(ServerWorld world, BlockPos ground) {
        for (int y = ground.getY() + 2; y >= world.getBottomY(); y--) {
            BlockPos feet = new BlockPos(
                    ground.getX(),
                    y,
                    ground.getZ()
            );

            if (!world.getBiome(feet).matchesKey(ModBiomes.VOID_PLAINS_KEY)) {
                return null;
            }

            if (!world.getBlockState(feet.down()).isSolidBlock(world, feet.down())) {
                continue;
            }

            if (!world.getBlockState(feet).isAir()) {
                continue;
            }

            if (!world.getBlockState(feet.up()).isAir()) {
                continue;
            }

            return feet;
        }

        return null;
    }

    private static class WorldSpawnState extends PersistentState {

        private static final String KEY = "voidiscoming_world_spawn_v4";
        private boolean initialized;

        private WorldSpawnState() {
            initialized = false;
        }

        public static WorldSpawnState get(ServerWorld world) {
            PersistentStateManager manager = world.getPersistentStateManager();

            return manager.getOrCreate(
                    WorldSpawnState::fromNbt,
                    WorldSpawnState::new,
                    KEY
            );
        }

        private static WorldSpawnState fromNbt(NbtCompound nbt) {
            WorldSpawnState state = new WorldSpawnState();
            state.initialized = nbt.getBoolean("initialized");
            return state;
        }

        @Override
        public NbtCompound writeNbt(NbtCompound nbt) {
            nbt.putBoolean("initialized", initialized);
            return nbt;
        }
    }
}