package me.clumsycat.furnitureexpanded.blocks;

import me.clumsycat.furnitureexpanded.blocks.tileentities.TrashCanTileEntity;
import me.clumsycat.furnitureexpanded.util.BSProperties;
import me.clumsycat.furnitureexpanded.util.ModShapes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

@SuppressWarnings("NullableProblems")
public class TrashCan extends BaseEntityBlock {
    private static final DirectionProperty face = HorizontalDirectionalBlock.FACING;
    private static final IntegerProperty type = BSProperties.TYPE_0_1;

    public TrashCan() {
        super(Properties.of()
                .strength(1f, 2f)
                .sound(SoundType.METAL)
        );
        this.registerDefaultState(this.getStateDefinition().any().setValue(face, Direction.NORTH).setValue(type, 0));
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level worldIn, BlockState state, BlockEntityType<T> entitytype) {
        return !worldIn.isClientSide ? TrashCanTileEntity::tick : null;
    }

    @Override
    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        if (state.getValue(type) == 1) {
            BlockEntity tileentity = worldIn.getBlockEntity(pos);
            if (tileentity instanceof TrashCanTileEntity) {
                player.openMenu((TrashCanTileEntity) tileentity);
            }
        }
        return worldIn.isClientSide ? InteractionResult.SUCCESS : InteractionResult.CONSUME;
    }

    @Override
    public void playerWillDestroy(Level worldIn, BlockPos pos, BlockState state, Player player) {
        dropContents(worldIn, pos, worldIn.getBlockEntity(pos), !player.isCreative());
        super.playerWillDestroy(worldIn, pos, state, player);
    }

    @Override
    public void onBlockExploded(BlockState state, Level worldIn, BlockPos pos, Explosion explosion) {
        dropContents(worldIn, pos, worldIn.getBlockEntity(pos), true);
        super.onBlockExploded(state, worldIn, pos, explosion);
    }

    private void dropContents(Level worldIn, BlockPos pos, BlockEntity tileentity, boolean shouldDrop) {
        TrashCanTileEntity te = (TrashCanTileEntity) tileentity;
        if (!te.isEmpty()) {
            CompoundTag compoundnbt = te.saveToNbt(new CompoundTag());
            NonNullList<ItemStack> invdrop = NonNullList.withSize(9, ItemStack.EMPTY);
            ContainerHelper.loadAllItems(compoundnbt, invdrop);
            Containers.dropContents(worldIn, pos, invdrop);
        }
        if (shouldDrop) Containers.dropItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(this));
    }

    @Override
    public void entityInside(BlockState state, Level worldIn, BlockPos pos, Entity entity) {
        if (!worldIn.isClientSide) {
            if (entity instanceof Player) {
                if ((entity.getY()-pos.getY()) == .046875) {
                    TrashCanTileEntity te = (TrashCanTileEntity) worldIn.getBlockEntity(pos);
                    te.resetCountdown();
                }
            }
        }
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new TrashCanTileEntity(pPos, pState);
    }

    @Override
    public PushReaction getPistonPushReaction(BlockState p_149656_1_) {
        return PushReaction.BLOCK;
    }

    @Override
    public RenderShape getRenderShape(BlockState p_149645_1_) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(face, context.getHorizontalDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
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
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        boolean s = state.getValue(type) == 0;
        return switch (state.getValue(face)) {
            case NORTH -> s ? ModShapes.TRASH_CAN_N : ModShapes.TRASH_CAN_NOLID_N;
            case EAST -> s ? ModShapes.TRASH_CAN_E : ModShapes.TRASH_CAN_NOLID_E;
            case SOUTH -> s ? ModShapes.TRASH_CAN_S : ModShapes.TRASH_CAN_NOLID_S;
            default -> s ? ModShapes.TRASH_CAN_W : ModShapes.TRASH_CAN_NOLID_W;
        };
    }
}
