package me.clumsycat.furnitureexpanded.entities;

import me.clumsycat.furnitureexpanded.registries.RegistryHandler;
import me.clumsycat.furnitureexpanded.util.SeatHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class SeatEntity extends Entity {
    private BlockPos bpos;
    private Vector3d previousPos;
    public SeatEntity(EntityType<SeatEntity> type, World worldIn) { super(type, worldIn); }

    public SeatEntity(World world, BlockPos pos, Vector3d playerPos, double offsetY) {
        super(RegistryHandler.SEAT.get(), world);
        setPos(pos.getX() + 0.5D, pos.getY()-1D + offsetY, pos.getZ() + 0.5D);
        noPhysics = true;
        this.bpos = pos;
        this.previousPos = playerPos;
    }

    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public void remove() {
        super.remove();
        SeatHandler.removeSeatEntity(level, bpos);
        if (this.isAlive()) this.kill();
    }

    @Override
    public Vector3d getDismountLocationForPassenger(LivingEntity p_230268_1_) {
        return previousPos != null ? previousPos : p_230268_1_.position();
    }

    @Override
    protected void defineSynchedData() { }

    @Override
    protected void readAdditionalSaveData(CompoundNBT p_70037_1_) { }

    @Override
    protected void addAdditionalSaveData(CompoundNBT p_213281_1_) { }

    @Override
    public void tick() {
        super.tick();
        if (this.getPassengers().isEmpty())
            if (!level.isClientSide)
                this.remove();
    }

    //TODO: improve previous position to find a good spot.
    //TODO: Limit body/head turning too much when sitting.
}
