package me.clumsycat.furnitureexpanded.items;

import me.clumsycat.furnitureexpanded.registries.RegistryHandler;
import me.clumsycat.furnitureexpanded.util.ModSetup;
import net.minecraft.ChatFormatting;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

@SuppressWarnings("NullableProblems")
public class ItemCardbox extends BlockItem {
    public ItemCardbox(Block block) {
        super(block, new Properties()
                .stacksTo(1)
                .tab(ModSetup.TAB));
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        if (stack.getItem() == RegistryHandler.CARDBOX_ITEM.get()) {
            CompoundTag compoundnbt = stack.getTagElement("BlockEntityTag");
            if (compoundnbt != null) {
                if (compoundnbt.contains("Items", 9)) {
                    NonNullList<ItemStack> nonnulllist = NonNullList.withSize(27, ItemStack.EMPTY);
                    ContainerHelper.loadAllItems(compoundnbt, nonnulllist);
                    int i = 0;
                    for (ItemStack is : nonnulllist) {
                        if (!is.isEmpty()) {
                            if (i <= 6) {
                                ++i;
                                MutableComponent iftc = is.getDisplayName().copy();
                                iftc.append(" x").append(String.valueOf(is.getCount()));
                                tooltip.add(iftc);
                            }
                        }
                    }
                    if (i > 6) {
                        tooltip.add((Component.translatable("container.shulkerBox.more", i)).withStyle(ChatFormatting.ITALIC));
                    }
                }
            }
        }
    }

    public static float getFullnessPropertyOverride(ItemStack stack, @Nullable ClientLevel world, @Nullable LivingEntity livingEntity, int i) {
        if (stack.getItem() == RegistryHandler.CARDBOX_ITEM.get())
            if (stack.hasTag()) {
                assert stack.getTag() != null;
                if (stack.getTag().contains("sealed"))
                    if (stack.getTag().getBoolean("sealed")) return 1.0f;
            }
        return 0.0f;
    }
}
