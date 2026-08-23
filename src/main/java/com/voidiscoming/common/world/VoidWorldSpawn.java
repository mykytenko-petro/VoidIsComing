package com.voidiscoming.common.world;

import com.voidiscoming.common.world.biome.ModBiomes;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
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

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class VoidWorldSpawn {

    private static final int SEARCH_STEP = 128;
    private static final int SEARCH_RADIUS = 16;
    private static final int TELEPORT_DELAY_TICKS = 10;

    private static final Set<UUID> PENDING_PLAYERS = new HashSet<>();
    private static final Set<UUID> PROCESSING_PLAYERS = new HashSet<>();

    public static void register() {
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> PENDING_PLAYERS.add(handler.getPlayer().getUuid()));

        ServerTickEvents.END_SERVER_TICK.register(server -> {
            if (PENDING_PLAYERS.isEmpty()) {
                return;
            }

            for (UUID uuid : new HashSet<>(PENDING_PLAYERS)) {
                ServerPlayerEntity player = server.getPlayerManager().getPlayer(uuid);

                if (player == null) {
                    continue;
                }

                PENDING_PLAYERS.remove(uuid);

                if (!PROCESSING_PLAYERS.add(uuid)) {
                    continue;
                }

                server.execute(() -> initializePlayer(player, server));
            }
        });
    }

    private static void initializePlayer(ServerPlayerEntity player, MinecraftServer server) {
        try {
            if (player.getWorld().getRegistryKey() != World.OVERWORLD) {
                return;
            }

            ServerWorld world = server.getOverworld();
            WorldSpawnState state = WorldSpawnState.get(world);

            if (state.initialized) {
                return;
            }

            System.out.println("[VoidIsComing] Searching for Void Plains spawn...");

            BlockPos spawnPos = findVoidPlainsSpawn(world);

            if (spawnPos == null) {
                System.out.println("[VoidIsComing] Could not find Void Plains spawn.");
                return;
            }

            world.getChunk(spawnPos.getX() >> 4, spawnPos.getZ() >> 4);

            BlockPos finalSpawnPos = findSafeSpawnPosition(world, spawnPos);

            if (finalSpawnPos == null) {
                System.out.println("[VoidIsComing] Could not find safe Void Plains surface.");
                return;
            }

            world.setSpawnPos(finalSpawnPos, 0.0F);

            player.teleport(
                    world,
                    finalSpawnPos.getX() + 0.5,
                    finalSpawnPos.getY(),
                    finalSpawnPos.getZ() + 0.5,
                    player.getYaw(),
                    player.getPitch()
            );

            state.initialized = true;
            state.markDirty();

            System.out.println("[VoidIsComing] Player teleported to Void Plains at " + finalSpawnPos);

            VoidGolemSpawn.spawnNearPlayer(world, finalSpawnPos);
        } finally {
            PROCESSING_PLAYERS.remove(player.getUuid());
        }
    }

    private static BlockPos findVoidPlainsSpawn(ServerWorld world) {
        RegistryKey<Biome> voidKey = ModBiomes.VOID_PLAINS_KEY;

        for (int areaX = -SEARCH_RADIUS; areaX <= SEARCH_RADIUS; areaX++) {
            for (int areaZ = -SEARCH_RADIUS; areaZ <= SEARCH_RADIUS; areaZ++) {
                int centerX = areaX * SEARCH_STEP + SEARCH_STEP / 2;
                int centerZ = areaZ * SEARCH_STEP + SEARCH_STEP / 2;

                BlockPos biomePos = new BlockPos(centerX, world.getSeaLevel(), centerZ);

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
                    System.out.println("[VoidIsComing] Found Void Plains spawn at " + safe);
                    return safe;
                }
            }
        }

        return null;
    }

    private static BlockPos findSafeSpawnPosition(ServerWorld world, BlockPos ground) {
        for (int y = ground.getY() + 2; y >= world.getBottomY(); y--) {
            BlockPos feet = new BlockPos(ground.getX(), y, ground.getZ());

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
            return manager.getOrCreate(WorldSpawnState::fromNbt, WorldSpawnState::new, KEY);
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