package me.clumsycat.furnitureexpanded.blocks;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import me.clumsycat.furnitureexpanded.ExpandedMod;
import me.clumsycat.furnitureexpanded.blocks.tileentities.CardboxTileEntity;
import me.clumsycat.furnitureexpanded.util.BSProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.*;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class Cardbox extends BaseEntityBlock {
    public static final DirectionProperty face = HorizontalDirectionalBlock.FACING;
    public static final BooleanProperty sealed = BSProperties.SEALED;
    public static final MapCodec<Cardbox> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(propertiesCodec()).apply(instance, Cardbox::new));

    public Cardbox(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(face, Direction.NORTH).setValue(sealed, false));
    }

    public Cardbox() {
        this(Properties.of().strength(0.3f, 1f).sound(SoundType.WOOL).pushReaction(PushReaction.BLOCK));
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult blockHitResult) {
        if (stack.is(ExpandedMod.TAPE.get())) {
            if (!state.getValue(sealed)) {
                level.setBlockAndUpdate(pos, state.setValue(sealed, true));
                level.playSound(null, pos, SoundEvents.BEEHIVE_SHEAR, SoundSource.BLOCKS, 1.0f, 1.0f);
                stack.hurtAndBreak(1, player, LivingEntity.getSlotForHand(hand));
            }
        } else if (stack.is(Items.SHEARS) && state.getValue(sealed)) {
            if (state.getValue(sealed)) {
                level.setBlockAndUpdate(pos, state.setValue(sealed, false));
                level.playSound(null, pos, SoundEvents.BEEHIVE_EXIT, SoundSource.BLOCKS, 1.0f, 1.0f);
                stack.hurtAndBreak(3, player, LivingEntity.getSlotForHand(hand));
            }
        } else if (player.isCrouching()) {
            BlockEntity tileentity = level.getBlockEntity(pos);
            if (tileentity instanceof CardboxTileEntity) {
                if (state.getValue(sealed) || ((CardboxTileEntity) tileentity).isEmpty()) {
                    for (ItemStack inv : player.getInventory().items) {
                        if (inv.isEmpty()) {
                            player.addItem(getDrop((CardboxTileEntity) tileentity, state.getValue(sealed)));
                            level.removeBlockEntity(pos);
                            level.removeBlock(pos, false);
                            break;
                        }
                    }
                }
            }
        } else if (!state.getValue(sealed)) {
            if (level.isEmptyBlock(pos.above())) {
                BlockEntity tileentity = level.getBlockEntity(pos);
                if (tileentity instanceof CardboxTileEntity) {
                    player.openMenu((CardboxTileEntity) tileentity);
                }
            }
        }
        return level.isClientSide ? ItemInteractionResult.SUCCESS : ItemInteractionResult.CONSUME;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(face, context.getHorizontalDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(sealed, face);
    }

    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new CardboxTileEntity(pPos, pState);
    }

    @Override
    public void setPlacedBy(Level worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
        super.setPlacedBy(worldIn, pos, state, placer, stack);
        if (stack.getOrDefault(ExpandedMod.COMPONENTS_SEALED.get(), false))
            worldIn.setBlockAndUpdate(pos, state.setValue(sealed, true));
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState state2, boolean bl) {
        if (!state.is(state2.getBlock())) {
            boolean s = state.getValue(sealed);
            BlockEntity tileentity = level.getBlockEntity(pos);
            if (tileentity instanceof CardboxTileEntity te) {
                Player player = te.destroyedBy;
                if (player == null) s = false;
                ItemStack is = getDrop(te, s);
                if (player == null || !player.isCreative()) Containers.dropItemStack(level, pos.getX(), pos.getY(), pos.getZ(), is);
                if (!s) {
                    if (!te.isEmpty()) {
                        Containers.dropContents(level, pos, te);
                    }
                }
            }
            super.onRemove(state, level, pos, state2, bl);
        }
    }

    @Override
    public BlockState playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        if (level.getBlockEntity(pos) instanceof CardboxTileEntity te)
            te.destroyedBy = player;
        return super.playerWillDestroy(level, pos, state, player);
    }

    protected ItemStack getDrop(CardboxTileEntity tileentity, boolean sealed) {
        ItemStack itemstack = new ItemStack(this);
        if (tileentity != null && sealed) {
            if (!tileentity.isEmpty()) {
                itemstack.applyComponents(tileentity.collectComponents());
                if (tileentity.hasCustomName()) itemstack.set(DataComponents.ITEM_NAME, tileentity.getCustomName());
            }
            itemstack.set(ExpandedMod.COMPONENTS_SEALED.get(), true);
        }
        return itemstack;
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
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return Shapes.block();
    }
}
