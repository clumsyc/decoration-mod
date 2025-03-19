package me.clumsycat.furnitureexpanded.blocks.tileentities;

import me.clumsycat.furnitureexpanded.ExpandedMod;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class ModernBedTileEntity extends BlockEntity {
    private DyeColor color;

    public ModernBedTileEntity(BlockPos blockPos, BlockState blockState) {
        super(ExpandedMod.MODERN_BED_TE.get(), blockPos, blockState);
        this.color = ((BedBlock)blockState.getBlock()).getColor();
    }

    public ModernBedTileEntity(BlockPos blockPos, BlockState blockState, DyeColor dyeColor) {
        super(ExpandedMod.MODERN_BED_TE.get(), blockPos, blockState);
        this.color = dyeColor;
    }

    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    public DyeColor getColor() {
        return this.color;
    }

    public void setColor(DyeColor dyeColor) {
        this.color = dyeColor;
    }
}