package me.clumsycat.furnitureexpanded.blocks;

import me.clumsycat.furnitureexpanded.blocks.tileentities.TrashCanTileEntity;
import me.clumsycat.furnitureexpanded.util.BSProperties;
import me.clumsycat.furnitureexpanded.util.ModShapes;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.entity.Entity;
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
import net.minecraft.world.explosion.Explosion;

public class TrashCan extends BlockWithEntity {
    private static final DirectionProperty face = HorizontalFacingBlock.FACING;
    private static final IntProperty type = BSProperties.TYPE_0_1;

    public TrashCan() {
        super(Settings.of(Material.METAL)
                .strength(1f, 2f)
                .sounds(BlockSoundGroup.METAL)
        );
        this.setDefaultState(this.getStateManager().getDefaultState().with(face, Direction.NORTH).with(type, 0));
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World worldIn, BlockState state, BlockEntityType<T> entitytype) {
        return !worldIn.isClient ? TrashCanTileEntity::tick : null;
    }

    @Override
    public ActionResult onUse(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockHitResult hit) {
        if (state.get(type) == 1) {
            BlockEntity tileentity = worldIn.getBlockEntity(pos);
            if (tileentity instanceof TrashCanTileEntity) {
                player.openHandledScreen((TrashCanTileEntity) tileentity);
            }
        }
        return worldIn.isClient ? ActionResult.SUCCESS : ActionResult.CONSUME;
    }

    @Override
    public void onBreak(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
        dropContents(worldIn, pos, worldIn.getBlockEntity(pos), !player.isCreative());
        super.onBreak(worldIn, pos, state, player);
    }

    @Override
    public void onDestroyedByExplosion(World worldIn, BlockPos pos, Explosion explosion) {
        dropContents(worldIn, pos, worldIn.getBlockEntity(pos), true);
        super.onDestroyedByExplosion(worldIn, pos, explosion);
    }

    private void dropContents(World worldIn, BlockPos pos, BlockEntity tileentity, boolean shouldDrop) {
        TrashCanTileEntity te = (TrashCanTileEntity) tileentity;
        if (!te.isEmpty()) {
            NbtCompound compoundnbt = te.saveToNbt(new NbtCompound());
            DefaultedList<ItemStack> invdrop = DefaultedList.ofSize(9, ItemStack.EMPTY);
            Inventories.readNbt(compoundnbt, invdrop);
            ItemScatterer.spawn(worldIn, pos, invdrop);
        }
        if (shouldDrop) ItemScatterer.spawn(worldIn, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(this));
    }

    @Override
    public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entity) {
        if (!worldIn.isClient) {
            if (entity instanceof PlayerEntity) {
                if ((entity.getY()-pos.getY()) == .046875) {
                    TrashCanTileEntity te = (TrashCanTileEntity) worldIn.getBlockEntity(pos);
                    te.resetCountdown();
                }
            }
        }
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pPos, BlockState pState) {
        return new TrashCanTileEntity(pPos, pState);
    }

    @Override
    public PistonBehavior getPistonBehavior(BlockState p_149656_1_) {
        return PistonBehavior.BLOCK;
    }

    @Override
    public BlockRenderType getRenderType(BlockState p_149645_1_) {
        return BlockRenderType.MODEL;
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext context) {
        return this.getDefaultState().with(face, context.getPlayerFacing().getOpposite());
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(face, type);
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
        boolean s = state.get(type) == 0;
        return switch (state.get(face)) {
            case NORTH -> s ? ModShapes.TRASH_CAN_N : ModShapes.TRASH_CAN_NOLID_N;
            case EAST -> s ? ModShapes.TRASH_CAN_E : ModShapes.TRASH_CAN_NOLID_E;
            case SOUTH -> s ? ModShapes.TRASH_CAN_S : ModShapes.TRASH_CAN_NOLID_S;
            default -> s ? ModShapes.TRASH_CAN_W : ModShapes.TRASH_CAN_NOLID_W;
        };
    }
}
