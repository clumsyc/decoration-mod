package me.clumsycat.furnitureexpanded.items;

import me.clumsycat.furnitureexpanded.ExpandedMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class FEItemTags {
    public static final TagKey<Item> BEDSHEETS = bind(ExpandedMod.MOD_ID + ":bedsheets");
    public static final TagKey<Item> BEDSHEETS_BAMBOO = bind(ExpandedMod.MOD_ID + ":bedsheets_bamboo");
    public static final TagKey<Item> BEDSHEETS_BARK = bind(ExpandedMod.MOD_ID + ":bedsheets_bark");
    public static final TagKey<Item> BEDSHEETS_CORAL = bind(ExpandedMod.MOD_ID + ":bedsheets_coral");
    public static final TagKey<Item> BEDSHEETS_MUSHROOM = bind(ExpandedMod.MOD_ID + ":bedsheets_mushroom");
    public static final TagKey<Item> BEDSHEETS_OTHERS = bind(ExpandedMod.MOD_ID + ":bedsheets_others");

    private FEItemTags() {}

    private static TagKey<Item> bind(String string) {
        return TagKey.create(Registries.ITEM, new ResourceLocation(string));
    }
}
