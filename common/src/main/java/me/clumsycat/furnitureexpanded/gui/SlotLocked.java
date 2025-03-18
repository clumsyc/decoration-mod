package me.clumsycat.furnitureexpanded.gui;

import me.clumsycat.furnitureexpanded.util.InvUtils;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class SlotLocked extends Slot {

    public SlotLocked(Container inv, int index, int xPos, int yPos) {
        super(inv, index, xPos, yPos);
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        return !InvUtils.hasContainer(stack);
    }

}
