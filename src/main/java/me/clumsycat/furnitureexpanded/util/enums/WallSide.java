package me.clumsycat.furnitureexpanded.util.enums;

import net.minecraft.util.StringIdentifiable;

public enum WallSide implements StringIdentifiable {
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

    public String asString() {
        return this.name;
    }
}
