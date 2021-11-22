package me.clumsycat.furnitureexpanded.util.enums;

import net.minecraft.util.IStringSerializable;

public enum WallHeight implements IStringSerializable {
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

    public String getSerializedName() {
        return this.name;
    }
}
