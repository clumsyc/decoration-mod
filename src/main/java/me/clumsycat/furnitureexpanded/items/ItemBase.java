package me.clumsycat.furnitureexpanded.items;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;

public class ItemBase extends Item {
    public ItemBase(int maxStackSize) {
        super(new FabricItemSettings()
                .maxCount(maxStackSize)
        );
    }
}
