package me.clumsycat.furnitureexpanded.blocks;

import me.clumsycat.furnitureexpanded.blocks.tileentities.FileCabinetTileEntity;
import me.clumsycat.furnitureexpanded.util.BSProperties;
import me.clumsycat.furnitureexpanded.util.ModShapes;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.*;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.explosion.Explosion;

import javax.annotation.Nullable;

public class FileCabinet extends BlockWithEntity {
    public static final DirectionProperty face = HorizontalFacingBlock.FACING;
    public static final IntProperty type = BSProperties.TYPE_0_2;

    public FileCabinet() {
        super(Settings.of(Material.METAL)
                .strength(2f, 2f)
                .sounds(BlockSoundGroup.METAL));
        this.setDefaultState(this.getStateManager().getDefaultState().with(face, Direction.NORTH).with(type, 1));
    }

    @Override
    public ActionResult onUse(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockHitResult hit) {
        if (worldIn.isClient) return ActionResult.SUCCESS;
        else {
            BlockEntity tileentity = worldIn.getBlockEntity(pos);
            if (state.get(type) != 0)
                if (worldIn.getBlockState(pos.down()).getBlock() == this)
                    if (worldIn.getBlockState(pos.down()).get(type) == 0)
                        tileentity = worldIn.getBlockEntity(pos.down());
            if (tileentity instanceof FileCabinetTileEntity) {
                player.openHandledScreen((FileCabinetTileEntity) tileentity);
            }
            return ActionResult.CONSUME;
        }
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World worldIn, BlockState state, BlockEntityType<T> entitytype) {
        return !worldIn.isClient ? (state.get(type) == 0 ? FileCabinetTileEntity::tick : null) : null;
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pPos, BlockState pState) {
        return pState.get(type) == 0 ? new FileCabinetTileEntity(pPos, pState) : null;
    }

    @Override
    public void onBreak(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
        BlockPos finalPos = state.get(type) == 0 ? pos : pos.down();
        boolean isValid = worldIn.getBlockState(finalPos).isOf(this) && worldIn.getBlockState(finalPos).hasBlockEntity();
        if (isValid) dropContents(worldIn, state, pos, finalPos, worldIn.getBlockEntity(finalPos), !player.isCreative());
        super.onBreak(worldIn, pos, state, player);
    }

    @Override
    public void onDestroyedByExplosion(World worldIn, BlockPos pos, Explosion explosion) {
        BlockState state = worldIn.getBlockState(pos);
        BlockPos finalPos = state.get(type) == 0 ? pos : pos.down();
        boolean isValid = worldIn.getBlockState(finalPos).isOf(this) && worldIn.getBlockState(finalPos).hasBlockEntity();
        if (isValid) dropContents(worldIn, state, pos, finalPos, worldIn.getBlockEntity(finalPos), true);
        super.onDestroyedByExplosion(worldIn, pos, explosion);
    }

    private void dropContents(World worldIn, BlockState state, BlockPos pos, BlockPos finalPos, BlockEntity tileentity, boolean dropBlock) {
        if (worldIn.getBlockState(finalPos).isOf(this)) {
            FileCabinetTileEntity te = (FileCabinetTileEntity) tileentity;
            if (!te.isEmpty()) {
                NbtCompound compoundnbt = te.saveToNbt(new NbtCompound());
                DefaultedList<ItemStack> invdrop = DefaultedList.ofSize(54, ItemStack.EMPTY);
                Inventories.readNbt(compoundnbt, invdrop);
                ItemScatterer.spawn(worldIn, finalPos, invdrop);
            }
        }
        if (dropBlock) ItemScatterer.spawn(worldIn, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(this));
        if (worldIn.getBlockState(state.get(type) == 0 ? pos.up() : pos.down()).getBlock() == this.asBlock())
            worldIn.removeBlock(state.get(type) == 0 ? pos.up() : pos.down(), true);
    }

    @Override
    public void onPlaced(World worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
        if (stack.hasCustomName()) {
            BlockEntity tileentity = worldIn.getBlockEntity(pos);
            if (tileentity instanceof FileCabinetTileEntity) {
                ((FileCabinetTileEntity) tileentity).setCustomName(stack.getName());
            }
        }
        worldIn.setBlockState(pos.up(), state.with(type, 1).with(face, state.get(face)), 3);
        super.onPlaced(worldIn, pos, state, placer, stack);
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView worldIn, BlockPos pos) {
        return worldIn.getBlockState(pos.up()).isAir();
    }

    @Override
    public PistonBehavior getPistonBehavior(BlockState state) {
        return PistonBehavior.BLOCK;
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext context) {
        return this.getDefaultState().with(type, 0).with(face, context.getPlayerFacing());
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
        builder.add(type, face);
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
        if (state.get(type) == 0) {
            return switch (state.get(face)) {
                case NORTH -> ModShapes.FILECABINET_N;
                case EAST -> ModShapes.FILECABINET_E;
                case SOUTH -> ModShapes.FILECABINET_S;
                default -> ModShapes.FILECABINET_W;
            };
        } else if (state.get(type) == 2) {
            return switch (state.get(face)) {
                case NORTH -> ModShapes.FILECABINET_OPEN_N;
                case EAST -> ModShapes.FILECABINET_OPEN_E;
                case SOUTH -> ModShapes.FILECABINET_OPEN_S;
                default -> ModShapes.FILECABINET_OPEN_W;
            };
        } else {
            return switch (state.get(face)) {
                case NORTH -> ModShapes.FILECABINET_CLOSED_N;
                case EAST -> ModShapes.FILECABINET_CLOSED_E;
                case SOUTH -> ModShapes.FILECABINET_CLOSED_S;
                default -> ModShapes.FILECABINET_CLOSED_W;
            };
        }
    }
}
