package me.clumsycat.furnitureexpanded.util;

import me.clumsycat.furnitureexpanded.sounds.ShowerSoundInstance;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.core.BlockPos;

import java.util.HashMap;
import java.util.Map;

@Environment(EnvType.CLIENT)
public class SoundMap {
    private static SoundMap instance;
    private final Map<BlockPos, AbstractTickableSoundInstance> soundInstanceMap = new HashMap<>();

    private SoundMap() {}

    public static SoundMap getInstance() {
        if (instance == null) {
            instance = new SoundMap();
        }
        return instance;
    }

    public void addSoundInstance(BlockPos pos, AbstractTickableSoundInstance soundInstance) {
        soundInstanceMap.put(pos, soundInstance);
    }

    public void removeSoundInstance(BlockPos pos) {
        soundInstanceMap.remove(pos);
    }

    public AbstractTickableSoundInstance getSoundInstance(BlockPos pos) {
        return soundInstanceMap.getOrDefault(pos, null);
    }

    public ShowerSoundInstance getOrCreateShowerSoundInstance(BlockPos pos) {
        AbstractTickableSoundInstance atsi = getSoundInstance(pos);
        if (atsi instanceof ShowerSoundInstance || atsi == null) {
            ShowerSoundInstance is = (ShowerSoundInstance) getSoundInstance(pos);
            if (is == null) {
                is = new ShowerSoundInstance(pos);
                addSoundInstance(pos, is);
            }
            return is;
        }
        return null;
    }
}
