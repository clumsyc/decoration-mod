package me.clumsycat.furnitureexpanded.blocks;

import me.clumsycat.furnitureexpanded.util.BSProperties;
import me.clumsycat.furnitureexpanded.util.ModShapes;
import me.clumsycat.furnitureexpanded.util.enums.WallHeight;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.Containers;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

@SuppressWarnings("NullableProblems")
public class TowelBar extends Block {
    private static final DirectionProperty face = HorizontalDirectionalBlock.FACING;
    private static final EnumProperty<WallHeight> height = BSProperties.WALL_HEIGHT;

    public TowelBar() {
        super(Properties.of(Material.STONE)
                .strength(1f, 1f)
                .sound(SoundType.STONE)
                .noCollission());
        this.registerDefaultState(this.getStateDefinition().any().setValue(face, Direction.NORTH).setValue(height, WallHeight.NORMAL));
    }

    @Override
    public void playerDestroy(Level worldIn, Player player, BlockPos pos, BlockState state, @Nullable BlockEntity tileentity, ItemStack stack) {
        Containers.dropItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(this));
        super.playerDestroy(worldIn, player, pos, state, tileentity, stack);
    }

    @Override
    public void onBlockExploded(BlockState state, Level worldIn, BlockPos pos, Explosion explosion) {
        Containers.dropItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(this));
        super.onBlockExploded(state, worldIn, pos, explosion);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        double posY = context.getClickLocation().y - context.getClickedPos().getY();
        WallHeight _height = WallHeight.LOWER;
        if (posY > 0.33 && posY < 0.66) _height = WallHeight.NORMAL;
        else if (posY > 0.66) _height = WallHeight.HIGHER;
        return this.defaultBlockState().setValue(face, context.getHorizontalDirection().getOpposite()).setValue(height, _height);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader reader, BlockPos pos) {
        Direction direction = state.getValue(face).getOpposite();
        BlockPos relative = pos.relative(direction);
        return !reader.isEmptyBlock(relative);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
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
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return switch (state.getValue(face)) {
            case NORTH -> ModShapes.TOWEL_BAR_N(state.getValue(height));
            case EAST -> ModShapes.TOWEL_BAR_E(state.getValue(height));
            case SOUTH -> ModShapes.TOWEL_BAR_S(state.getValue(height));
            default -> ModShapes.TOWEL_BAR_W(state.getValue(height));
        };
    }
}
