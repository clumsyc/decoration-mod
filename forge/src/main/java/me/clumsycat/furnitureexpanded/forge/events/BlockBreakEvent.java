package me.clumsycat.furnitureexpanded.forge.events;

import me.clumsycat.furnitureexpanded.entities.SeatEntity;
import me.clumsycat.furnitureexpanded.util.SeatHandler;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.level.BlockEvent;

public class BlockBreakEvent {
    public static void eventHandler(BlockEvent.BreakEvent event) {
        if(!event.getLevel().isClientSide()) {
            SeatEntity entity = SeatHandler.getSeatEntity((Level) event.getLevel(), event.getPos());
            SeatHandler.removeSeatEntity((Level) event.getLevel(), event.getPos());
            if(entity != null)
                entity.kill();
        }
    }
}
