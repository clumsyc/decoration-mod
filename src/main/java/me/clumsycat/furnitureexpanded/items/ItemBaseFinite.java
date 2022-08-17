package me.clumsycat.furnitureexpanded.items;

import me.clumsycat.furnitureexpanded.util.ModSetup;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class ItemBaseFinite extends Item {
    public ItemBaseFinite(int maxStackSize, int durability) {
        super(new Properties()
                .tab(ModSetup.TAB)
                .stacksTo(maxStackSize)
                .durability(durability)
        );
    }

    @Override
    public boolean hasCraftingRemainingItem(ItemStack stack) {
        return true;
    }

    @Override
    public ItemStack getCraftingRemainingItem(ItemStack itemStack) {
        ItemStack is = itemStack.copy();
        is.setDamageValue(itemStack.getDamageValue()+1);
        if (is.getDamageValue() >= is.getMaxDamage()) is.setCount(is.getCount()-1);
        return is;
    }
}
