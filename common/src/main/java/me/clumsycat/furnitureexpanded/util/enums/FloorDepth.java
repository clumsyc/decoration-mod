package me.clumsycat.furnitureexpanded.util.enums;

import net.minecraft.util.StringRepresentable;

public enum FloorDepth implements StringRepresentable {
    FAR("far"),
    NORMAL("normal"),
    NEAR("near");

    private final String name;

    FloorDepth(String p_i49342_3_) {
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
