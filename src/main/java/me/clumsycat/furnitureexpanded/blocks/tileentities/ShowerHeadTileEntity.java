package me.clumsycat.furnitureexpanded.blocks.tileentities;

import me.clumsycat.furnitureexpanded.registries.RegistryHandler;
import me.clumsycat.furnitureexpanded.sounds.ShowerSoundInstance;
import me.clumsycat.furnitureexpanded.util.SoundMap;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ShowerHeadTileEntity extends BlockEntity {
    public ShowerHeadTileEntity(BlockPos pPos, BlockState pBlockState) {
        super(RegistryHandler.SHOWER_HEAD_TE.get(), pPos, pBlockState);
    }

    @OnlyIn(Dist.CLIENT)
    public void playSound() {
        SoundMap soundmap = SoundMap.getInstance();
        ShowerSoundInstance si = soundmap.getOrCreateSoundInstance(this.getBlockPos());
        if (!Minecraft.getInstance().getSoundManager().isActive(si) && this.getBlockState().getValue(BlockStateProperties.ENABLED)) {
            Minecraft.getInstance().getSoundManager().play(si);
        }
    }

    public static void particleTick(Level pLevel, BlockPos pPos, BlockState pState, ShowerHeadTileEntity pBlockEntity) {
        Direction facing = pState.getValue(HorizontalDirectionalBlock.FACING);
        double xoffset = 0.5D + (facing == Direction.WEST ? -0.1D : facing == Direction.EAST ? 0.1D : 0.0D);
        double yoffset = 0.5D + (facing == Direction.NORTH ? -0.1D : facing == Direction.SOUTH ? 0.1D : 0.0D);
        int quality = Minecraft.useFancyGraphics() ? 2 : 1;
        for (int i = 0; i < quality; ++i)
            pLevel.addParticle(RegistryHandler.SHOWER_PARTICLES.get(),
                    pPos.getX()+xoffset,
                    (double)pPos.getY() + 0.55D,
                    pPos.getZ()+yoffset,
                    0.0D, 0.0D, 0.0D);
        pBlockEntity.playSound();
    }
}
