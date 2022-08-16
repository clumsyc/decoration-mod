package me.clumsycat.furnitureexpanded.events;

import me.clumsycat.furnitureexpanded.registries.RegistryHandler;
import me.clumsycat.furnitureexpanded.util.BSProperties;
import me.clumsycat.furnitureexpanded.util.DyeHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Containers;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

public class LeftClickEvent {
    public static void eventHandler(PlayerInteractEvent.LeftClickBlock event) {
        BlockPos pos = event.getPos();
        BlockState state = event.getWorld().getBlockState(pos);
        if (event.getWorld().getBlockState(event.getPos()).is(RegistryHandler.TOILET.get())) {
            if (state.getValue(BSProperties.DYE_17) != 16) {
                if (!event.getPlayer().isCreative()) Containers.dropItemStack(event.getWorld(), pos.getX(), pos.getY()+.5, pos.getZ(), new ItemStack(DyeHandler.CARPET_DYES.get(DyeColor.byId(state.getValue(BSProperties.DYE_17)))));
                event.getWorld().setBlockAndUpdate(pos, state.setValue(BSProperties.DYE_17, 16));
                event.setCanceled(true);
            }
        }
    }
}
