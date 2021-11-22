package me.clumsycat.furnitureexpanded.blocks.tileentities;

import me.clumsycat.furnitureexpanded.registries.RegistryHandler;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;

import java.util.Date;

public class ClockSignTileEntity extends TileEntity implements ITickableTileEntity {
    public int hour;
    public int minutes;
    public ClockSignTileEntity() {
        super(RegistryHandler.CLOCK_SIGN_TE.get());
    }

    @Override
    public void tick() {
        assert this.level != null;
        Date _date = convertData((this.level.getDayTime() + 9000) * 72);
        hour = _date.getHours(); //TODO: use Java Calendar
        minutes = _date.getMinutes();
    }

    private Date convertData(long parTicks) {
        return new Date(parTicks * 50);
    }

}
