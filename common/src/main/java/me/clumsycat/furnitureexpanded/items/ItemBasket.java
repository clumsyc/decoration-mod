package me.clumsycat.furnitureexpanded.items;

import me.clumsycat.furnitureexpanded.ExpandedMod;
import me.clumsycat.furnitureexpanded.gui.BasketContainer;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.*;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.level.Level;

import java.util.Iterator;

public class ItemBasket extends Item {
    public ItemBasket() {
        super(new Item.Properties()
                .stacksTo(1)
                .arch$tab(ExpandedMod.EXPANDED_TAB)
                .component(DataComponents.CONTAINER, ItemContainerContents.EMPTY)
        );
    }

    public static float getFullnessPropertyOverride(ItemStack stack, ClientLevel world, LivingEntity livingEntity, int i) {
        Iterator stacks = stack.getOrDefault(DataComponents.CONTAINER, ItemContainerContents.EMPTY).nonEmptyItems().iterator();
        int j = 0;
        while(stacks.hasNext()) {
            if (!((ItemStack)stacks.next()).isEmpty())
                ++j;
        }
        if (j > 7) return 1F;
        else if (j > 4) return 0.5F;
        else if (j > 0) return 0.25F;
        else return 0F;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        if (!world.isClientSide) {
            ItemStack stack = player.getItemInHand(hand);
            player.openMenu(
                    new MenuProvider() {
                        @Override
                        public Component getDisplayName() {
                            return stack.getHoverName();
                        }

                        @Override
                        public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
                            return new BasketContainer(pContainerId, pPlayerInventory, stack);
                        }
                    }
            );
            return InteractionResultHolder.sidedSuccess(player.getItemInHand(hand), world.isClientSide());
        }
        return InteractionResultHolder.success(player.getItemInHand(hand));
    }

    @Override
    public boolean canFitInsideContainerItems() {
        return false;
    }
}
