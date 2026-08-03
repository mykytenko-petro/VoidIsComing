package com.voidiscoming.client;

import com.voidiscoming.client.description.ModDescriptions;
import com.voidiscoming.client.gui.ModGUI;
import com.voidiscoming.client.gui.SkillHotbarHud; 
import com.voidiscoming.client.renderer.ModRenderers;
import com.voidiscoming.common.VoidIsComing;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback; 
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.network.PacketByteBuf;

@Environment(EnvType.CLIENT)
public class VoidIsComingClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ModRenderers.registerRenderers();
        ModGUI.init();
        ModDescriptions.init();
        
        HudRenderCallback.EVENT.register(new SkillHotbarHud());


        ModKeyBinds.register();

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null) return;

            if (ModKeyBinds.skill1Key.wasPressed()) {
                useSkillInSlot(0); 
            }
            if (ModKeyBinds.skill2Key.wasPressed()) {
                useSkillInSlot(1); 
            }
            if (ModKeyBinds.skill3Key.wasPressed()) {
                useSkillInSlot(2);
            }
            if (ModKeyBinds.skill4Key.wasPressed()) {
                useSkillInSlot(3); 
            }
        });
    }

    private void useSkillInSlot(int slotIndex) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeInt(slotIndex);

        ClientPlayNetworking.send(VoidIsComing.USE_SKILL_PACKET, buf);
    }
}