package me.clumsycat.furnitureexpanded.blocks;

import me.clumsycat.furnitureexpanded.util.BSProperties;
import me.clumsycat.furnitureexpanded.util.DyeHandler;
import me.clumsycat.furnitureexpanded.util.ModShapes;
import me.clumsycat.furnitureexpanded.util.SeatHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;


@SuppressWarnings("NullableProblems")
public class Toilet extends Block {
    private static final IntegerProperty type = BSProperties.TYPE_0_1;
    private static final DirectionProperty face = HorizontalDirectionalBlock.FACING;
    private static final IntegerProperty dye = BSProperties.DYE_17;


    public Toilet() {
        super(Block.Properties.of(Material.CLAY)
                .strength(1f, 2f)
                .sound(SoundType.STONE));
        this.registerDefaultState(this.getStateDefinition().any().setValue(face, Direction.NORTH).setValue(type, 0).setValue(dye, 16));
    }

    @Override
    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        if (worldIn.isClientSide) return InteractionResult.SUCCESS;
        ItemStack stack = player.getMainHandItem();
        if (!SeatHandler.isOccupied(worldIn, pos)) {
            if (stack.is(ItemTags.WOOL_CARPETS)) {
                int j = DyeHandler.carpetResolver(16, stack);
                if (state.getValue(dye) != j && j < 16) {
                    if (!player.isCreative()) {
                        stack.setCount(stack.getCount() - 1);
                        if (state.getValue(dye) != 16)
                            Containers.dropItemStack(worldIn, pos.getX(), pos.getY() + .5, pos.getZ(), new ItemStack(DyeHandler.CARPET_DYES.get(DyeColor.byId(state.getValue(dye)))));
                    }
                    worldIn.setBlockAndUpdate(pos, state.setValue(dye, j));
                }
            } else if (stack.isEmpty()) {
                if (state.getValue(type) == 0) worldIn.setBlockAndUpdate(pos, state.setValue(type, 1));
                else {
                    int y = hit.getBlockPos().getY();
                    if (hit.getDirection() == Direction.UP && hit.getLocation().y == (y + .625) && !player.isCrouching()) SeatHandler.create(worldIn, pos, player, Vec3.ZERO);
                    else worldIn.setBlockAndUpdate(pos, state.setValue(type, 0));
                }
                worldIn.playSound(null, pos, SoundType.WOOD.getBreakSound(), SoundSource.BLOCKS, 0.5f, 0.5f);
            }
        }
        return InteractionResult.CONSUME;
    }

    @Override
    public void playerDestroy(Level worldIn, Player player, BlockPos pos, BlockState state, @Nullable BlockEntity tileentity, ItemStack stack) {
        Containers.dropItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(this));
        super.playerDestroy(worldIn, player, pos, state, tileentity, stack);
    }

    @Override
    public void onBlockExploded(BlockState state, Level worldIn, BlockPos pos, Explosion explosion) {
        Containers.dropItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(this));
        super.onBlockExploded(state, worldIn, pos, explosion);
    }

    @Override
    public PushReaction getPistonPushReaction(BlockState p_149656_1_) {
        return PushReaction.BLOCK;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(face, context.getHorizontalDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
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
    public float getShadeBrightness(BlockState p_220080_1_, BlockGetter p_220080_2_, BlockPos p_220080_3_) {
        return 0.5f;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return switch (state.getValue(face)) {
            case NORTH -> ModShapes.TOILET_N;
            case EAST -> ModShapes.TOILET_E;
            case SOUTH -> ModShapes.TOILET_S;
            default -> ModShapes.TOILET_W;
        };
    }
}
