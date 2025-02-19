package me.clumsycat.furnitureexpanded.util.enums;

import net.minecraft.util.StringRepresentable;

public enum BedsheetPatterns implements StringRepresentable {
    BAMBOO("bamboo"),
    BARK("bark"),
    CORAL("coral"),
    MUSHROOM("mushroom"),
    EXTRA1("extra1");

    private final String name;

    BedsheetPatterns(String p_i49342_3_) {
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
