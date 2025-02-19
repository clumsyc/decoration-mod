package me.clumsycat.furnitureexpanded.blocks;

import me.clumsycat.furnitureexpanded.util.BSProperties;
import me.clumsycat.furnitureexpanded.util.ModShapes;
import me.clumsycat.furnitureexpanded.util.enums.FloorDepth;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BedsideTable extends Block {
    public static final DirectionProperty face = HorizontalDirectionalBlock.FACING;
    public static final EnumProperty<FloorDepth> depth = BSProperties.FLOOR_DEPTH;

    public BedsideTable() {
        super(Properties.of()
                .strength(2f, 2f)
                .sound(SoundType.METAL)
        );
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(face, Direction.NORTH)
                .setValue(depth, FloorDepth.NORMAL));
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        FloorDepth _depth = FloorDepth.NORMAL;
        Direction nearest = context.getHorizontalDirection().getOpposite();
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
        builder.add(face).add(depth);
    }

    @Override
    public float getShadeBrightness(BlockState state, BlockGetter worldIn, BlockPos pos) {
        return 0.5f;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        if (state.getValue(depth) == FloorDepth.FAR) {
            return switch (state.getValue(face)) {
                case NORTH -> ModShapes.BEDSIDE_TABLE_FAR_N;
                case EAST -> ModShapes.BEDSIDE_TABLE_FAR_E;
                case SOUTH -> ModShapes.BEDSIDE_TABLE_FAR_S;
                default -> ModShapes.BEDSIDE_TABLE_FAR_W;
            };
        } else if (state.getValue(depth) == FloorDepth.NEAR) {
            return switch (state.getValue(face)) {
                case NORTH -> ModShapes.BEDSIDE_TABLE_NEAR_N;
                case EAST -> ModShapes.BEDSIDE_TABLE_NEAR_E;
                case SOUTH -> ModShapes.BEDSIDE_TABLE_NEAR_S;
                default -> ModShapes.BEDSIDE_TABLE_NEAR_W;
            };
        } else {
            return switch (state.getValue(face)) {
                case NORTH -> ModShapes.BEDSIDE_TABLE_NORMAL_N;
                case EAST -> ModShapes.BEDSIDE_TABLE_NORMAL_E;
                case SOUTH -> ModShapes.BEDSIDE_TABLE_NORMAL_S;
                default -> ModShapes.BEDSIDE_TABLE_NORMAL_W;
            };
        }
    }

}
