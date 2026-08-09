package com.voidiscoming.common.network;

import com.voidiscoming.common.VoidIsComing;
import com.voidiscoming.common.component.ModComponents;
import com.voidiscoming.common.component.spell.SpellComponent;
import com.voidiscoming.common.mechanic.spell.ModSpells;
import com.voidiscoming.common.mechanic.spell.Spell;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class CastSpellPacket {
    
    public static final Identifier ID = VoidIsComing.id("cast_spell");

    
    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler,
                               PacketByteBuf buf, PacketSender responseSender) {
        
        
        int slot = buf.readInt();

        
        server.execute(() -> {
            SpellComponent spellComp = ModComponents.SPELLS.get(player);
            String[] equipped = spellComp.getEquippedSpells();

            if (slot >= 0 && slot < equipped.length) {
                String spellId = equipped[slot];
                if (spellId != null && !spellId.isEmpty()) {
                    Spell spell = ModSpells.getById(spellId);
                    if (spell != null) {
                        
                        spell.cast(player);
                    }
                }
            }
        });
    }
}