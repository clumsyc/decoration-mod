package me.clumsycat.furnitureexpanded.sounds;

import me.clumsycat.furnitureexpanded.ExpandedMod;
import me.clumsycat.furnitureexpanded.util.SoundMap;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

@Environment(EnvType.CLIENT)
public class ShowerSoundInstance extends AbstractTickableSoundInstance {
    public ShowerSoundInstance(BlockPos pos) {
        super(ExpandedMod.SHOWER_SFX.get(), SoundSource.BLOCKS, SoundInstance.createUnseededRandom());
        this.x = pos.getX() + 0.5D;
        this.y = pos.getY() + 0.5D;
        this.z = pos.getZ() + 0.5D;
        this.looping = true;
        this.delay = 0;
    }

    private void _stop(BlockPos soundPos) {
        SoundMap soundmap = SoundMap.getInstance();
        ShowerSoundInstance si = (ShowerSoundInstance) soundmap.getSoundInstance(soundPos);
        if (si != null) Minecraft.getInstance().getSoundManager().stop(si);
        soundmap.removeSoundInstance(soundPos);
    }

    @Override
    public void tick() {
        BlockPos soundPos = BlockPos.containing(this.x, this.y, this.z);
        BlockState state = Minecraft.getInstance().level.getBlockState(soundPos);
        if (!state.is(ExpandedMod.SHOWER_HEAD.get())) {
            this._stop(soundPos);
        } else {
            if (!state.getValue(BlockStateProperties.ENABLED) || !Minecraft.getInstance().player.blockPosition().closerThan(soundPos, 9))
                this._stop(soundPos);
        }
    }
}
