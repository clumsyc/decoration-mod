package me.clumsycat.furnitureexpanded.gui;

import me.clumsycat.furnitureexpanded.items.ItemBasket;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;

public class BasketContainer extends ScreenHandler {
    private final ItemStack basket;
    public final Inventory basketInventory;

    public BasketContainer(int pContainerId, PlayerInventory playerInv, ItemStack basket) {
        super(ScreenHandlerType.GENERIC_9X1, pContainerId);
        this.basket = basket;
        if (!playerInv.player.getWorld().isClient) basketInventory = ItemBasket.getInventory(basket);
        else basketInventory = new SimpleInventory(9);

        for (int col = 0; col < 9; ++col) {
            addSlot(new Slot(basketInventory, col, 17 + col * 18, 26));
        }

        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 9; ++col) {
                int _slot_index = col + row * 9 + 9;
                if (playerInv.getStack(_slot_index).isOf(basket.getItem()))
                    addSlot(new SlotLocked(playerInv, _slot_index, 8 + col * 18, 120 + row * 18));
                else
                    addSlot(new Slot(playerInv, _slot_index, 8 + col * 18, 120 + row * 18));
            }
        }

        for (int i = 0; i < 9; ++i) {
            if (playerInv.getStack(i).isOf(basket.getItem()))
                addSlot(new SlotLocked(playerInv, i, 8 + i * 18, 178));
            else
                addSlot(new Slot(playerInv, i, 8 + i * 18, 178));
        }
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        ItemStack main = player.getMainHandStack();
        ItemStack off = player.getOffHandStack();
        return !main.isEmpty() && main == basket || !off.isEmpty() && off == basket;
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int quickMovedSlotIndex) {
        ItemStack quickMovedStack = ItemStack.EMPTY;
        Slot quickMovedSlot = this.slots.get(quickMovedSlotIndex);

        if (quickMovedSlot != null && quickMovedSlot.hasStack()) {
            ItemStack rawStack = quickMovedSlot.getStack();
            quickMovedStack = rawStack.copy();

            if (quickMovedSlotIndex < 9) {
                if (!this.insertItem(rawStack, 9, 45, true)) {
                    return ItemStack.EMPTY;
                }
                quickMovedSlot.onQuickTransfer(rawStack, quickMovedStack);
            }
            else if (quickMovedSlotIndex < 45) {
                if (!this.insertItem(rawStack, 0, 9, false)) {
                    if (quickMovedSlotIndex < 36) {
                        if (!this.insertItem(rawStack, 36, 45, false)) {
                            return ItemStack.EMPTY;
                        }
                    }
                    else if (!this.insertItem(rawStack, 9, 35, false)) {
                        return ItemStack.EMPTY;
                    }
                }
            }
            else if (!this.insertItem(rawStack, 9, 45, false)) {
                return ItemStack.EMPTY;
            }

            if (rawStack.isEmpty()) {
                quickMovedSlot.setStack(ItemStack.EMPTY);
            } else {
                quickMovedSlot.markDirty();
            }

            if (rawStack.getCount() == quickMovedStack.getCount()) {
                return ItemStack.EMPTY;
            }
            quickMovedSlot.onTakeItem(player, rawStack);
        }
        return quickMovedStack;
    }

}
