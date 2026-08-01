package com.voidiscoming.common.mechanic.level;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ArmorMaterials;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;

public class ArmorLevelRestriction {

    private static final EquipmentSlot[] ARMOR_SLOTS = {
        EquipmentSlot.HEAD,
        EquipmentSlot.CHEST,
        EquipmentSlot.LEGS,
        EquipmentSlot.FEET
    };

    public static void enforceArmorRestrictions(ServerPlayerEntity player) {
        int level = player.experienceLevel;

        for (EquipmentSlot slot : ARMOR_SLOTS) {
            ItemStack stack = player.getEquippedStack(slot);

            if (stack.getItem() instanceof ArmorItem armor) {
                int req = getRequiredLevel(armor.getMaterial());

                if (level < req) {
                    player.equipStack(slot, ItemStack.EMPTY);

                    if (!player.getInventory().insertStack(stack)) {
                        player.dropItem(stack, false);
                    }
                }
            }
        }
    }

    public static int getRequiredLevel(ArmorMaterial material) {
        if (material == ArmorMaterials.LEATHER) return 10;
        if (material == ArmorMaterials.IRON) return 20;
        if (material == ArmorMaterials.DIAMOND) return 30;
        if (material == ArmorMaterials.NETHERITE) return 40;
        return 0;
    }
}
