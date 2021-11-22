package me.clumsycat.furnitureexpanded.blocks;

import me.clumsycat.furnitureexpanded.blocks.tileentities.ClockSignTileEntity;
import me.clumsycat.furnitureexpanded.util.BSProperties;
import me.clumsycat.furnitureexpanded.util.ModShapes;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

import javax.annotation.Nullable;

public class ClockSign extends ContainerBlock {
    public static final BooleanProperty main = BSProperties.MAIN;
    public static final DirectionProperty face = HorizontalBlock.FACING;

    public ClockSign() { // TODO: Improve model
        super(Properties.of(Material.METAL)
                .strength(2f, 2f)
                .harvestTool(ToolType.PICKAXE)
                .sound(SoundType.METAL));
        this.registerDefaultState(this.stateDefinition.any().setValue(face, Direction.NORTH).setValue(main, true));
    }

    @Override
    public void setPlacedBy(World worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        super.setPlacedBy(worldIn, pos, state, placer, stack);
        Vector3i v3 = state.getValue(face).getClockWise().getNormal();
        worldIn.setBlockAndUpdate(pos.offset(v3), state.setValue(main, false));
    }

    @Override

    public void playerWillDestroy(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
        destroyAdj(worldIn, pos, state);
        super.playerWillDestroy(worldIn, pos, state, player);
    }

    @Override
    public void playerDestroy(World worldIn, PlayerEntity player, BlockPos pos, BlockState state, @Nullable TileEntity tileentity, ItemStack stack) {
        InventoryHelper.dropItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(this));
    }

    @Override
    public void onBlockExploded(BlockState state, World worldIn, BlockPos pos, Explosion explosion) {
        InventoryHelper.dropItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(), state.getBlock().asItem().getDefaultInstance());
        destroyAdj(worldIn, pos, state);
        super.onBlockExploded(state, worldIn, pos, explosion);
    }

    private void destroyAdj(World worldIn, BlockPos pos, BlockState state) {
        boolean _main = state.getValue(main);
        Direction _fc = state.getValue(face);
        BlockPos _pcw = pos.offset(_fc.getClockWise().getNormal());
        BlockPos _ccw = pos.offset(_fc.getCounterClockWise().getNormal());
        if(worldIn.getBlockState((_main ? _pcw : _ccw)).getBlock() == this) {
            if(worldIn.getBlockState((_main ? _pcw : _ccw)).getValue(face) == _fc) {
                worldIn.removeBlock((_main ? _pcw : _ccw), false);
            }
        }
    }

    @Override
    public boolean canSurvive(BlockState state, IWorldReader worldIn, BlockPos pos) {
        return worldIn.isEmptyBlock(pos.offset(state.getValue(face).getClockWise().getNormal()));
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return state.getValue(main);
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return (state.getValue(main)) ? newBlockEntity(world) : null;
    }

    @Nullable
    @Override
    public TileEntity newBlockEntity(IBlockReader worldIn) {
        return new ClockSignTileEntity();
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
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
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(face, main);
    }

    @Override
    public float getShadeBrightness(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return 0.5f;
    }

    @Override
    public BlockRenderType getRenderShape(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        switch (state.getValue(face)) {
            case NORTH: return state.getValue(main) ? ModShapes.CLOCK_SIGN_N : ModShapes.CLOCK_SIGN_S;
            case EAST: return state.getValue(main) ? ModShapes.CLOCK_SIGN_E : ModShapes.CLOCK_SIGN_W;
            case SOUTH: return state.getValue(main) ? ModShapes.CLOCK_SIGN_S : ModShapes.CLOCK_SIGN_N;
            default: return state.getValue(main) ? ModShapes.CLOCK_SIGN_W : ModShapes.CLOCK_SIGN_E;
        }
    }
}
