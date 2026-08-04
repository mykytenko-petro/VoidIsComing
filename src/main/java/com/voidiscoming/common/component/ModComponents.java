package com.voidiscoming.common.component;

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

    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.registerForPlayers(MANA, PlayerManaComponent::new, RespawnCopyStrategy.ALWAYS_COPY);

        registry.registerForPlayers(SPELLS, PlayerSpellComponent::new, RespawnCopyStrategy.ALWAYS_COPY);
    }
}