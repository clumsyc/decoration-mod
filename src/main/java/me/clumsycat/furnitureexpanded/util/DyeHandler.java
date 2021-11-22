package me.clumsycat.furnitureexpanded.util;

import com.google.common.collect.Maps;
import net.minecraft.block.Blocks;
import net.minecraft.item.DyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.Util;

import java.util.Map;

public class DyeHandler {
    public static final Map<DyeColor, IItemProvider> CARPET_DYES = Util.make(Maps.newEnumMap(DyeColor.class), (p_203402_0_) -> {
        p_203402_0_.put(DyeColor.WHITE, Blocks.WHITE_CARPET);
        p_203402_0_.put(DyeColor.ORANGE, Blocks.ORANGE_CARPET);
        p_203402_0_.put(DyeColor.MAGENTA, Blocks.MAGENTA_CARPET);
        p_203402_0_.put(DyeColor.LIGHT_BLUE, Blocks.LIGHT_BLUE_CARPET);
        p_203402_0_.put(DyeColor.YELLOW, Blocks.YELLOW_CARPET);
        p_203402_0_.put(DyeColor.LIME, Blocks.LIME_CARPET);
        p_203402_0_.put(DyeColor.PINK, Blocks.PINK_CARPET);
        p_203402_0_.put(DyeColor.GRAY, Blocks.GRAY_CARPET);
        p_203402_0_.put(DyeColor.LIGHT_GRAY, Blocks.LIGHT_GRAY_CARPET);
        p_203402_0_.put(DyeColor.CYAN, Blocks.CYAN_CARPET);
        p_203402_0_.put(DyeColor.PURPLE, Blocks.PURPLE_CARPET);
        p_203402_0_.put(DyeColor.BLUE, Blocks.BLUE_CARPET);
        p_203402_0_.put(DyeColor.BROWN, Blocks.BROWN_CARPET);
        p_203402_0_.put(DyeColor.GREEN, Blocks.GREEN_CARPET);
        p_203402_0_.put(DyeColor.RED, Blocks.RED_CARPET);
        p_203402_0_.put(DyeColor.BLACK, Blocks.BLACK_CARPET);
    });

    public static int carpetResolver(int j, ItemStack stack) {
        for (int i = 0; i < DyeHandler.CARPET_DYES.size(); i++) {
            if (stack.getItem().equals(DyeHandler.CARPET_DYES.get(DyeColor.byId(i)).asItem())) {
                j = i;
                break;
            }
        }
        return j;
    }
}
