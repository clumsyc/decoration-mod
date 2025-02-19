package me.clumsycat.furnitureexpanded.fabric.registries;

import me.clumsycat.furnitureexpanded.ExpandedMod;
import me.clumsycat.furnitureexpanded.items.ItemBasket;
import me.clumsycat.furnitureexpanded.items.ItemCardbox;
import me.clumsycat.furnitureexpanded.particles.ShowerParticles;
import me.clumsycat.furnitureexpanded.renderer.AlarmClockTileEntityRenderer;
import me.clumsycat.furnitureexpanded.renderer.ClockSignTileEntityRenderer;
import me.clumsycat.furnitureexpanded.renderer.SeatRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;

public class ClientSetup implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        registerRenderers();
        registerBlockModelLayers();
        registerPropertyOverride();
        registerParticleFactories();
        registerItemColors();
    }

    private void registerRenderers() {
        BlockEntityRenderers.register(ExpandedMod.CLOCK_SIGN_TE.get(), ClockSignTileEntityRenderer::new);
        BlockEntityRenderers.register(ExpandedMod.ALARM_CLOCK_TE.get(), AlarmClockTileEntityRenderer::new);
        EntityRendererRegistry.register(ExpandedMod.SEAT.get(), SeatRenderer::new);
    }

    private void registerBlockModelLayers() {
        BlockRenderLayerMap.INSTANCE.putBlock(ExpandedMod.SHOWER_BOX.get(), RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ExpandedMod.BATHTUB.get(), RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ExpandedMod.BATHROOM_SINK.get(), RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ExpandedMod.TOILET.get(), RenderType.translucent());
    }

    private void registerPropertyOverride() {
        ItemProperties.register(ExpandedMod.CARDBOX_ITEM.get(), new ResourceLocation("fullness"), ItemCardbox::getFullnessPropertyOverride);
        ItemProperties.register(ExpandedMod.BASKET.get(), new ResourceLocation("fullness"), ItemBasket::getFullnessPropertyOverride);
    }

    private void registerParticleFactories() {
        ParticleFactoryRegistry.getInstance().register(ExpandedMod.SHOWER_PARTICLES.get(), ShowerParticles.Provider::new);
    }

    private void registerItemColors() {
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> ((ItemBasket) stack.getItem()).getColor(stack), ExpandedMod.BASKET.get());
    }



}
