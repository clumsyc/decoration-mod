package me.clumsycat.furnitureexpanded.recipes;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import me.clumsycat.furnitureexpanded.ExpandedMod;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;

import java.util.Iterator;

public class DamageableItemRemainderRecipe extends ShapelessRecipe {
    final String group;
    final CraftingBookCategory category;
    final ItemStack result;
    final NonNullList<Ingredient> ingredients;

    public DamageableItemRemainderRecipe(String string, CraftingBookCategory craftingBookCategory, ItemStack itemStack, NonNullList<Ingredient> nonNullList) {
        super(string, craftingBookCategory, itemStack, nonNullList);
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
    public NonNullList<ItemStack> getRemainingItems(CraftingInput recipeInput) {
        NonNullList<ItemStack> nonNullList = NonNullList.withSize(recipeInput.size(), ItemStack.EMPTY);
        for(int i = 0; i < nonNullList.size(); ++i) {
            ItemStack itemStack = recipeInput.getItem(i);
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
        private static final MapCodec<DamageableItemRemainderRecipe> CODEC = RecordCodecBuilder.mapCodec((instance) -> {
            return instance.group(Codec.STRING.optionalFieldOf("group", "").forGetter((damageableRemainder) -> {
                return damageableRemainder.group;
            }), CraftingBookCategory.CODEC.fieldOf("category").orElse(CraftingBookCategory.MISC).forGetter((damageableRemainder) -> {
                return damageableRemainder.category;
            }), ItemStack.STRICT_CODEC.fieldOf("result").forGetter((damageableRemainder) -> {
                return damageableRemainder.result;
            }), Ingredient.CODEC_NONEMPTY.listOf().fieldOf("ingredients").flatXmap((list) -> {
                Ingredient[] ingredients = (Ingredient[])list.stream().filter((ingredient) -> {
                    return !ingredient.isEmpty();
                }).toArray((i) -> {
                    return new Ingredient[i];
                });
                if (ingredients.length == 0) {
                    return DataResult.error(() -> {
                        return "No ingredients for furnitureexpanded:damageable_item_remainder recipe";
                    });
                } else {
                    return ingredients.length > 9 ? DataResult.error(() -> {
                        return "Too many ingredients for furnitureexpanded:damageable_item_remainder recipe";
                    }) : DataResult.success(NonNullList.of(Ingredient.EMPTY, ingredients));
                }
            }, DataResult::success).forGetter((damageableRemainder) -> {
                return damageableRemainder.ingredients;
            })).apply(instance, DamageableItemRemainderRecipe::new);
        });

        public static final StreamCodec<RegistryFriendlyByteBuf, DamageableItemRemainderRecipe> STREAM_CODEC = StreamCodec.of(DamageableItemRemainderRecipe.Serializer::toNetwork, DamageableItemRemainderRecipe.Serializer::fromNetwork);

        public Serializer() {}

        @Override
        public MapCodec<DamageableItemRemainderRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, DamageableItemRemainderRecipe> streamCodec() {
            return STREAM_CODEC;
        }

        private static DamageableItemRemainderRecipe fromNetwork(RegistryFriendlyByteBuf registryFriendlyByteBuf) {
            String string = registryFriendlyByteBuf.readUtf();
            CraftingBookCategory craftingBookCategory = (CraftingBookCategory)registryFriendlyByteBuf.readEnum(CraftingBookCategory.class);
            int i = registryFriendlyByteBuf.readVarInt();
            NonNullList<Ingredient> nonNullList = NonNullList.withSize(i, Ingredient.EMPTY);
            nonNullList.replaceAll((ingredient) -> {
                return (Ingredient)Ingredient.CONTENTS_STREAM_CODEC.decode(registryFriendlyByteBuf);
            });
            ItemStack itemStack = (ItemStack)ItemStack.STREAM_CODEC.decode(registryFriendlyByteBuf);
            return new DamageableItemRemainderRecipe(string, craftingBookCategory, itemStack, nonNullList);
        }

        private static void toNetwork(RegistryFriendlyByteBuf registryFriendlyByteBuf, DamageableItemRemainderRecipe damageableRemainder) {
            registryFriendlyByteBuf.writeUtf(damageableRemainder.group);
            registryFriendlyByteBuf.writeEnum(damageableRemainder.category);
            registryFriendlyByteBuf.writeVarInt(damageableRemainder.ingredients.size());
            Iterator var2 = damageableRemainder.ingredients.iterator();

            while(var2.hasNext()) {
                Ingredient ingredient = (Ingredient)var2.next();
                Ingredient.CONTENTS_STREAM_CODEC.encode(registryFriendlyByteBuf, ingredient);
            }

            ItemStack.STREAM_CODEC.encode(registryFriendlyByteBuf, damageableRemainder.result);
        }
    }
}