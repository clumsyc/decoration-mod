package me.clumsycat.furnitureexpanded.blocks.tileentities;

import me.clumsycat.furnitureexpanded.registries.RegistryHandler;
import me.clumsycat.furnitureexpanded.util.BSProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.DispenserMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;


public class TrashCanTileEntity extends RandomizableContainerBlockEntity {
    private static final IntegerProperty type = BSProperties.TYPE_0_1;
    private int ticksLeft = 0;
    private NonNullList<ItemStack> chestContents = NonNullList.withSize(9, ItemStack.EMPTY);
    public TrashCanTileEntity(BlockPos pPos, BlockState pBlockState) {
        super(RegistryHandler.TRASH_CAN_TE.get(), pPos, pBlockState);
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
    protected Component getDefaultName() {
        return Component.translatable("block.furnitureexpanded.trash_can");
    }

    @Override
    protected AbstractContainerMenu createMenu(int id, Inventory inventory) {
        return new DispenserMenu(id, inventory, this);
    }

    public static <T extends BlockEntity> void tick(Level worldIn, BlockPos pos, BlockState state, BlockEntity tileentity) {
        if (!worldIn.isClientSide) {
            if (tileentity instanceof TrashCanTileEntity) {
                TrashCanTileEntity te = (TrashCanTileEntity) worldIn.getBlockEntity(pos);
                if (te.ticksLeft > 0) {
                    te.ticksLeft--;
                    if (state.getValue(type) != 1)
                        worldIn.setBlockAndUpdate(pos, state.setValue(type, 1));
                } else if (state.getValue(type) != 0)
                    worldIn.setBlockAndUpdate(pos, state.setValue(type, 0));
            }
        }
    }
}
