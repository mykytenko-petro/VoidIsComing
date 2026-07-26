package com.voidiscoming;

import com.voidiscoming.entity.VoidCowEntity;
import com.voidiscoming.entity.VoidPigEntity;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VoidIsComing implements ModInitializer {

    public static final String MOD_ID = "voidiscoming";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static final EntityType<VoidPigEntity> VOID_PIG = Registry.register(
            Registries.ENTITY_TYPE,
            id("void_pig"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, VoidPigEntity::new)
                    .dimensions(EntityDimensions.fixed(0.9F, 0.9F))
                    .build()
    );

    public static final EntityType<VoidCowEntity> VOID_COW = Registry.register(
            Registries.ENTITY_TYPE,
            id("void_cow"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, VoidCowEntity::new)
                    .dimensions(EntityDimensions.fixed(0.9F, 1.4F))
                    .build()
    );

    @Override
    public void onInitialize() {
        FabricDefaultAttributeRegistry.register(VOID_PIG, VoidPigEntity.createVoidPigAttributes());
        FabricDefaultAttributeRegistry.register(VOID_COW, VoidCowEntity.createVoidCowAttributes());

        LOGGER.info("Void Pig successfully registered!");
        LOGGER.info("Void Cow successfully registered!");
    }

    public static Identifier id(String path) {
        return new Identifier(MOD_ID, path);
    }
}