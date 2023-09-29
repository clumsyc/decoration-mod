package me.clumsycat.furnitureexpanded.items;

import me.clumsycat.furnitureexpanded.Expanded;
import me.clumsycat.furnitureexpanded.gui.BasketContainer;
import me.clumsycat.furnitureexpanded.gui.CustomItemInventory;
import me.clumsycat.furnitureexpanded.registries.RegistryHandler;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.DyeableItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class ItemBasket extends Item implements DyeableItem {
    public ItemBasket() {
        super(new FabricItemSettings()
                .group(Expanded.TAB)
                .maxCount(1)
        );
    }

    public static SimpleInventory getInventory(ItemStack stack) {
        return new CustomItemInventory(stack, 9) {
            @Override
            public boolean isValid(int slot, ItemStack stack) {
                return !stack.isOf(RegistryHandler.BASKET);
            }
        };
    }

    public static int getFullnessPropertyOverride(ItemStack stack, ClientWorld world, LivingEntity livingEntity, int i) {
        if (stack.hasNbt()) {
            if (stack.getNbt().contains("Items")) {
                NbtList lst = stack.getOrCreateNbt().getList("Items", NbtElement.COMPOUND_TYPE);
                int v = 0;
                for (int c = 0; c < 9; c++) {
                    if (!ItemStack.fromNbt(lst.getCompound(c)).isEmpty()) v++;
                }

                if (v > 7) return 3;
                else if (v > 4) return 2;
                else if (v > 0) return 1;
                else return 0;
            }
        }
        return 0;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        if (!world.isClient) {
            ItemStack stack = player.getStackInHand(hand);
            player.openHandledScreen(
                    new NamedScreenHandlerFactory() {
                        @Override
                        public Text getDisplayName() {
                            return stack.getName();
                        }

                        @Nullable
                        @Override
                        public ScreenHandler createMenu(int pContainerId, PlayerInventory pPlayerInventory, PlayerEntity pPlayer) {
                            return new BasketContainer(pContainerId, pPlayerInventory, stack);
                        }
                    }
            );
            return TypedActionResult.success(player.getStackInHand(hand), world.isClient());
        }
        return TypedActionResult.success(player.getStackInHand(hand));
    }
}
