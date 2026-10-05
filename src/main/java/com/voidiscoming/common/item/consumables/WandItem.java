package com.voidiscoming.common.item.consumables;

import com.voidiscoming.common.component.ModComponents;
import com.voidiscoming.common.component.mana.ManaComponent;
import com.voidiscoming.common.entity.projectile.WandProjectileEntity;
import com.voidiscoming.common.mechanic.skill.ModSkills; 

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
    private final float manaCost;

    public WandItem(Settings settings, float projectileDamage, float manaCost) {
        super(settings);
        this.projectileDamage = projectileDamage;
        this.manaCost = manaCost;
    }

    public WandItem(Settings settings, float projectileDamage) {
        this(settings, projectileDamage, 1.0F);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);

        boolean isCreative = user.isCreative();

        ManaComponent manaComponent = ModComponents.MANA.get(user);

        if (!isCreative && manaComponent.getMana() < manaCost) {
            return TypedActionResult.fail(itemStack);
        }

        if (!world.isClient) {
            if (!isCreative) {
                manaComponent.removeMana(manaCost);
            }

float finalDamage = this.projectileDamage;
            var skills = ModComponents.SKILLS.get(user);

            if (skills.hasUnlocked(ModSkills.WAND_POWER)) {
                finalDamage = (float) Math.ceil(finalDamage * 1.10f);
            }

            WandProjectileEntity projectile = new WandProjectileEntity(world, user, finalDamage);
            projectile.setVelocity(user, user.getPitch(), user.getYaw(), 0.0F, 1.5F, 1.0F);
            world.spawnEntity(projectile);
        }

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

        user.getItemCooldownManager().set(this, 8);

        return TypedActionResult.success(itemStack, world.isClient());
    }
}