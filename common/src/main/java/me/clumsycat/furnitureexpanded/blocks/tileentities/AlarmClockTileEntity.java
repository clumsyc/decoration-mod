package me.clumsycat.furnitureexpanded.blocks.tileentities;

import me.clumsycat.furnitureexpanded.ExpandedMod;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class AlarmClockTileEntity extends BlockEntity {
    public AlarmClockTileEntity(BlockPos pPos, BlockState pBlockState) {
        super(ExpandedMod.ALARM_CLOCK_TE.get(), pPos, pBlockState);
    }
}
