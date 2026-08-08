package com.voidiscoming.common.network.spell;

import com.voidiscoming.common.component.ModComponents;
import com.voidiscoming.common.mechanic.spell.ModSpells;
import com.voidiscoming.common.mechanic.spell.Spell;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;

public class CastSpellReceiver {

    public static void receive(
        MinecraftServer server, 
        ServerPlayerEntity player, 
        ServerPlayNetworkHandler handler,
        PacketByteBuf buf, 
        PacketSender responseSender
    ) {
        int slotIndex = buf.readInt();

        server.execute(() -> {
            ModComponents.SPELLS.maybeGet(player).ifPresent(spellComp -> {
                String[] equipped = spellComp.getEquippedSpells();
                
                if (slotIndex >= 0 && slotIndex < equipped.length) {
                    String spellId = equipped[slotIndex];
                    
                    if (spellId != null && !spellId.isEmpty()) {
                        Spell spell = ModSpells.getById(spellId);
                        
                        if (spell != null && !spell.isPassive()) {
                            spell.cast(player);
                        }
                    }
                }
            });
        });
    }
    
}
