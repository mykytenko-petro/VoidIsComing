package com.voidiscoming.client.network.skill;

import com.voidiscoming.common.network.ModNetworking;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.network.PacketByteBuf;

public class SkillResetSender {
    public static void send() {
        PacketByteBuf buf = PacketByteBufs.create();

        ClientPlayNetworking.send(ModNetworking.RESET_SKILLS_PACKET, buf);
    }
}
