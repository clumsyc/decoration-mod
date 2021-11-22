package me.clumsycat.furnitureexpanded.events;

import me.clumsycat.furnitureexpanded.registries.RegistryHandler;
import me.clumsycat.furnitureexpanded.util.BSProperties;
import me.clumsycat.furnitureexpanded.util.DyeHandler;
import net.minecraft.block.BlockState;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.DyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

public class LeftClickEvent {
    public static void eventHandler(PlayerInteractEvent.LeftClickBlock event) {
        BlockPos pos = event.getPos();
        BlockState state = event.getWorld().getBlockState(pos);
        if (event.getWorld().getBlockState(event.getPos()).is(RegistryHandler.TOILET.get())) {
            if (state.getValue(BSProperties.TYPE_17) != 16) {
                if (!event.getPlayer().isCreative()) InventoryHelper.dropItemStack(event.getWorld(), pos.getX(), pos.getY()+.5, pos.getZ(), new ItemStack(DyeHandler.CARPET_DYES.get(DyeColor.byId(state.getValue(BSProperties.TYPE_17)))));
                event.getWorld().setBlockAndUpdate(pos, state.setValue(BSProperties.TYPE_17, 16));
                event.setCanceled(true);
            }
        }
    }
}
