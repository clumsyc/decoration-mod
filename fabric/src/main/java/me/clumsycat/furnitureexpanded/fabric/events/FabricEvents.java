package me.clumsycat.furnitureexpanded.fabric.events;

import me.clumsycat.furnitureexpanded.ExpandedMod;
import me.clumsycat.furnitureexpanded.blocks.ModernBed;
import me.clumsycat.furnitureexpanded.entities.SeatEntity;
import me.clumsycat.furnitureexpanded.util.BSProperties;
import me.clumsycat.furnitureexpanded.util.DyeHandler;
import me.clumsycat.furnitureexpanded.util.SeatHandler;
import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class FabricEvents {
    public static void init() {
        AttackBlockCallback.EVENT.register((player, world, hand, pos, direction) -> {
            BlockState state = world.getBlockState(pos);
            boolean isNonSpectatorEmptyHandedBuilder = player.getMainHandItem().isEmpty() && !player.isSpectator() && player.mayBuild();
            if (state.is(ExpandedMod.TOILET.get())) {
                if (isNonSpectatorEmptyHandedBuilder && state.getValue(BSProperties.DYE_17) != 16) {
                    if (!player.isCreative() && !world.isClientSide()) Containers.dropItemStack(world, pos.getX(), pos.getY()+.5, pos.getZ(),
                            new ItemStack(DyeHandler.CARPET_DYES.get(DyeColor.byId(state.getValue(BSProperties.DYE_17)))));
                    world.setBlockAndUpdate(pos, state.setValue(BSProperties.DYE_17, 16));
                    return InteractionResult.CONSUME;
                }
            } else if (state.is(ExpandedMod.MODERN_BED.get())) {
                if (isNonSpectatorEmptyHandedBuilder && state.getValue(BSProperties.DYE_17) != 16) {
                    if (!player.isCreative() && !world.isClientSide())
                        ModernBed.dropSheet(world, pos, state.getValue(BSProperties.BED_PATTERNS), state.getValue(BSProperties.DYE_17));
                    ModernBed.updateBedColor(world, pos, state, state.getValue(BSProperties.BED_PATTERNS), 16);
                    return InteractionResult.CONSUME;
                }
            }
            return InteractionResult.PASS;
        });
    }

    public static void onBreakEvent(Level level, Player player, BlockPos pos, BlockState state, BlockEntity blockEntity) {
        if(!level.isClientSide()) {
            SeatEntity mobentity = SeatHandler.getSeatEntity(level, pos);
            SeatHandler.removeSeatEntity(level, pos);
            if(mobentity != null)
                mobentity.kill();
        }
    }
}


