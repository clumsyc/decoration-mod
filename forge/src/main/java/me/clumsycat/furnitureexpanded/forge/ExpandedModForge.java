package me.clumsycat.furnitureexpanded.forge;

import dev.architectury.platform.forge.EventBuses;
import me.clumsycat.furnitureexpanded.ExpandedMod;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(ExpandedMod.MOD_ID)
public class ExpandedModForge {
    public ExpandedModForge() {
        EventBuses.registerModEventBus(ExpandedMod.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
        ExpandedMod.init();
    }
}
