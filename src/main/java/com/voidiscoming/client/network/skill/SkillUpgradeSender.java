package com.voidiscoming.client.network.skill;

import com.voidiscoming.common.network.ModNetworking;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

public class SkillUpgradeSender {
    public static void send(Identifier skillId) {
        PacketByteBuf buf = PacketByteBufs.create();
        
        buf.writeIdentifier(skillId);

        ClientPlayNetworking.send(ModNetworking.UNLOCK_SKILL_PACKET, buf);
    }
}
