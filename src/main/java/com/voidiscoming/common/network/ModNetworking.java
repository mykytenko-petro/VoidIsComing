package com.voidiscoming.common.network;

import com.voidiscoming.common.VoidIsComing;
import com.voidiscoming.common.network.skill.UnlockSkillReceiver;
import com.voidiscoming.common.network.spell.CastSpellReceiver;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;

public class ModNetworking {
    public static final Identifier USE_SPELL_PACKET = VoidIsComing.id("use_spell");
    
    // Додаємо ідентифікатор мережевого каналу для розблокування скіллів
    public static final Identifier UNLOCK_SKILL_PACKET = VoidIsComing.id("unlock_skill_packet");

    public static void registerPackets() {
        // Реєструємо існуючий пакет касту спеллів
        ServerPlayNetworking.registerGlobalReceiver(USE_SPELL_PACKET, CastSpellReceiver::receive);

        // Реєструємо новий пакет розблокування скіллів у дереві навичок
        ServerPlayNetworking.registerGlobalReceiver(UNLOCK_SKILL_PACKET, UnlockSkillReceiver::receive);
    }
}