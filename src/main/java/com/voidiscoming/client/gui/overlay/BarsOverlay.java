package com.voidiscoming.client.gui.overlay;

import com.voidiscoming.common.component.ModComponents;
import com.voidiscoming.common.component.mana.ManaComponent;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.player.PlayerEntity;

public class BarsOverlay implements HudRenderCallback {

    @Override
    public void onHudRender(DrawContext context, float tickDelta) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null || client.options.hudHidden) return;

        PlayerEntity player = client.player;

        if (player.isCreative()) return;

        ManaComponent manaComponent = ModComponents.MANA.get(player);

        int scaledWidth = client.getWindow().getScaledWidth();
        int scaledHeight = client.getWindow().getScaledHeight();

        int barWidth = 75;
        int barHeight = 8;
        int y = scaledHeight - 39;

        int healthX = scaledWidth / 2 - 91;
        float health = player.getHealth();
        float maxHealth = player.getMaxHealth();

        context.fill(healthX - 1, y - 1, healthX + barWidth + 1, y + barHeight + 1, 0xFF000000);
        context.fill(healthX, y, healthX + barWidth, y + barHeight, 0x80202020);
        int healthFilledWidth = (int) ((health / maxHealth) * barWidth);
        context.fill(healthX, y, healthX + healthFilledWidth, y + barHeight, 0xFFE04343);

        String healthText = (int) health + " / " + (int) maxHealth;
        int healthTextWidth = client.textRenderer.getWidth(healthText);
        int healthTextX = healthX + (barWidth - healthTextWidth) / 2;
        int healthTextY = y + (barHeight - 8) / 2;
        context.drawText(client.textRenderer, healthText, healthTextX, healthTextY, 0xFFFFFFFF, true);


        int manaX = scaledWidth / 2 + 16;
        double mana = manaComponent.getMana();
        double maxMana = manaComponent.getMaxMana();


        context.fill(manaX - 1, y - 1, manaX + barWidth + 1, y + barHeight + 1, 0xFF000000);

        context.fill(manaX, y, manaX + barWidth, y + barHeight, 0x80202020);

        int manaFilledWidth = (int) ((mana / maxMana) * barWidth);
        context.fill(manaX, y, manaX + manaFilledWidth, y + barHeight, 0xFF3B82F6);

        String manaText = (int) mana + " / " + (int) maxMana;
        int manaTextWidth = client.textRenderer.getWidth(manaText);
        int manaTextX = manaX + (barWidth - manaTextWidth) / 2;
        int manaTextY = y + (barHeight - 8) / 2;
        context.drawText(client.textRenderer, manaText, manaTextX, manaTextY, 0xFFFFFFFF, true);
    }
}