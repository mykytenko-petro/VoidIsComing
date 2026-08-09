package com.voidiscoming.common.entity.stonegolem;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.thrown.SnowballEntity;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;

public class StoneProjectileEntity extends SnowballEntity {

    public StoneProjectileEntity(World world, LivingEntity owner) {
        super(world, owner);
    }

    public StoneProjectileEntity(EntityType<? extends SnowballEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        Entity target = entityHitResult.getEntity();

        if (!this.getWorld().isClient()) {
            LivingEntity owner = this.getOwner() instanceof LivingEntity living ? living : null;
            target.damage(this.getDamageSources().mobAttack(owner), 20.0F);
        }
    }
}