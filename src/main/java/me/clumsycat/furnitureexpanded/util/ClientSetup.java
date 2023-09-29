package me.clumsycat.furnitureexpanded.util;

import me.clumsycat.furnitureexpanded.Expanded;
import me.clumsycat.furnitureexpanded.items.ItemBasket;
import me.clumsycat.furnitureexpanded.items.ItemCardbox;
import me.clumsycat.furnitureexpanded.particles.ShowerParticles;
import me.clumsycat.furnitureexpanded.registries.RegistryHandler;
import me.clumsycat.furnitureexpanded.renderer.ClockSignTileEntityRenderer;
import me.clumsycat.furnitureexpanded.renderer.SeatRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;

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
        BlockEntityRendererRegistry.register(RegistryHandler.CLOCK_SIGN_TE, ClockSignTileEntityRenderer::new);
        EntityRendererRegistry.register(Expanded.SEAT, SeatRenderer::new);
    }

    private void registerBlockModelLayers() {
        BlockRenderLayerMap.INSTANCE.putBlock(RegistryHandler.SHOWER_BOX, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(RegistryHandler.BATHTUB, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(RegistryHandler.BATHROOM_SINK, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(RegistryHandler.TOILET, RenderLayer.getTranslucent());
    }

    private void registerPropertyOverride() {
        ModelPredicateProviderRegistry.register(RegistryHandler.CARDBOX.asItem(), new Identifier("fullness"), ItemCardbox::getFullnessPropertyOverride);
        ModelPredicateProviderRegistry.register(RegistryHandler.BASKET, new Identifier("fullness"), ItemBasket::getFullnessPropertyOverride);
    }

    private void registerParticleFactories() {
        ParticleFactoryRegistry.getInstance().register(RegistryHandler.SHOWER_PARTICLE, ShowerParticles.Provider::new);
    }

    private void registerItemColors() {
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> ((ItemBasket) stack.getItem()).getColor(stack), RegistryHandler.BASKET);
    }



}
