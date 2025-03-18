package me.clumsycat.furnitureexpanded.gui;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;
import org.jetbrains.annotations.NotNull;

public class CustomItemInventory extends SimpleContainer {
    private final ItemStack stack;

    public CustomItemInventory(ItemStack stack, int expectedSize) {
        super(expectedSize);
        this.stack = stack;
        ItemContainerContents icc = stack.getOrDefault(DataComponents.CONTAINER, ItemContainerContents.EMPTY);
        icc.copyInto(this.getItems());
    }

    @Override
    public boolean stillValid(@NotNull Player player) {
        return player.getMainHandItem() == this.stack || player.getOffhandItem() == this.stack;
    }

    @Override
    public void setChanged() {
        super.setChanged();
        this.stack.set(DataComponents.CONTAINER, ItemContainerContents.fromItems(this.getItems()));
    }

}
