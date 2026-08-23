package com.voidiscoming.common.item.consumables;

import com.voidiscoming.common.component.ModComponents;
import com.voidiscoming.common.component.mana.ManaComponent;
import com.voidiscoming.common.entity.projectile.WandProjectileEntity;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class WandItem extends Item {
    private final float projectileDamage;
    private static final float MANA_COST = 1.0f;

    public WandItem(Settings settings, float projectileDamage) {
        super(settings);
        this.projectileDamage = projectileDamage;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);

        // Проверяем режим креатива
        boolean isCreative = user.isCreative();

        // Получаем ману игрока
        ManaComponent manaComponent = ModComponents.MANA.get(user);

        // Если игрок НЕ в креативе и маны не хватает — отменяем выстрел без сообщений
        if (!isCreative && manaComponent.getMana() < MANA_COST) {
            return TypedActionResult.fail(itemStack);
        }

        // Логика выстрела только на сервере
        if (!world.isClient) {
            // Списываем ману только если игрок НЕ в креативе
            if (!isCreative) {
                manaComponent.removeMana(MANA_COST);
            }

            WandProjectileEntity projectile = new WandProjectileEntity(world, user, projectileDamage);
            projectile.setVelocity(user, user.getPitch(), user.getYaw(), 0.0F, 1.5F, 1.0F);
            world.spawnEntity(projectile);
        }

        // Воспроизведение звука
        world.playSound(
            null,
            user.getX(), user.getY(), user.getZ(),
            SoundEvents.ENTITY_ARROW_SHOOT,
            SoundCategory.PLAYERS,
            0.5F,
            0.4F / (world.getRandom().nextFloat() * 0.4F + 0.8F)
        );

        user.incrementStat(Stats.USED.getOrCreateStat(this));
        itemStack.damage(1, user, p -> p.sendToolBreakStatus(hand));

        return TypedActionResult.success(itemStack, world.isClient());
    }
}