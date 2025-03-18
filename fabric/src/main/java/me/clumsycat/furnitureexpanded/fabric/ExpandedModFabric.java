package me.clumsycat.furnitureexpanded.fabric;

import me.clumsycat.furnitureexpanded.ExpandedMod;
import me.clumsycat.furnitureexpanded.fabric.events.FabricEvents;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;

public class ExpandedModFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        ExpandedMod.init();

        FabricEvents.init();
        PlayerBlockBreakEvents.AFTER.register(FabricEvents::onBreakEvent);
    }
}
