package me.clumsycat.furnitureexpanded.util;

import com.google.common.collect.Maps;
import me.clumsycat.furnitureexpanded.ExpandedMod;
import me.clumsycat.furnitureexpanded.items.ItemBedsheet;
import me.clumsycat.furnitureexpanded.util.enums.BedsheetPatterns;
import net.minecraft.Util;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.Map;

public class DyeHandler {
    public static final Map<DyeColor, Block> CARPET_DYES = Util.make(Maps.newEnumMap(DyeColor.class), (p_203402_0_) -> {
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


    public static final Map<BedsheetPatterns, Map<DyeColor, ItemBedsheet>> BEDSHEET_DYES = Util.make(Maps.newEnumMap(BedsheetPatterns.class), (p_203402_0_) -> {
        p_203402_0_.put(BedsheetPatterns.BAMBOO, Util.make(Maps.newEnumMap(DyeColor.class), (p_203403_0_) -> {
            p_203403_0_.put(DyeColor.WHITE, ExpandedMod.BEDSHEET_BAMBOO_WHITE.get());
            p_203403_0_.put(DyeColor.ORANGE, ExpandedMod.BEDSHEET_BAMBOO_ORANGE.get());
            p_203403_0_.put(DyeColor.MAGENTA, ExpandedMod.BEDSHEET_BAMBOO_MAGENTA.get());
            p_203403_0_.put(DyeColor.LIGHT_BLUE, ExpandedMod.BEDSHEET_BAMBOO_LIGHT_BLUE.get());
            p_203403_0_.put(DyeColor.YELLOW, ExpandedMod.BEDSHEET_BAMBOO_YELLOW.get());
            p_203403_0_.put(DyeColor.LIME, ExpandedMod.BEDSHEET_BAMBOO_LIME.get());
            p_203403_0_.put(DyeColor.PINK, ExpandedMod.BEDSHEET_BAMBOO_PINK.get());
            p_203403_0_.put(DyeColor.GRAY, ExpandedMod.BEDSHEET_BAMBOO_GRAY.get());
            p_203403_0_.put(DyeColor.LIGHT_GRAY, ExpandedMod.BEDSHEET_BAMBOO_LIGHT_GRAY.get());
            p_203403_0_.put(DyeColor.CYAN, ExpandedMod.BEDSHEET_BAMBOO_CYAN.get());
            p_203403_0_.put(DyeColor.PURPLE, ExpandedMod.BEDSHEET_BAMBOO_PURPLE.get());
            p_203403_0_.put(DyeColor.BLUE, ExpandedMod.BEDSHEET_BAMBOO_BLUE.get());
            p_203403_0_.put(DyeColor.BROWN, ExpandedMod.BEDSHEET_BAMBOO_BROWN.get());
            p_203403_0_.put(DyeColor.GREEN, ExpandedMod.BEDSHEET_BAMBOO_GREEN.get());
            p_203403_0_.put(DyeColor.RED, ExpandedMod.BEDSHEET_BAMBOO_RED.get());
            p_203403_0_.put(DyeColor.BLACK, ExpandedMod.BEDSHEET_BAMBOO_BLACK.get());
        }));

        p_203402_0_.put(BedsheetPatterns.BARK, Util.make(Maps.newEnumMap(DyeColor.class), (p_203403_0_) -> {
            p_203403_0_.put(DyeColor.WHITE, ExpandedMod.BEDSHEET_BARK_WHITE.get());
            p_203403_0_.put(DyeColor.ORANGE, ExpandedMod.BEDSHEET_BARK_ORANGE.get());
            p_203403_0_.put(DyeColor.MAGENTA, ExpandedMod.BEDSHEET_BARK_MAGENTA.get());
            p_203403_0_.put(DyeColor.LIGHT_BLUE, ExpandedMod.BEDSHEET_BARK_LIGHT_BLUE.get());
            p_203403_0_.put(DyeColor.YELLOW, ExpandedMod.BEDSHEET_BARK_YELLOW.get());
            p_203403_0_.put(DyeColor.LIME, ExpandedMod.BEDSHEET_BARK_LIME.get());
            p_203403_0_.put(DyeColor.PINK, ExpandedMod.BEDSHEET_BARK_PINK.get());
            p_203403_0_.put(DyeColor.GRAY, ExpandedMod.BEDSHEET_BARK_GRAY.get());
            p_203403_0_.put(DyeColor.LIGHT_GRAY, ExpandedMod.BEDSHEET_BARK_LIGHT_GRAY.get());
            p_203403_0_.put(DyeColor.CYAN, ExpandedMod.BEDSHEET_BARK_CYAN.get());
            p_203403_0_.put(DyeColor.PURPLE, ExpandedMod.BEDSHEET_BARK_PURPLE.get());
            p_203403_0_.put(DyeColor.BLUE, ExpandedMod.BEDSHEET_BARK_BLUE.get());
            p_203403_0_.put(DyeColor.BROWN, ExpandedMod.BEDSHEET_BARK_BROWN.get());
            p_203403_0_.put(DyeColor.GREEN, ExpandedMod.BEDSHEET_BARK_GREEN.get());
            p_203403_0_.put(DyeColor.RED, ExpandedMod.BEDSHEET_BARK_RED.get());
            p_203403_0_.put(DyeColor.BLACK, ExpandedMod.BEDSHEET_BARK_BLACK.get());
        }));

        p_203402_0_.put(BedsheetPatterns.CORAL, Util.make(Maps.newEnumMap(DyeColor.class), (p_203403_0_) -> {
            p_203403_0_.put(DyeColor.WHITE, ExpandedMod.BEDSHEET_CORAL_WHITE.get());
            p_203403_0_.put(DyeColor.ORANGE, ExpandedMod.BEDSHEET_CORAL_ORANGE.get());
            p_203403_0_.put(DyeColor.MAGENTA, ExpandedMod.BEDSHEET_CORAL_MAGENTA.get());
            p_203403_0_.put(DyeColor.LIGHT_BLUE, ExpandedMod.BEDSHEET_CORAL_LIGHT_BLUE.get());
            p_203403_0_.put(DyeColor.YELLOW, ExpandedMod.BEDSHEET_CORAL_YELLOW.get());
            p_203403_0_.put(DyeColor.LIME, ExpandedMod.BEDSHEET_CORAL_LIME.get());
            p_203403_0_.put(DyeColor.PINK, ExpandedMod.BEDSHEET_CORAL_PINK.get());
            p_203403_0_.put(DyeColor.GRAY, ExpandedMod.BEDSHEET_CORAL_GRAY.get());
            p_203403_0_.put(DyeColor.LIGHT_GRAY, ExpandedMod.BEDSHEET_CORAL_LIGHT_GRAY.get());
            p_203403_0_.put(DyeColor.CYAN, ExpandedMod.BEDSHEET_CORAL_CYAN.get());
            p_203403_0_.put(DyeColor.PURPLE, ExpandedMod.BEDSHEET_CORAL_PURPLE.get());
            p_203403_0_.put(DyeColor.BLUE, ExpandedMod.BEDSHEET_CORAL_BLUE.get());
            p_203403_0_.put(DyeColor.BROWN, ExpandedMod.BEDSHEET_CORAL_BROWN.get());
            p_203403_0_.put(DyeColor.GREEN, ExpandedMod.BEDSHEET_CORAL_GREEN.get());
            p_203403_0_.put(DyeColor.RED, ExpandedMod.BEDSHEET_CORAL_RED.get());
            p_203403_0_.put(DyeColor.BLACK, ExpandedMod.BEDSHEET_CORAL_BLACK.get());
        }));

        p_203402_0_.put(BedsheetPatterns.MUSHROOM, Util.make(Maps.newEnumMap(DyeColor.class), (p_203403_0_) -> {
            p_203403_0_.put(DyeColor.WHITE, ExpandedMod.BEDSHEET_MUSHROOM_WHITE.get());
            p_203403_0_.put(DyeColor.ORANGE, ExpandedMod.BEDSHEET_MUSHROOM_ORANGE.get());
            p_203403_0_.put(DyeColor.MAGENTA, ExpandedMod.BEDSHEET_MUSHROOM_MAGENTA.get());
            p_203403_0_.put(DyeColor.LIGHT_BLUE, ExpandedMod.BEDSHEET_MUSHROOM_LIGHT_BLUE.get());
            p_203403_0_.put(DyeColor.YELLOW, ExpandedMod.BEDSHEET_MUSHROOM_YELLOW.get());
            p_203403_0_.put(DyeColor.LIME, ExpandedMod.BEDSHEET_MUSHROOM_LIME.get());
            p_203403_0_.put(DyeColor.PINK, ExpandedMod.BEDSHEET_MUSHROOM_PINK.get());
            p_203403_0_.put(DyeColor.GRAY, ExpandedMod.BEDSHEET_MUSHROOM_GRAY.get());
            p_203403_0_.put(DyeColor.LIGHT_GRAY, ExpandedMod.BEDSHEET_MUSHROOM_LIGHT_GRAY.get());
            p_203403_0_.put(DyeColor.CYAN, ExpandedMod.BEDSHEET_MUSHROOM_CYAN.get());
            p_203403_0_.put(DyeColor.PURPLE, ExpandedMod.BEDSHEET_MUSHROOM_PURPLE.get());
            p_203403_0_.put(DyeColor.BLUE, ExpandedMod.BEDSHEET_MUSHROOM_BLUE.get());
            p_203403_0_.put(DyeColor.BROWN, ExpandedMod.BEDSHEET_MUSHROOM_BROWN.get());
            p_203403_0_.put(DyeColor.GREEN, ExpandedMod.BEDSHEET_MUSHROOM_GREEN.get());
            p_203403_0_.put(DyeColor.RED, ExpandedMod.BEDSHEET_MUSHROOM_RED.get());
            p_203403_0_.put(DyeColor.BLACK, ExpandedMod.BEDSHEET_MUSHROOM_BLACK.get());
        }));

        p_203402_0_.put(BedsheetPatterns.EXTRA1, Util.make(Maps.newEnumMap(DyeColor.class), (p_203403_0_) -> {
            p_203403_0_.put(DyeColor.WHITE, ExpandedMod.BEDSHEET_EXTRA1_BEE_NEST.get());
            p_203403_0_.put(DyeColor.ORANGE, ExpandedMod.BEDSHEET_EXTRA1_BLAST_FURNACE.get());
            p_203403_0_.put(DyeColor.MAGENTA, ExpandedMod.BEDSHEET_EXTRA1_CHORUS.get());
            p_203403_0_.put(DyeColor.LIGHT_BLUE, ExpandedMod.BEDSHEET_EXTRA1_CORAL.get());
            p_203403_0_.put(DyeColor.YELLOW, ExpandedMod.BEDSHEET_EXTRA1_CRIMSON_STEM.get());
            p_203403_0_.put(DyeColor.LIME, ExpandedMod.BEDSHEET_EXTRA1_HAY.get());
            p_203403_0_.put(DyeColor.PINK, ExpandedMod.BEDSHEET_EXTRA1_HONEYCOMB.get());
            p_203403_0_.put(DyeColor.GRAY, ExpandedMod.BEDSHEET_EXTRA1_MAGMA.get());
            p_203403_0_.put(DyeColor.LIGHT_GRAY, ExpandedMod.BEDSHEET_EXTRA1_STRIPPED_ACACIA_LOG.get());
            p_203403_0_.put(DyeColor.CYAN, ExpandedMod.BEDSHEET_EXTRA1_WARPED_STEM.get());
            p_203403_0_.put(DyeColor.PURPLE, null);
            p_203403_0_.put(DyeColor.BLUE, null);
            p_203403_0_.put(DyeColor.BROWN, null);
            p_203403_0_.put(DyeColor.GREEN, null);
            p_203403_0_.put(DyeColor.RED, null);
            p_203403_0_.put(DyeColor.BLACK, null);
        }));
    });



    public static int bedsheetResolver(int j, ItemStack stack, BedsheetPatterns pattern) {
        for (int i = 0; i < DyeHandler.BEDSHEET_DYES.get(pattern).size(); i++) {
            if (stack.getItem().equals(DyeHandler.BEDSHEET_DYES.get(pattern).get(DyeColor.byId(i)).asItem())) {
                j = i;
                break;
            }
        }
        return j;
    }



}
