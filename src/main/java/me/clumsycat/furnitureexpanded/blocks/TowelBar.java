package me.clumsycat.furnitureexpanded.blocks;

import me.clumsycat.furnitureexpanded.util.BSProperties;
import me.clumsycat.furnitureexpanded.util.ModShapes;
import me.clumsycat.furnitureexpanded.util.enums.WallHeight;
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

public class TowelBar extends Block {
    private static final DirectionProperty face = HorizontalFacingBlock.FACING;
    private static final EnumProperty<WallHeight> height = BSProperties.WALL_HEIGHT;

    public TowelBar() {
        super(Settings.create()
                .strength(1f, 1f)
                .sounds(BlockSoundGroup.STONE)
                .noCollision());
        this.setDefaultState(this.getStateManager().getDefaultState().with(face, Direction.NORTH).with(height, WallHeight.NORMAL));
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
        double posY = context.getHitPos().y - context.getBlockPos().getY();
        WallHeight _height = WallHeight.LOWER;
        if (posY > 0.33 && posY < 0.66) _height = WallHeight.NORMAL;
        else if (posY > 0.66) _height = WallHeight.HIGHER;
        return this.getDefaultState().with(face, context.getHorizontalPlayerFacing().getOpposite()).with(height, _height);
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView reader, BlockPos pos) {
        Direction direction = state.get(face).getOpposite();
        BlockPos relative = pos.offset(direction);
        return !reader.isAir(relative);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(face, height);
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
            case NORTH -> ModShapes.TOWEL_BAR_N(state.get(height));
            case EAST -> ModShapes.TOWEL_BAR_E(state.get(height));
            case SOUTH -> ModShapes.TOWEL_BAR_S(state.get(height));
            default -> ModShapes.TOWEL_BAR_W(state.get(height));
        };
    }
}
