package me.clumsycat.furnitureexpanded.blocks;

import me.clumsycat.furnitureexpanded.util.BSProperties;
import me.clumsycat.furnitureexpanded.util.ModShapes;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

import javax.annotation.Nullable;

public class BathroomSink extends Block {
    private static final DirectionProperty face = HorizontalBlock.FACING;
    public static final IntegerProperty type = BSProperties.TYPE_0_1;

    public BathroomSink() {
        super(AbstractBlock.Properties.of(Material.CLAY)
                .strength(1f, 2f)
                .harvestTool(ToolType.PICKAXE)
                .sound(SoundType.STONE));
        this.registerDefaultState(this.stateDefinition.any().setValue(face, Direction.NORTH).setValue(type, 0));
    }



    @Override
    public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if(player.getMainHandItem().isEmpty()) {
            worldIn.setBlockAndUpdate(pos, state.setValue(type, state.getValue(type) == 0 ? 1 : 0));
            worldIn.playSound(player, pos, SoundType.LANTERN.getBreakSound(), SoundCategory.BLOCKS, 0.5f, 0.5f);
        }
        return worldIn.isClientSide ? ActionResultType.SUCCESS : ActionResultType.CONSUME;
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

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        switch (state.getValue(face)) {
            case NORTH: return ModShapes.BATHROOM_SINK_N;
            case EAST: return ModShapes.BATHROOM_SINK_E;
            case SOUTH: return ModShapes.BATHROOM_SINK_S;
            default: return ModShapes.BATHROOM_SINK_W;
        }
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
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
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(face, type);
    }

    @Override
    public float getShadeBrightness(BlockState state, IBlockReader worldIn, BlockPos pos) { return 0.2f; }

}
