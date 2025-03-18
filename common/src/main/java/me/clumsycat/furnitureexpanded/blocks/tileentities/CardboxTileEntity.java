package me.clumsycat.furnitureexpanded.blocks.tileentities;

import me.clumsycat.furnitureexpanded.ExpandedMod;
import me.clumsycat.furnitureexpanded.blocks.Cardbox;
import me.clumsycat.furnitureexpanded.gui.CardboxContainer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class CardboxTileEntity extends RandomizableContainerBlockEntity {
    private NonNullList<ItemStack> chestContents = NonNullList.withSize(27, ItemStack.EMPTY);
    public Player destroyedBy;

    public CardboxTileEntity(BlockPos pPos, BlockState pBlockState) {
        super(ExpandedMod.CARDBOX_TE.get(), pPos, pBlockState);
    }

    @Override
    protected void saveAdditional(CompoundTag compound, HolderLookup.Provider provider) {
        super.saveAdditional(compound, provider);
        if (!this.trySaveLootTable(compound)) {
            ContainerHelper.saveAllItems(compound, this.chestContents, provider);
        }
    }

    @Override
    public void loadAdditional(CompoundTag pTag, HolderLookup.Provider provider) {
        super.loadAdditional(pTag, provider);
        this.chestContents = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        if (!this.tryLoadLootTable(pTag)) {
            ContainerHelper.loadAllItems(pTag, this.chestContents, provider);
        }
    }

    public boolean isEmpty() {
        for(ItemStack itemstack : chestContents) {
            if (!itemstack.isEmpty()) return false;
        }
        return true;
    }

    @Override
    public int getContainerSize() {
        return 27;
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        return this.chestContents;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> itemsIn) {
        this.chestContents = itemsIn;
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable("block.furnitureexpanded.cardbox");
    }

    @Override
    public boolean stillValid(Player player) {
        return !this.getBlockState().getValue(Cardbox.sealed) && super.stillValid(player);
    }

    @Override
    protected AbstractContainerMenu createMenu(int id, Inventory player) {
        return new CardboxContainer(id, player, this);
    }

}
