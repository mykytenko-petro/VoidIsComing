package com.voidiscoming.common.component;

import com.voidiscoming.common.component.mana.ManaComponent;
import com.voidiscoming.common.component.mana.PlayerManaComponent;
import com.voidiscoming.common.component.skill.PlayerSkillComponent;
import com.voidiscoming.common.component.skill.SkillComponent;
import com.voidiscoming.common.component.spell.PlayerSpellComponent;
import com.voidiscoming.common.component.spell.SpellComponent;
import com.voidiscoming.common.VoidIsComing;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import dev.onyxstudios.cca.api.v3.entity.RespawnCopyStrategy;

public class ModComponents implements EntityComponentInitializer {
    
    public static final ComponentKey<ManaComponent> MANA = 
        ComponentRegistry.getOrCreate(VoidIsComing.id("mana"), ManaComponent.class);

    public static final ComponentKey<SpellComponent> SPELLS = 
        ComponentRegistry.getOrCreate(VoidIsComing.id("spells"), SpellComponent.class);

    public static final ComponentKey<SkillComponent> SKILLS =
        ComponentRegistry.getOrCreate(VoidIsComing.id("skills"), SkillComponent.class);

    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.registerForPlayers(MANA, PlayerManaComponent::new, RespawnCopyStrategy.ALWAYS_COPY);
        registry.registerForPlayers(SKILLS, PlayerSkillComponent::new, RespawnCopyStrategy.ALWAYS_COPY);
        registry.registerForPlayers(SPELLS, PlayerSpellComponent::new, RespawnCopyStrategy.ALWAYS_COPY);
    }
}