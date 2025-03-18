package me.clumsycat.furnitureexpanded.blocks;

import com.mojang.serialization.MapCodec;
import me.clumsycat.furnitureexpanded.blocks.tileentities.TrashCanTileEntity;
import me.clumsycat.furnitureexpanded.util.BSProperties;
import me.clumsycat.furnitureexpanded.util.ModShapes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class TrashCan extends BaseEntityBlock {
    private static final DirectionProperty face = HorizontalDirectionalBlock.FACING;
    private static final IntegerProperty type = BSProperties.TYPE_0_1;

    public TrashCan(BlockBehaviour.Properties properties) {
        super(Properties.of()
                .strength(1f, 2f)
                .sound(SoundType.METAL)
                .pushReaction(PushReaction.BLOCK)
        );
        this.registerDefaultState(this.getStateDefinition().any().setValue(face, Direction.NORTH).setValue(type, 0));
    }

    public TrashCan() {
        this(null);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return simpleCodec(TrashCan::new);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level worldIn, BlockState state, BlockEntityType<T> entitytype) {
        return !worldIn.isClientSide ? TrashCanTileEntity::tick : null;
    }

    @Override
    public InteractionResult useWithoutItem(BlockState state, Level worldIn, BlockPos pos, Player player, BlockHitResult hit) {
        if (state.getValue(type) == 1) {
            BlockEntity tileentity = worldIn.getBlockEntity(pos);
            if (tileentity instanceof TrashCanTileEntity te) {
                player.openMenu(te);
            }
        }
        return worldIn.isClientSide ? InteractionResult.SUCCESS : InteractionResult.CONSUME;
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState state2, boolean bl) {
        if (!state.is(state2.getBlock())) {
            if (level.getBlockEntity(pos) instanceof TrashCanTileEntity te) {
                if (!te.isEmpty()) {
                    Containers.dropContents(level, pos, te);
                }
            }
            super.onRemove(state, level, pos, state2, bl);
        }
    }

    @Override
    public void entityInside(BlockState state, Level worldIn, BlockPos pos, Entity entity) {
        if (!worldIn.isClientSide) {
            if (entity instanceof Player) {
                if ((entity.getY()-pos.getY()) == .046875 && worldIn.getBlockEntity(pos) instanceof TrashCanTileEntity te) {
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
    public RenderShape getRenderShape(BlockState p_149645_1_) {
        return RenderShape.MODEL;
    }

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
