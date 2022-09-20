package me.clumsycat.furnitureexpanded.blocks;

import me.clumsycat.furnitureexpanded.blocks.tileentities.ClockSignTileEntity;
import me.clumsycat.furnitureexpanded.util.BSProperties;
import me.clumsycat.furnitureexpanded.util.ModShapes;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.explosion.Explosion;

public class ClockSign extends BlockWithEntity {
    public static final BooleanProperty main = BSProperties.MAIN;
    public static final DirectionProperty face = HorizontalFacingBlock.FACING;

    public ClockSign() {
        super(AbstractBlock.Settings.of(Material.METAL)
                .strength(2f, 2f)
                .sounds(BlockSoundGroup.METAL));
        this.setDefaultState(this.getStateManager().getDefaultState().with(face, Direction.NORTH).with(main, true));
    }
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return (state.get(main)) ? new ClockSignTileEntity(pos, state) : null;
    }

    @Override
    public void onPlaced(World worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
        super.onPlaced(worldIn, pos, state, placer, stack);
        Direction v3 = state.get(face).rotateYClockwise();
        worldIn.setBlockState(pos.offset(v3), state.with(main, false), Block.NOTIFY_ALL);
    }
    @Override
    public void onBreak(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
        destroyAdj(worldIn, pos, state);
        if (!player.isCreative()) ItemScatterer.spawn(worldIn, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(this));
        super.onBreak(worldIn, pos, state, player);
    }

    @Override
    public void onDestroyedByExplosion(World worldIn, BlockPos pos, Explosion explosion) {
        BlockState state = worldIn.getBlockState(pos);
        ItemScatterer.spawn(worldIn, pos.getX(), pos.getY(), pos.getZ(), state.getBlock().asItem().getDefaultStack());
        destroyAdj(worldIn, pos, state);
        super.onDestroyedByExplosion(worldIn, pos, explosion);
    }

    private void destroyAdj(World worldIn, BlockPos pos, BlockState state) {
        boolean _main = state.get(main);
        Direction _fc = state.get(face);
        BlockPos _pcw = pos.offset(_fc.rotateYClockwise());
        BlockPos _ccw = pos.offset(_fc.rotateYCounterclockwise());
        if(worldIn.getBlockState((_main ? _pcw : _ccw)).getBlock() == this) {
            if(worldIn.getBlockState((_main ? _pcw : _ccw)).get(face) == _fc) {
                worldIn.removeBlock((_main ? _pcw : _ccw), false);
            }
        }
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView worldIn, BlockPos pos) {
        return worldIn.isAir(pos.offset(state.get(face).rotateYClockwise()));
    }

    @Override
    public PistonBehavior getPistonBehavior(BlockState p_149656_1_) {
        return PistonBehavior.BLOCK;
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext context) {
        return this.getDefaultState().with(face, context.getPlayerFacing());
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
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(face, main);
    }

    @Override
    public float getAmbientOcclusionLightLevel(BlockState state, BlockView world, BlockPos pos) {
        return 0.5f;
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView worldIn, BlockPos pos, ShapeContext context) {
        return switch (state.get(face)) {
            case NORTH -> state.get(main) ? ModShapes.CLOCK_SIGN_N : ModShapes.CLOCK_SIGN_S;
            case EAST -> state.get(main) ? ModShapes.CLOCK_SIGN_E : ModShapes.CLOCK_SIGN_W;
            case SOUTH -> state.get(main) ? ModShapes.CLOCK_SIGN_S : ModShapes.CLOCK_SIGN_N;
            default -> state.get(main) ? ModShapes.CLOCK_SIGN_W : ModShapes.CLOCK_SIGN_E;
        };
    }
}
