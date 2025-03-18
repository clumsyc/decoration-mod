package me.clumsycat.furnitureexpanded.blocks;

import com.mojang.serialization.MapCodec;
import me.clumsycat.furnitureexpanded.blocks.tileentities.NightstandTileEntity;
import me.clumsycat.furnitureexpanded.util.ModShapes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class Nightstand extends BaseEntityBlock {
    public static final DirectionProperty face = HorizontalDirectionalBlock.FACING;
    public static final BooleanProperty open = BlockStateProperties.OPEN;

    public Nightstand(BlockBehaviour.Properties properties) {
        super(BlockBehaviour.Properties.of()
                .strength(2f, 2f)
                .sound(SoundType.WOOD)
                .pushReaction(PushReaction.BLOCK)
        );
        this.registerDefaultState(this.stateDefinition.any().setValue(face, Direction.NORTH).setValue(open, false));
    }

    public Nightstand() {
        this(null);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return simpleCodec(Nightstand::new);
    }

    @Override
    public InteractionResult useWithoutItem(BlockState state, Level worldIn, BlockPos pos, Player player, BlockHitResult hit) {
        if (worldIn.isClientSide) return InteractionResult.SUCCESS;
        BlockEntity tileentity = worldIn.getBlockEntity(pos);
        if (tileentity instanceof NightstandTileEntity te)
            player.openMenu(te);
        return InteractionResult.CONSUME;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new NightstandTileEntity(pos, state);
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState state2, boolean bl) {
        if (!state.is(state2.getBlock())) {
            BlockEntity tileentity = level.getBlockEntity(pos);
            if (tileentity instanceof NightstandTileEntity te) {
                if (!te.isEmpty()) {
                    Containers.dropContents(level, pos, te);
                }
            }
            super.onRemove(state, level, pos, state2, bl);
        }
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(face, context.getHorizontalDirection());
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
        builder.add(face, open);
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
        return switch (state.getValue(face)) {
            case NORTH -> ModShapes.NIGHTSTAND_N;
            case EAST -> ModShapes.NIGHTSTAND_E;
            case SOUTH -> ModShapes.NIGHTSTAND_S;
            default -> ModShapes.NIGHTSTAND_W;
        };
    }
}
