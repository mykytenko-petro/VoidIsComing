package com.voidiscoming.common.mechanic.level;

import com.voidiscoming.common.mechanic.level.Armor;

import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ArmorItem;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class ArrmorDescriptionReqirements {
    public static void register() {
        ItemTooltipCallback.EVENT.register((stack, context, lines) -> {
            if (stack.getItem() instanceof ArmorItem) {
                ArmorItem armor = (ArmorItem) stack.getItem();
                int req = Armor.getRequiredLevel(armor.getMaterial());

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
