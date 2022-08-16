package me.clumsycat.furnitureexpanded.entities;

import me.clumsycat.furnitureexpanded.registries.RegistryHandler;
import me.clumsycat.furnitureexpanded.util.SeatHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;

@SuppressWarnings("NullableProblems")
public class SeatEntity extends Entity {
    private BlockPos bpos;
    private Vec3 previousPos;
    public SeatEntity(EntityType<SeatEntity> type, Level worldIn) { super(type, worldIn); }

    public SeatEntity(Level world, BlockPos pos, Vec3 playerPos, double offsetY) {
        super(RegistryHandler.SEAT.get(), world);
        setPos(pos.getX() + 0.5D, pos.getY()-1D + offsetY, pos.getZ() + 0.5D);
        noPhysics = true;
        this.bpos = pos;
        this.previousPos = playerPos;
    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public void remove(RemovalReason pReason) {
        super.remove(pReason);
        SeatHandler.removeSeatEntity(level, bpos);
        if (this.isAlive()) this.kill();
    }

    @Override
    public Vec3 getDismountLocationForPassenger(LivingEntity p_230268_1_) {
        return previousPos != null ? previousPos : p_230268_1_.position();
    }

    @Override
    protected void defineSynchedData() { }

    @Override
    protected void readAdditionalSaveData(CompoundTag p_70037_1_) { }

    @Override
    protected void addAdditionalSaveData(CompoundTag p_213281_1_) { }

    @Override
    public void tick() {
        super.tick();
        if (this.getPassengers().isEmpty())
            if (!level.isClientSide)
                this.remove(RemovalReason.DISCARDED);
    }
}
