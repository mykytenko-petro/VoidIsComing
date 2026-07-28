package com.voidiscoming.common.entity;


import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class VoidPigEntity extends PigEntity {
    public VoidPigEntity(EntityType<? extends PigEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createVoidPigAttributes() {
        return PigEntity.createPigAttributes()
            .add(EntityAttributes.GENERIC_MAX_HEALTH, 20.0D)
            .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 4.0D)
            .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.3D);
    }

    @Override
    protected void initGoals() {
        super.initGoals();
        
        this.goalSelector.add(1, new MeleeAttackGoal(this, 1.2D, false));
        this.targetSelector.add(1, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
    }
}