package me.clumsycat.furnitureexpanded.blocks;

import com.mojang.serialization.MapCodec;
import me.clumsycat.furnitureexpanded.blocks.tileentities.AlarmClockTileEntity;
import me.clumsycat.furnitureexpanded.util.BSProperties;
import me.clumsycat.furnitureexpanded.util.ModShapes;
import me.clumsycat.furnitureexpanded.util.enums.FloorDepth;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class AlarmClock extends BaseEntityBlock {
    public static final DirectionProperty face = HorizontalDirectionalBlock.FACING;
    public static final EnumProperty<FloorDepth> depth = BSProperties.FLOOR_DEPTH;
    public static final BooleanProperty isPowered = BlockStateProperties.POWERED;

    public AlarmClock(BlockBehaviour.Properties properties) {
        super(BlockBehaviour.Properties.of()
                .strength(2f, 2f)
                .sound(SoundType.METAL)
                .pushReaction(PushReaction.BLOCK)
        );
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(face, Direction.NORTH)
                .setValue(depth, FloorDepth.NORMAL)
                .setValue(isPowered, false));
    }

    public AlarmClock() {
        this(null);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return simpleCodec(AlarmClock::new);
    }

    @Override
    public InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult blockHitResult) {
        if (level.isClientSide) return InteractionResult.SUCCESS;
        boolean p = state.getValue(isPowered);
        level.setBlockAndUpdate(pos, state.setValue(isPowered, !p));
        level.playSound(null, pos, p ? SoundEvents.STONE_BUTTON_CLICK_OFF : SoundEvents.STONE_BUTTON_CLICK_ON, SoundSource.BLOCKS, 1.5f, level.random.nextFloat() * 0.1F + 0.9F + (p ? -0.9F : 0.25F));
        return InteractionResult.CONSUME;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new AlarmClockTileEntity(pos, state);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader worldIn, BlockPos pos) {
        return !worldIn.isEmptyBlock(pos.below());
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        FloorDepth _depth = FloorDepth.NORMAL;
        Direction nearest = context.getHorizontalDirection();
        Direction direction = nearest.getOpposite();
        double axis = context.getClickLocation().get(direction.getAxis());
        double posX = axis - context.getClickedPos().get(direction.getAxis());
        int pointer = direction.getNormal().get(direction.getAxis());
        if (pointer < 0) posX = context.getClickedPos().get(direction.getAxis()) - axis;

        if (posX >= 0 && posX <= 0.33 || posX <= -0.67) _depth = FloorDepth.FAR;
        else if (posX >= 0.67 || posX >= -0.33 && posX <= 0) _depth = FloorDepth.NEAR;

        return this.defaultBlockState().setValue(face, context.getHorizontalDirection().getOpposite()).setValue(depth, _depth);
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
        builder.add(face).add(depth).add(isPowered);
    }

    @Override
    public float getShadeBrightness(BlockState state, BlockGetter worldIn, BlockPos pos) {
        return 0.75f;
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        if (state.getValue(depth) == FloorDepth.FAR) {
            return switch (state.getValue(face)) {
                case NORTH -> ModShapes.ALARM_CLOCK_FAR_N;
                case EAST -> ModShapes.ALARM_CLOCK_FAR_E;
                case SOUTH -> ModShapes.ALARM_CLOCK_FAR_S;
                default -> ModShapes.ALARM_CLOCK_FAR_W;
            };
        } else if (state.getValue(depth) == FloorDepth.NEAR) {
            return switch (state.getValue(face)) {
                case NORTH -> ModShapes.ALARM_CLOCK_NEAR_N;
                case EAST -> ModShapes.ALARM_CLOCK_NEAR_E;
                case SOUTH -> ModShapes.ALARM_CLOCK_NEAR_S;
                default -> ModShapes.ALARM_CLOCK_NEAR_W;
            };
        } else {
            return switch (state.getValue(face)) {
                case NORTH -> ModShapes.ALARM_CLOCK_NORMAL_N;
                case EAST -> ModShapes.ALARM_CLOCK_NORMAL_E;
                case SOUTH -> ModShapes.ALARM_CLOCK_NORMAL_S;
                default -> ModShapes.ALARM_CLOCK_NORMAL_W;
            };
        }
    }

}
