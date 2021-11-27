package me.clumsycat.furnitureexpanded.items;

import me.clumsycat.furnitureexpanded.registries.RegistryHandler;
import me.clumsycat.furnitureexpanded.util.ModSetup;
import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class ItemCardbox extends BlockItem {
    public ItemCardbox(Block block) {
        super(block, new Properties()
                .stacksTo(1)
                .tab(ModSetup.TAB));
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        if (stack.getItem() == RegistryHandler.CARDBOX_ITEM.get()) {
            CompoundNBT compoundnbt = stack.getTagElement("BlockEntityTag");
            if (compoundnbt != null) {
                if (compoundnbt.contains("Items", 9)) {
                    NonNullList<ItemStack> nonnulllist = NonNullList.withSize(27, ItemStack.EMPTY);
                    ItemStackHelper.loadAllItems(compoundnbt, nonnulllist);
                    int i = 0;
                    for (ItemStack is : nonnulllist) { //TODO: Sort list
                        if (!is.isEmpty()) {
                            if (i <= 6) {
                                ++i;
                                IFormattableTextComponent iftc = is.getDisplayName().copy();
                                iftc.append(" x").append(String.valueOf(is.getCount()));
                                tooltip.add(iftc);
                            }
                        }
                    }
                    if (i > 6) {
                        tooltip.add((new TranslationTextComponent("container.shulkerBox.more", i)).withStyle(TextFormatting.ITALIC));
                    }
                }
            }
        }
    }

    public static float getFullnessPropertyOverride(ItemStack stack, @Nullable World world, @Nullable LivingEntity livingEntity) {
        if (stack.getItem() == RegistryHandler.CARDBOX_ITEM.get())
            if (stack.hasTag()) {
                assert stack.getTag() != null;
                if (stack.getTag().contains("sealed"))
                    if (stack.getTag().getBoolean("sealed")) return 1.0f;
            }
        return 0.0f;
    }

}
