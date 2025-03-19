package me.clumsycat.furnitureexpanded.blocks;

import me.clumsycat.furnitureexpanded.items.FEItemTags;
import me.clumsycat.furnitureexpanded.items.ItemBedsheet;
import me.clumsycat.furnitureexpanded.util.BSProperties;
import me.clumsycat.furnitureexpanded.util.DyeHandler;
import me.clumsycat.furnitureexpanded.util.ModShapes;
import me.clumsycat.furnitureexpanded.util.enums.BedsheetPatterns;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BedBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BedPart;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class ModernBed extends BedBlock {
    private static final DirectionProperty face = HorizontalDirectionalBlock.FACING;
    private static final IntegerProperty dye = BSProperties.DYE_17;
    private static final EnumProperty<BedsheetPatterns> BedPattern = BSProperties.BED_PATTERNS;

    public ModernBed() {
        super(DyeColor.RED, BlockBehaviour.Properties.of()
                .strength(2f, 2f)
                .sound(SoundType.STONE)
                .pushReaction(PushReaction.BLOCK)
        );
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(face, Direction.NORTH)
                .setValue(PART, BedPart.FOOT)
                .setValue(BedPattern, BedsheetPatterns.MUSHROOM)
                .setValue(dye, 16)
                .setValue(OCCUPIED, false));
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new BedBlockEntity(blockPos, blockState);
    }

    @Override
    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        if (worldIn.isClientSide) super.use(state, worldIn, pos, player, handIn, hit);
        ItemStack stack = player.getMainHandItem();
        if (!state.getValue(OCCUPIED)) {
            int dyeColor = state.getValue(dye);
            if (stack.is(FEItemTags.BEDSHEETS)) {
                ItemBedsheet i = (ItemBedsheet) stack.getItem();
                int j = DyeHandler.bedsheetResolver(16, stack, i.getPattern());
                if (!(dyeColor == j && i.getPattern() == state.getValue(BedPattern)) && j < 16) {
                    if (!player.isCreative()) {
                        stack.setCount(stack.getCount() - 1);
                        dropSheet(worldIn, pos, state.getValue(BedPattern), dyeColor);
                    }
                    updateBedColor(worldIn, pos, state, i.getPattern(), j);
                    return InteractionResult.CONSUME;
                }
            }
        }
        super.use(state, worldIn, pos, player, handIn, hit);
        return InteractionResult.CONSUME;
    }

    public static void updateBedColor(Level level, BlockPos pos, BlockState state, BedsheetPatterns pattern, int dyeColor) {
        level.setBlockAndUpdate(pos, state.setValue(dye, dyeColor).setValue(BedPattern, pattern));
        BlockPos spos = pos.relative(state.getValue(face), state.getValue(PART) == BedPart.FOOT ? 1 : -1);
        BlockState sstate = level.getBlockState(spos);
        level.setBlockAndUpdate(spos, sstate.setValue(dye, dyeColor).setValue(BedPattern, pattern));
    }

    public static void dropSheet(Level level, BlockPos pos, BedsheetPatterns pattern, int dyeColor) {
        if (dyeColor != 16)
            Containers.dropItemStack(level, pos.getX(), pos.getY() + .5, pos.getZ(), new ItemStack(DyeHandler.BEDSHEET_DYES.get(pattern).get(DyeColor.byId(dyeColor))));
    }

    @Override
    public void onRemove(BlockState blockState, Level level, BlockPos blockPos, BlockState blockState2, boolean bl) {
        if (!blockState.is(blockState2.getBlock())) {
            if (blockState.getValue(PART) == BedPart.FOOT) dropSheet(level, blockPos, blockState.getValue(BedPattern), blockState.getValue(dye));
            super.onRemove(blockState, level, blockPos, blockState2, bl);
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, PART, BedPattern, dye, OCCUPIED);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        boolean is_top_part = state.getValue(PART) == BedPart.HEAD;
        return switch (state.getValue(face)) {
            case NORTH -> is_top_part ? ModShapes.MODERN_BED_TOP_N : ModShapes.MODERN_BED_BOTTOM_S;
            case EAST -> is_top_part ? ModShapes.MODERN_BED_TOP_E : ModShapes.MODERN_BED_BOTTOM_W;
            case SOUTH -> is_top_part ? ModShapes.MODERN_BED_TOP_S : ModShapes.MODERN_BED_BOTTOM_N;
            default -> is_top_part ? ModShapes.MODERN_BED_TOP_W : ModShapes.MODERN_BED_BOTTOM_E;
        };
    }

    @Override
    public RenderShape getRenderShape(BlockState blockState) {
        return RenderShape.MODEL;
    }
}
