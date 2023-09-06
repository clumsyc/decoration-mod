package me.clumsycat.furnitureexpanded.sounds;

import me.clumsycat.furnitureexpanded.registries.RegistryHandler;
import me.clumsycat.furnitureexpanded.util.SoundMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ShowerSoundInstance extends AbstractTickableSoundInstance {
    public ShowerSoundInstance(BlockPos pos) {
        super(RegistryHandler.SHOWER_SFX.get(), SoundSource.BLOCKS, SoundInstance.createUnseededRandom());
        this.x = pos.getX() + 0.5D;
        this.y = pos.getY() + 0.5D;
        this.z = pos.getZ() + 0.5D;
        this.looping = true;
        this.delay = 0;
    }

    private void _stop(BlockPos soundPos) {
        SoundMap soundmap = SoundMap.getInstance();
        ShowerSoundInstance si = soundmap.getSoundInstance(soundPos);
        Minecraft.getInstance().getSoundManager().stop(si);
    }

    @Override
    public void tick() {
        BlockPos soundPos = new BlockPos(this.x, this.y, this.z);
        BlockState state = Minecraft.getInstance().level.getBlockState(soundPos);
        if (!state.is(RegistryHandler.SHOWER_HEAD.get())) {
            this._stop(soundPos);
        } else {
            if (!state.getValue(BlockStateProperties.ENABLED))
                this._stop(soundPos);
        }
    }
}
