package com.voidiscoming.common.item;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.BookScreen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.Language;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class ModBookItem extends Item {
    public ModBookItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);

        if (world.isClient) {
            List<Text> pages = new ArrayList<>();
            int i = 1;

            // Автоматически ищет страницы в JSON пока они есть (page.1, page.2, page.3 ...)
            while (Language.getInstance().hasTranslation("book.voidiscoming.page." + i)) {
                pages.add(Text.translatable("book.voidiscoming.page." + i));
                i++;
            }

            MinecraftClient.getInstance().setScreen(new BookScreen(new BookScreen.Contents() {
                @Override
                public int getPageCount() {
                    return pages.size();
                }

                @Override
                public Text getPage(int index) {
                    return pages.get(index);
                }

                @Override
                public Text getPageUnchecked(int index) {
                    return pages.get(index);
                }
            }));
        }

        return TypedActionResult.success(itemStack, world.isClient());
    }
}