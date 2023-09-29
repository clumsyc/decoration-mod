package me.clumsycat.furnitureexpanded.util;

import me.clumsycat.furnitureexpanded.sounds.ShowerSoundInstance;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.math.BlockPos;

import java.util.HashMap;
import java.util.Map;

@Environment(EnvType.CLIENT)
public class SoundMap {
    private static SoundMap instance;
    private final Map<BlockPos, ShowerSoundInstance> soundInstanceMap = new HashMap<>();

    private SoundMap() {}

    public static SoundMap getInstance() {
        if (instance == null) {
            instance = new SoundMap();
        }
        return instance;
    }

    public void addSoundInstance(BlockPos pos, ShowerSoundInstance soundInstance) {
        soundInstanceMap.put(pos, soundInstance);
    }

    public void removeSoundInstance(BlockPos pos) {
        soundInstanceMap.remove(pos);
    }

    public ShowerSoundInstance getSoundInstance(BlockPos pos) {
        return soundInstanceMap.getOrDefault(pos, null);
    }

    public ShowerSoundInstance getOrCreateSoundInstance(BlockPos pos) {
        ShowerSoundInstance is = getSoundInstance(pos);
        if (is == null) {
            is = new ShowerSoundInstance(pos);
            addSoundInstance(pos, is);
        }
        return is;
    }
}
