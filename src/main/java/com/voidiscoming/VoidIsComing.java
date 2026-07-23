package com.voidiscoming;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.voidiscoming.entyty.VoidPigEntity;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

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

    @Override
    public void onInitialize() {
        FabricDefaultAttributeRegistry.register(VOID_PIG, VoidPigEntity.createVoidPigAttributes());
        LOGGER.info("Void Pig successfully registered!");
    }

    public static Identifier id(String path) {
        return new Identifier(MOD_ID, path);
    }
}