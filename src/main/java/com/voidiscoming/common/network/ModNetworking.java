package com.voidiscoming.common.network;

import com.voidiscoming.common.VoidIsComing;
import com.voidiscoming.common.network.spell.CastSpellReceiver;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;

public class ModNetworking {
    public static final Identifier USE_SPELL_PACKET = VoidIsComing.id("use_spell");

    public static void registerPackets() {
        ServerPlayNetworking.registerGlobalReceiver(USE_SPELL_PACKET, CastSpellReceiver::receive);
    }
}