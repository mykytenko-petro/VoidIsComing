package com.voidiscoming.client.network.spell;

import com.voidiscoming.common.network.ModNetworking;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.network.PacketByteBuf;

public class CastSpellSender {
    public static void send(int slotIndex) {
        PacketByteBuf buf = PacketByteBufs.create();
        
        buf.writeInt(slotIndex);

        ClientPlayNetworking.send(ModNetworking.USE_SPELL_PACKET, buf);
    }
}
