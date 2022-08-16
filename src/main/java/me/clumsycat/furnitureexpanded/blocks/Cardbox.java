package me.clumsycat.furnitureexpanded.blocks;

import me.clumsycat.furnitureexpanded.blocks.tileentities.CardboxTileEntity;
import me.clumsycat.furnitureexpanded.registries.RegistryHandler;
import me.clumsycat.furnitureexpanded.util.BSProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

@SuppressWarnings("NullableProblems")
public class Cardbox extends BaseEntityBlock {
    public static final DirectionProperty face = HorizontalDirectionalBlock.FACING;
    public static final BooleanProperty sealed = BSProperties.SEALED;

    public Cardbox() {
        super(AbstractChestBlock.Properties.of(Material.WOOD)
                .strength(0.3f, 1f)
                .sound(SoundType.WOOL));
        this.registerDefaultState(this.stateDefinition.any().setValue(face, Direction.NORTH).setValue(sealed, false));
    }

    @Override
    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
            if (player.getMainHandItem().getItem() == RegistryHandler.TAPE.get().asItem()) {
                if (!state.getValue(sealed)) {
                    worldIn.setBlockAndUpdate(pos, state.setValue(sealed, true));
                    worldIn.playSound(null, pos, SoundEvents.BEEHIVE_SHEAR, SoundSource.BLOCKS, 1.0f, 1.0f);
                    ItemStack tape = player.getMainHandItem();
                    if (tape.getItem() == RegistryHandler.TAPE.get().asItem())
                        tape.hurtAndBreak(1, player, (p_226874_1_) -> {
                        p_226874_1_.broadcastBreakEvent(handIn);
                    });
                }
            } else if (player.getMainHandItem().getItem() == Items.SHEARS) {
                if (state.getValue(sealed)) {
                    worldIn.setBlockAndUpdate(pos, state.setValue(sealed, false));
                    worldIn.playSound(null, pos, SoundEvents.BEEHIVE_EXIT, SoundSource.BLOCKS, 1.0f, 1.0f);
                    ItemStack shears = player.getMainHandItem();
                    if (shears.getItem() == Items.SHEARS.asItem())
                        shears.hurtAndBreak(3, player, (p_226874_1_) -> {
                            p_226874_1_.broadcastBreakEvent(handIn);
                        });
                }
            } else {
                if (worldIn.isClientSide) return InteractionResult.SUCCESS;
                if (player.isCrouching()) {
                    BlockEntity tileentity = worldIn.getBlockEntity(pos);
                    if (tileentity instanceof CardboxTileEntity) {
                        if (state.getValue(sealed) || ((CardboxTileEntity) tileentity).isEmpty()) {
                            for (ItemStack inv : player.getInventory().items) {
                                if (inv.isEmpty()) {
                                    player.addItem(getDrop((CardboxTileEntity) tileentity, state.getValue(sealed)));
                                    worldIn.removeBlockEntity(pos);
                                    worldIn.removeBlock(pos, false);
                                    break;
                                }
                            }
                        }
                    }
                    return InteractionResult.CONSUME;
                } else {
                    if (!state.getValue(sealed)) {
                        if (worldIn.isEmptyBlock(pos.above())) {
                            BlockEntity tileentity = worldIn.getBlockEntity(pos);
                            if (tileentity instanceof CardboxTileEntity) {
                                player.openMenu((CardboxTileEntity) tileentity);
                            }
                            return InteractionResult.CONSUME;
                        }
                    }
                }
            }
        return worldIn.isClientSide ? InteractionResult.SUCCESS : InteractionResult.CONSUME;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(face, context.getHorizontalDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(sealed, face);
    }

    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new CardboxTileEntity(pPos, pState);
    }

    @Override
    public void setPlacedBy(Level worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        super.setPlacedBy(worldIn, pos, state, placer, stack);
        if (stack.hasTag()) {
            if (stack.getTag() != null)
                if (stack.getTag().contains("sealed"))
                    if (stack.getTag().getBoolean("sealed"))
                        worldIn.setBlockAndUpdate(pos, state.setValue(sealed, true));
        }
    }


    @Override
    public void onBlockExploded(BlockState state, Level worldIn, BlockPos pos, Explosion explosion) {
        CardboxTileEntity te = (CardboxTileEntity) worldIn.getBlockEntity(pos);
        assert te != null;
        if (!te.isEmpty()) {
            CompoundTag compoundnbt = te.saveToNbt(new CompoundTag());
            NonNullList<ItemStack> invdrop = NonNullList.withSize(27, ItemStack.EMPTY);
            ContainerHelper.loadAllItems(compoundnbt, invdrop);
            Containers.dropContents(worldIn, pos, invdrop);
        }
        Containers.dropItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(this));
        super.onBlockExploded(state, worldIn, pos, explosion);
    }

    @Override
    public void playerWillDestroy(Level worldIn, BlockPos pos, BlockState state, Player player) {
        CardboxTileEntity te = (CardboxTileEntity) worldIn.getBlockEntity(pos);
        ItemStack is = getDrop(te, state.getValue(sealed));
        if (!player.isCreative()) Containers.dropItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(), is);
        if (!state.getValue(sealed)) {
            if (!te.isEmpty()) {
                CompoundTag compoundnbt = te.saveToNbt(new CompoundTag());
                NonNullList<ItemStack> invdrop = NonNullList.withSize(27, ItemStack.EMPTY);
                ContainerHelper.loadAllItems(compoundnbt, invdrop);
                Containers.dropContents(worldIn, pos, invdrop);
            }
        }
        super.playerWillDestroy(worldIn, pos, state, player);
    }

    protected ItemStack getDrop(CardboxTileEntity tileentity, boolean sealed) {
        ItemStack itemstack = new ItemStack(this);
        if (tileentity != null && sealed) {
            if (!tileentity.isEmpty()) {
                CompoundTag compoundnbt = tileentity.saveToNbt(new CompoundTag());
                if (!compoundnbt.isEmpty()) itemstack.addTagElement("BlockEntityTag", compoundnbt);
                if (tileentity.hasCustomName()) itemstack.setHoverName(tileentity.getCustomName());
            }
            if (itemstack.getTag() != null) itemstack.getTag().putBoolean("sealed", true);
        }
        return itemstack;
    }

    @Override
    public PushReaction getPistonPushReaction(BlockState p_149656_1_) {
        return PushReaction.BLOCK;
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
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return Shapes.block();
    }
}
