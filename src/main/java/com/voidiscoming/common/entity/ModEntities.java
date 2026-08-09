package com.voidiscoming.common.entity;

import com.voidiscoming.common.VoidIsComing;
import com.voidiscoming.common.entity.stonegolem.StoneGolemEntity;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ModEntities {

    public static final EntityType<VoidPigEntity> VOID_PIG = registerMob(
            "void_pig",
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, VoidPigEntity::new)
                    .dimensions(EntityDimensions.fixed(0.9F, 0.9F))
    );

    public static final EntityType<VoidCowEntity> VOID_COW = registerMob(
            "void_cow",
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, VoidCowEntity::new)
                    .dimensions(EntityDimensions.fixed(0.9F, 0.9F))
    );

    public static final EntityType<StoneGolemEntity> STONE_GOLEM = registerMob(
            "stone_golem",
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, StoneGolemEntity::new)
                    .dimensions(EntityDimensions.fixed(2.5F, 4.5F))
    );

    private static <T extends Entity> EntityType<T> registerMob(String name, FabricEntityTypeBuilder<T> builder) {
        return Registry.register(
                Registries.ENTITY_TYPE,
                VoidIsComing.id(name),
                builder.build()
        );
    }

    public static void registerModEntities() {
        FabricDefaultAttributeRegistry.register(VOID_PIG, VoidPigEntity.createVoidPigAttributes());
        FabricDefaultAttributeRegistry.register(VOID_COW, VoidCowEntity.createVoidCowAttributes());
        FabricDefaultAttributeRegistry.register(STONE_GOLEM, StoneGolemEntity.createStoneGolemAttributes());
    }
}