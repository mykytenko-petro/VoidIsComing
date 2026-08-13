package com.voidiscoming.common.mechanic.spell;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.voidiscoming.common.component.ModComponents;
import com.voidiscoming.common.mechanic.spell.impl.HealSpell;
import com.voidiscoming.common.mechanic.spell.impl.InvisibilitySpell;
import com.voidiscoming.common.mechanic.spell.impl.TeleportSpell;
import com.voidiscoming.common.mechanic.spell.impl.VampirismSpell;

import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class ModSpells {
    private static final Map<Identifier, Spell> SPELLS = new HashMap<>();

    public static final HealSpell HEAL = new HealSpell();
    public static final VampirismSpell VAMPIRISM = new VampirismSpell();
    public static final InvisibilitySpell INVISIBILITY = new InvisibilitySpell(); 
    public static final TeleportSpell TELEPORT = new TeleportSpell(); 

    public static void registerSpells() {
        register(HEAL);
        register(VAMPIRISM);
        register(INVISIBILITY); 
        register(TELEPORT); 
    }

    private static void register(Spell spell) {
        SPELLS.put(spell.getId(), spell);
    }

    public static Spell getById(Identifier id) {
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
                            Spell spell = ModSpells.getById(spellId);
                            if (spell != null) {
                                spell.onKill(player, entity); 
                            }
                        }
                    }
                });
            }
        });
    }
}