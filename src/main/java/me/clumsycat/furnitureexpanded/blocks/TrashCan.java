package me.clumsycat.furnitureexpanded.blocks;

import me.clumsycat.furnitureexpanded.blocks.tileentities.TrashCanTileEntity;
import me.clumsycat.furnitureexpanded.util.BSProperties;
import me.clumsycat.furnitureexpanded.util.ModShapes;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
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

public class TrashCan extends ContainerBlock {
    private static final DirectionProperty face = HorizontalBlock.FACING;
    private static final IntegerProperty type = BSProperties.TYPE_0_1;

    public TrashCan() { //TODO: Add delete button to empty Trash Can(?).
        super(Properties.of(Material.METAL)
                .strength(1f, 2f)
                .harvestTool(ToolType.PICKAXE)
                .sound(SoundType.METAL)
        );
        this.registerDefaultState(this.getStateDefinition().any().setValue(face, Direction.NORTH).setValue(type, 0));
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return newBlockEntity(world);
    }

    @Nullable
    @Override
    public TileEntity newBlockEntity(IBlockReader worldIn) {
        return new TrashCanTileEntity();
    }

    @Override
    public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (state.getValue(type) == 1) {
            TileEntity tileentity = worldIn.getBlockEntity(pos);
            if (tileentity instanceof TrashCanTileEntity) {
                player.openMenu((TrashCanTileEntity) tileentity);
            }
        }
        return worldIn.isClientSide ? ActionResultType.SUCCESS : ActionResultType.CONSUME;
    }

    @Override
    public void playerWillDestroy(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
        dropContents(worldIn, pos, worldIn.getBlockEntity(pos), !player.isCreative());
        super.playerWillDestroy(worldIn, pos, state, player);
    }

    @Override
    public void onBlockExploded(BlockState state, World worldIn, BlockPos pos, Explosion explosion) {
        dropContents(worldIn, pos, worldIn.getBlockEntity(pos), true);
        super.onBlockExploded(state, worldIn, pos, explosion);
    }

    private void dropContents(World worldIn, BlockPos pos, TileEntity tileentity, boolean shouldDrop) {
        TrashCanTileEntity te = (TrashCanTileEntity) tileentity;
        if (!te.isEmpty()) {
            CompoundNBT compoundnbt = te.saveToNbt(new CompoundNBT());
            NonNullList<ItemStack> invdrop = NonNullList.withSize(9, ItemStack.EMPTY);
            ItemStackHelper.loadAllItems(compoundnbt, invdrop);
            InventoryHelper.dropContents(worldIn, pos, invdrop);
        }
        if (shouldDrop) InventoryHelper.dropItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(this));
    }

    @Override
    public void entityInside(BlockState state, World worldIn, BlockPos pos, Entity entity) {
        if (!worldIn.isClientSide) {
            if (entity instanceof PlayerEntity) {
                if ((entity.getY()-pos.getY()) == .046875) {
                    TrashCanTileEntity te = (TrashCanTileEntity) worldIn.getBlockEntity(pos);
                    te.resetCountdown();
                }
            }
        }
    }

    @Override
    public BlockRenderType getRenderShape(BlockState p_149645_1_) {
        return BlockRenderType.MODEL;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.defaultBlockState().setValue(face, context.getHorizontalDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(face, type);
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
        boolean s = state.getValue(type) == 0;
        switch (state.getValue(face)) {
            case NORTH: return s ? ModShapes.TRASH_CAN_N : ModShapes.TRASH_CAN_NOLID_N;
            case EAST: return s ? ModShapes.TRASH_CAN_E : ModShapes.TRASH_CAN_NOLID_E;
            case SOUTH: return s ? ModShapes.TRASH_CAN_S : ModShapes.TRASH_CAN_NOLID_S;
            default: return s ? ModShapes.TRASH_CAN_W : ModShapes.TRASH_CAN_NOLID_W;
        }
    }
}
