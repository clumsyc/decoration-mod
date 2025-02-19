package me.clumsycat.furnitureexpanded.forge.events;

import me.clumsycat.furnitureexpanded.ExpandedMod;
import me.clumsycat.furnitureexpanded.blocks.ModernBed;
import me.clumsycat.furnitureexpanded.util.BSProperties;
import me.clumsycat.furnitureexpanded.util.DyeHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Containers;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

public class LeftClickEvent {
    public static void eventHandler(PlayerInteractEvent.LeftClickBlock event) {
        Level level = event.getLevel();
        BlockPos pos = event.getPos();
        BlockState state = level.getBlockState(pos);
        if (state.is(ExpandedMod.TOILET.get())) {
            if (state.getValue(BSProperties.DYE_17) != 16) {
                if (!event.getEntity().isCreative()) Containers.dropItemStack(level, pos.getX(), pos.getY()+.5, pos.getZ(), new ItemStack(DyeHandler.CARPET_DYES.get(DyeColor.byId(state.getValue(BSProperties.DYE_17)))));
                level.setBlockAndUpdate(pos, state.setValue(BSProperties.DYE_17, 16));
                event.setCanceled(true);
            }
        } else if (state.is(ExpandedMod.MODERN_BED.get())) {
            if (state.getValue(BSProperties.DYE_17) != 16) {
                if (!event.getEntity().isCreative())
                    ModernBed.dropSheet(level, pos, state.getValue(BSProperties.BED_PATTERNS), state.getValue(BSProperties.DYE_17));
                ModernBed.updateBedColor(level, pos, state, state.getValue(BSProperties.BED_PATTERNS), 16);
                event.setCanceled(true);
            }
        }
    }
}
