package me.clumsycat.furnitureexpanded.util;

import me.clumsycat.furnitureexpanded.util.enums.WallHeight;
import me.clumsycat.furnitureexpanded.util.enums.WallSide;
import net.minecraft.block.Block;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;

import java.util.stream.Stream;

public class ModShapes {

    // ==============
    //   Shower Box
    // ==============
    public static final VoxelShape SHOWER_BOX_TOP_CLOSED = Stream.of(Block.createCuboidShape(15, 0, 0, 16, 16, 16), Block.createCuboidShape(0, 0, 0, 1, 16, 16), Block.createCuboidShape(1, 0, 15, 15, 16, 16), Block.createCuboidShape(1, 0, 0, 15, 16, 1)).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();

    public static final VoxelShape SHOWER_BOX_TOP_OPEN_N = Stream.of(Block.createCuboidShape(0, 0, 0, 1, 16, 16), Block.createCuboidShape(1, 0, 15, 15, 16, 16), Block.createCuboidShape(1, 15, 0, 15, 16, 1), Block.createCuboidShape(15, 0, 0, 16, 16, 16)).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();
    public static final VoxelShape SHOWER_BOX_TOP_OPEN_E = Stream.of(Block.createCuboidShape(0, 0, 0, 16, 16, 1), Block.createCuboidShape(0, 0, 1, 1, 16, 15), Block.createCuboidShape(15, 15, 1, 16, 16, 15), Block.createCuboidShape(0, 0, 15, 16, 16, 16)).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();
    public static final VoxelShape SHOWER_BOX_TOP_OPEN_S = Stream.of(Block.createCuboidShape(15, 0, 0, 16, 16, 16), Block.createCuboidShape(1, 0, 0, 15, 16, 1), Block.createCuboidShape(1, 15, 15, 15, 16, 16), Block.createCuboidShape(0, 0, 0, 1, 16, 16)).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();
    public static final VoxelShape SHOWER_BOX_TOP_OPEN_W = Stream.of(Block.createCuboidShape(0, 0, 15, 16, 16, 16), Block.createCuboidShape(15, 0, 1, 16, 16, 15), Block.createCuboidShape(0, 15, 1, 1, 16, 15), Block.createCuboidShape(0, 0, 0, 16, 16, 1)).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();

    public static final VoxelShape SHOWER_BOX_BOTTOM_CLOSED = Stream.of(Block.createCuboidShape(15, 0, 0, 16, 16, 16), Block.createCuboidShape(1, 0, 1, 15, 1, 15), Block.createCuboidShape(1, 0, 0, 15, 16, 1), Block.createCuboidShape(0, 0, 0, 1, 16, 16), Block.createCuboidShape(1, 0, 15, 15, 16, 16)).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();

    public static final VoxelShape SHOWER_BOX_BOTTOM_OPEN_N = Stream.of(Block.createCuboidShape(1, 0, 1, 15, 1, 15), Block.createCuboidShape(1, 0, 0, 15, 2, 1), Block.createCuboidShape(0, 0, 0, 1, 16, 16), Block.createCuboidShape(1, 0, 15, 15, 16, 16), Block.createCuboidShape(15, 0, 0, 16, 16, 16)).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();
    public static final VoxelShape SHOWER_BOX_BOTTOM_OPEN_E = Stream.of(Block.createCuboidShape(1, 0, 1, 15, 1, 15), Block.createCuboidShape(15, 0, 1, 16, 2, 15), Block.createCuboidShape(0, 0, 0, 16, 16, 1), Block.createCuboidShape(0, 0, 1, 1, 16, 15), Block.createCuboidShape(0, 0, 15, 16, 16, 16)).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();
    public static final VoxelShape SHOWER_BOX_BOTTOM_OPEN_S = Stream.of(Block.createCuboidShape(1, 0, 1, 15, 1, 15), Block.createCuboidShape(1, 0, 15, 15, 2, 16), Block.createCuboidShape(15, 0, 0, 16, 16, 16), Block.createCuboidShape(1, 0, 0, 15, 16, 1), Block.createCuboidShape(0, 0, 0, 1, 16, 16)).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();
    public static final VoxelShape SHOWER_BOX_BOTTOM_OPEN_W = Stream.of(Block.createCuboidShape(1, 0, 1, 15, 1, 15), Block.createCuboidShape(0, 0, 1, 1, 2, 15), Block.createCuboidShape(0, 0, 15, 16, 16, 16), Block.createCuboidShape(15, 0, 1, 16, 16, 15), Block.createCuboidShape(0, 0, 0, 16, 16, 1)).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();


