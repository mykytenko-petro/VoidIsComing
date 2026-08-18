package com.voidiscoming.common.network;

import com.voidiscoming.common.VoidIsComing;
import com.voidiscoming.common.network.skill.SkillResetReceiver;
import com.voidiscoming.common.network.skill.UnlockSkillReceiver;
import com.voidiscoming.common.network.spell.CastSpellReceiver;
import com.voidiscoming.common.network.spell.SpellEquipUpdateReceiver;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;

public class ModNetworking {
    public static final Identifier USE_SPELL_PACKET = VoidIsComing.id("use_spell");
    public static final Identifier SPELL_EQUIP_UPDATE_PACKET = VoidIsComing.id("spell_equip_update");
    public static final Identifier UNLOCK_SKILL_PACKET = VoidIsComing.id("unlock_skill_packet");
    public static final Identifier RESET_SKILLS_PACKET = VoidIsComing.id("reset_skill_packet");
    
    public static void registerPackets() {
        ServerPlayNetworking.registerGlobalReceiver(USE_SPELL_PACKET, CastSpellReceiver::receive);
        ServerPlayNetworking.registerGlobalReceiver(SPELL_EQUIP_UPDATE_PACKET, SpellEquipUpdateReceiver::receive);
        ServerPlayNetworking.registerGlobalReceiver(UNLOCK_SKILL_PACKET, UnlockSkillReceiver::receive);
        ServerPlayNetworking.registerGlobalReceiver(RESET_SKILLS_PACKET, SkillResetReceiver::receive);
    }
}