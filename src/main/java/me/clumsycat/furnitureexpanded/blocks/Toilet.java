package me.clumsycat.furnitureexpanded.blocks;

import me.clumsycat.furnitureexpanded.util.BSProperties;
import me.clumsycat.furnitureexpanded.util.DyeHandler;
import me.clumsycat.furnitureexpanded.util.ModShapes;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.DyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tags.ItemTags;
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

public class Toilet extends Block {
    private static final IntegerProperty type = BSProperties.TYPE_0_1;
    private static final DirectionProperty face = HorizontalBlock.FACING;
    private static final IntegerProperty dye = BSProperties.TYPE_17;


    public Toilet() {
        super(AbstractBlock.Properties.of(Material.CLAY)
                .strength(1f, 2f)
                .harvestTool(ToolType.PICKAXE)
                .sound(SoundType.STONE)
                .noOcclusion());
        this.registerDefaultState(this.getStateDefinition().any().setValue(face, Direction.NORTH).setValue(type, 0).setValue(dye, 16));
    }

    @Override
    public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        ItemStack stack = player.getMainHandItem();
        if (ItemTags.CARPETS.contains(stack.getItem())) {
            int j = DyeHandler.carpetResolver(16, stack);
            if (state.getValue(dye) != j && j < 16) {
                if (!player.isCreative()) {
                    stack.setCount(stack.getCount() - 1);
                    if (state.getValue(dye) != 16) InventoryHelper.dropItemStack(worldIn, pos.getX(), pos.getY()+.5, pos.getZ(), new ItemStack(DyeHandler.CARPET_DYES.get(DyeColor.byId(state.getValue(dye)))));
                }
                worldIn.setBlockAndUpdate(pos, state.setValue(dye, j));
            }
        }
        else if(stack.isEmpty()) {
            if (state.getValue(type) == 0) worldIn.setBlockAndUpdate(pos, state.setValue(type, 1));
            else {
                /*
                int y = hit.getBlockPos().getY();
                if (hit.getDirection() == Direction.UP && hit.getLocation().y == (y+.625)) seat(); //TODO: Re-add seat function
                else worldIn.setBlockAndUpdate(pos, state.setValue(type, 0));
                */
                worldIn.setBlockAndUpdate(pos, state.setValue(type, 0));
            }
            worldIn.playSound(player, pos, SoundType.WOOD.getBreakSound(), SoundCategory.BLOCKS, 0.5f, 0.5f);
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

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.defaultBlockState().setValue(face, context.getHorizontalDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(face, type, dye);
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
            case NORTH: return ModShapes.TOILET_N;
            case EAST: return ModShapes.TOILET_E;
            case SOUTH: return ModShapes.TOILET_S;
            default: return ModShapes.TOILET_W;
        }
    }
}
