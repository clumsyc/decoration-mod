package me.clumsycat.furnitureexpanded.blocks;

import me.clumsycat.furnitureexpanded.blocks.tileentities.ClockSignTileEntity;
import me.clumsycat.furnitureexpanded.util.BSProperties;
import me.clumsycat.furnitureexpanded.util.ModShapes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class ClockSign extends BaseEntityBlock {
    public static final BooleanProperty main = BSProperties.MAIN;
    public static final DirectionProperty face = HorizontalDirectionalBlock.FACING;

    public ClockSign() {
        super(BlockBehaviour.Properties.of()
                .strength(2f, 2f)
                .sound(SoundType.METAL)
                .pushReaction(PushReaction.BLOCK)
        );
        this.registerDefaultState(this.stateDefinition.any().setValue(face, Direction.NORTH).setValue(main, true));
    }
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return (state.getValue(main)) ? new ClockSignTileEntity(pos, state) : null;
    }

    @Override
    public void setPlacedBy(Level worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
        super.setPlacedBy(worldIn, pos, state, placer, stack);
        Vec3i v3 = state.getValue(face).getClockWise().getNormal();
        worldIn.setBlockAndUpdate(pos.offset(v3), state.setValue(main, false));
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState state2, boolean bl) {
        if (!state.is(state2.getBlock())) {
            boolean _main = state.getValue(main);
            Direction _fc = state.getValue(face);
            BlockPos _pcw = pos.offset(_fc.getClockWise().getNormal());
            BlockPos _ccw = pos.offset(_fc.getCounterClockWise().getNormal());
            if(level.getBlockState((_main ? _pcw : _ccw)).getBlock() == this) {
                if(level.getBlockState((_main ? _pcw : _ccw)).getValue(face) == _fc) {
                    level.removeBlock((_main ? _pcw : _ccw), false);
                }
            }
            super.onRemove(state, level, pos, state2, bl);
        }
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader worldIn, BlockPos pos) {
        return worldIn.isEmptyBlock(pos.offset(state.getValue(face).getClockWise().getNormal()));
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
        builder.add(face, main);
    }

    @Override
    public float getShadeBrightness(BlockState state, BlockGetter worldIn, BlockPos pos) {
        return 0.5f;
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return switch (state.getValue(face)) {
            case NORTH -> state.getValue(main) ? ModShapes.CLOCK_SIGN_N : ModShapes.CLOCK_SIGN_S;
            case EAST -> state.getValue(main) ? ModShapes.CLOCK_SIGN_E : ModShapes.CLOCK_SIGN_W;
            case SOUTH -> state.getValue(main) ? ModShapes.CLOCK_SIGN_S : ModShapes.CLOCK_SIGN_N;
            default -> state.getValue(main) ? ModShapes.CLOCK_SIGN_W : ModShapes.CLOCK_SIGN_E;
        };
    }
}