    // ==============
    //   Shower Head
    // ==============
    public static final VoxelShape SHOWER_HEAD_N = Stream.of(Block.createCuboidShape(5.5, 5, 15, 10.5, 10, 16), Block.createCuboidShape(9.75, 1, 15.25, 10.25, 5, 15.75), Block.createCuboidShape(5.5, 10, 13, 10.5, 12, 16), Block.createCuboidShape(5.5, 11, 4, 10.5, 12, 13)).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();
    public static final VoxelShape SHOWER_HEAD_E = Stream.of(Block.createCuboidShape(0, 5, 5.5, 1, 10, 10.5), Block.createCuboidShape(0.25, 1, 9.75, 0.75, 5, 10.25), Block.createCuboidShape(0, 10, 5.5, 3, 12, 10.5), Block.createCuboidShape(3, 11, 5.5, 12, 12, 10.5)).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();
    public static final VoxelShape SHOWER_HEAD_S = Stream.of(Block.createCuboidShape(5.5, 5, 0, 10.5, 10, 1), Block.createCuboidShape(5.75, 1, 0.25, 6.25, 5, 0.75), Block.createCuboidShape(5.5, 10, 0, 10.5, 12, 3), Block.createCuboidShape(5.5, 11, 3, 10.5, 12, 12)).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();
    public static final VoxelShape SHOWER_HEAD_W = Stream.of(Block.createCuboidShape(15, 5, 5.5, 16, 10, 10.5), Block.createCuboidShape(15.25, 1, 5.75, 15.75, 5, 6.25), Block.createCuboidShape(13, 10, 5.5, 16, 12, 10.5), Block.createCuboidShape(4, 11, 5.5, 13, 12, 10.5)).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();


    // ==============
    //   Bath Tub
    // ==============

    public static final VoxelShape BATHTUB_TOP_N = Stream.of(Block.createCuboidShape(11.75, 0, 8, 12.75, 2, 11), Block.createCuboidShape(3.25, 0, 8, 4.25, 2, 11), Block.createCuboidShape(15, 12, 10, 16, 13, 12), Block.createCuboidShape(0, 12, 10, 1, 13, 12), Block.createCuboidShape(0, 10, 0, 1, 11, 6), Block.createCuboidShape(15, 10, 0, 16, 11, 6), Block.createCuboidShape(0, 11, 6, 2, 12, 10), Block.createCuboidShape(14, 11, 6, 16, 12, 10), Block.createCuboidShape(3, 2, 0, 13, 3, 13), Block.createCuboidShape(1, 5, 10, 2, 13, 14), Block.createCuboidShape(3, 7, 15, 13, 14, 16), Block.createCuboidShape(4, 5, 14, 12, 7, 15), Block.createCuboidShape(2, 4, 13, 14, 5, 14), Block.createCuboidShape(3, 3, 13, 13, 4, 14), Block.createCuboidShape(14, 5, 10, 15, 13, 14), Block.createCuboidShape(2, 5, 14, 4, 14, 15), Block.createCuboidShape(12, 5, 14, 14, 14, 15), Block.createCuboidShape(2, 3, 0, 3, 5, 13), Block.createCuboidShape(1, 5, 0, 2, 11, 10), Block.createCuboidShape(14, 5, 0, 15, 11, 10), Block.createCuboidShape(13, 3, 0, 14, 5, 13)).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();
    public static final VoxelShape BATHTUB_TOP_E = Stream.of(Block.createCuboidShape(5, 0, 11.75, 8, 2, 12.75), Block.createCuboidShape(5, 0, 3.25, 8, 2, 4.25), Block.createCuboidShape(4, 12, 15, 6, 13, 16), Block.createCuboidShape(4, 12, 0, 6, 13, 1), Block.createCuboidShape(10, 10, 0, 16, 11, 1), Block.createCuboidShape(10, 10, 15, 16, 11, 16), Block.createCuboidShape(6, 11, 0, 10, 12, 2), Block.createCuboidShape(6, 11, 14, 10, 12, 16), Block.createCuboidShape(3, 2, 3, 16, 3, 13), Block.createCuboidShape(2, 5, 1, 6, 13, 2), Block.createCuboidShape(0, 7, 3, 1, 14, 13), Block.createCuboidShape(1, 5, 4, 2, 7, 12), Block.createCuboidShape(2, 4, 2, 3, 5, 14), Block.createCuboidShape(2, 3, 3, 3, 4, 13), Block.createCuboidShape(2, 5, 14, 6, 13, 15), Block.createCuboidShape(1, 5, 2, 2, 14, 4), Block.createCuboidShape(1, 5, 12, 2, 14, 14), Block.createCuboidShape(3, 3, 2, 16, 5, 3), Block.createCuboidShape(6, 5, 1, 16, 11, 2), Block.createCuboidShape(6, 5, 14, 16, 11, 15), Block.createCuboidShape(3, 3, 13, 16, 5, 14)).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();
    public static final VoxelShape BATHTUB_TOP_S = Stream.of(Block.createCuboidShape(3.25, 0, 5, 4.25, 2, 8), Block.createCuboidShape(11.75, 0, 5, 12.75, 2, 8), Block.createCuboidShape(0, 12, 4, 1, 13, 6), Block.createCuboidShape(15, 12, 4, 16, 13, 6), Block.createCuboidShape(15, 10, 10, 16, 11, 16), Block.createCuboidShape(0, 10, 10, 1, 11, 16), Block.createCuboidShape(14, 11, 6, 16, 12, 10), Block.createCuboidShape(0, 11, 6, 2, 12, 10), Block.createCuboidShape(3, 2, 3, 13, 3, 16), Block.createCuboidShape(14, 5, 2, 15, 13, 6), Block.createCuboidShape(3, 7, 0, 13, 14, 1), Block.createCuboidShape(4, 5, 1, 12, 7, 2), Block.createCuboidShape(2, 4, 2, 14, 5, 3), Block.createCuboidShape(3, 3, 2, 13, 4, 3), Block.createCuboidShape(1, 5, 2, 2, 13, 6), Block.createCuboidShape(12, 5, 1, 14, 14, 2), Block.createCuboidShape(2, 5, 1, 4, 14, 2), Block.createCuboidShape(13, 3, 3, 14, 5, 16), Block.createCuboidShape(14, 5, 6, 15, 11, 16), Block.createCuboidShape(1, 5, 6, 2, 11, 16), Block.createCuboidShape(2, 3, 3, 3, 5, 16)).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();
    public static final VoxelShape BATHTUB_TOP_W = Stream.of(Block.createCuboidShape(8, 0, 3.25, 11, 2, 4.25), Block.createCuboidShape(8, 0, 11.75, 11, 2, 12.75), Block.createCuboidShape(10, 12, 0, 12, 13, 1), Block.createCuboidShape(10, 12, 15, 12, 13, 16), Block.createCuboidShape(0, 10, 15, 6, 11, 16), Block.createCuboidShape(0, 10, 0, 6, 11, 1), Block.createCuboidShape(6, 11, 14, 10, 12, 16), Block.createCuboidShape(6, 11, 0, 10, 12, 2), Block.createCuboidShape(0, 2, 3, 13, 3, 13), Block.createCuboidShape(10, 5, 14, 14, 13, 15), Block.createCuboidShape(15, 7, 3, 16, 14, 13), Block.createCuboidShape(14, 5, 4, 15, 7, 12), Block.createCuboidShape(13, 4, 2, 14, 5, 14), Block.createCuboidShape(13, 3, 3, 14, 4, 13), Block.createCuboidShape(10, 5, 1, 14, 13, 2), Block.createCuboidShape(14, 5, 12, 15, 14, 14), Block.createCuboidShape(14, 5, 2, 15, 14, 4), Block.createCuboidShape(0, 3, 13, 13, 5, 14), Block.createCuboidShape(0, 5, 14, 10, 11, 15), Block.createCuboidShape(0, 5, 1, 10, 11, 2), Block.createCuboidShape(0, 3, 2, 13, 5, 3)).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();

