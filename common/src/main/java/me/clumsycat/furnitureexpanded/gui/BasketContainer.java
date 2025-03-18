package me.clumsycat.furnitureexpanded.gui;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class BasketContainer extends AbstractContainerMenu {
    private ItemStack basketItem;
    public final Container basketInventory;

    public BasketContainer(int pContainerId, Inventory playerInv, ItemStack basket) {
        super(MenuType.GENERIC_9x1, pContainerId);
        this.basketItem = basket;
        basketInventory = new CustomItemInventory(basket, 9);

        for (int col = 0; col < 9; ++col) {
            addSlot(new SlotLocked(basketInventory, col, 17 + col * 18, 26));
        }

        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 9; ++col) {
                addSlot(new Slot(playerInv, col + row * 9 + 9, 8 + col * 18, 120 + row * 18));
            }
        }

        for (int i = 0; i < 9; ++i) {
            addSlot(new Slot(playerInv, i, 8 + i * 18, 178));
        }
    }

    @Override
    public boolean stillValid(@NotNull Player player) {
        return player.getMainHandItem() == this.basketItem || player.getOffhandItem() == this.basketItem;
    }

    @Override
    public ItemStack quickMoveStack(Player player, int quickMovedSlotIndex) {
        ItemStack quickMovedStack = ItemStack.EMPTY;
        Slot quickMovedSlot = this.slots.get(quickMovedSlotIndex);

        if (quickMovedSlot != null && quickMovedSlot.hasItem()) {
            ItemStack rawStack = quickMovedSlot.getItem();
            quickMovedStack = rawStack.copy();

            if (quickMovedSlotIndex < 9) {
                if (!this.moveItemStackTo(rawStack, 9, 45, true)) {
                    return ItemStack.EMPTY;
                }
                quickMovedSlot.onQuickCraft(rawStack, quickMovedStack);
            }
            else if (quickMovedSlotIndex < 45) {
                if (!this.moveItemStackTo(rawStack, 0, 9, false)) {
                    if (quickMovedSlotIndex < 36) {
                        if (!this.moveItemStackTo(rawStack, 36, 45, false)) {
                            return ItemStack.EMPTY;
                        }
                    }
                    else if (!this.moveItemStackTo(rawStack, 9, 35, false)) {
                        return ItemStack.EMPTY;
                    }
                }
            }
            else if (!this.moveItemStackTo(rawStack, 9, 45, false)) {
                return ItemStack.EMPTY;
            }

            if (rawStack.isEmpty()) {
                quickMovedSlot.set(ItemStack.EMPTY);
            } else {
                quickMovedSlot.setChanged();
            }

            if (rawStack.getCount() == quickMovedStack.getCount()) {
                return ItemStack.EMPTY;
            }
            quickMovedSlot.onTake(player, rawStack);
        }
        return quickMovedStack;
    }
}
