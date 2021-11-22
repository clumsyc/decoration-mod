package me.clumsycat.furnitureexpanded.blocks;

import me.clumsycat.furnitureexpanded.blocks.tileentities.CardboxTileEntity;
import me.clumsycat.furnitureexpanded.registries.RegistryHandler;
import me.clumsycat.furnitureexpanded.util.BSProperties;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

import javax.annotation.Nullable;

public class Cardbox extends ContainerBlock {
    public static final DirectionProperty face = HorizontalBlock.FACING;
    public static final BooleanProperty sealed = BSProperties.SEALED;

    public Cardbox() {
        super(AbstractBlock.Properties.of(Material.WOOD)
                .strength(0.3f, 1f)
                .harvestTool(ToolType.AXE)
                .sound(SoundType.WOOL));
        this.registerDefaultState(this.stateDefinition.any().setValue(face, Direction.NORTH).setValue(sealed, false));
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
        return new CardboxTileEntity();
    }

    @Override
    public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
            if (player.getMainHandItem().getItem() == RegistryHandler.TAPE.get().getItem()) {
                if (!state.getValue(sealed)) {
                    worldIn.setBlockAndUpdate(pos, state.setValue(sealed, true));
                    worldIn.playSound(null, pos, SoundEvents.BEEHIVE_SHEAR, SoundCategory.BLOCKS, 1.0f, 1.0f);
                    ItemStack tape = player.getMainHandItem();
                    if (tape.getItem() == RegistryHandler.TAPE.get().getItem())
                        tape.hurtAndBreak(1, player, (p_226874_1_) -> {
                        p_226874_1_.broadcastBreakEvent(handIn);
                    });
                }
            } else if (player.getMainHandItem().getItem() == Items.SHEARS) {
                if (state.getValue(sealed)) {
                    worldIn.setBlockAndUpdate(pos, state.setValue(sealed, false));
                    worldIn.playSound(null, pos, SoundEvents.BEEHIVE_EXIT, SoundCategory.BLOCKS, 1.0f, 1.0f);
                    ItemStack shears = player.getMainHandItem();
                    if (shears.getItem() == Items.SHEARS.getItem())
                        shears.hurtAndBreak(3, player, (p_226874_1_) -> {
                            p_226874_1_.broadcastBreakEvent(handIn);
                        });
                }
            } else {
                if (worldIn.isClientSide) return ActionResultType.SUCCESS;
                if (player.isCrouching()) {
                    TileEntity tileentity = worldIn.getBlockEntity(pos);
                    if (tileentity instanceof CardboxTileEntity) {
                        if (state.getValue(sealed) || ((CardboxTileEntity) tileentity).isEmpty()) {
                            for (ItemStack inv : player.inventory.items) {
                                if (inv.isEmpty()) {
                                    player.addItem(getDrop((CardboxTileEntity) tileentity, state.getValue(sealed)));
                                    worldIn.removeBlockEntity(pos);
                                    worldIn.removeBlock(pos, false);
                                    break;
                                }
                            }
                        }
                    }
                    return ActionResultType.CONSUME;
                } else {
                    if (!state.getValue(sealed)) {
                        if (worldIn.isEmptyBlock(pos.above())) {
                            TileEntity tileentity = worldIn.getBlockEntity(pos);
                            if (tileentity instanceof CardboxTileEntity) {
                                player.openMenu((CardboxTileEntity) tileentity);
                            }
                            return ActionResultType.CONSUME;
                        }
                    }
                }
            }
        return worldIn.isClientSide ? ActionResultType.SUCCESS : ActionResultType.CONSUME;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.defaultBlockState().setValue(face, context.getHorizontalDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(sealed, face);
    }

    @Override
    public void setPlacedBy(World worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        super.setPlacedBy(worldIn, pos, state, placer, stack);
        if (stack.hasTag()) {
            if (stack.getTag() != null)
                if (stack.getTag().contains("sealed"))
                    if (stack.getTag().getBoolean("sealed"))
                        worldIn.setBlockAndUpdate(pos, state.setValue(sealed, true));
        }
    }


    @Override
    public void onBlockExploded(BlockState state, World worldIn, BlockPos pos, Explosion explosion) {
        CardboxTileEntity te = (CardboxTileEntity) worldIn.getBlockEntity(pos);
        assert te != null;
        if (!te.isEmpty()) {
            CompoundNBT compoundnbt = te.saveToNbt(new CompoundNBT());
            NonNullList<ItemStack> invdrop = NonNullList.withSize(27, ItemStack.EMPTY);
            ItemStackHelper.loadAllItems(compoundnbt, invdrop);
            InventoryHelper.dropContents(worldIn, pos, invdrop);
        }
        InventoryHelper.dropItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(this));
        super.onBlockExploded(state, worldIn, pos, explosion);
    }

    @Override
    public void playerDestroy(World worldIn, PlayerEntity player, BlockPos pos, BlockState state, @Nullable TileEntity tileEntity, ItemStack stack) {
        ItemStack is = getDrop((CardboxTileEntity) tileEntity, state.getValue(sealed));
        worldIn.removeBlockEntity(pos);
        InventoryHelper.dropItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(), is);
        if (!state.getValue(sealed)) {
            CardboxTileEntity te = (CardboxTileEntity) tileEntity;
            assert te != null;
            if (!te.isEmpty()) {
                CompoundNBT compoundnbt = te.saveToNbt(new CompoundNBT());
                NonNullList<ItemStack> invdrop = NonNullList.withSize(27, ItemStack.EMPTY);
                ItemStackHelper.loadAllItems(compoundnbt, invdrop);
                InventoryHelper.dropContents(worldIn, pos, invdrop);
            }
        }
        super.playerDestroy(worldIn, player, pos, state, tileEntity, stack);
    }

    protected ItemStack getDrop(CardboxTileEntity tileentity, boolean sealed) {
        ItemStack itemstack = new ItemStack(this);
        if (tileentity != null && sealed) {
            if (!tileentity.isEmpty()) {
                CompoundNBT compoundnbt = tileentity.saveToNbt(new CompoundNBT());
                if (!compoundnbt.isEmpty()) itemstack.addTagElement("BlockEntityTag", compoundnbt);
                if (tileentity.hasCustomName()) itemstack.setHoverName(tileentity.getCustomName());
            }
            if (itemstack.getTag() != null) itemstack.getTag().putBoolean("sealed", true);
        }
        return itemstack;
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
    public BlockRenderType getRenderShape(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return VoxelShapes.block();
    }
}
