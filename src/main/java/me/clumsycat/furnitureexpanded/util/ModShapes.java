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
    public static final VoxelShape SHOWER_BOX_TOP_N = Stream.of(Block.createCuboidShape(0, 0, 0, 1, 16, 16), Block.createCuboidShape(1, 0, 15, 15, 16, 16), Block.createCuboidShape(1, 15, 0, 15, 16, 1), Block.createCuboidShape(15, 0, 0, 16, 16, 16)).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();
    public static final VoxelShape SHOWER_BOX_TOP_E = Stream.of(Block.createCuboidShape(0, 0, 0, 16, 16, 1), Block.createCuboidShape(0, 0, 1, 1, 16, 15), Block.createCuboidShape(15, 15, 1, 16, 16, 15), Block.createCuboidShape(0, 0, 15, 16, 16, 16)).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();
    public static final VoxelShape SHOWER_BOX_TOP_S = Stream.of(Block.createCuboidShape(15, 0, 0, 16, 16, 16), Block.createCuboidShape(1, 0, 0, 15, 16, 1), Block.createCuboidShape(1, 15, 15, 15, 16, 16), Block.createCuboidShape(0, 0, 0, 1, 16, 16)).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();
    public static final VoxelShape SHOWER_BOX_TOP_W = Stream.of(Block.createCuboidShape(0, 0, 15, 16, 16, 16), Block.createCuboidShape(15, 0, 1, 16, 16, 15), Block.createCuboidShape(0, 15, 1, 1, 16, 15), Block.createCuboidShape(0, 0, 0, 16, 16, 1)).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();
    public static final VoxelShape SHOWER_BOX_BOTTOM_N = Stream.of(Block.createCuboidShape(1, 0, 1, 15, 1, 15), Block.createCuboidShape(1, 0, 0, 15, 2, 1), Block.createCuboidShape(0, 0, 0, 1, 16, 16), Block.createCuboidShape(1, 0, 15, 15, 16, 16), Block.createCuboidShape(15, 0, 0, 16, 16, 16)).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();
    public static final VoxelShape SHOWER_BOX_BOTTOM_E = Stream.of(Block.createCuboidShape(1, 0, 1, 15, 1, 15), Block.createCuboidShape(15, 0, 1, 16, 2, 15), Block.createCuboidShape(0, 0, 0, 16, 16, 1), Block.createCuboidShape(0, 0, 1, 1, 16, 15), Block.createCuboidShape(0, 0, 15, 16, 16, 16)).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();
    public static final VoxelShape SHOWER_BOX_BOTTOM_S = Stream.of(Block.createCuboidShape(1, 0, 1, 15, 1, 15), Block.createCuboidShape(1, 0, 15, 15, 2, 16), Block.createCuboidShape(15, 0, 0, 16, 16, 16), Block.createCuboidShape(1, 0, 0, 15, 16, 1), Block.createCuboidShape(0, 0, 0, 1, 16, 16)).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();
    public static final VoxelShape SHOWER_BOX_BOTTOM_W = Stream.of(Block.createCuboidShape(1, 0, 1, 15, 1, 15), Block.createCuboidShape(0, 0, 1, 1, 2, 15), Block.createCuboidShape(0, 0, 15, 16, 16, 16), Block.createCuboidShape(15, 0, 1, 16, 16, 15), Block.createCuboidShape(0, 0, 0, 16, 16, 1)).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();

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
    public static final VoxelShape BATHROOM_SINK_N = Stream.of(Block.createCuboidShape(4, 0, 6, 12, 9, 15), Block.createCuboidShape(2, 9, 3, 14, 11, 16), Block.createCuboidShape(0, 11, 0, 16, 16, 16), Block.createCuboidShape(7, 16, 13.5, 9, 20, 15.5), Block.createCuboidShape(7, 20, 9.5, 9, 21, 14.5), Block.createCuboidShape(7.25, 19.25, 9.75, 8.75, 20, 11.25), Block.createCuboidShape(9, 17, 14, 11, 18, 15)).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();
    public static final VoxelShape BATHROOM_SINK_E = Stream.of(Block.createCuboidShape(1, 0, 4, 10, 9, 12), Block.createCuboidShape(0, 9, 2, 13, 11, 14), Block.createCuboidShape(0, 11, 0, 16, 16, 16), Block.createCuboidShape(0.5, 16, 7, 2.5, 20, 9), Block.createCuboidShape(1.5, 20, 7, 6.5, 21, 9), Block.createCuboidShape(4.75, 19.25, 7.25, 6.25, 20, 8.75), Block.createCuboidShape(1, 17, 9, 2, 18, 11)).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();
    public static final VoxelShape BATHROOM_SINK_S = Stream.of(Block.createCuboidShape(4, 0, 1, 12, 9, 10), Block.createCuboidShape(2, 9, 0, 14, 11, 13), Block.createCuboidShape(0, 11, 0, 16, 16, 16), Block.createCuboidShape(7, 16, 0.5, 9, 20, 2.5), Block.createCuboidShape(7, 20, 1.5, 9, 21, 6.5), Block.createCuboidShape(7.25, 19.25, 4.75, 8.75, 20, 6.25), Block.createCuboidShape(5, 17, 1, 7, 18, 2)).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();
    public static final VoxelShape BATHROOM_SINK_W = Stream.of(Block.createCuboidShape(6, 0, 4, 15, 9, 12), Block.createCuboidShape(3, 9, 2, 16, 11, 14), Block.createCuboidShape(0, 11, 0, 16, 16, 16), Block.createCuboidShape(13.5, 16, 7, 15.5, 20, 9), Block.createCuboidShape(9.5, 20, 7, 14.5, 21, 9), Block.createCuboidShape(9.75, 19.25, 7.25, 11.25, 20, 8.75), Block.createCuboidShape(14, 17, 5, 15, 18, 7)).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();


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
