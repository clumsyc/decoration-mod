package me.clumsycat.furnitureexpanded.items;

import me.clumsycat.furnitureexpanded.ExpandedMod;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.ChatFormatting;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.level.block.Block;

import java.util.Iterator;
import java.util.List;

public class ItemCardbox extends BlockItem {
    public ItemCardbox(Block block) {
        this(block, new Properties()
                .stacksTo(1)
                .arch$tab(ExpandedMod.EXPANDED_TAB)
                .component(DataComponents.CONTAINER, ItemContainerContents.EMPTY)
                .component(ExpandedMod.COMPONENTS_SEALED.get(), false)
        );
    }

    public ItemCardbox(Block block, Item.Properties properties) {
        super(block, properties);
    }

    @Environment(EnvType.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, TooltipContext tooltipContext, List<Component> tooltip, TooltipFlag flagIn) {
        super.appendHoverText(stack, tooltipContext, tooltip, flagIn);
        if (stack.has(DataComponents.CONTAINER)) {
            int i = 0;
            int j = 0;
            Iterator var7 = stack.getOrDefault(DataComponents.CONTAINER, ItemContainerContents.EMPTY).nonEmptyItems().iterator();

            while(var7.hasNext()) {
                ItemStack itemStack2 = (ItemStack)var7.next();
                ++j;
                if (i <= 6) {
                    ++i;
                    tooltip.add(Component.translatable("container.shulkerBox.itemCount", itemStack2.getHoverName(), itemStack2.getCount()));
                }
            }

            if (i > 6) {
                tooltip.add(Component.translatable("container.shulkerBox.more", j - i).withStyle(ChatFormatting.ITALIC));
            }
        }
    }

    public static float getFullnessPropertyOverride(ItemStack stack, ClientLevel world, LivingEntity livingEntity, int i) {
        if (stack.getItem() == ExpandedMod.CARDBOX_ITEM.get()) {
            boolean s = stack.getOrDefault(ExpandedMod.COMPONENTS_SEALED.get(), false);
            if (s) return 1.0f;
        }
        return 0.0f;
    }

    @Override
    public boolean canFitInsideContainerItems() {
        return false;
    }
}