    public static final VoxelShape BATHTUB_BOTTOM_N = Stream.of(Block.createCuboidShape(11.75, 0, 8, 12.75, 2, 11), Block.createCuboidShape(3.25, 0, 8, 4.25, 2, 11), Block.createCuboidShape(15, 10, 0, 16, 11, 14), Block.createCuboidShape(0, 10, 0, 1, 11, 14), Block.createCuboidShape(3, 2, 0, 13, 3, 14), Block.createCuboidShape(1, 5, 0, 2, 11, 15), Block.createCuboidShape(2, 5, 15, 14, 11, 16), Block.createCuboidShape(14, 5, 0, 15, 11, 15), Block.createCuboidShape(2, 4, 14, 14, 5, 15), Block.createCuboidShape(3, 3, 14, 13, 4, 15), Block.createCuboidShape(2, 3, 0, 3, 5, 14), Block.createCuboidShape(13, 3, 0, 14, 5, 14)).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();
    public static final VoxelShape BATHTUB_BOTTOM_E = Stream.of(Block.createCuboidShape(5, 0, 11.75, 8, 2, 12.75), Block.createCuboidShape(5, 0, 3.25, 8, 2, 4.25), Block.createCuboidShape(2, 10, 15, 16, 11, 16), Block.createCuboidShape(2, 10, 0, 16, 11, 1), Block.createCuboidShape(2, 2, 3, 16, 3, 13), Block.createCuboidShape(1, 5, 1, 16, 11, 2), Block.createCuboidShape(0, 5, 2, 1, 11, 14), Block.createCuboidShape(1, 5, 14, 16, 11, 15), Block.createCuboidShape(1, 4, 2, 2, 5, 14), Block.createCuboidShape(1, 3, 3, 2, 4, 13), Block.createCuboidShape(2, 3, 2, 16, 5, 3), Block.createCuboidShape(2, 3, 13, 16, 5, 14)).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();
    public static final VoxelShape BATHTUB_BOTTOM_S = Stream.of(Block.createCuboidShape(3.25, 0, 5, 4.25, 2, 8), Block.createCuboidShape(11.75, 0, 5, 12.75, 2, 8), Block.createCuboidShape(0, 10, 2, 1, 11, 16), Block.createCuboidShape(15, 10, 2, 16, 11, 16), Block.createCuboidShape(3, 2, 2, 13, 3, 16), Block.createCuboidShape(14, 5, 1, 15, 11, 16), Block.createCuboidShape(2, 5, 0, 14, 11, 1), Block.createCuboidShape(1, 5, 1, 2, 11, 16), Block.createCuboidShape(2, 4, 1, 14, 5, 2), Block.createCuboidShape(3, 3, 1, 13, 4, 2), Block.createCuboidShape(13, 3, 2, 14, 5, 16), Block.createCuboidShape(2, 3, 2, 3, 5, 16)).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();
    public static final VoxelShape BATHTUB_BOTTOM_W = Stream.of(Block.createCuboidShape(8, 0, 3.25, 11, 2, 4.25), Block.createCuboidShape(8, 0, 11.75, 11, 2, 12.75), Block.createCuboidShape(0, 10, 0, 14, 11, 1), Block.createCuboidShape(0, 10, 15, 14, 11, 16), Block.createCuboidShape(0, 2, 3, 14, 3, 13), Block.createCuboidShape(0, 5, 14, 15, 11, 15), Block.createCuboidShape(15, 5, 2, 16, 11, 14), Block.createCuboidShape(0, 5, 1, 15, 11, 2), Block.createCuboidShape(14, 4, 2, 15, 5, 14), Block.createCuboidShape(14, 3, 3, 15, 4, 13), Block.createCuboidShape(0, 3, 13, 14, 5, 14), Block.createCuboidShape(0, 3, 2, 14, 5, 3)).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();


