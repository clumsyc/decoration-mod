package me.clumsycat.furnitureexpanded.registries;

import me.clumsycat.furnitureexpanded.Expanded;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class CreativeExpandedTab {
    public static ItemGroup expandedTab = Registry.register(Registries.ITEM_GROUP, new Identifier(Expanded.MOD_ID, "expanded_tab"),
            FabricItemGroup.builder()
                    .displayName(Text.translatable("itemGroup." + Expanded.MOD_ID + ".expanded_tab"))
                    .icon(() -> new ItemStack(RegistryHandler.CARDBOX.asItem()))
                    .entries((displayContext, entries) -> {
                        entries.add(RegistryHandler.TOILET);
                        entries.add(RegistryHandler.PAPER_HOLDER);
                        entries.add(RegistryHandler.TRASH_CAN);
                        entries.add(RegistryHandler.BATHROOM_SINK);
                        entries.add(RegistryHandler.BATHTUB);
                        entries.add(RegistryHandler.SHOWER_BOX);
                        entries.add(RegistryHandler.SHOWER_HEAD);
                        entries.add(RegistryHandler.TOWEL_BAR);
                        entries.add(RegistryHandler.MIRROR);
                        entries.add(RegistryHandler.FILE_CABINET);
                        entries.add(RegistryHandler.CARDBOX);
                        entries.add(RegistryHandler.CLOCK_SIGN);
                        entries.add(RegistryHandler.BASKET);
                        entries.add(RegistryHandler.TAPE);
                        entries.add(RegistryHandler.SAW);
                        entries.add(RegistryHandler.SAWDUST);
                    })
                    .build()
    );

    public static void registerTab() {}
}
