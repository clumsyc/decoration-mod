package me.clumsycat.furnitureexpanded.blocks;

import me.clumsycat.furnitureexpanded.util.BSProperties;
import me.clumsycat.furnitureexpanded.util.ModShapes;
import me.clumsycat.furnitureexpanded.util.enums.WallHeight;
import me.clumsycat.furnitureexpanded.util.enums.WallSide;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.*;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.explosion.Explosion;

public class PaperHolder extends Block {
    private static final DirectionProperty face = HorizontalFacingBlock.FACING;
    private static final EnumProperty<WallHeight> height = BSProperties.WALL_HEIGHT;
    private static final EnumProperty<WallSide> side = BSProperties.WALL_SIDE;

    public PaperHolder() {
        super(Settings.of(Material.WOOL)
                .strength(.5f, 1f)
                .sounds(BlockSoundGroup.WOOL)
                .noCollision());
        this.setDefaultState(this.getStateManager().getDefaultState().with(face, Direction.NORTH).with(height, WallHeight.NORMAL).with(side, WallSide.NORMAL));
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        return world.isClient ? ActionResult.SUCCESS : ActionResult.CONSUME;
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
    public BlockState getPlacementState(ItemPlacementContext context) {
        WallHeight _height = WallHeight.NORMAL;
        WallSide _side = WallSide.NORMAL;
        Direction nearest = context.getHorizontalPlayerFacing();
        if (!(nearest == Direction.DOWN || nearest == Direction.UP)) {
            Direction direction = nearest.rotateYClockwise();
            double axis = context.getHitPos().getComponentAlongAxis(direction.getAxis());
            double posX = axis - context.getBlockPos().getComponentAlongAxis(direction.getAxis());
            if (direction == Direction.WEST || direction == Direction.NORTH) posX = 1-posX;
            if (posX >= 0 && posX <= 0.33 || posX <= -0.67) _side = WallSide.LEFT;
            else if (posX >= 0.67 || posX >= -0.33 && posX <= 0) _side = WallSide.RIGHT;

            double posY = context.getHitPos().y - context.getBlockPos().getY();
            if (posY <= 0.33) _height = WallHeight.LOWER;
            else if (posY > 0.66) _height = WallHeight.HIGHER;
        }
        return this.getDefaultState().with(face, context.getHorizontalPlayerFacing().getOpposite()).with(height, _height).with(side, _side);
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView reader, BlockPos pos) {
        Direction direction = state.get(face).getOpposite();
        BlockPos relative = pos.offset(direction);
        return !reader.isAir(relative);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(face, height, side);
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
    public VoxelShape getOutlineShape(BlockState state, BlockView worldIn, BlockPos pos, ShapeContext context) {
        return switch (state.get(face)) {
            case NORTH -> ModShapes.PAPER_HOLDER_N(state.get(height), state.get(side));
            case EAST -> ModShapes.PAPER_HOLDER_E(state.get(height), state.get(side));
            case SOUTH -> ModShapes.PAPER_HOLDER_S(state.get(height), state.get(side));
            default -> ModShapes.PAPER_HOLDER_W(state.get(height), state.get(side));
        };
    }
}
