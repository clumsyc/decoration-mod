package me.clumsycat.furnitureexpanded.items;

import me.clumsycat.furnitureexpanded.util.ModSetup;
import net.minecraft.item.Item;

public class ItemBase extends Item {
    public ItemBase(int maxStackSize) {
        super(new Item.Properties()
                .tab(ModSetup.TAB)
                .stacksTo(maxStackSize)
        );
    }
}
