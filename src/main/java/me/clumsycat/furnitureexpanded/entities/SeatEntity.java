package me.clumsycat.furnitureexpanded.entities;

import me.clumsycat.furnitureexpanded.Expanded;
import me.clumsycat.furnitureexpanded.registries.RegistryHandler;
import me.clumsycat.furnitureexpanded.util.SeatHandler;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class SeatEntity extends Entity {
    private BlockPos bpos;
    public SeatEntity(EntityType<? extends SeatEntity> type, World worldIn) { super(type, worldIn); }

    public SeatEntity(World world, BlockPos pos, Vec3d playerPos, Vec3d offset) {
        super(Expanded.SEAT, world);
        setPos(pos.getX() + 0.5D + offset.x, pos.getY() + 0.5D + offset.y, pos.getZ() + 0.5D + offset.z);
        noClip = true;
        this.bpos = pos;
    }

    @Override
    public Packet<ClientPlayPacketListener> createSpawnPacket() {
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
        if (this.bpos != null) {
            BlockState state = this.world.getBlockState(this.bpos);
            if (state.isOf(RegistryHandler.TOILET)) {
                Direction direction = state.get(HorizontalFacingBlock.FACING);
                return findDismountSpot(new Vec3d(this.bpos.getX() + 0.5, this.bpos.getY() + 0.5, this.bpos.getZ() + 0.5).offset(direction, 0.75));
            }
            if (state.isOf(RegistryHandler.BATHTUB)) {
                return new Vec3d(this.bpos.getX() + 0.5, this.bpos.getY() + 0.5, this.bpos.getZ() + 0.5);
            }
        }
        return p_230268_1_.getPos();
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
        if (!world.isClient)
            if (!this.hasPassengers())
                this.remove(RemovalReason.DISCARDED);
    }

    private Vec3d findDismountSpot(Vec3d location) {
        BlockPos p1 = BlockPos.ofFloored(location);
        if (!this.world.getBlockState(p1).shouldSuffocate(this.world, p1) && !this.world.getBlockState(p1.up()).shouldSuffocate(this.world, p1.up())) {
            return location;
        } else {
            for (Direction direction : HorizontalFacingBlock.FACING.getValues()) {
                direction = direction.rotateYClockwise();
                p1 = new BlockPos(this.bpos.offset(direction));
                if (!this.world.getBlockState(p1).shouldSuffocate(this.world, p1) && !this.world.getBlockState(p1.up()).shouldSuffocate(this.world, p1.up())) {
                    return new Vec3d(p1.getX() + 0.5D, p1.getY() + 0.5D, p1.getZ() + 0.5D);
                }
            }
            return new Vec3d(this.bpos.getX() + 0.5D, this.bpos.getY() + 0.5D, this.bpos.getZ() + 0.5D);
        }
    }
}
