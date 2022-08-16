package me.clumsycat.furnitureexpanded.blocks.tileentities;

import me.clumsycat.furnitureexpanded.registries.RegistryHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class ClockSignTileEntity extends BlockEntity {
    public ClockSignTileEntity(BlockPos pPos, BlockState pBlockState) {
        super(RegistryHandler.CLOCK_SIGN_TE.get(), pPos, pBlockState);
    }

}
