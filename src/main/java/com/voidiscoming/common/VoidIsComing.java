package com.voidiscoming.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.voidiscoming.common.component.ManaComponent;
import com.voidiscoming.common.component.ModComponents;
import com.voidiscoming.common.component.SkillComponent;
import com.voidiscoming.common.entity.ModEntities;
import com.voidiscoming.common.mechanic.ModMechanics;
import com.voidiscoming.common.skill.ModSkills;
import com.voidiscoming.common.skill.Skill;
import com.voidiscoming.server.command.ModCommands;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;

public class VoidIsComing implements ModInitializer {
    public static final String MOD_ID = "voidiscoming";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static final Identifier USE_SKILL_PACKET = id("use_skill");

    @Override
    public void onInitialize() {
        LOGGER.info("Initializing Void Is Coming...");

        ModMechanics.registerMechanics();
        ModCommands.registerCommands();
        ModEntities.registerModEntities();

       
        AttackEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
            if (!world.isClient() && player instanceof PlayerEntity) {
                player.heal(1.0F);
            }
            return ActionResult.PASS;
        });

    
        ServerPlayNetworking.registerGlobalReceiver(USE_SKILL_PACKET, (server, player, handler, buf, responseSender) -> {
            int slotIndex = buf.readInt();

            server.execute(() -> {
                SkillComponent skillComponent = ModComponents.SKILLS.get(player);
                String[] equipped = skillComponent.getEquippedSkills();

                String skillId = equipped[slotIndex];
                if (slotIndex == 0 && skillId == null) skillId = "vampirism";
                if (slotIndex == 1 && skillId == null) skillId = "heal";

                if (skillId != null) {
                    Skill skill = ModSkills.getById(skillId);

                    if (skill != null && !skill.isPassive()) {
                        ManaComponent manaComponent = ModComponents.MANA.get(player);

                        if (manaComponent.getMana() >= skill.getCost()) {
                            manaComponent.removeMana(skill.getCost());

                            // Накладаємо ефект Регенерації II 
                            player.addStatusEffect(new StatusEffectInstance(
                                StatusEffects.REGENERATION, 
                                60, 
                                1
                            ));
                        }
                    }
                }
            });
        });
    }

    public static Identifier id(String path) {
        return new Identifier(MOD_ID, path);
    }
}