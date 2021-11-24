package me.clumsycat.furnitureexpanded.blocks;

import me.clumsycat.furnitureexpanded.blocks.tileentities.FileCabinetTileEntity;
import me.clumsycat.furnitureexpanded.util.BSProperties;
import me.clumsycat.furnitureexpanded.util.ModShapes;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.PushReaction;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.stats.Stats;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.*;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ToolType;

import javax.annotation.Nullable;
import java.util.Random;

public class FileCabinet extends ContainerBlock {
    public static final DirectionProperty face = HorizontalBlock.FACING;
    public static final IntegerProperty type = BSProperties.TYPE_0_2;

    public FileCabinet() {
        super(Properties.of(Material.METAL)
                .strength(2f, 2f)
                .harvestTool(ToolType.PICKAXE)
                .sound(SoundType.METAL));
        this.registerDefaultState(this.stateDefinition.any().setValue(face, Direction.NORTH).setValue(type, 1));
    }

    @Override
    public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (worldIn.isClientSide) { return ActionResultType.SUCCESS;
        } else {
            TileEntity tileentity = worldIn.getBlockEntity(pos);
            if (state.getValue(type) != 0)
                if (worldIn.getBlockState(pos.below()).getBlock() == this)
                    if (worldIn.getBlockState(pos.below()).getValue(type) == 0)
                        tileentity = worldIn.getBlockEntity(pos.below());
            if (tileentity instanceof FileCabinetTileEntity) {
                player.openMenu((FileCabinetTileEntity) tileentity);
                player.awardStat(Stats.OPEN_BARREL);
            }
            return ActionResultType.CONSUME;
        }
    }

    @Override
    public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) {
        TileEntity tileentity = worldIn.getBlockEntity(pos);
        if (tileentity instanceof FileCabinetTileEntity) {
            ((FileCabinetTileEntity)tileentity).fileTick();
        }
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return state.getValue(type) == 0;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return state.getValue(type) == 0 ? newBlockEntity(world) : null;
    }

    @Nullable
    @Override
    public TileEntity newBlockEntity(IBlockReader worldIn) {
        return new FileCabinetTileEntity();
    }

    @Override
    public void playerWillDestroy(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
        BlockPos finalPos = state.getValue(type) == 0 ? pos : pos.below();
        boolean isValid = worldIn.getBlockState(finalPos).is(this) && worldIn.getBlockState(finalPos).hasTileEntity();
        if (isValid) dropContents(worldIn, state, pos, finalPos, worldIn.getBlockEntity(finalPos), !player.isCreative());
        super.playerWillDestroy(worldIn, pos, state, player);
    }

    @Override
    public void onBlockExploded(BlockState state, World worldIn, BlockPos pos, Explosion explosion) {
        BlockPos finalPos = state.getValue(type) == 0 ? pos : pos.below();
        boolean isValid = worldIn.getBlockState(finalPos).is(this) && worldIn.getBlockState(finalPos).hasTileEntity();
        if (isValid) dropContents(worldIn, state, pos, finalPos, worldIn.getBlockEntity(finalPos), true);
        super.onBlockExploded(state, worldIn, pos, explosion);
    }

    private void dropContents(World worldIn, BlockState state, BlockPos pos, BlockPos finalPos, TileEntity tileentity, boolean dropBlock) {
        if (worldIn.getBlockState(finalPos).is(this)) {
            FileCabinetTileEntity te = (FileCabinetTileEntity) tileentity;
            if (!te.isEmpty()) {
                CompoundNBT compoundnbt = te.saveToNbt(new CompoundNBT());
                NonNullList<ItemStack> invdrop = NonNullList.withSize(54, ItemStack.EMPTY);
                ItemStackHelper.loadAllItems(compoundnbt, invdrop);
                InventoryHelper.dropContents(worldIn, finalPos, invdrop);
            }
        }
        if (dropBlock) InventoryHelper.dropItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(this));
        if (worldIn.getBlockState(state.getValue(type) == 0 ? pos.above() : pos.below()).getBlock() == this.getBlock())
            worldIn.removeBlock(state.getValue(type) == 0 ? pos.above() : pos.below(), true);
    }

    @Override
    public void setPlacedBy(World worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
        if (stack.hasCustomHoverName()) {
            TileEntity tileentity = worldIn.getBlockEntity(pos);
            if (tileentity instanceof FileCabinetTileEntity) {
                ((FileCabinetTileEntity) tileentity).setCustomName(stack.getDisplayName());
            }
        }
        worldIn.setBlock(pos.above(), state.setValue(type, 1).setValue(face, state.getValue(face)), 3);
        super.setPlacedBy(worldIn, pos, state, placer, stack);
    }

    @Override
    public boolean canSurvive(BlockState state, IWorldReader worldIn, BlockPos pos) {
        return worldIn.getBlockState(pos.above()).is(Blocks.AIR);
    }

    @Override
    public PushReaction getPistonPushReaction(BlockState state) {
        return PushReaction.BLOCK;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.defaultBlockState().setValue(type, 0).setValue(face, context.getHorizontalDirection());
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rot) {
        return state.setValue(face, rot.rotate(state.getValue(face)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return state.rotate(mirrorIn.getRotation(state.getValue(face)));
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(type, face);
    }

    @Override
    public float getShadeBrightness(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return 0.5f;
    }

    @Override
    public BlockRenderType getRenderShape(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        if (state.getValue(type) == 0) {
            switch (state.getValue(face)) {
                case NORTH: return ModShapes.FILECABINET_N;
                case EAST: return ModShapes.FILECABINET_E;
                case SOUTH: return ModShapes.FILECABINET_S;
                default: return ModShapes.FILECABINET_W;
            }
        } else if (state.getValue(type) == 2) {
            switch (state.getValue(face)) {
                case NORTH: return ModShapes.FILECABINET_OPEN_N;
                case EAST: return ModShapes.FILECABINET_OPEN_E;
                case SOUTH: return ModShapes.FILECABINET_OPEN_S;
                default: return ModShapes.FILECABINET_OPEN_W;
            }
        } else {
            switch (state.getValue(face)) {
                case NORTH: return ModShapes.FILECABINET_CLOSED_N;
                case EAST: return ModShapes.FILECABINET_CLOSED_E;
                case SOUTH: return ModShapes.FILECABINET_CLOSED_S;
                default: return ModShapes.FILECABINET_CLOSED_W;
            }
        }
    }
}
