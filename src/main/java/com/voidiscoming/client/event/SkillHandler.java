package com.voidiscoming.client.event;

import net.minecraft.client.MinecraftClient;

import com.voidiscoming.client.gui.screen.SkillTreeScreen;
import com.voidiscoming.client.keybind.ModKeyBindings;

public class SkillHandler {
    public static void handle(MinecraftClient client) {
        if (ModKeyBindings.skillKey.wasPressed()) {
            client.setScreen(new SkillTreeScreen());
        }
    }
}
