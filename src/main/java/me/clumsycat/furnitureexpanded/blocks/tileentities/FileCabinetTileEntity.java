package me.clumsycat.furnitureexpanded.blocks.tileentities;

import me.clumsycat.furnitureexpanded.blocks.FileCabinet;
import me.clumsycat.furnitureexpanded.registries.RegistryHandler;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.ChestContainer;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class FileCabinetTileEntity extends LockableLootTileEntity {
    private NonNullList<ItemStack> chestContents = NonNullList.withSize(54, ItemStack.EMPTY);
    private int numPlayersUsing;
    public FileCabinetTileEntity() {
        super(RegistryHandler.FILE_CABINET_TE.get());
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
    @Override
    public int getContainerSize() {
        return 54;
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
        return new TranslationTextComponent("block.furnitureexpanded.file_cabinet");
    }

    @Override
    protected Container createMenu(int id, PlayerInventory player) {
        return ChestContainer.sixRows(id, player, this);
    }

    @Override
    public void startOpen(PlayerEntity player) { // Open inventory
        if (!player.isSpectator()) {
            if (this.numPlayersUsing < 0) {
                this.numPlayersUsing = 0;
            }
            ++this.numPlayersUsing;
            BlockState stateUP = this.getLevel().getBlockState(getBlockPos().above());
            if (!this.getLevel().isEmptyBlock(getBlockPos().above())) {
                if (stateUP.getValue(FileCabinet.type) == 1) {
                    this.playSound(stateUP, SoundEvents.BARREL_OPEN);
                    this.setOpenProperty(stateUP, 2);
                }
            }
            this.scheduleTick();
        }
    }

    @Override
    public void stopOpen(PlayerEntity player) {
        if (!player.isSpectator()) {
            --this.numPlayersUsing;
        }
    }

    private void scheduleTick() {
        this.getLevel().getBlockTicks().scheduleTick(this.getBlockPos(), this.getBlockState().getBlock(), 5);
    }

    public void fileTick() {
        int i = this.getBlockPos().getX();
        int j = this.getBlockPos().getY();
        int k = this.getBlockPos().getZ();
        this.numPlayersUsing = ChestTileEntity.getOpenCount(this.getLevel(), this, i, j, k);
        if (this.numPlayersUsing > 0) {
            this.scheduleTick();
        } else {
            BlockState state = this.getBlockState();
            BlockState stateUP = this.getLevel().getBlockState(getBlockPos().above());
            if (!state.is(RegistryHandler.FILE_CABINET.get())) {
                this.setRemoved();
                return;
            }
            if (!this.getLevel().isEmptyBlock(getBlockPos().above())) {
                if (getLevel().getBlockState(getBlockPos().above()).is(RegistryHandler.FILE_CABINET.get()))
                    if (stateUP.getValue(FileCabinet.type) == 2) {
                        this.playSound(stateUP, SoundEvents.BARREL_CLOSE);
                        this.setOpenProperty(stateUP, 1);
                    }
            }
        }
    }

    private void setOpenProperty(BlockState state, int open) {
        if (!this.getLevel().isEmptyBlock(getBlockPos().above())) {
            this.getLevel().setBlockAndUpdate(this.getBlockPos().above(), state.setValue(FileCabinet.type, open));
        }
    }

    private void playSound(BlockState state, SoundEvent sound) {
        Vector3i vector3i = this.getLevel().getBlockState(getBlockPos().above()).getValue(FileCabinet.face).getNormal();
        double d0 = (double)this.getBlockPos().getX() + 0.5D + (double)vector3i.getX() / 2.0D;
        double d1 = (double)this.getBlockPos().getY() + 0.5D + (double)vector3i.getY() / 2.0D;
        double d2 = (double)this.getBlockPos().getZ() + 0.5D + (double)vector3i.getZ() / 2.0D;
        this.getLevel().playSound(null, d0, d1, d2, sound, SoundCategory.BLOCKS, 0.5F, this.getLevel().random.nextFloat() * 0.1F + 0.9F);
    }
}
