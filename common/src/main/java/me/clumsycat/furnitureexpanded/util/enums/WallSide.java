package me.clumsycat.furnitureexpanded.util.enums;

import net.minecraft.util.StringRepresentable;

public enum WallSide implements StringRepresentable {
    LEFT("left"),
    NORMAL("normal"),
    RIGHT("right");

    private final String name;

    WallSide(String p_i49342_3_) {
        this.name = p_i49342_3_;
    }

    public String toString() {
        return this.name;
    }

    @SuppressWarnings("NullableProblems")
    public String getSerializedName() {
        return this.name;
    }
}
