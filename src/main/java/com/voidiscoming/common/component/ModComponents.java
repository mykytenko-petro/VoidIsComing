package com.voidiscoming.common.component;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import dev.onyxstudios.cca.api.v3.entity.RespawnCopyStrategy;
import net.minecraft.util.Identifier;

public class ModComponents implements EntityComponentInitializer {
    
    public static final ComponentKey<ManaComponent> MANA = 
        ComponentRegistry.getOrCreate(new Identifier("voidiscoming", "mana"), ManaComponent.class);

    public static final ComponentKey<SkillComponent> SKILLS = 
        ComponentRegistry.getOrCreate(new Identifier("voidiscoming", "skills"), SkillComponent.class);

    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.registerForPlayers(MANA, PlayerManaComponent::new, RespawnCopyStrategy.ALWAYS_COPY);
        
        registry.registerForPlayers(SKILLS, PlayerSkillComponent::new, RespawnCopyStrategy.ALWAYS_COPY);
    }
}