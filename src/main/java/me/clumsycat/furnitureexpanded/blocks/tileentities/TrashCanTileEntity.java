package me.clumsycat.furnitureexpanded.blocks.tileentities;

import me.clumsycat.furnitureexpanded.registries.RegistryHandler;
import me.clumsycat.furnitureexpanded.util.BSProperties;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.DispenserContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.IntegerProperty;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class TrashCanTileEntity extends LockableLootTileEntity implements ITickableTileEntity {
    private final IntegerProperty type = BSProperties.TYPE_0_1;
    private int ticksLeft = 0;
    private NonNullList<ItemStack> chestContents = NonNullList.withSize(9, ItemStack.EMPTY);
    public TrashCanTileEntity() {
        super(RegistryHandler.TRASH_CAN_TE.get());
    }

    public CompoundNBT saveToNbt(CompoundNBT compound) {
        if (!this.trySaveLootTable(compound))
            ItemStackHelper.saveAllItems(compound, this.chestContents, false);
        return compound;
    }

    @Override
    public CompoundNBT save(CompoundNBT compound) {
        super.save(compound);
        if (!this.trySaveLootTable(compound)) {
            ItemStackHelper.saveAllItems(compound, this.chestContents);
        }
        return compound;
    }

    @Override
    public void load(BlockState state, CompoundNBT nbt) {
        super.load(state, nbt);
        this.chestContents = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        if (!this.tryLoadLootTable(nbt)) {
            ItemStackHelper.loadAllItems(nbt, this.chestContents);
        }

    }

    public boolean isEmpty() {
        for(ItemStack itemstack : chestContents) {
            if (!itemstack.isEmpty()) return false;
        }
        return true;
    }

    public void resetCountdown() {
        this.ticksLeft = 5;
    }

    @Override
    public int getContainerSize() {
        return chestContents.size();
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
    protected ITextComponent getDefaultName() {
        return new TranslationTextComponent("block.furnitureexpanded.trash_can");
    }

    @Override
    protected Container createMenu(int id, PlayerInventory player) {
        return new DispenserContainer(id, player, this);
    }

    @Override
    public void tick() {
        if (!getLevel().isClientSide) {
            if (this.ticksLeft > 0) {
                this.ticksLeft--;
                if (getBlockState().getValue(type) != 1)
                    getLevel().setBlockAndUpdate(getBlockPos(), getBlockState().setValue(type, 1));
            } else if (getBlockState().getValue(type) != 0)
                getLevel().setBlockAndUpdate(getBlockPos(), getBlockState().setValue(type, 0));
        }
    }
}
