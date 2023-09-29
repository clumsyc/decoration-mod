package me.clumsycat.furnitureexpanded.items;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;

public class BlockItemBase extends BlockItem {
    public BlockItemBase(Block block, int maxStackSize) {
        super(block, new FabricItemSettings()
                .maxCount(maxStackSize)
        );
    }
}
