package me.clumsycat.furnitureexpanded.items;

import me.clumsycat.furnitureexpanded.registries.RegistryHandler;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;

public class ItemBaseFinite extends Item {
    private Item itemRemainder;
    public ItemBaseFinite(int maxStackSize, int durability) {
        super(new FabricItemSettings()
                .maxCount(maxStackSize)
                .maxDamage(durability)
        );
    }

    public ItemBaseFinite itemRemainder(Item remainder) {
        if (remainder == RegistryHandler.SAW) this.itemRemainder = RegistryHandler.SAW;
        return this;
    }
}
