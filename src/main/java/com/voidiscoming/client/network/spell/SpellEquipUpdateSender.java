package com.voidiscoming.client.network.spell;
import com.voidiscoming.common.network.ModNetworking;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

public class SpellEquipUpdateSender {
     public static void send(Identifier skillId) {
        PacketByteBuf buf = PacketByteBufs.create();
        
        buf.writeIdentifier(skillId);

        ClientPlayNetworking.send(ModNetworking.SPELL_EQUIP_UPDATE_PACKET, buf);
    }
}
