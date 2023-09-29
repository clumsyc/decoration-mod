package me.clumsycat.furnitureexpanded.util;

import me.clumsycat.furnitureexpanded.entities.SeatEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Map;

public class SeatHandler {
    public static final Map<Identifier, Map<BlockPos, SeatEntity>> OCCUPIED = new HashMap<>();

    public static boolean addSeatEntity(World world, BlockPos blockPos, SeatEntity entity) {
        if(!world.isClient){
            Identifier id = getDimensionTypeId(world);
            if(!OCCUPIED.containsKey(id)) OCCUPIED.put(id, new HashMap<>());
            OCCUPIED.get(id).put(blockPos, entity);
            return true;
        }
        return false;
    }

    public static boolean removeSeatEntity(World world, BlockPos pos) {
        if(!world.isClient) {
            Identifier id = getDimensionTypeId(world);
            if(OCCUPIED.containsKey(id)) {
                OCCUPIED.get(id).remove(pos);
                return true;
            }
        }
        return false;
    }

    public static SeatEntity getSeatEntity(World world, BlockPos pos) {
        if(!world.isClient) {
            Identifier id = getDimensionTypeId(world);
            if(OCCUPIED.containsKey(id) && OCCUPIED.get(id).containsKey(pos)) return OCCUPIED.get(id).get(pos);
        }
        return null;
    }

    public static boolean isOccupied(World world, BlockPos pos) {
        Identifier id = getDimensionTypeId(world);
        return OCCUPIED.containsKey(id) && OCCUPIED.get(id).containsKey(pos);
    }

    private static Identifier getDimensionTypeId(World world) {
        return world.getDimension().effects();
    }

    public static void create(World world, BlockPos pos, PlayerEntity player, Vec3d offset) {
        if(!world.isClient && !isOccupied(world, pos)) {
            SeatEntity seat = new SeatEntity(world, pos, player.getPos(), offset);
            if (addSeatEntity(world, pos, seat)) {
                world.spawnEntity(seat);
                player.startRiding(seat);
            }
        }
    }
}
