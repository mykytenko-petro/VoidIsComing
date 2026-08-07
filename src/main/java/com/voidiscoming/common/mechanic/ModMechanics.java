package com.voidiscoming.common.mechanic;

import com.voidiscoming.common.mechanic.spell.impl.VampirismSpell;
import com.voidiscoming.common.VoidIsComing;
import com.voidiscoming.common.component.ModComponents;
import com.voidiscoming.common.mechanic.level.ArmorLevelRestriction;
import com.voidiscoming.common.mechanic.spell.ModSpells;
import com.voidiscoming.common.mechanic.spell.Spell;
import com.voidiscoming.common.mechanic.stat.PlayerStatApplier;

import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;

public class ModMechanics {
    public static void registerMechanics() {
        // Join
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            PlayerStatApplier.syncPlayerStats(handler.getPlayer());
        });

        // Respawn
        ServerPlayerEvents.AFTER_RESPAWN.register((oldPlayer, newPlayer, alive) -> {
            PlayerStatApplier.onSpawn(newPlayer);
        });

        // On death (збереження досвіду)
        ServerPlayerEvents.COPY_FROM.register((oldPlayer, newPlayer, alive) -> {
            if (!alive) {
                newPlayer.experienceLevel = oldPlayer.experienceLevel;
                newPlayer.experienceProgress = oldPlayer.experienceProgress;
                newPlayer.totalExperience = oldPlayer.totalExperience;
            }
        });

        // Every tick (перевірка обмежень броні)
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
                ArmorLevelRestriction.enforceArmorRestrictions(player);
            }
        });

        ServerLivingEntityEvents.AFTER_DEATH.register((entity, damageSource) -> {
            if (damageSource.getAttacker() instanceof PlayerEntity player) {
                if (!player.getWorld().isClient()) {
                    ModComponents.SPELLS.maybeGet(player).ifPresent(spellComp -> {
                        for (String spellId : spellComp.getEquippedSpells()) {
                            if ("vampirism".equals(spellId)) {
                                Spell spell = ModSpells.getById(spellId);
                                if (spell instanceof VampirismSpell vampirism) {
                                    vampirism.onKill(player, entity);
                                }
                            }
                        }
                    });
                }
            }
        });

        ServerPlayNetworking.registerGlobalReceiver(VoidIsComing.USE_SPELL_PACKET, (server, player, handler, buf, responseSender) -> {
            int slotIndex = buf.readInt();

            server.execute(() -> {
                ModComponents.SPELLS.maybeGet(player).ifPresent(spellComp -> {
                    String[] equipped = spellComp.getEquippedSpells();
                    
                    if (slotIndex >= 0 && slotIndex < equipped.length) {
                        String spellId = equipped[slotIndex];
                        
                        if (spellId != null && !spellId.isEmpty()) {
                            Spell spell = ModSpells.getById(spellId);
                            
                            if (spell != null && !spell.isPassive()) {
                                // VoidIsComing.LOGGER.info("Використано активний спел: " + spellId + " гравцем " + player.getName().getString());
                                spell.cast(player);
                            }
                        }
                    }
                });
            });
        });
    }
}