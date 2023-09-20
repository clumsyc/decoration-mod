package me.clumsycat.furnitureexpanded.entities;

import me.clumsycat.furnitureexpanded.registries.RegistryHandler;
import me.clumsycat.furnitureexpanded.util.SeatHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;

@SuppressWarnings("NullableProblems")
public class SeatEntity extends Entity {
    private BlockPos bpos;
    public SeatEntity(EntityType<SeatEntity> type, Level worldIn) { super(type, worldIn); }

    public SeatEntity(Level world, BlockPos pos, Vec3 playerPos, Vec3 offset) {
        super(RegistryHandler.SEAT.get(), world);
        setPos(pos.getX() + 0.5D + offset.x, pos.getY()-1D + offset.y, pos.getZ() + 0.5D + offset.z);
        noPhysics = true;
        this.bpos = pos;
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public void remove(RemovalReason pReason) {
        super.remove(pReason);
        SeatHandler.removeSeatEntity(this.level(), bpos);
        if (this.isAlive()) this.kill();
    }

    @Override
    public Vec3 getDismountLocationForPassenger(LivingEntity p_230268_1_) {
        if (this.bpos == null) return p_230268_1_.position().add(0, 0.5, 0);
        BlockState state = this.level().getBlockState(this.bpos);
        if (state.is(RegistryHandler.TOILET.get())) {
            Direction direction = state.getValue(HorizontalDirectionalBlock.FACING);
            return findDismountSpot(new Vec3(this.bpos.getX() + 0.5, this.bpos.getY() + 0.5, this.bpos.getZ() + 0.5).relative(direction, 0.75));
        }
        if (state.is(RegistryHandler.BATHTUB.get())) {
            return new Vec3(this.bpos.getX() + 0.5, this.bpos.getY() + 0.5, this.bpos.getZ() + 0.5);
        }
        return p_230268_1_.position();
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
        if (!this.level().isClientSide)
            if (this.getPassengers().isEmpty())
                this.remove(RemovalReason.DISCARDED);
    }

    private Vec3 findDismountSpot(Vec3 location) {
        BlockPos p1 = BlockPos.containing(location);
        if (!this.level().getBlockState(p1).isSuffocating(this.level(), p1) && !this.level().getBlockState(p1.above()).isSuffocating(this.level(), p1.above())) {
            return location;
        } else {
            for (Direction direction : HorizontalDirectionalBlock.FACING.getPossibleValues()) {
                direction = direction.getClockWise();
                p1 = new BlockPos(this.bpos.relative(direction));
                if (!this.level().getBlockState(p1).isSuffocating(this.level(), p1) && !this.level().getBlockState(p1.above()).isSuffocating(this.level(), p1.above())) {
                    return new Vec3(p1.getX() + 0.5D, p1.getY() + 0.5D, p1.getZ() + 0.5D);
                }
            }
            return new Vec3(this.bpos.getX() + 0.5D, this.bpos.getY() + 0.5D, this.bpos.getZ() + 0.5D);
        }
    }
}
