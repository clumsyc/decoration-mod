package me.clumsycat.furnitureexpanded.forge.registries;

import me.clumsycat.furnitureexpanded.ExpandedMod;
import me.clumsycat.furnitureexpanded.items.ItemBasket;
import me.clumsycat.furnitureexpanded.items.ItemCardbox;
import me.clumsycat.furnitureexpanded.particles.ShowerParticles;
import me.clumsycat.furnitureexpanded.renderer.AlarmClockTileEntityRenderer;
import me.clumsycat.furnitureexpanded.renderer.ClockSignTileEntityRenderer;
import me.clumsycat.furnitureexpanded.renderer.SeatRenderer;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = ExpandedMod.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientSetup {

    @SubscribeEvent
    public static void init(final FMLClientSetupEvent event) {
        event.enqueueWork(ClientSetup::registerPropertyOverride);
    }

    public static void registerPropertyOverride() {
        ItemProperties.register(ExpandedMod.CARDBOX_ITEM.get(), new ResourceLocation("fullness"), ItemCardbox::getFullnessPropertyOverride);
        ItemProperties.register(ExpandedMod.BASKET.get(), new ResourceLocation("fullness"), ItemBasket::getFullnessPropertyOverride);
    }

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(ExpandedMod.CLOCK_SIGN_TE.get(), ClockSignTileEntityRenderer::new);
        event.registerBlockEntityRenderer(ExpandedMod.ALARM_CLOCK_TE.get(), AlarmClockTileEntityRenderer::new);
        event.registerEntityRenderer(ExpandedMod.SEAT.get(), SeatRenderer::new);
    }

    @SubscribeEvent
    public static void registerItemColors(RegisterColorHandlersEvent.Item event) {
        event.register((pStack, pTintIndex) -> ((DyeableLeatherItem) pStack.getItem()).getColor(pStack), ExpandedMod.BASKET.get());
    }

    @SubscribeEvent
    public static void registerParticleFactories(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(ExpandedMod.SHOWER_PARTICLES.get(), ShowerParticles.Provider::new);
    }


}
