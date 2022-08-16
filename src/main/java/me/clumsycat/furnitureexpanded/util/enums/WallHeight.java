package me.clumsycat.furnitureexpanded.util.enums;

import net.minecraft.util.StringRepresentable;

public enum WallHeight implements StringRepresentable {
    HIGHER("higher"),
    NORMAL("normal"),
    LOWER("lower");

    private final String name;

    WallHeight(String p_i49342_3_) {
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
