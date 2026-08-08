package com.voidiscoming.common.mechanic.spell;

import java.util.HashMap;
import java.util.Map;

import com.voidiscoming.common.VoidIsComing;
import com.voidiscoming.common.component.ModComponents;
import com.voidiscoming.common.mechanic.spell.impl.HealSpell;
import com.voidiscoming.common.mechanic.spell.impl.VampirismSpell;

import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.server.network.ServerPlayerEntity;

public class ModSpells {
    private static final Map<String, Spell> SPELLS = new HashMap<>();

    public static final HealSpell HEAL = new HealSpell();
    public static final VampirismSpell VAMPIRISM = new VampirismSpell();

    public static void registerSpells() {
        register(HEAL);
        register(VAMPIRISM);
    }

    private static void register(Spell spell) {
        SPELLS.put(spell.getId(), spell);
    }

    public static Spell getById(String id) {
        return SPELLS.get(id);
    }

    public static void registerEvents() {
        ServerLivingEntityEvents.AFTER_DEATH.register((entity, damageSource) -> {
            if (damageSource.getAttacker() instanceof ServerPlayerEntity player) {
                ModComponents.SPELLS.maybeGet(player).ifPresent(spellComp -> {
                    for (String spellId : spellComp.getEquippedSpells()) {
                        Spell spell = ModSpells.getById(spellId);
                        if (spell != null) {
                            spell.onKill(player, entity); 
                        }
                    }
                });
            }
        });
    }
}