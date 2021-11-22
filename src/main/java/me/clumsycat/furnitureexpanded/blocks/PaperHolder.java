package me.clumsycat.furnitureexpanded.blocks;

import me.clumsycat.furnitureexpanded.util.BSProperties;
import me.clumsycat.furnitureexpanded.util.ModShapes;
import me.clumsycat.furnitureexpanded.util.enums.WallHeight;
import me.clumsycat.furnitureexpanded.util.enums.WallSide;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class PaperHolder extends Block {
    private static final DirectionProperty face = HorizontalBlock.FACING;
    private static final EnumProperty<WallHeight> height = BSProperties.WALL_HEIGHT;
    private static final EnumProperty<WallSide> side = BSProperties.WALL_SIDE;

    public PaperHolder() {
        super(Properties.of(Material.WOOL)
                .strength(.5f, 1f)
                .sound(SoundType.WOOL));
        this.registerDefaultState(this.getStateDefinition().any().setValue(face, Direction.NORTH).setValue(height, WallHeight.NORMAL).setValue(side, WallSide.NORMAL));
    }

    @Override
    public void playerDestroy(World worldIn, PlayerEntity player, BlockPos pos, BlockState state, @Nullable TileEntity tileentity, ItemStack stack) {
        InventoryHelper.dropItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(this));
        super.playerDestroy(worldIn, player, pos, state, tileentity, stack);
    }

    @Override
    public void onBlockExploded(BlockState state, World worldIn, BlockPos pos, Explosion explosion) {
        InventoryHelper.dropItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(this));
        super.onBlockExploded(state, worldIn, pos, explosion);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        WallHeight _height = WallHeight.NORMAL;
        WallSide _side = WallSide.NORMAL;
        Direction nearest = context.getNearestLookingDirection();
        if (!(nearest == Direction.DOWN || nearest == Direction.UP)) {
            Direction direction = nearest.getClockWise();
            double axis = context.getClickLocation().get(direction.getAxis());
            double posX = axis - context.getClickedPos().get(direction.getAxis());
            int pointer = direction.getNormal().get(direction.getAxis());
            if (pointer < 0) posX = context.getClickedPos().get(direction.getAxis()) - axis;

            if (posX >= 0 && posX <= 0.33 || posX <= -0.66) _side = WallSide.LEFT;
            else if (posX >= 0.33 && posX <= 0.66 || posX <= -0.33 && posX >= -0.66) _side = WallSide.NORMAL;
            else _side = WallSide.RIGHT;

            double posY = context.getClickLocation().y - context.getClickedPos().getY();
            //if (posY < 0) posY =+ 1; //TODO: test 1.17 y map
            if (posY <= 0.33) _height = WallHeight.LOWER;
            else if (posY > 0.66) _height = WallHeight.HIGHER;
        }
        return this.defaultBlockState().setValue(face, context.getHorizontalDirection().getOpposite()).setValue(height, _height).setValue(side, _side);
    }

    @Override
    public boolean canSurvive(BlockState state, IWorldReader reader, BlockPos pos) {
        Direction direction = state.getValue(face).getOpposite();
        BlockPos relative = pos.relative(direction);
        return !reader.isEmptyBlock(relative);
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(face, height, side);
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
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        switch (state.getValue(face)) {
            case NORTH: return ModShapes.PAPER_HOLDER_N(state.getValue(height), state.getValue(side));
            case EAST: return ModShapes.PAPER_HOLDER_E(state.getValue(height), state.getValue(side));
            case SOUTH: return ModShapes.PAPER_HOLDER_S(state.getValue(height), state.getValue(side));
            default: return ModShapes.PAPER_HOLDER_W(state.getValue(height), state.getValue(side));
        }
    }
}
