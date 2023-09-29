package me.clumsycat.furnitureexpanded.gui;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;

public class CustomItemInventory extends SimpleInventory {
    private static final String TAG_ITEMS = "Items";
    private final ItemStack stack;

    public CustomItemInventory(ItemStack stack, int expectedSize) {
        super(expectedSize);
        this.stack = stack;
        NbtList lst = stack.hasNbt() && stack.getOrCreateNbt().contains(TAG_ITEMS) ? stack.getOrCreateNbt().getList(TAG_ITEMS, NbtElement.COMPOUND_TYPE) : new NbtList();
        int i = 0;
        for (; i < expectedSize && i < lst.size(); i++) {
            setStack(i, ItemStack.fromNbt(lst.getCompound(i)));
        }
    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        return !stack.isEmpty();
    }

    @Override
    public void markDirty() {
        super.markDirty();
        NbtList list = new NbtList();
        for (int i = 0; i < size(); i++) {
            list.add(getStack(i).writeNbt(new NbtCompound()));
        }
        stack.getOrCreateNbt().put(TAG_ITEMS, list);
    }
}
