package com.voidiscoming.client.event;

import com.voidiscoming.client.keybind.ModKeyBindings;
import com.voidiscoming.client.network.spell.CastSpellSender;

public class SpellHandler {
    public static void handle() {
        if (ModKeyBindings.spell1Key.wasPressed()) {
            CastSpellSender.send(0);
        }
        if (ModKeyBindings.spell2Key.wasPressed()) {
            CastSpellSender.send(1);
        }
        if (ModKeyBindings.spell3Key.wasPressed()) {
            CastSpellSender.send(2);
        }
        if (ModKeyBindings.spell4Key.wasPressed()) {
            CastSpellSender.send(3);
        }
    }
}
