package me.clumsycat.furnitureexpanded.blocks;

import me.clumsycat.furnitureexpanded.util.BSProperties;
import me.clumsycat.furnitureexpanded.util.ModShapes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BathroomSink extends Block {
    private static final DirectionProperty face = HorizontalDirectionalBlock.FACING;
    public static final IntegerProperty type = BSProperties.TYPE_0_1;

    public BathroomSink() {
        super(Block.Properties.of()
                .strength(1f, 2f)
                .sound(SoundType.STONE)
        );
        this.registerDefaultState(this.stateDefinition.any().setValue(face, Direction.NORTH).setValue(type, 0));
    }

    @Override
    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        if(player.getMainHandItem().isEmpty()) {
            worldIn.setBlockAndUpdate(pos, state.setValue(type, state.getValue(type) == 0 ? 1 : 0));
            worldIn.playSound(player, pos, SoundType.LANTERN.getBreakSound(), SoundSource.BLOCKS, 0.5f, 0.5f);
        }
        return worldIn.isClientSide ? InteractionResult.SUCCESS : InteractionResult.CONSUME;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return switch (state.getValue(face)) {
            case NORTH -> ModShapes.BATHROOM_SINK_N;
            case EAST -> ModShapes.BATHROOM_SINK_E;
            case SOUTH -> ModShapes.BATHROOM_SINK_S;
            default -> ModShapes.BATHROOM_SINK_W;
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
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(face, type);
    }

    @Override
    public float getShadeBrightness(BlockState state, BlockGetter worldIn, BlockPos pos) { return 0.5f; }
}
