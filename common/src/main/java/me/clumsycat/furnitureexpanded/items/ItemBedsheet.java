package me.clumsycat.furnitureexpanded.items;

import me.clumsycat.furnitureexpanded.ExpandedMod;
import me.clumsycat.furnitureexpanded.util.enums.BedsheetPatterns;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class ItemBedsheet extends Item {
    private final BedsheetPatterns bedsheetPattern;
    private final DyeColor bedsheetColor;
    private final ChatFormatting bedsheetColorFormatting;
    public ItemBedsheet(int maxStackSize, BedsheetPatterns pattern, DyeColor color, ChatFormatting formatting) {
        super(new Properties()
                .stacksTo(maxStackSize)
                .arch$tab(ExpandedMod.EXPANDED_TAB)
        );
        bedsheetPattern = pattern;
        bedsheetColor = color;
        bedsheetColorFormatting = formatting;
    }

    public BedsheetPatterns getPattern() {
        return this.bedsheetPattern;
    }

    public DyeColor getColor() {
        return this.bedsheetColor;
    }

    @Override
    public Component getName(ItemStack itemStack) {
        return Component.translatable("item.furnitureexpanded.bedsheet", Component.translatable("patterns.furnitureexpanded." + bedsheetPattern));
    }

    @Environment(EnvType.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, TooltipContext tooltipContext, List<Component> tooltip, TooltipFlag flagIn) {
        tooltip.add(Component.translatable("tooltip.furnitureexpanded.pattern", Component.translatable("patterns.furnitureexpanded." + bedsheetPattern)));
        if (!bedsheetPattern.equals(BedsheetPatterns.EXTRA1))
            tooltip.add(Component.translatable("tooltip.furnitureexpanded.color", Component.translatable("color.minecraft." + bedsheetColor).withStyle(bedsheetColorFormatting)));
        super.appendHoverText(stack, tooltipContext, tooltip, flagIn);
    }
}
