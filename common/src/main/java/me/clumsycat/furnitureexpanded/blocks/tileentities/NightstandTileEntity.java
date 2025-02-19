package me.clumsycat.furnitureexpanded.blocks.tileentities;

import me.clumsycat.furnitureexpanded.ExpandedMod;
import me.clumsycat.furnitureexpanded.blocks.Nightstand;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class NightstandTileEntity extends RandomizableContainerBlockEntity {
    private NonNullList<ItemStack> chestContents = NonNullList.withSize(9, ItemStack.EMPTY);
    private int numPlayersUsing = 0;
    public NightstandTileEntity(BlockPos pos, BlockState state) {
        super(ExpandedMod.NIGHTSTAND_TE.get(), pos, state);
    }

    public CompoundTag saveToNbt(CompoundTag compound) {
        if (!this.trySaveLootTable(compound))
            ContainerHelper.saveAllItems(compound, this.chestContents, false);
        return compound;
    }

    @Override
    public void saveAdditional(CompoundTag compound) {
        super.saveAdditional(compound);
        if (!this.trySaveLootTable(compound)) {
            ContainerHelper.saveAllItems(compound, this.chestContents);
        }
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        this.loadFromTag(pTag);
    }

    public void loadFromTag(CompoundTag nbt) {
        this.chestContents = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        if (!this.tryLoadLootTable(nbt)) {
            ContainerHelper.loadAllItems(nbt, this.chestContents);
        }
    }

    @Override
    public int getContainerSize() {
        return 9;
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
        return Component.translatable("block.furnitureexpanded.nightstand");
    }

    @Override
    protected AbstractContainerMenu createMenu(int id, Inventory player) {
        return new ChestMenu(MenuType.GENERIC_9x1, id, player, this, 1);
    }

    @Override
    public void startOpen(Player player) {
        if (!player.isSpectator()) {
            if (this.numPlayersUsing < 0) {
                this.numPlayersUsing = 0;
            }
            ++this.numPlayersUsing;
            BlockState state = this.getBlockState();
            if (!state.getValue(Nightstand.open)) {
                this.playSound(SoundEvents.BARREL_OPEN);
                this.setOpenProperty(state, true);
            }
        }
    }

    @Override
    public void stopOpen(Player player) {
        if (!player.isSpectator()) {
            --this.numPlayersUsing;
            if (this.numPlayersUsing < 1) {
                BlockState state = this.getBlockState();
                if (state.getValue(Nightstand.open)) {
                    this.playSound(SoundEvents.BARREL_CLOSE);
                    this.setOpenProperty(state, false);
                }
            }
        }
    }

    private void setOpenProperty(BlockState state, boolean open) {
        this.getLevel().setBlockAndUpdate(this.getBlockPos(), state.setValue(Nightstand.open, open));
    }

    private void playSound(SoundEvent sound) {
        BlockPos pos = this.getBlockPos();
        this.level.playSound(null, pos.getX() + 0.5D, pos.getY() + 0.8D, pos.getZ() + 0.5D, sound, SoundSource.BLOCKS, 0.5F, this.level.random.nextFloat() * 0.1F + 0.9F);
    }
}
