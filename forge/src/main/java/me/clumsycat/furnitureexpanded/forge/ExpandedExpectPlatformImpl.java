package me.clumsycat.furnitureexpanded.forge;

import me.clumsycat.furnitureexpanded.ExpandedExpectPlatform;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.network.NetworkHooks;

import java.nio.file.Path;

public class ExpandedExpectPlatformImpl {
    /**
     * This is our actual method to {@link ExpandedExpectPlatform#getConfigDirectory()}.
     */
    public static Path getConfigDirectory() {
        return FMLPaths.CONFIGDIR.get();
    }

    public static Packet<ClientGamePacketListener> getEntitySpawningPacket(Entity entity) {
        return NetworkHooks.getEntitySpawningPacket(entity);
    }

    public static SimpleParticleType getSimpleParticle(boolean alwaysSpawn) {
        return new SimpleParticleType(alwaysSpawn);
    }

}