    // ==============
    //   Toilet
    // ==============
    public static final VoxelShape TOILET_N = Stream.of(Block.createCuboidShape(5, 0, 3, 11, 5, 12), Block.createCuboidShape(3, 5, 1, 13, 10, 13), Block.createCuboidShape(3, 7, 13, 13, 10, 15), Block.createCuboidShape(5, 6, 13, 11, 7, 14), Block.createCuboidShape(3, 10, 11, 13, 19, 15), Block.createCuboidShape(6.25, 18.5, 12, 9.75, 19.5, 14)).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();
    public static final VoxelShape TOILET_E = Stream.of(Block.createCuboidShape(4, 0, 5, 13, 5, 11), Block.createCuboidShape(3, 5, 3, 15, 10, 13), Block.createCuboidShape(1, 7, 3, 3, 10, 13), Block.createCuboidShape(2, 6, 5, 3, 7, 11), Block.createCuboidShape(1, 10, 3, 5, 19, 13), Block.createCuboidShape(2, 18.5, 6.25, 4, 19.5, 9.75)).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();
    public static final VoxelShape TOILET_S = Stream.of(Block.createCuboidShape(5, 0, 4, 11, 5, 13), Block.createCuboidShape(3, 5, 3, 13, 10, 15), Block.createCuboidShape(3, 7, 1, 13, 10, 3), Block.createCuboidShape(5, 6, 2, 11, 7, 3), Block.createCuboidShape(3, 10, 1, 13, 19, 5), Block.createCuboidShape(6.25, 18.5, 2, 9.75, 19.5, 4)).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();
    public static final VoxelShape TOILET_W = Stream.of(Block.createCuboidShape(3, 0, 5, 12, 5, 11), Block.createCuboidShape(1, 5, 3, 13, 10, 13), Block.createCuboidShape(13, 7, 3, 15, 10, 13), Block.createCuboidShape(13, 6, 5, 14, 7, 11), Block.createCuboidShape(11, 10, 3, 15, 19, 13), Block.createCuboidShape(12, 18.5, 6.25, 14, 19.5, 9.75)).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();


    // ==============
    // Bathroom Sink
    // ==============
    public static final VoxelShape BATHROOM_SINK_N = Stream.of(Block.createCuboidShape(4, 0, 6, 12, 9, 15), Block.createCuboidShape(2, 9, 3, 14, 11, 16), Block.createCuboidShape(2, 11, 2, 14, 12, 13), Block.createCuboidShape(0, 11, 2, 2, 16, 13), Block.createCuboidShape(14, 11, 2, 16, 16, 13), Block.createCuboidShape(0, 11, 0, 16, 16, 2), Block.createCuboidShape(0, 11, 13, 16, 16, 16), Block.createCuboidShape(7, 16, 13.5, 9, 20, 15.5), Block.createCuboidShape(7, 20, 9.5, 9, 21, 14.5)).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();
    public static final VoxelShape BATHROOM_SINK_E = Stream.of(Block.createCuboidShape(1, 0, 4, 10, 9, 12), Block.createCuboidShape(0, 9, 2, 13, 11, 14), Block.createCuboidShape(3, 11, 2, 14, 12, 14), Block.createCuboidShape(3, 11, 0, 14, 16, 2), Block.createCuboidShape(3, 11, 14, 14, 16, 16), Block.createCuboidShape(14, 11, 0, 16, 16, 16), Block.createCuboidShape(0, 11, 0, 3, 16, 16), Block.createCuboidShape(0.5, 16, 7, 2.5, 20, 9), Block.createCuboidShape(1.5, 20, 7, 6.5, 21, 9)).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();
    public static final VoxelShape BATHROOM_SINK_S = Stream.of(Block.createCuboidShape(4, 0, 1, 12, 9, 10), Block.createCuboidShape(2, 9, 0, 14, 11, 13), Block.createCuboidShape(2, 11, 3, 14, 12, 14), Block.createCuboidShape(14, 11, 3, 16, 16, 14), Block.createCuboidShape(0, 11, 3, 2, 16, 14), Block.createCuboidShape(0, 11, 14, 16, 16, 16), Block.createCuboidShape(0, 11, 0, 16, 16, 3), Block.createCuboidShape(7, 16, 0.5, 9, 20, 2.5), Block.createCuboidShape(7, 20, 1.5, 9, 21, 6.5)).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();
    public static final VoxelShape BATHROOM_SINK_W = Stream.of(Block.createCuboidShape(6, 0, 4, 15, 9, 12), Block.createCuboidShape(3, 9, 2, 16, 11, 14), Block.createCuboidShape(2, 11, 2, 13, 12, 14), Block.createCuboidShape(2, 11, 14, 13, 16, 16), Block.createCuboidShape(2, 11, 0, 13, 16, 2), Block.createCuboidShape(0, 11, 0, 2, 16, 16), Block.createCuboidShape(13, 11, 0, 16, 16, 16), Block.createCuboidShape(13.5, 16, 7, 15.5, 20, 9), Block.createCuboidShape(9.5, 20, 7, 14.5, 21, 9)).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();


