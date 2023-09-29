package me.clumsycat.furnitureexpanded.blocks;

import me.clumsycat.furnitureexpanded.blocks.tileentities.CardboxTileEntity;
import me.clumsycat.furnitureexpanded.registries.RegistryHandler;
import me.clumsycat.furnitureexpanded.util.BSProperties;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.util.*;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;

public class Cardbox extends BlockWithEntity {
    public static final DirectionProperty face = HorizontalFacingBlock.FACING;
    public static final BooleanProperty sealed = BSProperties.SEALED;

    public Cardbox() {
        super(AbstractBlock.Settings.create()
                .strength(0.3f, 1f)
                .sounds(BlockSoundGroup.WOOL)
                .pistonBehavior(PistonBehavior.BLOCK));
        this.setDefaultState(this.getStateManager().getDefaultState().with(face, Direction.NORTH).with(sealed, false));
    }

    @Override
    public ActionResult onUse(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockHitResult hit) {
            if (player.getMainHandStack().getItem() == RegistryHandler.TAPE) {
                if (!state.get(sealed)) {
                    worldIn.setBlockState(pos, state.with(sealed, true), Block.NOTIFY_ALL);
                    worldIn.playSound(null, pos, SoundEvents.BLOCK_BEEHIVE_SHEAR, SoundCategory.BLOCKS, 1.0f, 1.0f);
                    ItemStack tape = player.getMainHandStack();
                    if (tape.getItem() == RegistryHandler.TAPE)
                        tape.damage(1, player, (p_226874_1_) -> {
                        p_226874_1_.sendToolBreakStatus(handIn);
                    });
                }
            } else if (player.getMainHandStack().getItem() == Items.SHEARS) {
                if (state.get(sealed)) {
                    worldIn.setBlockState(pos, state.with(sealed, false), Block.NOTIFY_ALL);
                    worldIn.playSound(null, pos, SoundEvents.BLOCK_BEEHIVE_EXIT, SoundCategory.BLOCKS, 1.0f, 1.0f);
                    ItemStack shears = player.getMainHandStack();
                    if (shears.getItem() == Items.SHEARS.asItem())
                        shears.damage(3, player, (p_226874_1_) -> {
                            p_226874_1_.sendToolBreakStatus(handIn);
                        });
                }
            } else {
                if (worldIn.isClient) return ActionResult.SUCCESS;
                if (player.isSneaking()) {
                    BlockEntity tileentity = worldIn.getBlockEntity(pos);
                    if (tileentity instanceof CardboxTileEntity) {
                        if (state.get(sealed) || ((CardboxTileEntity) tileentity).isEmpty()) {
                            for (ItemStack inv : player.getInventory().main) {
                                if (inv.isEmpty()) {
                                    player.giveItemStack(getDrop((CardboxTileEntity) tileentity, state.get(sealed)));
                                    worldIn.removeBlockEntity(pos);
                                    worldIn.removeBlock(pos, false);
                                    break;
                                }
                            }
                        }
                    }
                    return ActionResult.CONSUME;
                } else {
                    if (!state.get(sealed)) {
                        if (worldIn.isAir(pos.up())) {
                            BlockEntity tileentity = worldIn.getBlockEntity(pos);
                            if (tileentity instanceof CardboxTileEntity) {
                                player.openHandledScreen((CardboxTileEntity) tileentity);
                            }
                            return ActionResult.CONSUME;
                        }
                    }
                }
            }
        return worldIn.isClient ? ActionResult.SUCCESS : ActionResult.CONSUME;
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext context) {
        return this.getDefaultState().with(face, context.getHorizontalPlayerFacing().getOpposite());
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(sealed, face);
    }

    public BlockEntity createBlockEntity(BlockPos pPos, BlockState pState) {
        return new CardboxTileEntity(pPos, pState);
    }

    @Override
    public void onPlaced(World worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
        super.onPlaced(worldIn, pos, state, placer, stack);
        if (stack.hasNbt()) {
            if (stack.getNbt() != null)
                if (stack.getNbt().contains("sealed"))
                    if (stack.getNbt().getBoolean("sealed"))
                        worldIn.setBlockState(pos, state.with(sealed, true));
        }
    }
    @Override
    public void onDestroyedByExplosion(World worldIn, BlockPos pos, Explosion explosion) {
        CardboxTileEntity te = (CardboxTileEntity) worldIn.getBlockEntity(pos);
        assert te != null;
        if (!te.isEmpty()) {
            NbtCompound compoundnbt = te.saveToNbt(new NbtCompound());
            DefaultedList<ItemStack> invdrop = DefaultedList.ofSize(27, ItemStack.EMPTY);
            Inventories.readNbt(compoundnbt, invdrop);
            ItemScatterer.spawn(worldIn, pos, invdrop);
        }
        ItemScatterer.spawn(worldIn, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(this));
        super.onDestroyedByExplosion(worldIn, pos, explosion);
    }


    @Override
    public void onBreak(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
        CardboxTileEntity te = (CardboxTileEntity) worldIn.getBlockEntity(pos);
        ItemStack is = getDrop(te, state.get(sealed));
        if (!player.isCreative()) ItemScatterer.spawn(worldIn, pos.getX(), pos.getY(), pos.getZ(), is);
        if (!state.get(sealed)) {
            if (!te.isEmpty()) {
                NbtCompound compoundnbt = te.saveToNbt(new NbtCompound());
                DefaultedList<ItemStack> invdrop = DefaultedList.ofSize(27, ItemStack.EMPTY);
                Inventories.readNbt(compoundnbt, invdrop);
                ItemScatterer.spawn(worldIn, pos, invdrop);
            }
        }
        super.onBreak(worldIn, pos, state, player);
    }

    protected ItemStack getDrop(CardboxTileEntity tileentity, boolean sealed) {
        ItemStack itemstack = new ItemStack(this);
        if (tileentity != null && sealed) {
            if (!tileentity.isEmpty()) {
                NbtCompound compoundnbt = tileentity.saveToNbt(new NbtCompound());
                if (!compoundnbt.isEmpty()) itemstack.setSubNbt("BlockEntityTag", compoundnbt);
                if (tileentity.hasCustomName()) itemstack.setCustomName(tileentity.getCustomName());
            }
            if (itemstack.getNbt() != null) itemstack.getNbt().putBoolean("sealed", true);
        }
        return itemstack;
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
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView worldIn, BlockPos pos, ShapeContext context) {
        return VoxelShapes.fullCube();
    }
}
