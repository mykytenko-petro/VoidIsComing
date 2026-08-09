package com.voidiscoming.common.network;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

public class ModMessages {
    public static void registerC2SPackets() {
        
        ServerPlayNetworking.registerGlobalReceiver(CastSpellPacket.ID, CastSpellPacket::receive);
    }
}