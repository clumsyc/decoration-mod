package me.clumsycat.furnitureexpanded.forge.registries;

import me.clumsycat.furnitureexpanded.ExpandedMod;
import me.clumsycat.furnitureexpanded.forge.events.BlockBreakEvent;
import me.clumsycat.furnitureexpanded.forge.events.LeftClickEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(modid = ExpandedMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModSetup {

    public static void init(final FMLCommonSetupEvent event) {
        MinecraftForge.EVENT_BUS.addListener(LeftClickEvent::eventHandler);
        MinecraftForge.EVENT_BUS.addListener(BlockBreakEvent::eventHandler);

    }

}