package me.clumsycat.furnitureexpanded.blocks;

import me.clumsycat.furnitureexpanded.util.BSProperties;
import me.clumsycat.furnitureexpanded.util.ModShapes;
import me.clumsycat.furnitureexpanded.util.SeatHandler;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.*;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.explosion.Explosion;

public class BathTub extends Block {
    public static final DirectionProperty face = HorizontalFacingBlock.FACING;
    public static final IntProperty type = BSProperties.TYPE_0_1;
    public static final BooleanProperty main = BSProperties.MAIN;

    public BathTub() {
        super(FabricBlockSettings.create()
                .strength(1f, 2f)
                .sounds(BlockSoundGroup.STONE)
                .nonOpaque()
        );
        this.setDefaultState(this.getStateManager().getDefaultState().with(face, Direction.NORTH).with(type, 0).with(main, true));
    }

    @Override
    public ActionResult onUse(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockHitResult hit) {
        if(player.getMainHandStack().isEmpty()) {
            double distSqr = pos.getSquaredDistance(new Vec3d(player.getX(), player.getY(), player.getZ()));
            if (distSqr < 2 && !player.isSneaking()) {
                Direction direction = state.get(face);
                SeatHandler.create(worldIn, pos, player, new Vec3d(0, -0.6, 0).offset(direction, -0.25));
            } else {
                BlockPos adjPos = getAdjBlockOrNull(worldIn, pos, state.get(face));
                if (adjPos != null) {
                    BlockState adjState = worldIn.getBlockState(adjPos);
                    int _type = state.get(type) == 0 ? 1 : 0;
                    worldIn.setBlockState(adjPos, adjState.with(type, _type), Block.NOTIFY_ALL);
                    worldIn.setBlockState(pos, state.with(type, _type), Block.NOTIFY_ALL);

                    worldIn.playSound(player, pos, SoundEvents.BLOCK_LANTERN_BREAK, SoundCategory.BLOCKS, 0.5f, 0.5f);
                }
            }
        }
        return worldIn.isClient ? ActionResult.SUCCESS : ActionResult.CONSUME;
    }

    private BlockPos getAdjBlockOrNull(World level, BlockPos pos, Direction direction) {
        BlockState adjState = level.getBlockState(pos.offset(direction));
        if (adjState.isOf(this)) {
            if (adjState.get(face).getOpposite() == direction) return pos.offset(direction);
        }
        return null;
    }

    private void destroyAdjPart(World level, BlockPos pos, Direction direction) {
        BlockPos adjPos = getAdjBlockOrNull(level, pos, direction);
        if (adjPos != null) level.removeBlock(adjPos, false);
    }

    @Override
    public void onPlaced(World worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
        super.onPlaced(worldIn, pos, state, placer, stack);
        Direction direction = state.get(face);
        worldIn.setBlockState(pos.offset(direction), state.with(face, direction.getOpposite()).with(main, false), Block.NOTIFY_ALL);
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView reader, BlockPos pos) {
        return reader.isAir(pos.offset(state.get(face)));
    }

    @Override
    public void onBreak(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
        destroyAdjPart(worldIn, pos, state.get(face));
        if (!player.isCreative()) ItemScatterer.spawn(worldIn, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(this));
        super.onBreak(worldIn, pos, state, player);
    }

    @Override
    public void onDestroyedByExplosion(World worldIn, BlockPos pos, Explosion explosion) {
        destroyAdjPart(worldIn, pos, worldIn.getBlockState(pos).get(face));
        ItemScatterer.spawn(worldIn, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(this));
        super.onDestroyedByExplosion(worldIn, pos, explosion);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView worldIn, BlockPos pos, ShapeContext context) {
        return switch (state.get(face)) {
            case NORTH -> state.get(main) ? ModShapes.BATHTUB_TOP_N : ModShapes.BATHTUB_BOTTOM_N;
            case EAST -> state.get(main) ? ModShapes.BATHTUB_TOP_E : ModShapes.BATHTUB_BOTTOM_E;
            case SOUTH -> state.get(main) ? ModShapes.BATHTUB_TOP_S : ModShapes.BATHTUB_BOTTOM_S;
            default -> state.get(main) ? ModShapes.BATHTUB_TOP_W : ModShapes.BATHTUB_BOTTOM_W;
        };
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext context) {
        return this.getDefaultState().with(face, context.getHorizontalPlayerFacing());
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
        builder.add(face, main, type);
    }

    @Override
    public float getAmbientOcclusionLightLevel(BlockState state, BlockView world, BlockPos pos) { return 0.5f; }
}
