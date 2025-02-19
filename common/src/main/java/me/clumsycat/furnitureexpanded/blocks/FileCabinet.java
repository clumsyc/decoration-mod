package me.clumsycat.furnitureexpanded.blocks;

import me.clumsycat.furnitureexpanded.blocks.tileentities.FileCabinetTileEntity;
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
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class FileCabinet extends BaseEntityBlock {
    public static final DirectionProperty face = HorizontalDirectionalBlock.FACING;
    public static final IntegerProperty type = BSProperties.TYPE_0_2;

    public FileCabinet() {
        super(Properties.of()
                .strength(2f, 2f)
                .sound(SoundType.METAL)
                .pushReaction(PushReaction.BLOCK)
        );
        this.registerDefaultState(this.stateDefinition.any().setValue(face, Direction.NORTH).setValue(type, 1));
    }

    @Override
    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        if (worldIn.isClientSide) return InteractionResult.SUCCESS;
        else {
            BlockEntity tileentity = worldIn.getBlockEntity(pos);
            if (state.getValue(type) != 0)
                if (worldIn.getBlockState(pos.below()).getBlock() == this)
                    if (worldIn.getBlockState(pos.below()).getValue(type) == 0)
                        tileentity = worldIn.getBlockEntity(pos.below());
            if (tileentity instanceof FileCabinetTileEntity) {
                player.openMenu((FileCabinetTileEntity) tileentity);
            }
            return InteractionResult.CONSUME;
        }
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return pState.getValue(type) == 0 ? new FileCabinetTileEntity(pPos, pState) : null;
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState state2, boolean bl) {
        if (!state.is(state2.getBlock())) {
            BlockPos tePos = state.getValue(type) == 0 ? pos : level.getBlockState(pos.below()).is(this) ?
                    level.getBlockState(pos.below()).getValue(type) == 0 ? pos.below() : null : null;
            BlockPos adjPos = state.getValue(type) == 0 ? level.getBlockState(pos.above()).is(this) ?
                    level.getBlockState(pos.above()).getValue(type) != 0 ? pos.above() : null : null : null;

            if (tePos != null) {
                BlockEntity tileentity = level.getBlockEntity(pos);
                if (tileentity instanceof FileCabinetTileEntity te) {
                    if (!te.isEmpty()) {
                        CompoundTag compoundnbt = te.saveToNbt(new CompoundTag());
                        NonNullList<ItemStack> invdrop = NonNullList.withSize(te.getContainerSize(), ItemStack.EMPTY);
                        ContainerHelper.loadAllItems(compoundnbt, invdrop);
                        Containers.dropContents(level, pos, invdrop);
                    }
                }
            }
            if (tePos != null && tePos != pos) level.removeBlock(tePos, false);
            if (adjPos != null && adjPos != pos) level.removeBlock(adjPos, false);
            super.onRemove(state, level, pos, state2, bl);
        }
    }

    @Override
    public void setPlacedBy(Level worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
        if (stack.hasCustomHoverName()) {
            BlockEntity tileentity = worldIn.getBlockEntity(pos);
            if (tileentity instanceof FileCabinetTileEntity te) {
                (te).setCustomName(stack.getDisplayName());
            }
        }
        worldIn.setBlock(pos.above(), state.setValue(type, 1).setValue(face, state.getValue(face)), 3);
        super.setPlacedBy(worldIn, pos, state, placer, stack);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader worldIn, BlockPos pos) {
        return worldIn.getBlockState(pos.above()).is(Blocks.AIR);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(type, 0).setValue(face, context.getHorizontalDirection());
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
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(type, face);
    }

    @Override
    public float getShadeBrightness(BlockState state, BlockGetter worldIn, BlockPos pos) {
        return 0.5f;
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        if (state.getValue(type) == 0) {
            return switch (state.getValue(face)) {
                case NORTH -> ModShapes.FILECABINET_N;
                case EAST -> ModShapes.FILECABINET_E;
                case SOUTH -> ModShapes.FILECABINET_S;
                default -> ModShapes.FILECABINET_W;
            };
        } else if (state.getValue(type) == 2) {
            return switch (state.getValue(face)) {
                case NORTH -> ModShapes.FILECABINET_OPEN_N;
                case EAST -> ModShapes.FILECABINET_OPEN_E;
                case SOUTH -> ModShapes.FILECABINET_OPEN_S;
                default -> ModShapes.FILECABINET_OPEN_W;
            };
        } else {
            return switch (state.getValue(face)) {
                case NORTH -> ModShapes.FILECABINET_CLOSED_N;
                case EAST -> ModShapes.FILECABINET_CLOSED_E;
                case SOUTH -> ModShapes.FILECABINET_CLOSED_S;
                default -> ModShapes.FILECABINET_CLOSED_W;
            };
        }
    }
}
