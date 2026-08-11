package com.voidiscoming.common.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;

public class VoidSheepEntity extends SheepEntity {

    // 2 минуты = 2400 тиков
    private static final int CLONE_COOLDOWN = 2400;

    private int cloneCooldown = 0;

    public VoidSheepEntity(
            EntityType<? extends SheepEntity> entityType,
            World world
    ) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createVoidSheepAttributes() {
        return SheepEntity.createSheepAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 27.0D)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 4.5D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25D);
    }

    @Override
    protected void initGoals() {
        super.initGoals();

        this.goalSelector.add(
                1,
                new MeleeAttackGoal(this, 1.0D, false)
        );

        this.targetSelector.add(
                1,
                new ActiveTargetGoal<>(
                        this,
                        PlayerEntity.class,
                        true
                )
        );
    }

    @Override
    public void tick() {
        super.tick();

        if (this.cloneCooldown > 0) {
            this.cloneCooldown--;
        }

        // Способность работает только на сервере
        if (!this.getWorld().isClient) {

            // Нужна шерсть
            if (!this.isSheared()) {

                // Кулдаун закончился
                if (this.cloneCooldown <= 0) {

                    // Овца должна иметь цель
                    if (this.getTarget() != null) {
                        createClone();
                    }
                }
            }
        }
    }

    private void createClone() {

        if (!(this.getWorld() instanceof ServerWorld serverWorld)) {
            return;
        }

        /*
         * Создаём полноценную Void Sheep.
         *
         * Никакого кастинга:
         * VOID_SHEEP уже имеет правильный тип.
         */
        VoidSheepEntity clone =
                new VoidSheepEntity(
                        ModEntities.VOID_SHEEP,
                        serverWorld
                );

        /*
         * Спавним рядом с оригиналом.
         */
        clone.refreshPositionAndAngles(
                this.getX() + 1.5D,
                this.getY(),
                this.getZ() + 1.5D,
                this.getYaw(),
                this.getPitch()
        );

        /*
         * Клон полностью здоров.
         */
        clone.setHealth(clone.getMaxHealth());

        /*
         * Клон появляется БЕЗ ШЕРСТИ.
         */
        clone.setSheared(true);

        /*
         * У клона свой независимый кулдаун.
         */
        clone.cloneCooldown = CLONE_COOLDOWN;

        /*
         * Добавляем клона в мир.
         */
        serverWorld.spawnEntity(clone);

        /*
         * Оригинал теряет шерсть.
         */
        this.setSheared(true);

        /*
         * Запускаем кулдаун оригинала.
         */
        this.cloneCooldown = CLONE_COOLDOWN;
    }
}