    // ==============
    //    Mirror
    // ==============
    public static final VoxelShape MIRROR_N = Block.createCuboidShape(3, 1, 15, 13, 15, 16);
    public static final VoxelShape MIRROR_E = Block.createCuboidShape(0, 1, 3, 1, 15, 13);
    public static final VoxelShape MIRROR_S = Block.createCuboidShape(3, 1, 0, 13, 15, 1);
    public static final VoxelShape MIRROR_W = Block.createCuboidShape(15, 1, 3, 16, 15, 13);


    // ==============
    //  Paper Holder
    // ==============

    private static final double PH_V = 5;
    private static final double PH_H = 3.5;
    public static VoxelShape PAPER_HOLDER_N(WallHeight height, WallSide side) {
        double _height = height == WallHeight.HIGHER ? +PH_V : height == WallHeight.LOWER ? -PH_V : 0;
        double _side = side == WallSide.LEFT ? +PH_H : side == WallSide.RIGHT ? -PH_H : 0;
        double _siadd = side == WallSide.RIGHT ? -1 : 0;
        return Stream.of(
                Block.createCuboidShape(10.5+_side+_siadd, 8+_height, 13, 11.5+_side+_siadd, 9+_height, 16),
                Block.createCuboidShape(5.5+_side+_siadd, 8+_height, 13, 10.5+_side+_siadd, 9+_height, 14),
                Block.createCuboidShape(5.75+_side+_siadd, 7+_height, 12, 10.25+_side+_siadd, 10+_height, 15)
        ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();
    }
    public static VoxelShape PAPER_HOLDER_E(WallHeight height, WallSide side) {
        double _height = height == WallHeight.HIGHER ? +PH_V : height == WallHeight.LOWER ? -PH_V : 0;
        double _side = side == WallSide.LEFT ? +PH_H : side == WallSide.RIGHT ? -PH_H : 0;
        double _siadd = side == WallSide.RIGHT ? -1 : 0;
        return Stream.of(
                Block.createCuboidShape(0, 8+_height, 10.5+_side+_siadd, 3, 9+_height, 11.5+_side+_siadd),
                Block.createCuboidShape(2, 8+_height, 5.5+_side+_siadd, 3, 9+_height, 10.5+_side+_siadd),
                Block.createCuboidShape(1, 7+_height, 5.75+_side+_siadd, 4, 10+_height, 10.25+_side+_siadd)
        ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();
    }
    public static VoxelShape PAPER_HOLDER_S(WallHeight height, WallSide side) {
        double _height = height == WallHeight.HIGHER ? +PH_V : height == WallHeight.LOWER ? -PH_V : 0;
        double _side = side == WallSide.LEFT ? -PH_H : side == WallSide.RIGHT ? +PH_H : 0;
        double _siadd = side == WallSide.RIGHT ? 1 : 0;
        return Stream.of(
                Block.createCuboidShape(4.5+_side+_siadd, 8+_height, 0, 5.5+_side+_siadd, 9+_height, 3),
                Block.createCuboidShape(5.5+_side+_siadd, 8+_height, 2, 10.5+_side+_siadd, 9+_height, 3),
                Block.createCuboidShape(5.75+_side+_siadd, 7+_height, 1, 10.25+_side+_siadd, 10+_height, 4)
        ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();
    }
    public static VoxelShape PAPER_HOLDER_W(WallHeight height, WallSide side) {
        double _height = height == WallHeight.HIGHER ? +PH_V : height == WallHeight.LOWER ? -PH_V : 0;
        double _side = side == WallSide.LEFT ? -PH_H : side == WallSide.RIGHT ? +PH_H : 0;
        double _siadd = side == WallSide.RIGHT ? 1 : 0;
        return Stream.of(
                Block.createCuboidShape(13, 8+_height, 4.5+_side+_siadd, 16, 9+_height, 5.5+_side+_siadd),
                Block.createCuboidShape(13, 8+_height, 5.5+_side+_siadd, 14, 9+_height, 10.5+_side+_siadd),
                Block.createCuboidShape(12, 7+_height, 5.75+_side+_siadd, 15, 10+_height, 10.25+_side+_siadd)
        ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();
    }

    // ==============
    //  Towel Bar
    // ==============
    public static VoxelShape TOWEL_BAR_N(WallHeight height) {
        double _height = height == WallHeight.HIGHER ? 9 : height == WallHeight.LOWER ? 0 : 4.5;
        return Stream.of(
                Block.createCuboidShape(2, 2+_height, 15.75, 14, 5+_height, 16),
                Block.createCuboidShape(13, 3+_height, 12.75, 14, 4+_height, 15.75),
                Block.createCuboidShape(2, 3+_height, 12.75, 3, 4+_height, 15.75),
                Block.createCuboidShape(3, 3.25+_height, 13, 13, 3.75+_height, 13.5),
                Block.createCuboidShape(4, -5+_height, 12.75, 12, 4+_height, 13),
                Block.createCuboidShape(4, 1+_height, 13.5, 12, 4+_height, 13.75),
                Block.createCuboidShape(4, 3.75+_height, 13, 12, 4+_height, 13.5)
        ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();
    }
    public static VoxelShape TOWEL_BAR_E(WallHeight height) {
        double _height = height == WallHeight.HIGHER ? 9 : height == WallHeight.LOWER ? 0 : 4.5;
        return Stream.of(
                Block.createCuboidShape(0, 2+_height, 2, 0.25, 5+_height, 14),
                Block.createCuboidShape(0.25, 3+_height, 13, 3.25, 4+_height, 14),
                Block.createCuboidShape(0.25, 3+_height, 2, 3.25, 4+_height, 3),
                Block.createCuboidShape(2.5, 3.25+_height, 3, 3, 3.75+_height, 13),
                Block.createCuboidShape(3, -5+_height, 4, 3.25, 4+_height, 12),
                Block.createCuboidShape(2.25, 1+_height, 4, 2.5, 4+_height, 12),
                Block.createCuboidShape(2.5, 3.75+_height, 4, 3, 4+_height, 12)
        ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();

    }
    public static VoxelShape TOWEL_BAR_S(WallHeight height) {
        double _height = height == WallHeight.HIGHER ? 9 : height == WallHeight.LOWER ? 0 : 4.5;
        return Stream.of(
                Block.createCuboidShape(2, 2+_height, 0, 14, 5+_height, 0.25),
                Block.createCuboidShape(2, 3+_height, 0.25, 3, 4+_height, 3.25),
                Block.createCuboidShape(13, 3+_height, 0.25, 14, 4+_height, 3.25),
                Block.createCuboidShape(3, 3.25+_height, 2.5, 13, 3.75+_height, 3),
                Block.createCuboidShape(4, -5+_height, 3, 12, 4+_height, 3.25),
                Block.createCuboidShape(4, 1+_height, 2.25, 12, 4+_height, 2.5),
                Block.createCuboidShape(4, 3.75+_height, 2.5, 12, 4+_height, 3)
        ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();
    }
    public static VoxelShape TOWEL_BAR_W(WallHeight height) {
        double _height = height == WallHeight.HIGHER ? 9 : height == WallHeight.LOWER ? 0 : 4.5;
        return Stream.of(
                Block.createCuboidShape(15.75, 2+_height, 2, 16, 5+_height, 14),
                Block.createCuboidShape(12.75, 3+_height, 2, 15.75, 4+_height, 3),
                Block.createCuboidShape(12.75, 3+_height, 13, 15.75, 4+_height, 14),
                Block.createCuboidShape(13, 3.25+_height, 3, 13.5, 3.75+_height, 13),
                Block.createCuboidShape(12.75, -5+_height, 4, 13, 4+_height, 12),
                Block.createCuboidShape(13.5, 1+_height, 4, 13.75, 4+_height, 12),
                Block.createCuboidShape(13, 3.75+_height, 4, 13.5, 4+_height, 12)
        ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();
    }

    // ==============
    //  Trash Can 2
    // ==============
    public static final VoxelShape TRASH_CAN_N = Stream.of(Block.createCuboidShape(4.25, 11, 5.25, 11.75, 12, 10.75), Block.createCuboidShape(5.25, 11, 4.25, 10.75, 12, 11.75), Block.createCuboidShape(7, 0, 12, 9, 11, 13), Block.createCuboidShape(5, 0, 11, 11, 11, 12), Block.createCuboidShape(4, 0, 5, 12, 11, 11), Block.createCuboidShape(6.5, 0.25, 2.5, 9.5, 0.75, 4), Block.createCuboidShape(5, 0, 4, 11, 11, 5)).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();
    public static final VoxelShape TRASH_CAN_E = Stream.of(Block.createCuboidShape(5.25, 11, 4.25, 10.75, 12, 11.75), Block.createCuboidShape(4.25, 11, 5.25, 11.75, 12, 10.75), Block.createCuboidShape(3, 0, 7, 4, 11, 9), Block.createCuboidShape(4, 0, 5, 5, 11, 11), Block.createCuboidShape(5, 0, 4, 11, 11, 12), Block.createCuboidShape(12, 0.25, 6.5, 13.5, 0.75, 9.5), Block.createCuboidShape(11, 0, 5, 12, 11, 11)).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();
    public static final VoxelShape TRASH_CAN_S = Stream.of(Block.createCuboidShape(4.25, 11, 5.25, 11.75, 12, 10.75), Block.createCuboidShape(5.25, 11, 4.25, 10.75, 12, 11.75), Block.createCuboidShape(7, 0, 3, 9, 11, 4), Block.createCuboidShape(5, 0, 4, 11, 11, 5), Block.createCuboidShape(4, 0, 5, 12, 11, 11), Block.createCuboidShape(6.5, 0.25, 12, 9.5, 0.75, 13.5), Block.createCuboidShape(5, 0, 11, 11, 11, 12)).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();
    public static final VoxelShape TRASH_CAN_W = Stream.of(Block.createCuboidShape(5.25, 11, 4.25, 10.75, 12, 11.75), Block.createCuboidShape(4.25, 11, 5.25, 11.75, 12, 10.75), Block.createCuboidShape(12, 0, 7, 13, 11, 9), Block.createCuboidShape(11, 0, 5, 12, 11, 11), Block.createCuboidShape(5, 0, 4, 11, 11, 12), Block.createCuboidShape(2.5, 0.25, 6.5, 4, 0.75, 9.5), Block.createCuboidShape(4, 0, 5, 5, 11, 11)).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();

    public static final VoxelShape TRASH_CAN_NOLID_N = Stream.of(Block.createCuboidShape(7, 0, 12, 9, 11, 13), Block.createCuboidShape(5, 0, 11, 11, 11, 12), Block.createCuboidShape(4, 0, 5, 12, 11, 11), Block.createCuboidShape(6.5, 0.25, 2.5, 9.5, 0.75, 4), Block.createCuboidShape(5, 0, 4, 11, 11, 5)).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();
    public static final VoxelShape TRASH_CAN_NOLID_E = Stream.of(Block.createCuboidShape(3, 0, 7, 4, 11, 9), Block.createCuboidShape(4, 0, 5, 5, 11, 11), Block.createCuboidShape(5, 0, 4, 11, 11, 12), Block.createCuboidShape(12, 0.25, 6.5, 13.5, 0.75, 9.5), Block.createCuboidShape(11, 0, 5, 12, 11, 11)).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();
    public static final VoxelShape TRASH_CAN_NOLID_S = Stream.of(Block.createCuboidShape(7, 0, 3, 9, 11, 4), Block.createCuboidShape(5, 0, 4, 11, 11, 5), Block.createCuboidShape(4, 0, 5, 12, 11, 11), Block.createCuboidShape(6.5, 0.25, 12, 9.5, 0.75, 13.5), Block.createCuboidShape(5, 0, 11, 11, 11, 12)).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();
    public static final VoxelShape TRASH_CAN_NOLID_W = Stream.of(Block.createCuboidShape(12, 0, 7, 13, 11, 9), Block.createCuboidShape(11, 0, 5, 12, 11, 11), Block.createCuboidShape(5, 0, 4, 11, 11, 12), Block.createCuboidShape(2.5, 0.25, 6.5, 4, 0.75, 9.5), Block.createCuboidShape(4, 0, 5, 5, 11, 11)).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();

    // ==============
    //  File Cabinet
    // ==============
    public static final VoxelShape FILECABINET_N = Stream.of(Block.createCuboidShape(2.5, 3, 12, 13.5, 11, 13), Block.createCuboidShape(4.5, 9, 13, 11.5, 10, 14), Block.createCuboidShape(2, 0, 0, 14, 16, 12)).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();
    public static final VoxelShape FILECABINET_E = Stream.of(Block.createCuboidShape(3, 3, 2.5, 4, 11, 13.5), Block.createCuboidShape(2, 9, 4.5, 3, 10, 11.5), Block.createCuboidShape(4, 0, 2, 16, 16, 14)).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();
    public static final VoxelShape FILECABINET_S = Stream.of(Block.createCuboidShape(2.5, 3, 3, 13.5, 11, 4), Block.createCuboidShape(4.5, 9, 2, 11.5, 10, 3), Block.createCuboidShape(2, 0, 4, 14, 16, 16)).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();
    public static final VoxelShape FILECABINET_W = Stream.of(Block.createCuboidShape(12, 3, 2.5, 13, 11, 13.5), Block.createCuboidShape(13, 9, 4.5, 14, 10, 11.5), Block.createCuboidShape(0, 0, 2, 12, 16, 14)).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();
    public static final VoxelShape FILECABINET_OPEN_N = Stream.of(Block.createCuboidShape(3.5, -3, 12, 12.5, 5, 18), Block.createCuboidShape(2.5, -3, 18, 13.5, 5, 19), Block.createCuboidShape(4.5, 3, 19, 11.5, 4, 20), Block.createCuboidShape(2.5, 7, 12, 13.5, 15, 13), Block.createCuboidShape(4.5, 13, 13, 11.5, 14, 14), Block.createCuboidShape(2, 0, 0, 14, 16, 12)).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();
    public static final VoxelShape FILECABINET_OPEN_E = Stream.of(Block.createCuboidShape(-2, -3, 3.5, 4, 5, 12.5), Block.createCuboidShape(-3, -3, 2.5, -2, 5, 13.5), Block.createCuboidShape(-4, 3, 4.5, -3, 4, 11.5), Block.createCuboidShape(3, 7, 2.5, 4, 15, 13.5), Block.createCuboidShape(2, 13, 4.5, 3, 14, 11.5), Block.createCuboidShape(4, 0, 2, 16, 16, 14)).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();
    public static final VoxelShape FILECABINET_OPEN_S = Stream.of(Block.createCuboidShape(3.5, -3, -2, 12.5, 5, 4), Block.createCuboidShape(2.5, -3, -3, 13.5, 5, -2), Block.createCuboidShape(4.5, 3, -4, 11.5, 4, -3), Block.createCuboidShape(2.5, 7, 3, 13.5, 15, 4), Block.createCuboidShape(4.5, 13, 2, 11.5, 14, 3), Block.createCuboidShape(2, 0, 4, 14, 16, 16)).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();
    public static final VoxelShape FILECABINET_OPEN_W = Stream.of(Block.createCuboidShape(12, -3, 3.5, 18, 5, 12.5), Block.createCuboidShape(18, -3, 2.5, 19, 5, 13.5), Block.createCuboidShape(19, 3, 4.5, 20, 4, 11.5), Block.createCuboidShape(12, 7, 2.5, 13, 15, 13.5), Block.createCuboidShape(13, 13, 4.5, 14, 14, 11.5), Block.createCuboidShape(0, 0, 2, 12, 16, 14)).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();
    public static final VoxelShape FILECABINET_CLOSED_N = Stream.of(Block.createCuboidShape(2.5, -3, 12, 13.5, 5, 13), Block.createCuboidShape(4.5, 3, 13, 11.5, 4, 14), Block.createCuboidShape(2.5, 7, 12, 13.5, 15, 13), Block.createCuboidShape(4.5, 13, 13, 11.5, 14, 14), Block.createCuboidShape(2, 0, 0, 14, 16, 12)).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();
    public static final VoxelShape FILECABINET_CLOSED_E = Stream.of(Block.createCuboidShape(3, -3, 2.5, 4, 5, 13.5), Block.createCuboidShape(2, 3, 4.5, 3, 4, 11.5), Block.createCuboidShape(3, 7, 2.5, 4, 15, 13.5), Block.createCuboidShape(2, 13, 4.5, 3, 14, 11.5), Block.createCuboidShape(4, 0, 2, 16, 16, 14)).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();
    public static final VoxelShape FILECABINET_CLOSED_S = Stream.of(Block.createCuboidShape(2.5, -3, 3, 13.5, 5, 4), Block.createCuboidShape(4.5, 3, 2, 11.5, 4, 3), Block.createCuboidShape(2.5, 7, 3, 13.5, 15, 4), Block.createCuboidShape(4.5, 13, 2, 11.5, 14, 3), Block.createCuboidShape(2, 0, 4, 14, 16, 16)).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();
    public static final VoxelShape FILECABINET_CLOSED_W = Stream.of(Block.createCuboidShape(12, -3, 2.5, 13, 5, 13.5), Block.createCuboidShape(13, 3, 4.5, 14, 4, 11.5), Block.createCuboidShape(12, 7, 2.5, 13, 15, 13.5), Block.createCuboidShape(13, 13, 4.5, 14, 14, 11.5), Block.createCuboidShape(0, 0, 2, 12, 16, 14)).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();

    // ==============
    //  File Cabinet
    // ==============
    public static final VoxelShape CLOCK_SIGN_N = Stream.of(Block.createCuboidShape(0, 1, 4, 2, 13, 12), Block.createCuboidShape(2, 1, 6, 16, 13, 10), Block.createCuboidShape(0, 0, 4, 16, 1, 12), Block.createCuboidShape(0, 13, 4, 16, 14, 12)).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();
    public static final VoxelShape CLOCK_SIGN_E = Stream.of(Block.createCuboidShape(4, 1, 0, 12, 13, 2), Block.createCuboidShape(6, 1, 2, 10, 13, 16), Block.createCuboidShape(4, 0, 0, 12, 1, 16), Block.createCuboidShape(4, 13, 0, 12, 14, 16)).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();
    public static final VoxelShape CLOCK_SIGN_S = Stream.of(Block.createCuboidShape(14, 1, 4, 16, 13, 12), Block.createCuboidShape(0, 1, 6, 14, 13, 10), Block.createCuboidShape(0, 0, 4, 16, 1, 12), Block.createCuboidShape(0, 13, 4, 16, 14, 12)).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();
    public static final VoxelShape CLOCK_SIGN_W = Stream.of(Block.createCuboidShape(4, 1, 14, 12, 13, 16), Block.createCuboidShape(6, 1, 0, 10, 13, 14), Block.createCuboidShape(4, 0, 0, 12, 1, 16), Block.createCuboidShape(4, 13, 0, 12, 14, 16)).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();
}
