package me.clumsycat.furnitureexpanded.gui;

import me.clumsycat.furnitureexpanded.items.ItemBasket;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class BasketContainer extends AbstractContainerMenu {
    private final ItemStack basket;
    public final Container basketInventory;

    public BasketContainer(int pContainerId, Inventory playerInv, ItemStack basket) {
        super(MenuType.GENERIC_9x1, pContainerId);
        this.basket = basket;
        if (!playerInv.player.level().isClientSide) basketInventory = ItemBasket.getInventory(basket);
        else basketInventory = new SimpleContainer(9);

        for (int col = 0; col < 9; ++col) {
            addSlot(new Slot(basketInventory, col, 17 + col * 18, 26));
        }

        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 9; ++col) {
                int _slot_index = col + row * 9 + 9;
                if (playerInv.getItem(_slot_index).is(basket.getItem()))
                    addSlot(new SlotLocked(playerInv, _slot_index, 8 + col * 18, 120 + row * 18));
                else
                    addSlot(new Slot(playerInv, _slot_index, 8 + col * 18, 120 + row * 18));
            }
        }

        for (int i = 0; i < 9; ++i) {
            if (playerInv.getItem(i).is(basket.getItem()))
                addSlot(new SlotLocked(playerInv, i, 8 + i * 18, 178));
            else
                addSlot(new Slot(playerInv, i, 8 + i * 18, 178));
        }
    }

    @Override
    public boolean stillValid(@NotNull Player player) {
        ItemStack main = player.getMainHandItem();
        ItemStack off = player.getOffhandItem();
        return !main.isEmpty() && main == basket || !off.isEmpty() && off == basket;
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
