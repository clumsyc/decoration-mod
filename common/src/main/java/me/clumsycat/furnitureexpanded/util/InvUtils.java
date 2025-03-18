package me.clumsycat.furnitureexpanded.util;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;

public class InvUtils {
    public static boolean hasContainer(ItemStack stack) {
        return stack.has(DataComponents.CONTAINER) || stack.has(DataComponents.CONTAINER_LOOT);
    }

}
