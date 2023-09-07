package me.clumsycat.furnitureexpanded.items;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class BlockItemBase extends BlockItem {
    public BlockItemBase(Block block, int maxStackSize) {
        super(block, new Item.Properties()
                .stacksTo(maxStackSize)
        );
    }
}
