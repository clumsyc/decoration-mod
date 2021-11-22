package me.clumsycat.furnitureexpanded.util;

import me.clumsycat.furnitureexpanded.util.enums.WallHeight;
import me.clumsycat.furnitureexpanded.util.enums.WallSide;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.IntegerProperty;

public class BSProperties {
    public static final IntegerProperty TYPE_0_1 = IntegerProperty.create("type", 0, 1);
    public static final IntegerProperty TYPE_0_2 = IntegerProperty.create("type", 0, 2);
    public static final IntegerProperty TYPE_17 = IntegerProperty.create("dye", 0, 16);

    public static final EnumProperty<WallHeight> WALL_HEIGHT = EnumProperty.create("height", WallHeight.class);
    public static final EnumProperty<WallSide> WALL_SIDE = EnumProperty.create("side", WallSide.class);

    public static final BooleanProperty SEALED = BooleanProperty.create("sealed");
    public static final BooleanProperty MAIN = BooleanProperty.create("main");
}
