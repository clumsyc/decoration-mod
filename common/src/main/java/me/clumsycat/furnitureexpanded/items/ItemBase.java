package me.clumsycat.furnitureexpanded.items;

import me.clumsycat.furnitureexpanded.ExpandedMod;
import net.minecraft.world.item.Item;

public class ItemBase extends Item {
    public ItemBase(int maxStackSize) {
        super(new Item.Properties()
                .stacksTo(maxStackSize)
                .arch$tab(ExpandedMod.EXPANDED_TAB)
        );
    }
}
