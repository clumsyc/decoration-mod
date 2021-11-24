package me.clumsycat.furnitureexpanded.blocks;

import me.clumsycat.furnitureexpanded.registries.RegistryHandler;
import me.clumsycat.furnitureexpanded.util.ModShapes;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.PushReaction;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.*;
import net.minecraftforge.common.ToolType;

import javax.annotation.Nullable;

public class ShowerBox extends Block { // DoublePlantBlock
    private static final DirectionProperty face = HorizontalBlock.FACING;
    public static final EnumProperty<DoubleBlockHalf> half = BlockStateProperties.DOUBLE_BLOCK_HALF;
    DoubleBlockHalf _lower = DoubleBlockHalf.LOWER;
    DoubleBlockHalf _upper = DoubleBlockHalf.UPPER;

    public ShowerBox() { //TODO: Import new model
        super(Properties.of(Material.METAL)
                .strength(1f, 1f)
                .harvestTool(ToolType.PICKAXE)
                .sound(SoundType.BASALT)
                .noOcclusion()
        );
        this.registerDefaultState(this.getStateDefinition().any().setValue(face, Direction.NORTH).setValue(half, _lower));
    }

    @Override
    public void setPlacedBy(World worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
        super.setPlacedBy(worldIn, pos, state, placer, stack);
        worldIn.setBlockAndUpdate(pos.above(), RegistryHandler.SHOWER_BOX.get().defaultBlockState().setValue(half, _upper).setValue(face, worldIn.getBlockState(pos).getBlockState().getValue(face)));
    }

    @Override
    public void playerWillDestroy(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
        destroy(worldIn, state, pos, false);
        super.playerWillDestroy(worldIn, pos, state, player);
    }

    @Override
    public void playerDestroy(World worldIn, PlayerEntity player, BlockPos pos, BlockState state, @Nullable TileEntity tileEntity, ItemStack stack) {
        destroy(worldIn, state, pos, true);
        super.playerDestroy(worldIn, player, pos, state, tileEntity, stack);
    }

    @Override
    public void onBlockExploded(BlockState state, World worldIn, BlockPos pos, Explosion explosion) {
        destroy(worldIn, state, pos, true);
        super.onBlockExploded(state, worldIn, pos, explosion);
    }

    private void destroy(World worldIn, BlockState state, BlockPos pos, boolean shouldDrop) {
        if (worldIn.getBlockState(state.getValue(half) == _lower ? pos.above() : pos.below()).getBlock() == this)
            worldIn.removeBlock(state.getValue(half) == _lower ? pos.above() : pos.below(), false);
        if (shouldDrop) InventoryHelper.dropItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(this));
    }

    @Override
    public boolean canSurvive(BlockState state, IWorldReader worldIn, BlockPos pos) {
        return worldIn.isEmptyBlock(pos.above());
    }

    @Override
    public PushReaction getPistonPushReaction(BlockState p_149656_1_) {
        return PushReaction.BLOCK;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.defaultBlockState().setValue(half, _lower).setValue(face, context.getHorizontalDirection().getOpposite());
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
        builder.add(half, face);
    }

    @Override
    public BlockRenderType getRenderShape(BlockState p_149645_1_) {
        return BlockRenderType.MODEL;
    }


    @Override
    public float getShadeBrightness(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return 0.2f;
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        if (state.getValue(half) == _upper) {
            switch (state.getValue(face)) {
                case NORTH: return ModShapes.SHOWER_BOX_TOP_N;
                case EAST: return ModShapes.SHOWER_BOX_TOP_E;
                case SOUTH: return ModShapes.SHOWER_BOX_TOP_S;
                default: return ModShapes.SHOWER_BOX_TOP_W;
            }
        } else {
            switch (state.getValue(face)) {
                case NORTH: return ModShapes.SHOWER_BOX_BOTTOM_N;
                case EAST: return ModShapes.SHOWER_BOX_BOTTOM_E;
                case SOUTH: return ModShapes.SHOWER_BOX_BOTTOM_S;
                default: return ModShapes.SHOWER_BOX_BOTTOM_W;
            }
        }
    }
}
