package me.clumsycat.furnitureexpanded.blocks.tileentities;

import me.clumsycat.furnitureexpanded.registries.RegistryHandler;
import me.clumsycat.furnitureexpanded.sounds.ShowerSoundInstance;
import me.clumsycat.furnitureexpanded.util.SoundMap;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class ShowerHeadTileEntity extends BlockEntity {
    public ShowerHeadTileEntity(BlockPos pPos, BlockState pBlockState) {
        super(RegistryHandler.SHOWER_HEAD_TE, pPos, pBlockState);
    }

    @Environment(EnvType.CLIENT)
    public void playSound() {
        SoundMap soundmap = SoundMap.getInstance();
        ShowerSoundInstance si = soundmap.getOrCreateSoundInstance(this.getPos());
        if (!MinecraftClient.getInstance().getSoundManager().isPlaying(si) && this.getCachedState().get(Properties.ENABLED)) {
            MinecraftClient.getInstance().getSoundManager().play(si);
        }
    }

    public static void particleTick(World pLevel, BlockPos pPos, BlockState pState, ShowerHeadTileEntity pBlockEntity) {
        Direction facing = pState.get(HorizontalFacingBlock.FACING);
        double xoffset = 0.5D + (facing == Direction.WEST ? -0.1D : facing == Direction.EAST ? 0.1D : 0.0D);
        double yoffset = 0.5D + (facing == Direction.NORTH ? -0.1D : facing == Direction.SOUTH ? 0.1D : 0.0D);
        int quality = MinecraftClient.isFancyGraphicsOrBetter() ? 2 : 1;
        for (int i = 0; i < quality; ++i)
            pLevel.addParticle(RegistryHandler.SHOWER_PARTICLE,
                    pPos.getX()+xoffset,
                    (double)pPos.getY() + 0.55D,
                    pPos.getZ()+yoffset,
                    0.0D, 0.0D, 0.0D);
        pBlockEntity.playSound();
    }
}
