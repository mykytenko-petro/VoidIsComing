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
        this.setNoGravity(true); // Летит идеально прямо без падения вниз
    }

    @Override
    public void tick() {
        super.tick();

        // Спавним синие частицы на сервере — теперь они летят точно вместе с пулей и видны всем!
        if (!this.getWorld().isClient()) {
            ServerWorld serverWorld = (ServerWorld) this.getWorld();
            serverWorld.spawnParticles(
                ParticleTypes.SOUL, // Яркие синие частицы души
                this.getX(), this.getY(), this.getZ(),
                4,                    // Количество частиц за один тик
                0.1, 0.1, 0.1,        // Небольшой разброс вокруг пули
                0.02                  // Скорость разлета частиц
            );
        }

        // Если пуля летит больше 4 секунд и никуда не попала — удаляем
        if (this.age > 80) {
            this.discard();
        }
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        if (!this.getWorld().isClient()) {
            // Наносим урон мобу/игроку
            entityHitResult.getEntity().damage(this.getDamageSources().arrow(this, this.getOwner()), customDamage);
            this.discard();
        }
    }

    @Override
    protected void onCollision(HitResult hitResult) {
        // Полностью убираем звук и застревание при ударе о блок
        HitResult.Type type = hitResult.getType();
        if (type == HitResult.Type.ENTITY) {
            super.onCollision(hitResult);
        } else if (type == HitResult.Type.BLOCK) {
            if (!this.getWorld().isClient()) {
                this.discard(); // Мгновенно исчезает при касании блока без звука
            }
        }
    }

    @Override
    protected ItemStack asItemStack() {
        return new ItemStack(Items.AIR);
    }
}