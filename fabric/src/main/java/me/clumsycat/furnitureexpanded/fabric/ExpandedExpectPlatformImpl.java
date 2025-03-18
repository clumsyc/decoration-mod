package me.clumsycat.furnitureexpanded.fabric;

import me.clumsycat.furnitureexpanded.ExpandedExpectPlatform;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.particles.SimpleParticleType;

import java.nio.file.Path;

public class ExpandedExpectPlatformImpl {
    /**
     * This is our actual method to {@link ExpandedExpectPlatform#getConfigDirectory()}.
     */
    public static Path getConfigDirectory() {
        return FabricLoader.getInstance().getConfigDir();
    }

    public static SimpleParticleType getSimpleParticle(boolean alwaysSpawn) {
        return FabricParticleTypes.simple();
    }

}
