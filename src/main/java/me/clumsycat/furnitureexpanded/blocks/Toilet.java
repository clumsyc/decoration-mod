package me.clumsycat.furnitureexpanded.blocks;

import me.clumsycat.furnitureexpanded.util.BSProperties;
import me.clumsycat.furnitureexpanded.util.DyeHandler;
import me.clumsycat.furnitureexpanded.util.ModShapes;
import me.clumsycat.furnitureexpanded.util.SeatHandler;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundCategory;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.*;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;

public class Toilet extends Block {
    private static final IntProperty type = BSProperties.TYPE_0_1;
    private static final DirectionProperty face = HorizontalFacingBlock.FACING;
    private static final IntProperty dye = BSProperties.DYE_17;

    public Toilet() {
        super(FabricBlockSettings.create()
                .strength(1f, 2f)
                .sounds(BlockSoundGroup.STONE)
                .nonOpaque()
                .pistonBehavior(PistonBehavior.BLOCK)
        );
        this.setDefaultState(this.getStateManager().getDefaultState().with(face, Direction.NORTH).with(type, 0).with(dye, 16));
    }

    @Override
    public ActionResult onUse(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockHitResult hit) {
        if (worldIn.isClient) return ActionResult.SUCCESS;
        ItemStack stack = player.getMainHandStack();
        if (!SeatHandler.isOccupied(worldIn, pos)) {
            if (stack.isIn(ItemTags.WOOL_CARPETS)) {
                int j = DyeHandler.carpetResolver(16, stack);
                if (state.get(dye) != j && j < 16) {
                    if (!player.isCreative()) {
                        stack.setCount(stack.getCount() - 1);
                        if (state.get(dye) != 16)
                            ItemScatterer.spawn(worldIn, pos.getX(), pos.getY() + .5, pos.getZ(), new ItemStack(DyeHandler.CARPET_DYES.get(DyeColor.byId(state.get(dye)))));
                    }
                    worldIn.setBlockState(pos, state.with(dye, j), Block.NOTIFY_ALL);
                }
            } else if (stack.isEmpty()) {
                if (state.get(type) == 0) worldIn.setBlockState(pos, state.with(type, 1), Block.NOTIFY_ALL);
                else {
                    int y = hit.getBlockPos().getY();
                    if (hit.getSide() == Direction.UP && hit.getPos().y == (y + .625) && !player.isSneaking())
                        SeatHandler.create(worldIn, pos, player, new Vec3d(0, -0.11, 0).offset(state.get(face), 0.05));
                    else worldIn.setBlockState(pos, state.with(type, 0), Block.NOTIFY_ALL);
                }
                worldIn.playSound(null, pos, BlockSoundGroup.WOOD.getBreakSound(), SoundCategory.BLOCKS, 0.5f, 0.5f);
            }
        }
        return ActionResult.CONSUME;
    }

    @Override
    public void onBlockBreakStart(BlockState state, World worldIn, BlockPos pos, PlayerEntity player) {
        super.onBlockBreakStart(state, worldIn, pos, player);
        if (!player.isCreative()) {
            if (state.isOf(this)) {
                if (state.get(BSProperties.DYE_17) != 16) {
                    ItemScatterer.spawn(worldIn, pos.getX(), pos.getY()+.5, pos.getZ(), new ItemStack(DyeHandler.CARPET_DYES.get(DyeColor.byId(state.get(BSProperties.DYE_17)))));
                    worldIn.setBlockState(pos, state.with(BSProperties.DYE_17, 16), 3);
                }
            }
        }
    }

    @Override
    public void afterBreak(World worldIn, PlayerEntity player, BlockPos pos, BlockState state, BlockEntity tileentity, ItemStack stack) {
        ItemScatterer.spawn(worldIn, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(this));
        if (state.get(dye) >= 0 && state.get(dye) < 16) ItemScatterer.spawn(worldIn, pos.getX(), pos.getY() + .5, pos.getZ(), new ItemStack(DyeHandler.CARPET_DYES.get(DyeColor.byId(state.get(dye)))));
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
    public BlockState getPlacementState(ItemPlacementContext context) {
        return this.getDefaultState().with(face, context.getHorizontalPlayerFacing().getOpposite());
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(face, type, dye);
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
    public float getAmbientOcclusionLightLevel(BlockState state, BlockView world, BlockPos pos) {
        return 0.5f;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView worldIn, BlockPos pos, ShapeContext context) {
        return switch (state.get(face)) {
            case NORTH -> ModShapes.TOILET_N;
            case EAST -> ModShapes.TOILET_E;
            case SOUTH -> ModShapes.TOILET_S;
            default -> ModShapes.TOILET_W;
        };
    }
}
