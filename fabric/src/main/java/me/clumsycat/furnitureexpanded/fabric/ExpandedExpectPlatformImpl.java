package me.clumsycat.furnitureexpanded.fabric;

import me.clumsycat.furnitureexpanded.ExpandedExpectPlatform;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.world.entity.Entity;

import java.nio.file.Path;

public class ExpandedExpectPlatformImpl {
    /**
     * This is our actual method to {@link ExpandedExpectPlatform#getConfigDirectory()}.
     */
    public static Path getConfigDirectory() {
        return FabricLoader.getInstance().getConfigDir();
    }

    public static Packet<ClientGamePacketListener> getEntitySpawningPacket(Entity entity) {
        return new ClientboundAddEntityPacket(entity);
    }

    public static SimpleParticleType getSimpleParticle(boolean alwaysSpawn) {
        return FabricParticleTypes.simple();
    }

}
