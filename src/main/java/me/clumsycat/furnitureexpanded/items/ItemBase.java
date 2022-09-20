package me.clumsycat.furnitureexpanded.items;

import me.clumsycat.furnitureexpanded.Expanded;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;

public class ItemBase extends Item {
    public ItemBase(int maxStackSize) {
        super(new FabricItemSettings()
                .group(Expanded.TAB)
                .maxCount(maxStackSize)
        );
    }
}
