package me.clumsycat.furnitureexpanded.neoforge.mixin;

import com.mojang.serialization.Dynamic;
import net.minecraft.util.datafix.fixes.ItemStackComponentizationFix;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

@Mixin(ItemStackComponentizationFix.class)
public abstract class ItemStackComponentizationDataFixer {

    @Inject(at = @At("HEAD"), method = "fixItemStack(Lnet/minecraft/util/datafix/fixes/ItemStackComponentizationFix$ItemStackData;Lcom/mojang/serialization/Dynamic;)V")
    private static void fixItemStack(ItemStackComponentizationFix.ItemStackData itemStackData, Dynamic<?> dynamic, CallbackInfo ci) {
        if (itemStackData.is("furnitureexpanded:basket")) {
            itemStackData.fixSubTag("Items", false, (dynamicx -> fixBasketItemTagToComponent(itemStackData, dynamicx)));
            itemStackData.removeTag("Items");
        }
        if (itemStackData.is("furnitureexpanded:cardbox")) {
            itemStackData.moveTagToComponent("sealed", "furnitureexpanded:sealed");
            itemStackData.fixSubTag("BlockEntityTag", false, (dynamicx -> fixCardboxItemTagToComponent(itemStackData, dynamicx, "furnitureexpanded:cardbox")));
        }
    }

    private static <T> Dynamic<T> fixBasketItemTagToComponent(ItemStackComponentizationFix.ItemStackData itemStackData, Dynamic<T> dynamic) {
        AtomicInteger i = new AtomicInteger();
        List<Dynamic<T>> list = dynamic.asList((dynamicx) -> {
            i.set(i.get() + 1);
            if ("minecraft:air".equals(dynamicx.get("id").asString(""))) return null;
            return dynamicx.emptyMap().set("slot", dynamicx.createInt(i.get()-1)).set("item", dynamicx);
        });
        list.removeIf(Objects::isNull);
        if (!list.isEmpty()) itemStackData.setComponent("minecraft:container", dynamic.createList(list.stream()));
        return dynamic;
    }

    private static <T> Dynamic<T> fixCardboxItemTagToComponent(ItemStackComponentizationFix.ItemStackData itemStackData, Dynamic<T> dynamic, String string) {
        dynamic = dynamic.set("id", dynamic.createString(string));
        List<Dynamic<T>> list = dynamic.get("Items")
                .asList(dynamicx -> dynamicx.emptyMap()
                        .set("slot", dynamicx.createInt(dynamicx.get("Slot").asByte((byte) 0) & 255))
                        .set("item", dynamicx.remove("Slot")
                                .renameField("Count", "count") //TODO: Fix data not being converted
                        ));
        if (!list.isEmpty()) {
            itemStackData.setComponent("minecraft:container", dynamic.createList(list.stream()));
        }

        return dynamic.remove("Items");
    }
}
