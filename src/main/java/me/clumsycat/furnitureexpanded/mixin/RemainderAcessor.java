package me.clumsycat.furnitureexpanded.mixin;

import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Item.class)
public interface RemainderAcessor {
    @Mutable
    @Accessor("recipeRemainder")
    void addRecipeRemainder(Item item);
}
