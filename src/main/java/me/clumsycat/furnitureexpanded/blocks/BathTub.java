package me.clumsycat.furnitureexpanded.blocks;

import me.clumsycat.furnitureexpanded.util.BSProperties;
import me.clumsycat.furnitureexpanded.util.ModShapes;
import me.clumsycat.furnitureexpanded.util.SeatHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
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
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

public class BathTub extends Block {
    public static final DirectionProperty face = HorizontalDirectionalBlock.FACING;
    public static final IntegerProperty type = BSProperties.TYPE_0_1;
    public static final BooleanProperty main = BSProperties.MAIN;

    public BathTub() {
        super(BlockBehaviour.Properties.of(Material.STONE)
                .strength(2f, 2f)
                .sound(SoundType.DEEPSLATE_TILES));
        this.registerDefaultState(this.stateDefinition.any().setValue(face, Direction.NORTH).setValue(main, true).setValue(type, 0));
    }

    @Override
    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        if(player.getMainHandItem().isEmpty()) {
            double distSqr = pos.distSqr(BlockPos.containing(player.position().x, player.position().y, player.position().z));
            if (distSqr < 2 && !player.isCrouching()) {
                Direction direction = state.getValue(face);
                SeatHandler.create(worldIn, pos, player, new Vec3(0, -0.33, 0).relative(direction, -0.25));
            } else {
                BlockPos adjPos = getAdjBlockOrNull(worldIn, pos, state.getValue(face));
                if (adjPos != null) {
                    BlockState adjState = worldIn.getBlockState(adjPos);
                    int _type = state.getValue(type) == 0 ? 1 : 0;
                    worldIn.setBlockAndUpdate(adjPos, adjState.setValue(type, _type));
                    worldIn.setBlockAndUpdate(pos, state.setValue(type, _type));

                    worldIn.playSound(player, pos, SoundType.LANTERN.getBreakSound(), SoundSource.BLOCKS, 0.5f, 0.5f);
                }
            }
        }
        return worldIn.isClientSide ? InteractionResult.SUCCESS : InteractionResult.CONSUME;
    }

    private BlockPos getAdjBlockOrNull(Level level, BlockPos pos, Direction direction) {
        BlockState adjState = level.getBlockState(pos.relative(direction));
        if (adjState.is(this)) {
            if (adjState.getValue(face).getOpposite() == direction) return pos.relative(direction);
        }
        return null;
    }

    private void destroyAdjPart(Level level, BlockPos pos, Direction direction) {
        BlockPos adjPos = getAdjBlockOrNull(level, pos, direction);
        if (adjPos != null) level.removeBlock(adjPos, false);
    }

    @Override
    public void setPlacedBy(Level worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        super.setPlacedBy(worldIn, pos, state, placer, stack);
        Direction direction = state.getValue(face);
        worldIn.setBlockAndUpdate(pos.relative(direction), state.setValue(face, direction.getOpposite()).setValue(main, false));
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader worldIn, BlockPos pos) {
        return worldIn.isEmptyBlock(pos.relative(state.getValue(face)));
    }

    @Override
    public void playerWillDestroy(Level worldIn, BlockPos pos, BlockState state, Player player) {
        destroyAdjPart(worldIn, pos, state.getValue(face));
        super.playerWillDestroy(worldIn, pos, state, player);
    }

    @Override
    public void playerDestroy(Level worldIn, Player player, BlockPos pos, BlockState state, @Nullable BlockEntity tileentity, ItemStack stack) {
        Containers.dropItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(this));
    }

    @Override
    public void onBlockExploded(BlockState state, Level worldIn, BlockPos pos, Explosion explosion) {
        Containers.dropItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(), state.getBlock().asItem().getDefaultInstance());
        destroyAdjPart(worldIn, pos, state.getValue(face));
        super.onBlockExploded(state, worldIn, pos, explosion);
    }

    @Override
    public PushReaction getPistonPushReaction(BlockState p_149656_1_) {
        return PushReaction.BLOCK;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(face, context.getHorizontalDirection());
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
        builder.add(face, main, type);
    }

    @Override
    public float getShadeBrightness(BlockState state, BlockGetter worldIn, BlockPos pos) {
        return 0.5f;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return switch (state.getValue(face)) {
            case NORTH -> state.getValue(main) ? ModShapes.BATHTUB_TOP_N : ModShapes.BATHTUB_BOTTOM_N;
            case EAST -> state.getValue(main) ? ModShapes.BATHTUB_TOP_E : ModShapes.BATHTUB_BOTTOM_E;
            case SOUTH -> state.getValue(main) ? ModShapes.BATHTUB_TOP_S : ModShapes.BATHTUB_BOTTOM_S;
            default -> state.getValue(main) ? ModShapes.BATHTUB_TOP_W : ModShapes.BATHTUB_BOTTOM_W;
        };
    }

}
