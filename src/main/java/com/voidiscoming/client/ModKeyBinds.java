package com.voidiscoming.client;

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class ModKeyBinds {
    public static final String KEY_CATEGORY = "key.categories.voidiscoming";

    public static KeyBinding skill1Key;
    public static KeyBinding skill2Key;
    public static KeyBinding skill3Key;
    public static KeyBinding skill4Key;

    public static void register() {
        skill1Key = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.voidiscoming.skill_1",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_R, // Слот 1 (R)
            KEY_CATEGORY
        ));

        skill2Key = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.voidiscoming.skill_2",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_G, // Слот 2 (G)
            KEY_CATEGORY
        ));

        skill3Key = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.voidiscoming.skill_3",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_B, // Слот 3 (B)
            KEY_CATEGORY
        ));

        skill4Key = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.voidiscoming.skill_4",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_V, // Слот 4 (V)
            KEY_CATEGORY
        ));
    }
}