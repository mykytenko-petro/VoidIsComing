package com.voidiscoming.client.keybind;

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class ModKeyBindings {
    public static final String KEY_CATEGORY = "key.categories.voidiscoming";

    public static KeyBinding spell1Key;
    public static KeyBinding spell2Key;
    public static KeyBinding spell3Key;
    public static KeyBinding spell4Key;

    public static void registerBindings() {
        spell1Key = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.voidiscoming.spell_1",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_R,
            KEY_CATEGORY
        ));

        spell2Key = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.voidiscoming.spell_2",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_G,
            KEY_CATEGORY
        ));

        spell3Key = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.voidiscoming.spell_3",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_B,
            KEY_CATEGORY
        ));

        spell4Key = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.voidiscoming.spell_4",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_V,
            KEY_CATEGORY
        ));
    }
}