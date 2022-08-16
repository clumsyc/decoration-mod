package me.clumsycat.furnitureexpanded.blocks;

import me.clumsycat.furnitureexpanded.registries.RegistryHandler;
import me.clumsycat.furnitureexpanded.util.ModShapes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.Containers;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

@SuppressWarnings("NullableProblems")
public class ShowerBox extends Block {
    private static final DirectionProperty face = HorizontalDirectionalBlock.FACING;
    public static final EnumProperty<DoubleBlockHalf> half = BlockStateProperties.DOUBLE_BLOCK_HALF;
    DoubleBlockHalf _lower = DoubleBlockHalf.LOWER;
    DoubleBlockHalf _upper = DoubleBlockHalf.UPPER;

    public ShowerBox() {
        super(Properties.of(Material.METAL)
                .strength(1f, 1f)
                .sound(SoundType.BASALT)
                .noOcclusion()
        );
        this.registerDefaultState(this.getStateDefinition().any().setValue(face, Direction.NORTH).setValue(half, _lower));
    }

    @Override
    public void setPlacedBy(Level worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
        super.setPlacedBy(worldIn, pos, state, placer, stack);
        worldIn.setBlockAndUpdate(pos.above(), RegistryHandler.SHOWER_BOX.get().defaultBlockState().setValue(half, _upper).setValue(face, worldIn.getBlockState(pos).getValue(face)));
    }

    @Override
    public void playerWillDestroy(Level worldIn, BlockPos pos, BlockState state, Player player) {
        destroy(worldIn, state, pos, false);
        super.playerWillDestroy(worldIn, pos, state, player);
    }

    @Override
    public void playerDestroy(Level worldIn, Player player, BlockPos pos, BlockState state, @Nullable BlockEntity tileEntity, ItemStack stack) {
        destroy(worldIn, state, pos, true);
        super.playerDestroy(worldIn, player, pos, state, tileEntity, stack);
    }

    @Override
    public void onBlockExploded(BlockState state, Level worldIn, BlockPos pos, Explosion explosion) {
        destroy(worldIn, state, pos, true);
        super.onBlockExploded(state, worldIn, pos, explosion);
    }

    private void destroy(Level worldIn, BlockState state, BlockPos pos, boolean shouldDrop) {
        if (worldIn.getBlockState(state.getValue(half) == _lower ? pos.above() : pos.below()).getBlock() == this)
            worldIn.removeBlock(state.getValue(half) == _lower ? pos.above() : pos.below(), false);
        if (shouldDrop) Containers.dropItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(this));
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader worldIn, BlockPos pos) {
        return worldIn.isEmptyBlock(pos.above());
    }

    @Override
    public PushReaction getPistonPushReaction(BlockState p_149656_1_) {
        return PushReaction.BLOCK;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
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
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(half, face);
    }

    @Override
    public RenderShape getRenderShape(BlockState p_149645_1_) {
        return RenderShape.MODEL;
    }

    @Override
    public float getShadeBrightness(BlockState state, BlockGetter worldIn, BlockPos pos) {
        return 0.2f;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        if (state.getValue(half) == _upper) {
            return switch (state.getValue(face)) {
                case NORTH -> ModShapes.SHOWER_BOX_TOP_N;
                case EAST -> ModShapes.SHOWER_BOX_TOP_E;
                case SOUTH -> ModShapes.SHOWER_BOX_TOP_S;
                default -> ModShapes.SHOWER_BOX_TOP_W;
            };
        } else {
            return switch (state.getValue(face)) {
                case NORTH -> ModShapes.SHOWER_BOX_BOTTOM_N;
                case EAST -> ModShapes.SHOWER_BOX_BOTTOM_E;
                case SOUTH -> ModShapes.SHOWER_BOX_BOTTOM_S;
                default -> ModShapes.SHOWER_BOX_BOTTOM_W;
            };
        }
    }
}
