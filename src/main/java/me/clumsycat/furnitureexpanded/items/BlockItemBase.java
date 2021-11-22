package me.clumsycat.furnitureexpanded.items;

import me.clumsycat.furnitureexpanded.util.ModSetup;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;

public class BlockItemBase extends BlockItem {
    public BlockItemBase(Block block, int maxStackSize) {
        super(block, new Item.Properties()
                .stacksTo(maxStackSize)
                .tab(ModSetup.TAB)
        );
    }
}
