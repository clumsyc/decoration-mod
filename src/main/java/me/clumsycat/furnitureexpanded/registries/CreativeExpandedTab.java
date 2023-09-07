package me.clumsycat.furnitureexpanded.registries;

import me.clumsycat.furnitureexpanded.Expanded;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Expanded.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CreativeExpandedTab {
    public static CreativeModeTab EXPANDED_TAB;

    @SubscribeEvent
    public static void registerExpandedTab(CreativeModeTabEvent.Register event) {
        EXPANDED_TAB = event.registerCreativeModeTab(new ResourceLocation(Expanded.MOD_ID, "expanded_tab"), builder ->
                builder.icon(() -> new ItemStack(RegistryHandler.CARDBOX_ITEM.get()))
                        .title(Component.translatable("item_group."+Expanded.MOD_ID+".expanded_tab")));
    }

    @SubscribeEvent
    public static void creativeExpandedTab(CreativeModeTabEvent.BuildContents event) {
        if (event.getTab() == CreativeExpandedTab.EXPANDED_TAB || event.getTab() == CreativeModeTabs.SEARCH) {
            event.accept(RegistryHandler.TOILET_ITEM.get());
            event.accept(RegistryHandler.PAPER_HOLDER_ITEM.get());
            event.accept(RegistryHandler.TRASH_CAN_ITEM.get());
            event.accept(RegistryHandler.BATHROOM_SINK_ITEM.get());
            event.accept(RegistryHandler.BATHTUB_ITEM.get());
            event.accept(RegistryHandler.SHOWER_BOX_ITEM.get());
            event.accept(RegistryHandler.SHOWER_HEAD_ITEM.get());
            event.accept(RegistryHandler.TOWEL_BAR_ITEM.get());
            event.accept(RegistryHandler.MIRROR_ITEM.get());
            event.accept(RegistryHandler.FILE_CABINET_ITEM.get());
            event.accept(RegistryHandler.CARDBOX_ITEM.get());
            event.accept(RegistryHandler.CLOCK_SIGN_ITEM.get());
            event.accept(RegistryHandler.BASKET.get());
            event.accept(RegistryHandler.TAPE.get());
            event.accept(RegistryHandler.SAW.get());
            event.accept(RegistryHandler.SAWDUST.get());
        }
    }
}
