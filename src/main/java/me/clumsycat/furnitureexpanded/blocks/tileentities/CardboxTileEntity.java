package me.clumsycat.furnitureexpanded.blocks.tileentities;

import me.clumsycat.furnitureexpanded.registries.RegistryHandler;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.LootableContainerBlockEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ShulkerBoxScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;

public class CardboxTileEntity extends LootableContainerBlockEntity {
    private DefaultedList<ItemStack> chestContents = DefaultedList.ofSize(27, ItemStack.EMPTY);

    public CardboxTileEntity(BlockPos pPos, BlockState pBlockState) {
        super(RegistryHandler.CARDBOX_TE, pPos, pBlockState);
    }

    public NbtCompound saveToNbt(NbtCompound compound) {
        if (!this.serializeLootTable(compound))
            Inventories.writeNbt(compound, this.chestContents, false);
        return compound;
    }

    @Override
    protected void writeNbt(NbtCompound compound) {
        super.writeNbt(compound);
        if (!this.serializeLootTable(compound)) {
            Inventories.writeNbt(compound, this.chestContents);
        }
    }

    @Override
    public void readNbt(NbtCompound pTag) {
        super.readNbt(pTag);
        this.loadFromTag(pTag);
    }

    public void loadFromTag(NbtCompound nbt) {
        this.chestContents = DefaultedList.ofSize(this.size(), ItemStack.EMPTY);
        if (!this.deserializeLootTable(nbt)) {
            Inventories.readNbt(nbt, this.chestContents);
        }
    }

    public boolean isEmpty() {
        for(ItemStack itemstack : chestContents) {
            if (!itemstack.isEmpty()) return false;
        }
        return true;
    }

    @Override
    public int size() {
        return 27;
    }

    @Override
    protected DefaultedList<ItemStack> getInvStackList() {
        return this.chestContents;
    }

    @Override
    protected void setInvStackList(DefaultedList<ItemStack> itemsIn) {
        this.chestContents = itemsIn;
    }

    @Override
    protected Text getContainerName() {
        return Text.translatable("block.furnitureexpanded.cardbox");
    }


    @Override
    protected ScreenHandler createScreenHandler(int id, PlayerInventory player) {
        return new ShulkerBoxScreenHandler(id, player, this);
    }

}
