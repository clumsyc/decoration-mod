package me.clumsycat.furnitureexpanded.blocks.tileentities;

import me.clumsycat.furnitureexpanded.registries.RegistryHandler;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;

public class ClockSignTileEntity extends BlockEntity {
    public ClockSignTileEntity(BlockPos pPos, BlockState pBlockState) {
        super(RegistryHandler.CLOCK_SIGN_TE, pPos, pBlockState);
    }

}
