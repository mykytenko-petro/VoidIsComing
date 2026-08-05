package com.voidiscoming.client;

import com.voidiscoming.common.VoidIsComing;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.network.PacketByteBuf;

public class ClientModEvents {

    public static void register() {
        

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null) return;

            if (ModKeyBinds.spell1Key.wasPressed()) {
                useSpellInSlot(0); 
            }
            if (ModKeyBinds.spell2Key.wasPressed()) {
                useSpellInSlot(1); 
            }
            if (ModKeyBinds.spell3Key.wasPressed()) {
                useSpellInSlot(2);
            }
            if (ModKeyBinds.spell4Key.wasPressed()) {
                useSpellInSlot(3); 
            }
        });
    }

    private static void useSpellInSlot(int slotIndex) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeInt(slotIndex);

        ClientPlayNetworking.send(VoidIsComing.USE_SPELL_PACKET, buf);
    }
}