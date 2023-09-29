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
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.*;
import net.minecraft.util.hit.BlockHitResult;
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
    public static final BooleanProperty open = Properties.OPEN;
    public static final BooleanProperty attached = Properties.ATTACHED;
    private static final BooleanProperty enabled = Properties.ENABLED;

    public ShowerBox() {
        super(Settings.create()
                .strength(1f, 1f)
                .sounds(BlockSoundGroup.BASALT)
                .nonOpaque()
                .pistonBehavior(PistonBehavior.BLOCK)
        );
        this.setDefaultState(this.getStateManager().getDefaultState().with(FACING, Direction.NORTH).with(half, _lower).with(open, false).with(attached, false));
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        double distSqr = pos.getSquaredDistance(player.getPos());
        Direction.Axis directionAxis = state.get(FACING).getAxis();
        double axis = pos.getComponentAlongAxis(directionAxis);
        double posZ = axis - hit.getPos().getComponentAlongAxis(directionAxis);
        int pointer = state.get(FACING).getVector().getComponentAlongAxis(directionAxis);
        if (pointer < 0) posZ = hit.getPos().getComponentAlongAxis(directionAxis) - axis;
        else posZ = 1-(posZ - posZ*2);
        if (posZ >= 0.9 && posZ < 1 && distSqr <= 2.1 && hit.getSide() == state.get(FACING) && state.get(half) == _upper) {
            BlockState abvState = world.getBlockState(pos.up());
            if (abvState.isOf(RegistryHandler.SHOWER_HEAD))
                ShowerHead.setShowerState(world, pos.up(), !abvState.get(enabled));
        } else {
            boolean newOpenState = !state.get(open);
            BlockPos adjPos = state.get(half) == _lower ? pos.up() : pos.down();
            BlockState adjState = world.getBlockState(adjPos);
            if (adjState.isOf(this)) {
                if (state.get(half) != adjState.get(half)) {
                    world.setBlockState(pos, state.with(open, newOpenState), Block.NOTIFY_ALL);
                    world.setBlockState(adjPos, adjState.with(open, newOpenState), Block.NOTIFY_ALL);
                    world.playSound(null, pos, newOpenState ? SoundEvents.BLOCK_WOODEN_DOOR_OPEN : SoundEvents.BLOCK_WOODEN_DOOR_CLOSE, SoundCategory.BLOCKS, 0.5f, 1.0f);
                }
            }
        }
        updateAttachments(state, world, pos);
        return (world.isClient) ? ActionResult.SUCCESS : ActionResult.CONSUME;
    }

    public void updateAttachments(BlockState state, World level, BlockPos pos) {
        int sh = state.get(half) == _upper ? 1 : state.get(half) == _lower ? 2 : -1;
        if (sh > 0) {
            BlockState upperState = level.getBlockState(pos.up(sh-1));
            if (upperState.isOf(this)) {
                BlockState shState = level.getBlockState(pos.up(sh));
                boolean isAttached = shState.isOf(RegistryHandler.SHOWER_HEAD);
                if (upperState.get(attached) != isAttached)
                    level.setBlockState(pos.up(sh-1), upperState.with(attached, isAttached), Block.NOTIFY_ALL);
            }
        }
    }

    @Override
    public void onPlaced(World worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
        super.onPlaced(worldIn, pos, state, placer, stack);
        worldIn.setBlockState(pos.up(), RegistryHandler.SHOWER_BOX.getDefaultState()
                .with(FACING, worldIn.getBlockState(pos).get(FACING))
                .with(half, _upper)
                .with(attached, worldIn.getBlockState(pos.up(2)).isOf(RegistryHandler.SHOWER_HEAD)), Block.NOTIFY_ALL);
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

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext context) {
        return this.getDefaultState().with(half, _lower).with(FACING, context.getHorizontalPlayerFacing().getOpposite()).with(open, false).with(attached, false);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, half, open, attached);
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
            if (state.get(open)) {
                return switch (state.get(FACING)) {
                    case NORTH -> ModShapes.SHOWER_BOX_TOP_OPEN_N;
                    case EAST -> ModShapes.SHOWER_BOX_TOP_OPEN_E;
                    case SOUTH -> ModShapes.SHOWER_BOX_TOP_OPEN_S;
                    default -> ModShapes.SHOWER_BOX_TOP_OPEN_W;
                };
            } else {
                return ModShapes.SHOWER_BOX_TOP_CLOSED;
            }
        } else {
            if (state.get(open)) {
                return switch (state.get(FACING)) {
                    case NORTH -> ModShapes.SHOWER_BOX_BOTTOM_OPEN_N;
                    case EAST -> ModShapes.SHOWER_BOX_BOTTOM_OPEN_E;
                    case SOUTH -> ModShapes.SHOWER_BOX_BOTTOM_OPEN_S;
                    default -> ModShapes.SHOWER_BOX_BOTTOM_OPEN_W;
                };
            } else {
                return ModShapes.SHOWER_BOX_BOTTOM_CLOSED;
            }
        }
    }
}
