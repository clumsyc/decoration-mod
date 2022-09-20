package me.clumsycat.furnitureexpanded.items;

import me.clumsycat.furnitureexpanded.Expanded;
import me.clumsycat.furnitureexpanded.registries.RegistryHandler;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.Block;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

import java.util.List;

public class ItemCardbox extends BlockItem {
    public ItemCardbox(Block block) {
        super(block, new FabricItemSettings()
                .group(Expanded.TAB)
                .maxCount(1)
        );
    }

    @Environment(value= EnvType.CLIENT)
    @Override
    public void appendTooltip(ItemStack stack, World worldIn, List<Text> tooltip, TooltipContext flagIn) {
        if (stack.getItem() == RegistryHandler.CARDBOX.asItem()) {
            NbtCompound compoundnbt = stack.getSubNbt("BlockEntityTag");
            if (compoundnbt != null) {
                if (compoundnbt.contains("Items", 9)) {
                    DefaultedList<ItemStack> nonnulllist = DefaultedList.ofSize(27, ItemStack.EMPTY);
                    Inventories.readNbt(compoundnbt, nonnulllist);
                    int i = 0;
                    for (ItemStack is : nonnulllist) {
                        if (!is.isEmpty()) {
                            if (i <= 6) {
                                ++i;
                                MutableText iftc = is.getName().copy();
                                iftc.append(" x").append(String.valueOf(is.getCount()));
                                tooltip.add(iftc);
                            }
                        }
                    }
                    if (i > 6) {
                        tooltip.add((Text.translatable("container.shulkerBox.more", i).formatted(Formatting.ITALIC)));
                    }
                }
            }
        }
    }

    public static float getFullnessPropertyOverride(ItemStack stack, World world, LivingEntity livingEntity, int i) {
        if (stack.getItem() == RegistryHandler.CARDBOX.asItem())
            if (stack.hasNbt()) {
                assert stack.getNbt() != null;
                if (stack.getNbt().contains("sealed"))
                    if (stack.getNbt().getBoolean("sealed")) return 1.0f;
            }
        return 0.0f;
    }
}
