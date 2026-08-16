package com.voidiscoming.common.network.spell;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import com.voidiscoming.common.component.ModComponents;
import net.minecraft.util.Identifier;

public class SpellEquipUpdateReceiver {
    public static void receive(
        MinecraftServer server,
        ServerPlayerEntity player,
        ServerPlayNetworkHandler handler,
        PacketByteBuf buf,
        PacketSender responseSender
    ) {
        Identifier skillId = buf.readIdentifier();

        server.execute(() -> {
            if (player == null || player.isRemoved()) return;

            ModComponents.SPELLS.maybeGet(player).ifPresent(spellComp -> {
                spellComp.toggleSpell(skillId);
            });
        });
    }
}