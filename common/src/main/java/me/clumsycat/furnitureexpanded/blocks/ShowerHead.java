package me.clumsycat.furnitureexpanded.blocks;

import me.clumsycat.furnitureexpanded.ExpandedMod;
import me.clumsycat.furnitureexpanded.blocks.tileentities.ShowerHeadTileEntity;
import me.clumsycat.furnitureexpanded.util.ModShapes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class ShowerHead extends BaseEntityBlock {
    private static final DirectionProperty face = HorizontalDirectionalBlock.FACING;
    private static final BooleanProperty enabled = BlockStateProperties.ENABLED;

    public ShowerHead() {
        super(Block.Properties.of()
                .strength(1f, 2f)
                .sound(SoundType.STONE)
                .pushReaction(PushReaction.BLOCK)
        );
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(face, Direction.NORTH)
                .setValue(enabled, Boolean.FALSE));
    }

    @Override
    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        if(player.getMainHandItem().isEmpty())
            setShowerState(worldIn, pos, !state.getValue(enabled));
        return worldIn.isClientSide ? InteractionResult.SUCCESS : InteractionResult.CONSUME;
    }

    public static void setShowerState(Level level, BlockPos pos, boolean isEnabled) {
        BlockState state = level.getBlockState(pos);
        level.setBlockAndUpdate(pos, state.setValue(enabled, isEnabled));
        level.playSound(null, pos, SoundType.LANTERN.getBreakSound(), SoundSource.BLOCKS, 0.5f, 2.5f);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        if (pLevel.isClientSide) {
            return pState.getValue(enabled) ? createTickerHelper(pBlockEntityType, ExpandedMod.SHOWER_HEAD_TE.get(), ShowerHeadTileEntity::particleTick) : null;
        }
        return null;
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader reader, BlockPos pos) {
        Direction direction = state.getValue(face).getOpposite();
        BlockPos relative = pos.relative(direction);
        return !reader.isEmptyBlock(relative);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return switch (state.getValue(face)) {
            case NORTH -> ModShapes.SHOWER_HEAD_N;
            case EAST -> ModShapes.SHOWER_HEAD_E;
            case SOUTH -> ModShapes.SHOWER_HEAD_S;
            default -> ModShapes.SHOWER_HEAD_W;
        };
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(face, context.getHorizontalDirection().getOpposite());
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
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new ShowerHeadTileEntity(pPos, pState);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(face, enabled);
    }

    @Override
    public float getShadeBrightness(BlockState state, BlockGetter worldIn, BlockPos pos) { return 0.7f; }
}
