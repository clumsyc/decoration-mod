package me.clumsycat.furnitureexpanded.items;

import me.clumsycat.furnitureexpanded.ExpandedMod;
import net.minecraft.world.item.Item;

public class ItemBaseFinite extends Item {
    public ItemBaseFinite(int maxStackSize, int durability) {
        super(new Properties()
                .stacksTo(maxStackSize)
                .durability(durability)
                .arch$tab(ExpandedMod.EXPANDED_TAB)
        );
    }
}
