package me.clumsycat.furnitureexpanded.blocks;

import me.clumsycat.furnitureexpanded.util.BSProperties;
import me.clumsycat.furnitureexpanded.util.ModShapes;
import me.clumsycat.furnitureexpanded.util.enums.WallHeight;
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

public class TowelBar extends Block {
    private static final DirectionProperty face = HorizontalBlock.FACING;
    private static final EnumProperty<WallHeight> height = BSProperties.WALL_HEIGHT;

    public TowelBar() { //TODO: Add support to custom towels
        super(Properties.of(Material.STONE)
                .strength(1f, 1f)
                .sound(SoundType.STONE));
        this.registerDefaultState(this.getStateDefinition().any().setValue(face, Direction.NORTH).setValue(height, WallHeight.NORMAL));
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
        double posY = context.getClickLocation().y - context.getClickedPos().getY();
        WallHeight _height = WallHeight.LOWER;
        if (posY > 0.33 && posY < 0.66) _height = WallHeight.NORMAL;
        else if (posY > 0.66) _height = WallHeight.HIGHER;
        return this.defaultBlockState().setValue(face, context.getHorizontalDirection().getOpposite()).setValue(height, _height);
    }

    @Override
    public boolean canSurvive(BlockState state, IWorldReader reader, BlockPos pos) {
        Direction direction = state.getValue(face).getOpposite();
        BlockPos relative = pos.relative(direction);
        return !reader.isEmptyBlock(relative);
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(face, height);
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
            case NORTH: return ModShapes.TOWEL_BAR_N(state.getValue(height));
            case EAST: return ModShapes.TOWEL_BAR_E(state.getValue(height));
            case SOUTH: return ModShapes.TOWEL_BAR_S(state.getValue(height));
            default: return ModShapes.TOWEL_BAR_W(state.getValue(height));
        }
    }
}
