package me.clumsycat.furnitureexpanded.blocks;

import me.clumsycat.furnitureexpanded.util.BSProperties;
import me.clumsycat.furnitureexpanded.util.ModShapes;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundCategory;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.*;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;

import javax.annotation.Nullable;

public class BathroomSink extends Block {
    private static final DirectionProperty face = HorizontalFacingBlock.FACING;
    public static final IntProperty type = BSProperties.TYPE_0_1;

    public BathroomSink() {
        super(FabricBlockSettings.of(Material.AGGREGATE)
                .strength(1f, 2f)
                .sounds(BlockSoundGroup.STONE)
                .nonOpaque()
        );
        this.setDefaultState(this.getStateManager().getDefaultState().with(face, Direction.NORTH).with(type, 0));
    }

    @Override
    public ActionResult onUse(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockHitResult hit) {
        if(player.getMainHandStack().isEmpty()) {
            worldIn.setBlockState(pos, state.with(type, state.get(type) == 0 ? 1 : 0), Block.NOTIFY_ALL);
            worldIn.playSound(player, pos, BlockSoundGroup.LANTERN.getBreakSound(), SoundCategory.BLOCKS, 0.5f, 0.5f);
        }
        return worldIn.isClient ? ActionResult.SUCCESS : ActionResult.CONSUME;
    }

    @Override
    public void afterBreak(World worldIn, PlayerEntity player, BlockPos pos, BlockState state, @Nullable BlockEntity tileentity, ItemStack stack) {
        ItemScatterer.spawn(worldIn, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(this));
        super.afterBreak(worldIn, player, pos, state, tileentity, stack);
    }

    @Override
    public void onDestroyedByExplosion(World worldIn, BlockPos pos, Explosion explosion) {
        ItemScatterer.spawn(worldIn, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(this));
        super.onDestroyedByExplosion(worldIn, pos, explosion);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView worldIn, BlockPos pos, ShapeContext context) {
        return switch (state.get(face)) {
            case NORTH -> ModShapes.BATHROOM_SINK_N;
            case EAST -> ModShapes.BATHROOM_SINK_E;
            case SOUTH -> ModShapes.BATHROOM_SINK_S;
            default -> ModShapes.BATHROOM_SINK_W;
        };
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext context) {
        return this.getDefaultState().with(face, context.getPlayerFacing().getOpposite());
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
        builder.add(face, type);
    }

    @Override
    public float getAmbientOcclusionLightLevel(BlockState state, BlockView world, BlockPos pos) { return 0.5f; }
}
