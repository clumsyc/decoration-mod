package me.clumsycat.furnitureexpanded.util.enums;

import net.minecraft.util.StringIdentifiable;

public enum WallHeight implements StringIdentifiable {
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

    public String asString() {
        return this.name;
    }
}
