package com.voidiscoming.common.mechanic.spell;

import java.util.HashMap;
import java.util.Map;

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
}