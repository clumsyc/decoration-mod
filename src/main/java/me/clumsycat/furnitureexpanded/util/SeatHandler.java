package me.clumsycat.furnitureexpanded.util;

import me.clumsycat.furnitureexpanded.entities.SeatEntity;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent;

import java.util.HashMap;
import java.util.Map;

public class SeatHandler {
    public static final Map<ResourceLocation, Map<BlockPos, SeatEntity>> OCCUPIED = new HashMap<>();

    public static boolean addSeatEntity(World world, BlockPos blockPos, SeatEntity entity) {
        if(!world.isClientSide){
            ResourceLocation id = getDimensionTypeId(world);
            if(!OCCUPIED.containsKey(id)) OCCUPIED.put(id, new HashMap<>());
            OCCUPIED.get(id).put(blockPos, entity);
            return true;
        }
        return false;
    }

    public static boolean removeSeatEntity(World world, BlockPos pos) {
        if(!world.isClientSide) {
            ResourceLocation id = getDimensionTypeId(world);
            if(OCCUPIED.containsKey(id)) {
                OCCUPIED.get(id).remove(pos);
                return true;
            }
        }
        return false;
    }

    public static SeatEntity getSeatEntity(World world, BlockPos pos) {
        if(!world.isClientSide) {
            ResourceLocation id = getDimensionTypeId(world);
            if(OCCUPIED.containsKey(id) && OCCUPIED.get(id).containsKey(pos)) return OCCUPIED.get(id).get(pos);
        }
        return null;
    }

    public static boolean isOccupied(World world, BlockPos pos) {
        ResourceLocation id = getDimensionTypeId(world);
        return OCCUPIED.containsKey(id) && OCCUPIED.get(id).containsKey(pos);
    }

    private static ResourceLocation getDimensionTypeId(World world) {
        return world.dimension().location();
    }

    public static void create(World world, BlockPos pos, PlayerEntity player, double offsetY) {
        if(!world.isClientSide && !isOccupied(world, pos)) {
            SeatEntity seat = new SeatEntity(world, pos, player.position(), offsetY);
            if (addSeatEntity(world, pos, seat)) {
                world.addFreshEntity(seat);
                player.startRiding(seat);
            }
        }
    }

    public static void onBreak(BlockEvent.BreakEvent event) {
        if(!event.getWorld().isClientSide()) {
            SeatEntity entity = getSeatEntity((World)event.getWorld(), event.getPos());
            if(entity != null) {
                removeSeatEntity((World)event.getWorld(), event.getPos());
                entity.ejectPassengers();
            }
        }
    }
}
