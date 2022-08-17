package me.clumsycat.furnitureexpanded.util;

import me.clumsycat.furnitureexpanded.entities.SeatEntity;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.level.BlockEvent;

import java.util.HashMap;
import java.util.Map;

public class SeatHandler {
    public static final Map<ResourceLocation, Map<BlockPos, SeatEntity>> OCCUPIED = new HashMap<>();

    public static boolean addSeatEntity(Level world, BlockPos blockPos, SeatEntity entity) {
        if(!world.isClientSide){
            ResourceLocation id = getDimensionTypeId(world);
            if(!OCCUPIED.containsKey(id)) OCCUPIED.put(id, new HashMap<>());
            OCCUPIED.get(id).put(blockPos, entity);
            return true;
        }
        return false;
    }

    @SuppressWarnings("UnusedReturnValue")
    public static boolean removeSeatEntity(Level world, BlockPos pos) {
        if(!world.isClientSide) {
            ResourceLocation id = getDimensionTypeId(world);
            if(OCCUPIED.containsKey(id)) {
                OCCUPIED.get(id).remove(pos);
                return true;
            }
        }
        return false;
    }

    public static SeatEntity getSeatEntity(Level world, BlockPos pos) {
        if(!world.isClientSide) {
            ResourceLocation id = getDimensionTypeId(world);
            if(OCCUPIED.containsKey(id) && OCCUPIED.get(id).containsKey(pos)) return OCCUPIED.get(id).get(pos);
        }
        return null;
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public static boolean isOccupied(Level world, BlockPos pos) {
        ResourceLocation id = getDimensionTypeId(world);
        return OCCUPIED.containsKey(id) && OCCUPIED.get(id).containsKey(pos);
    }

    private static ResourceLocation getDimensionTypeId(Level world) {
        return world.dimension().location();
    }

    public static void create(Level world, BlockPos pos, Player player, double offsetY) {
        if(!world.isClientSide && !isOccupied(world, pos)) {
            SeatEntity seat = new SeatEntity(world, pos, player.position(), offsetY);
            if (addSeatEntity(world, pos, seat)) {
                world.addFreshEntity(seat);
                player.startRiding(seat);
            }
        }
    }

    public static void onBreak(BlockEvent.BreakEvent event) {
        if(!event.getLevel().isClientSide()) {
            SeatEntity entity = getSeatEntity((Level) event.getLevel(), event.getPos());
            if(entity != null) {
                removeSeatEntity((Level) event.getLevel(), event.getPos());
                entity.ejectPassengers();
            }
        }
    }
}
