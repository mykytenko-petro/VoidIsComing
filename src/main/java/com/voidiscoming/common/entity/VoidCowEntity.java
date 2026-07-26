package com.voidiscoming.common.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class VoidCowEntity extends CowEntity {
    public VoidCowEntity(EntityType<? extends CowEntity> entityType, World world) {
        super(entityType, world);
    }

    
    public static DefaultAttributeContainer.Builder createVoidCowAttributes() {
        return CowEntity.createCowAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 30.0D)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 5.0D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.4D);
    }

    @Override
    protected void initGoals() {
        super.initGoals();
        
        this.goalSelector.add(1, new MeleeAttackGoal(this, 1.2D, false));
        this.targetSelector.add(1, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
    }
}