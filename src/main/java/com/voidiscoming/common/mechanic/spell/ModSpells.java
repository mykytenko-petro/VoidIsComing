package com.voidiscoming.common.mechanic.spell;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.voidiscoming.common.VoidIsComing;
import com.voidiscoming.common.component.ModComponents;
import com.voidiscoming.common.mechanic.spell.impl.FrostAuraSpell;
import com.voidiscoming.common.mechanic.spell.impl.HealSpell;
import com.voidiscoming.common.mechanic.spell.impl.HunterSenseSpell;
import com.voidiscoming.common.mechanic.spell.impl.InvisibilitySpell;
import com.voidiscoming.common.mechanic.spell.impl.LastStandSpell;
import com.voidiscoming.common.mechanic.spell.impl.ManaBoostSpell;
import com.voidiscoming.common.mechanic.spell.impl.RageSpell;
import com.voidiscoming.common.mechanic.spell.impl.TeleportSpell;
import com.voidiscoming.common.mechanic.spell.impl.VampirismSpell;

import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class ModSpells {
    private static final Map<Identifier, Spell> SPELLS = new HashMap<>();

    public static final Identifier HEAL = VoidIsComing.id("heal_spell");
    public static final Identifier VAMPIRISM = VoidIsComing.id("vampirism_spell");
    public static final Identifier INVISIBILITY = VoidIsComing.id("invisibility_spell");
    public static final Identifier TELEPORT = VoidIsComing.id("teleport_spell");
    public static final Identifier FROST_AURA = VoidIsComing.id("frost_aura_spell");
    public static final Identifier RAGE = VoidIsComing.id("rage_spell");
    public static final Identifier HUNTER_SENSE = VoidIsComing.id("hunter_sense_spell");
    public static final Identifier LAST_STAND = VoidIsComing.id("last_stand_spell");
    public static final Identifier MANA_BOOST = VoidIsComing.id("mana_boost_spell");

    public static void registerSpells(){
        register(HEAL, new HealSpell());
        register(VAMPIRISM, new VampirismSpell());
        register(INVISIBILITY, new InvisibilitySpell());
        register(TELEPORT, new TeleportSpell());
        register(FROST_AURA, new FrostAuraSpell());
        register(RAGE, new RageSpell());
        register(HUNTER_SENSE, new HunterSenseSpell());
        register(LAST_STAND, new LastStandSpell());
        register(MANA_BOOST, new ManaBoostSpell());
    }

    private static void register(Identifier id, Spell spell) {
        SPELLS.put(id, spell);
    }

    public static Spell get(Identifier id) {
        return SPELLS.get(id);
    }

    public static List<Identifier> getAllSpellIds() {
        return new ArrayList<>(SPELLS.keySet());
    }

    public static void registerEvents() {
        ServerLivingEntityEvents.AFTER_DEATH.register((entity, damageSource) -> {
            if (damageSource.getAttacker() instanceof ServerPlayerEntity player) {
                ModComponents.SPELLS.maybeGet(player).ifPresent(spellComp -> {
                    for (Identifier spellId : spellComp.getEquippedSpells()) {
                        if (spellId != null) {
                            Spell spell = ModSpells.get(spellId);
                            if (spell != null) {
                                spell.onKill(player, entity); 
                            }
                        }
                    }
                });
            }
        });

        ServerTickEvents.START_SERVER_TICK.register(server -> {
            for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
                ModComponents.SPELLS.maybeGet(player).ifPresent(spellComp -> {
                    for (Identifier spellId : spellComp.getEquippedSpells()) {
                        if (spellId != null) {
                            Spell spell = ModSpells.get(spellId);
                            if (spell != null && spell.isPassive()) {
                                spell.onTick(player);
                            }
                        }
                    }
                });
            }
        });
    }
}