package me.clumsycat.furnitureexpanded.entities;

import me.clumsycat.furnitureexpanded.Expanded;
import me.clumsycat.furnitureexpanded.util.SeatHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class SeatEntity extends Entity {
    private BlockPos bpos;
    private Vec3d previousPos;
    public SeatEntity(EntityType<? extends SeatEntity> type, World worldIn) { super(type, worldIn); }

    public SeatEntity(World world, BlockPos pos, Vec3d playerPos, double offsetY) {
        super(Expanded.SEAT, world);
        setPos(pos.getX() + 0.5D, pos.getY()-1D + offsetY, pos.getZ() + 0.5D);
        noClip = true;
        this.bpos = pos;
        this.previousPos = playerPos;
    }

    @Override
    public Packet<?> createSpawnPacket() {
        return new EntitySpawnS2CPacket(this);
    }

    @Override
    public void remove(RemovalReason pReason) {
        super.remove(pReason);
        SeatHandler.removeSeatEntity(world, bpos);
        if (this.isAlive()) this.kill();
    }

    @Override
    public Vec3d updatePassengerForDismount(LivingEntity p_230268_1_) {
        return previousPos != null ? previousPos : p_230268_1_.getPos();
    }

    @Override
    protected void initDataTracker() { }

    @Override
    protected void readCustomDataFromNbt(NbtCompound p_70037_1_) { }

    @Override
    protected void writeCustomDataToNbt(NbtCompound p_213281_1_) { }

    @Override
    public void tick() {
        super.tick();
        if (!this.hasPlayerRider())
            if (!world.isClient)
                this.remove(RemovalReason.DISCARDED);
    }
}
