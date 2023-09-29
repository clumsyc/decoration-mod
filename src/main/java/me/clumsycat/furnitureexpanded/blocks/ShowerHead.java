package me.clumsycat.furnitureexpanded.blocks;

import me.clumsycat.furnitureexpanded.blocks.tileentities.ShowerHeadTileEntity;
import me.clumsycat.furnitureexpanded.registries.RegistryHandler;
import me.clumsycat.furnitureexpanded.util.ModShapes;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
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

public class ShowerHead extends BlockWithEntity {
    private static final DirectionProperty face = HorizontalFacingBlock.FACING;
    private static final BooleanProperty enabled = Properties.ENABLED;

    public ShowerHead() {
        super(Settings.of(Material.AGGREGATE)
                .strength(1f, 2f)
                .sounds(BlockSoundGroup.STONE)
        );
        this.setDefaultState(this.getStateManager().getDefaultState().with(face, Direction.NORTH).with(enabled, false));
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if(player.getMainHandStack().isEmpty())
            setShowerState(world, pos, !state.get(enabled));
        return world.isClient ? ActionResult.SUCCESS : ActionResult.CONSUME;
    }

    public static void setShowerState(World level, BlockPos pos, boolean isEnabled) {
        BlockState state = level.getBlockState(pos);
        level.setBlockState(pos, state.with(enabled, isEnabled), Block.NOTIFY_ALL);
        level.playSound(null, pos, SoundEvents.BLOCK_LANTERN_BREAK, SoundCategory.BLOCKS, 0.5f, 2.5f);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        if (world.isClient) {
            return state.get(enabled) ? checkType(type, RegistryHandler.SHOWER_HEAD_TE, ShowerHeadTileEntity::particleTick) : null;
        }
        return null;
    }

    public BlockEntity createBlockEntity(BlockPos pPos, BlockState pState) {
        return new ShowerHeadTileEntity(pPos, pState);
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return !world.isAir(pos.offset(state.get(face).getOpposite()));
    }

    @Override
    public void afterBreak(World worldIn, PlayerEntity player, BlockPos pos, BlockState state, BlockEntity tileentity, ItemStack stack) {
        ItemScatterer.spawn(worldIn, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(this));
        super.afterBreak(worldIn, player, pos, state, tileentity, stack);
    }

    @Override
    public void onDestroyedByExplosion(World worldIn, BlockPos pos, Explosion explosion) {
        ItemScatterer.spawn(worldIn, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(this));
        super.onDestroyedByExplosion(worldIn, pos, explosion);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return switch (state.get(face)) {
            case NORTH -> ModShapes.SHOWER_HEAD_N;
            case EAST -> ModShapes.SHOWER_HEAD_E;
            case SOUTH -> ModShapes.SHOWER_HEAD_S;
            default -> ModShapes.SHOWER_HEAD_W;
        };
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext context) {
        return this.getDefaultState().with(face, context.getHorizontalPlayerFacing().getOpposite());
    }

    @Override
    public BlockState rotate(BlockState state, BlockRotation rot) {
        return state.with(face, rot.rotate(state.get(face)));
    }

    @Override
    public BlockState mirror(BlockState state, BlockMirror mirrorIn) {
        return state.rotate(mirrorIn.getRotation(state.get(face)));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(face, enabled);
    }

    @Override
    public float getAmbientOcclusionLightLevel(BlockState state, BlockView world, BlockPos pos) { return 0.7f; }
}
