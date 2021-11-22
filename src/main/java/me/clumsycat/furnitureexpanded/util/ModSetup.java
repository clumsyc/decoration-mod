package me.clumsycat.furnitureexpanded.util;

import me.clumsycat.furnitureexpanded.Expanded;
import me.clumsycat.furnitureexpanded.events.LeftClickEvent;
import me.clumsycat.furnitureexpanded.registries.RegistryHandler;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(modid = Expanded.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModSetup {

    public static void init(final FMLCommonSetupEvent event) {
        MinecraftForge.EVENT_BUS.addListener(LeftClickEvent::eventHandler);
    }


    public static final ItemGroup TAB = new ItemGroup("expandedTab") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(RegistryHandler.CARDBOX_ITEM.get());
        }
    };


}
