package me.clumsycat.furnitureexpanded.neoforge.registries;

import me.clumsycat.furnitureexpanded.ExpandedMod;
import me.clumsycat.furnitureexpanded.items.ItemBasket;
import me.clumsycat.furnitureexpanded.items.ItemCardbox;
import me.clumsycat.furnitureexpanded.particles.ShowerParticles;
import me.clumsycat.furnitureexpanded.renderer.AlarmClockTileEntityRenderer;
import me.clumsycat.furnitureexpanded.renderer.ClockSignTileEntityRenderer;
import me.clumsycat.furnitureexpanded.renderer.SeatRenderer;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.component.DyedItemColor;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;

@EventBusSubscriber(modid = ExpandedMod.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class ClientSetup {

    @SubscribeEvent
    public static void init(FMLClientSetupEvent event) {
        event.enqueueWork(ClientSetup::registerPropertyOverride);
    }

    public static void registerPropertyOverride() {
        ItemProperties.register(ExpandedMod.CARDBOX_ITEM.get(), ResourceLocation.parse("fullness"), ItemCardbox::getFullnessPropertyOverride);
        ItemProperties.register(ExpandedMod.BASKET.get(), ResourceLocation.parse("fullness"), ItemBasket::getFullnessPropertyOverride);
    }

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(ExpandedMod.CLOCK_SIGN_TE.get(), ClockSignTileEntityRenderer::new);
        event.registerBlockEntityRenderer(ExpandedMod.ALARM_CLOCK_TE.get(), AlarmClockTileEntityRenderer::new);
        event.registerEntityRenderer(ExpandedMod.SEAT.get(), SeatRenderer::new);
    }

    @SubscribeEvent
    public static void registerItemColors(RegisterColorHandlersEvent.Item event) {
        event.register((stack, tintIndex) -> DyedItemColor.getOrDefault(stack, DyedItemColor.LEATHER_COLOR), ExpandedMod.BASKET.get());
    }

    @SubscribeEvent
    public static void registerParticleFactories(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(ExpandedMod.SHOWER_PARTICLES.get(), ShowerParticles.Provider::new);
    }
}
