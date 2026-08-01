package com.voidiscoming.client.description;

import com.voidiscoming.common.mechanic.level.ArmorLevelRestriction;

import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ArmorItem;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class ArmorRequirementsDescription {
    public static void init() {
        ItemTooltipCallback.EVENT.register((stack, context, lines) -> {
            if (stack.getItem() instanceof ArmorItem armor) {
                int req = ArmorLevelRestriction.getRequiredLevel(armor.getMaterial());

                if (req > 0) {
                    MinecraftClient client = MinecraftClient.getInstance();
                    int playerLevel = 0;

                    if (client != null && client.player != null) {
                        playerLevel = client.player.experienceLevel;
                    }

                    Formatting color;
                    if (playerLevel >= req) {
                        color = Formatting.GREEN;
                    } else {
                        color = Formatting.RED;
                    }

                    lines.add(Text.literal("Потрібен рівень " + req).formatted(color));
                }
            }
        });
    }
}
