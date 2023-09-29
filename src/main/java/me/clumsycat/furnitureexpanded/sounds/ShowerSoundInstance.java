package me.clumsycat.furnitureexpanded.sounds;

import me.clumsycat.furnitureexpanded.registries.RegistryHandler;
import me.clumsycat.furnitureexpanded.util.SoundMap;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.MovingSoundInstance;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.sound.SoundCategory;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;

public class ShowerSoundInstance extends MovingSoundInstance {
    public ShowerSoundInstance(BlockPos pos) {
        super(RegistryHandler.SHOWER_SFX, SoundCategory.BLOCKS, SoundInstance.createRandom());
        this.x = pos.getX() + 0.5D;
        this.y = pos.getY() + 0.5D;
        this.z = pos.getZ() + 0.5D;
        this.repeat = true;
        this.repeatDelay = 0;
    }

    private void _stop(BlockPos soundPos) {
        SoundMap soundmap = SoundMap.getInstance();
        ShowerSoundInstance si = soundmap.getSoundInstance(soundPos);
        MinecraftClient.getInstance().getSoundManager().stop(si);
    }

    @Override
    public void tick() {
        BlockPos soundPos = new BlockPos(this.x, this.y, this.z);
        BlockState state = MinecraftClient.getInstance().world.getBlockState(soundPos);
        if (!state.isOf(RegistryHandler.SHOWER_HEAD)) {
            this._stop(soundPos);
        } else {
            if (!state.get(Properties.ENABLED))
                this._stop(soundPos);
        }
    }
}
