package com.voidiscoming.common.network.spell;

import com.voidiscoming.common.component.ModComponents;
import com.voidiscoming.common.mechanic.spell.ModSpells;
import com.voidiscoming.common.mechanic.spell.Spell;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

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
            if (player == null || player.isRemoved()) return;

            ModComponents.SPELLS.maybeGet(player).ifPresent(spellComp -> {
                Identifier[] equipped = spellComp.getEquippedSpells();
                
                // Перевіряємо валідність індексу слота (від 0 до 3) та чи є там скілл
                if (equipped != null && slotIndex >= 0 && slotIndex < equipped.length) {
                    Identifier spellId = equipped[slotIndex];
                    
                    if (spellId != null) {
                        Spell spell = ModSpells.getById(spellId);
                        
                        // Перевіряємо, чи існує скілл і чи він активний (не пасивний)
                        if (spell != null && !spell.isPassive()) {
                            spell.cast(player);
                        }
                    }
                }
            });
        });
    }
}