package me.clumsycat.furnitureexpanded.recipes;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import me.clumsycat.furnitureexpanded.ExpandedMod;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;

import java.util.Iterator;

public class DamageableItemRemainderRecipe extends ShapelessRecipe {
    private final ResourceLocation id;
    final String group;
    final CraftingBookCategory category;
    final ItemStack result;
    final NonNullList<Ingredient> ingredients;

    public DamageableItemRemainderRecipe(ResourceLocation resourceLocation, String string, CraftingBookCategory craftingBookCategory, ItemStack itemStack, NonNullList<Ingredient> nonNullList) {
        super(resourceLocation, string, craftingBookCategory, itemStack, nonNullList);
        this.id = resourceLocation;
        this.group = string;
        this.category = craftingBookCategory;
        this.result = itemStack;
        this.ingredients = nonNullList;

    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ExpandedMod.RECIPES_DAMAGEABLE_ITEM.get();
    }

    @Override
    public NonNullList<ItemStack> getRemainingItems(CraftingContainer container) {
        NonNullList<ItemStack> nonNullList = NonNullList.withSize(container.getContainerSize(), ItemStack.EMPTY);
        for(int i = 0; i < nonNullList.size(); ++i) {
            ItemStack itemStack = container.getItem(i);
            if (itemStack.isDamageableItem()) {
                ItemStack is = itemStack.copy();
                is.setDamageValue(itemStack.getDamageValue()+1);
                if (is.getDamageValue() >= is.getMaxDamage()) is.setCount(is.getCount()-1);
                nonNullList.set(i, is);
            }
        }
        return nonNullList;
    }

    public static class Serializer implements RecipeSerializer<DamageableItemRemainderRecipe> {
        public Serializer() {}

        @Override
        public DamageableItemRemainderRecipe fromJson(ResourceLocation resourceLocation, JsonObject jsonObject) {
            String string = GsonHelper.getAsString(jsonObject, "group", "");
            CraftingBookCategory craftingBookCategory = (CraftingBookCategory)CraftingBookCategory.CODEC.byName(GsonHelper.getAsString(jsonObject, "category", (String)null), CraftingBookCategory.MISC);
            NonNullList<Ingredient> nonNullList = itemsFromJson(GsonHelper.getAsJsonArray(jsonObject, "ingredients"));
            if (nonNullList.isEmpty()) {
                throw new JsonParseException("No ingredients for furnitureexpanded:damageable_item_remainder recipe");
            } else if (nonNullList.size() > 9) {
                throw new JsonParseException("Too many ingredients for furnitureexpanded:damageable_item_remainder recipe");
            } else {
                ItemStack itemStack = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(jsonObject, "result"));
                return new DamageableItemRemainderRecipe(resourceLocation, string, craftingBookCategory, itemStack, nonNullList);
            }
        }

        private static NonNullList<Ingredient> itemsFromJson(JsonArray jsonArray) {
            NonNullList<Ingredient> nonNullList = NonNullList.create();
            for(int i = 0; i < jsonArray.size(); ++i) {
                Ingredient ingredient = Ingredient.fromJson(jsonArray.get(i), false);
                if (!ingredient.isEmpty()) {
                    nonNullList.add(ingredient);
                }
            }
            return nonNullList;
        }

        @Override
        public DamageableItemRemainderRecipe fromNetwork(ResourceLocation resourceLocation, FriendlyByteBuf friendlyByteBuf) {
            String string = friendlyByteBuf.readUtf();
            CraftingBookCategory craftingBookCategory = (CraftingBookCategory)friendlyByteBuf.readEnum(CraftingBookCategory.class);
            int i = friendlyByteBuf.readVarInt();
            NonNullList<Ingredient> nonNullList = NonNullList.withSize(i, Ingredient.EMPTY);

            for(int j = 0; j < nonNullList.size(); ++j) {
                nonNullList.set(j, Ingredient.fromNetwork(friendlyByteBuf));
            }

            ItemStack itemStack = friendlyByteBuf.readItem();
            return new DamageableItemRemainderRecipe(resourceLocation, string, craftingBookCategory, itemStack, nonNullList);
        }

        @Override
        public void toNetwork(FriendlyByteBuf friendlyByteBuf, DamageableItemRemainderRecipe dmgRemainderRecipe) {
            friendlyByteBuf.writeUtf(dmgRemainderRecipe.group);
            friendlyByteBuf.writeEnum(dmgRemainderRecipe.category);
            friendlyByteBuf.writeVarInt(dmgRemainderRecipe.ingredients.size());
            Iterator var3 = dmgRemainderRecipe.ingredients.iterator();

            while(var3.hasNext()) {
                Ingredient ingredient = (Ingredient)var3.next();
                ingredient.toNetwork(friendlyByteBuf);
            }

            friendlyByteBuf.writeItem(dmgRemainderRecipe.result);
        }
    }
}