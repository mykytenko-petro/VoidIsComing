package com.voidiscoming.common.world;

import com.voidiscoming.common.world.biome.ModBiomes;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.block.BlockState;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

public class VoidWorldSpawn {

    private static final int SEARCH_STEP = 128;
    private static final int SEARCH_RADIUS = 32;
    private static boolean teleported = false;

    public static void register() {
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> onPlayerJoin(handler.getPlayer(), server));
    }

    private static void onPlayerJoin(ServerPlayerEntity player, MinecraftServer server) {
        if (teleported) {
            return;
        }

        if (player.getWorld().getRegistryKey() != World.OVERWORLD) {
            return;
        }

        ServerWorld world = server.getOverworld();
        BlockPos voidPos = findVoidPlains(world);

        if (voidPos == null) {
            return;
        }

        world.getChunk(voidPos.getX() >> 4, voidPos.getZ() >> 4);

        int surfaceY = findSurfaceY(world, voidPos.getX(), voidPos.getZ());

        if (surfaceY == Integer.MIN_VALUE) {
            System.out.println("[VoidIsComing] Could not find surface at " + voidPos.getX() + ", " + voidPos.getZ());
            return;
        }

        BlockPos spawnPos = new BlockPos(voidPos.getX(), surfaceY + 1, voidPos.getZ());

        world.setSpawnPos(spawnPos, 0.0F);

        player.teleport(world, spawnPos.getX() + 0.5, spawnPos.getY(), spawnPos.getZ() + 0.5, player.getYaw(), player.getPitch());
        VoidGolemSpawn.spawn(world, spawnPos);

        teleported = true;

        System.out.println("[VoidIsComing] First player teleported to Void Plains and world spawn set at " + spawnPos);
    }

    private static int findSurfaceY(ServerWorld world, int x, int z) {
        for (int y = world.getTopY() - 1; y >= world.getBottomY(); y--) {
            BlockPos pos = new BlockPos(x, y, z);
            BlockState state = world.getBlockState(pos);

            if (!state.isAir() && !state.getCollisionShape(world, pos).isEmpty()) {
                return y;
            }
        }

        return Integer.MIN_VALUE;
    }

    private static BlockPos findVoidPlains(ServerWorld world) {
        RegistryKey<Biome> voidKey = ModBiomes.VOID_PLAINS_KEY;

        for (int areaX = -SEARCH_RADIUS; areaX <= SEARCH_RADIUS; areaX++) {
            for (int areaZ = -SEARCH_RADIUS; areaZ <= SEARCH_RADIUS; areaZ++) {
                int x = areaX * SEARCH_STEP + SEARCH_STEP / 2;
                int z = areaZ * SEARCH_STEP + SEARCH_STEP / 2;

                BlockPos pos = new BlockPos(x, world.getSeaLevel(), z);

                if (world.getBiome(pos).matchesKey(voidKey)) {
                    return pos;
                }
            }
        }

        return null;
    }
}