package me.clumsycat.furnitureexpanded.util;

import me.clumsycat.furnitureexpanded.util.enums.WallHeight;
import me.clumsycat.furnitureexpanded.util.enums.WallSide;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.IntProperty;

public class BSProperties {
    public static final IntProperty TYPE_0_1 = IntProperty.of("type", 0, 1);
    public static final IntProperty TYPE_0_2 = IntProperty.of("type", 0, 2);
    public static final IntProperty DYE_17 = IntProperty.of("dye", 0, 16);

    public static final EnumProperty<WallHeight> WALL_HEIGHT = EnumProperty.of("height", WallHeight.class);
    public static final EnumProperty<WallSide> WALL_SIDE = EnumProperty.of("side", WallSide.class);

    public static final BooleanProperty SEALED = BooleanProperty.of("sealed");
    public static final BooleanProperty MAIN = BooleanProperty.of("main");
}
