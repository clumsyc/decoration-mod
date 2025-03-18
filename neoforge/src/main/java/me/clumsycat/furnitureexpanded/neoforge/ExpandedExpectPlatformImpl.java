package me.clumsycat.furnitureexpanded.neoforge;

import me.clumsycat.furnitureexpanded.ExpandedExpectPlatform;
import net.minecraft.core.particles.SimpleParticleType;
import net.neoforged.fml.loading.FMLPaths;

import java.nio.file.Path;

public class ExpandedExpectPlatformImpl {
    /**
     * This is our actual method to {@link ExpandedExpectPlatform#getConfigDirectory()}.
     */
    public static Path getConfigDirectory() {
        return FMLPaths.CONFIGDIR.get();
    }

    public static SimpleParticleType getSimpleParticle(boolean alwaysSpawn) {
        return new SimpleParticleType(alwaysSpawn);
    }

}
