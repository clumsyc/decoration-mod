package me.clumsycat.furnitureexpanded.registries;

import me.clumsycat.furnitureexpanded.Expanded;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Expanded.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CreativeExpandedTab {

    @SubscribeEvent
    public static void creativeExpandedTab(BuildCreativeModeTabContentsEvent event) {
        if (event.getTab() == RegistryHandler.EXPANDED_TAB.get() || event.getTabKey() == CreativeModeTabs.SEARCH) {
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
