package me.clumsycat.furnitureexpanded.registries;

import me.clumsycat.furnitureexpanded.Expanded;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class CreativeExpandedTab {
    public static ItemGroup expandedTab;

    public static void registerItemGroup() {
        expandedTab = FabricItemGroup.builder(new Identifier(Expanded.MOD_ID, "expanded_tab"))
                .displayName(Text.translatable("itemGroup." + Expanded.MOD_ID + ".expanded_tab"))
                .icon(() -> new ItemStack(RegistryHandler.CARDBOX.asItem()))
                .build();
    }

    public static void addItemToExpandedTab(Item item) {
        ItemGroupEvents.modifyEntriesEvent(expandedTab).register(entries -> entries.add(item));
    }
}
