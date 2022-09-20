package me.clumsycat.furnitureexpanded.blocks;

import me.clumsycat.furnitureexpanded.registries.RegistryHandler;
import me.clumsycat.furnitureexpanded.util.ModShapes;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.explosion.Explosion;
import org.jetbrains.annotations.Nullable;

public class ShowerBox extends HorizontalFacingBlock {
    private static final EnumProperty<DoubleBlockHalf> half = Properties.DOUBLE_BLOCK_HALF;
    private static final DoubleBlockHalf _lower = DoubleBlockHalf.LOWER;
    private static final DoubleBlockHalf _upper = DoubleBlockHalf.UPPER;

    public ShowerBox() {
        super(Settings.of(Material.METAL)
                .strength(1f, 1f)
                .sounds(BlockSoundGroup.BASALT)
                .nonOpaque()
        );
        this.setDefaultState(this.getStateManager().getDefaultState().with(FACING, Direction.NORTH).with(half, _lower));
    }

    @Override
    public void onPlaced(World worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
        super.onPlaced(worldIn, pos, state, placer, stack);
        worldIn.setBlockState(pos.up(), RegistryHandler.SHOWER_BOX.getDefaultState().with(FACING, worldIn.getBlockState(pos).get(FACING)).with(half, _upper), 3);
    }

    @Override
    public void onBreak(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
        destroy(worldIn, pos, false);
        super.onBreak(worldIn, pos, state, player);
    }

    @Override
    public void afterBreak(World worldIn, PlayerEntity player, BlockPos pos, BlockState state, BlockEntity tileEntity, ItemStack stack) {
        destroy(worldIn, pos, true);
        super.afterBreak(worldIn, player, pos, state, tileEntity, stack);
    }

    @Override
    public void onDestroyedByExplosion(World worldIn, BlockPos pos, Explosion explosion) {
        destroy(worldIn, pos, true);
        super.onDestroyedByExplosion(worldIn, pos, explosion);
    }

    private void destroy(World worldIn, BlockPos pos, boolean shouldDrop) {
        BlockState state = worldIn.getBlockState(pos);
        if (worldIn.getBlockState(state.get(half) == _lower ? pos.up() : pos.down()).getBlock() == this)
            worldIn.removeBlock(state.get(half) == _lower ? pos.up() : pos.down(), false);
        if (shouldDrop) ItemScatterer.spawn(worldIn, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(this));
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView worldIn, BlockPos pos) {
        return worldIn.isAir(pos.up());
    }

    @Override
    public PistonBehavior getPistonBehavior(BlockState p_149656_1_) {
        return PistonBehavior.BLOCK;
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext context) {
        return (BlockState)this.getDefaultState().with(FACING, context.getPlayerFacing().getOpposite());
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, half);
    }

    @Override
    public BlockRenderType getRenderType(BlockState p_149645_1_) {
        return BlockRenderType.MODEL;
    }

    @Override
    public BlockState rotate(BlockState state, BlockRotation rot) {
        return state.with(FACING, rot.rotate(state.get(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, BlockMirror mirrorIn) {
        return state.rotate(mirrorIn.getRotation(state.get(FACING)));
    }

    @Override
    public float getAmbientOcclusionLightLevel(BlockState state, BlockView world, BlockPos pos) {
        return 0.2f;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView worldIn, BlockPos pos, ShapeContext context) {
        if (state.get(half) == _upper) {
            return switch (state.get(FACING)) {
                case NORTH -> ModShapes.SHOWER_BOX_TOP_N;
                case EAST -> ModShapes.SHOWER_BOX_TOP_E;
                case SOUTH -> ModShapes.SHOWER_BOX_TOP_S;
                default -> ModShapes.SHOWER_BOX_TOP_W;
            };
        } else {
            return switch (state.get(FACING)) {
                case NORTH -> ModShapes.SHOWER_BOX_BOTTOM_N;
                case EAST -> ModShapes.SHOWER_BOX_BOTTOM_E;
                case SOUTH -> ModShapes.SHOWER_BOX_BOTTOM_S;
                default -> ModShapes.SHOWER_BOX_BOTTOM_W;
            };
        }
    }
}
