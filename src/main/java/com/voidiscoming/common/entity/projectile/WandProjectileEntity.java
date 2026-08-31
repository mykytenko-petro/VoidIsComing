package com.voidiscoming.common.entity.projectile;

import com.voidiscoming.common.entity.ModEntities;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;

public class WandProjectileEntity extends PersistentProjectileEntity {
    private float customDamage = 6.0F;

    public WandProjectileEntity(EntityType<? extends PersistentProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    public WandProjectileEntity(World world, LivingEntity owner, float damage) {
        super(ModEntities.WAND_PROJECTILE, owner, world);
        this.customDamage = damage;
        this.setDamage(damage);
        this.setNoGravity(true); 
    }

    @Override
    public void tick() {
        super.tick();

  
        if (!this.getWorld().isClient()) {
            ServerWorld serverWorld = (ServerWorld) this.getWorld();
            serverWorld.spawnParticles(
                ParticleTypes.SOUL, 
                this.getX(), this.getY(), this.getZ(),
                4,                 
                0.1, 0.1, 0.1,       
                0.02               
            );
        }

        if (this.age > 80) {
            this.discard();
        }
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        if (!this.getWorld().isClient()) {
            entityHitResult.getEntity().damage(this.getDamageSources().arrow(this, this.getOwner()), this.customDamage);
            this.discard();
        }
    }

    @Override
    protected void onCollision(HitResult hitResult) {
        HitResult.Type type = hitResult.getType();
        if (type == HitResult.Type.ENTITY) {
            super.onCollision(hitResult);
        } else if (type == HitResult.Type.BLOCK) {
            if (!this.getWorld().isClient()) {
                this.discard(); 
            }
        }
    }

    @Override
    protected ItemStack asItemStack() {
        return new ItemStack(Items.AIR);
    }
}