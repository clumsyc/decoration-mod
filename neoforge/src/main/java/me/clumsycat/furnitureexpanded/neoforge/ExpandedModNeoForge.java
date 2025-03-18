package me.clumsycat.furnitureexpanded.neoforge;

import me.clumsycat.furnitureexpanded.ExpandedMod;
import me.clumsycat.furnitureexpanded.neoforge.events.BlockBreakEvent;
import me.clumsycat.furnitureexpanded.neoforge.events.LeftClickEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;

@Mod(ExpandedMod.MOD_ID)
public class ExpandedModNeoForge {
    public ExpandedModNeoForge() {
        ExpandedMod.init();
        NeoForge.EVENT_BUS.addListener(LeftClickEvent::eventHandler);
        NeoForge.EVENT_BUS.addListener(BlockBreakEvent::eventHandler);
    }
}
