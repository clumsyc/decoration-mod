package me.clumsycat.furnitureexpanded.items;

import me.clumsycat.furnitureexpanded.ExpandedMod;
import me.clumsycat.furnitureexpanded.gui.BasketContainer;
import me.clumsycat.furnitureexpanded.gui.CustomItemInventory;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ItemBasket extends Item implements DyeableLeatherItem {
    public ItemBasket() {
        super(new Item.Properties()
                .stacksTo(1)
                .arch$tab(ExpandedMod.EXPANDED_TAB)
        );
    }

    public static SimpleContainer getInventory(ItemStack stack) {
        return new CustomItemInventory(stack, 9) {
            @Override
            public boolean canPlaceItem(int slot, ItemStack stack) {
                return !stack.is(ExpandedMod.BASKET.get().asItem()); //TODO: Check if has an inventory instead
            }
        };
    }

    public static float getFullnessPropertyOverride(ItemStack stack, ClientLevel world, LivingEntity livingEntity, int i) {
        if (stack.hasTag()) {
            if (stack.getTag().contains("Items")) {
                ListTag lst = stack.getOrCreateTag().getList("Items", Tag.TAG_COMPOUND);
                int v = 0;
                for (int c = 0; c < 9; c++) {
                    if (!ItemStack.of(lst.getCompound(c)).isEmpty()) v++;
                }
                if (v > 7) return 1F;
                else if (v > 4) return 0.5F;
                else if (v > 0) return 0.25F;
                else return 0F;
            }
        }
        return 0F;
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
}
