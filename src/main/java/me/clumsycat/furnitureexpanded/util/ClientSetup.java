package me.clumsycat.furnitureexpanded.util;

import me.clumsycat.furnitureexpanded.Expanded;
import me.clumsycat.furnitureexpanded.items.ItemCardbox;
import me.clumsycat.furnitureexpanded.registries.RegistryHandler;
import me.clumsycat.furnitureexpanded.renderer.ClockSignTileEntityRenderer;
import me.clumsycat.furnitureexpanded.renderer.SeatRenderer;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = Expanded.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientSetup {

    @SubscribeEvent
    public static void init(final FMLClientSetupEvent event) {
        ItemBlockRenderTypes.setRenderLayer(RegistryHandler.SHOWER_BOX.get(), (layer) -> layer == RenderType.solid() || layer == RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(RegistryHandler.BATHROOM_SINK.get(), (layer) -> layer == RenderType.solid() || layer == RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(RegistryHandler.TOILET.get(), (layer) -> layer == RenderType.solid() || layer == RenderType.translucent());

        event.enqueueWork(ClientSetup::registerPropertyOverride);
    }

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(RegistryHandler.CLOCK_SIGN_TE.get(), ClockSignTileEntityRenderer::new);
        event.registerEntityRenderer(RegistryHandler.SEAT.get(), SeatRenderer::new);
    }

    public static void registerPropertyOverride() {
        ItemProperties.register(RegistryHandler.CARDBOX_ITEM.get(), new ResourceLocation("fullness"), ItemCardbox::getFullnessPropertyOverride);
    }


}
