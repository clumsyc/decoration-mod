package me.clumsycat.furnitureexpanded.util;

import me.clumsycat.furnitureexpanded.Expanded;
import me.clumsycat.furnitureexpanded.blocks.tileentities.renderer.ClockSignTileEntityRenderer;
import me.clumsycat.furnitureexpanded.items.ItemCardbox;
import me.clumsycat.furnitureexpanded.registries.RegistryHandler;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = Expanded.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientSetup {

    public static void init(final FMLClientSetupEvent event) {
        ClientRegistry.bindTileEntityRenderer(RegistryHandler.CLOCK_SIGN_TE.get(), ClockSignTileEntityRenderer::new);

        RenderTypeLookup.setRenderLayer(RegistryHandler.SHOWER_BOX.get(), (layer) -> layer == RenderType.solid() || layer == RenderType.translucent());
        RenderTypeLookup.setRenderLayer(RegistryHandler.BATHROOM_SINK.get(), (layer) -> layer == RenderType.solid() || layer == RenderType.translucent());
        RenderTypeLookup.setRenderLayer(RegistryHandler.TOILET.get(), (layer) -> layer == RenderType.solid() || layer == RenderType.translucent());

        event.enqueueWork(ClientSetup::registerPropertyOverride);
    }

    public static void registerPropertyOverride() {
        ItemModelsProperties.register(RegistryHandler.CARDBOX_ITEM.get(), new ResourceLocation("fullness"), ItemCardbox::getFullnessPropertyOverride);
    }


}
