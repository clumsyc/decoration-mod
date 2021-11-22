package me.clumsycat.furnitureexpanded;

import me.clumsycat.furnitureexpanded.registries.RegistryHandler;
import me.clumsycat.furnitureexpanded.util.ClientSetup;
import me.clumsycat.furnitureexpanded.util.ModSetup;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Expanded.MOD_ID)
public class Expanded {
    public static final String MOD_ID = "furnitureexpanded";

    public Expanded() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(ModSetup::init);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(ClientSetup::init);

        RegistryHandler.init();

        MinecraftForge.EVENT_BUS.register(this);

    }

}
