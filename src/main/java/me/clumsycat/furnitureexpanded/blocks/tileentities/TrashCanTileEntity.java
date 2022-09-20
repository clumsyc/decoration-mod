package me.clumsycat.furnitureexpanded.blocks.tileentities;

import me.clumsycat.furnitureexpanded.registries.RegistryHandler;
import me.clumsycat.furnitureexpanded.util.BSProperties;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.LootableContainerBlockEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.Generic3x3ContainerScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.state.property.IntProperty;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;


public class TrashCanTileEntity extends LootableContainerBlockEntity {
    private static final IntProperty type = BSProperties.TYPE_0_1;
    private int ticksLeft = 0;
    private DefaultedList<ItemStack> chestContents = DefaultedList.ofSize(9, ItemStack.EMPTY);
    public TrashCanTileEntity(BlockPos pPos, BlockState pBlockState) {
        super(RegistryHandler.TRASH_CAN_TE, pPos, pBlockState);
    }

    public NbtCompound saveToNbt(NbtCompound compound) {
        if (!this.serializeLootTable(compound))
            Inventories.writeNbt(compound, this.chestContents, false);
        return compound;
    }

    @Override
    public void writeNbt(NbtCompound compound) {
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

    public void resetCountdown() {
        this.ticksLeft = 5;
    }

    @Override
    public int size() {
        return chestContents.size();
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
        return Text.translatable("block.furnitureexpanded.trash_can");
    }

    @Override
    protected ScreenHandler createScreenHandler(int id, PlayerInventory inventory) {
        return new Generic3x3ContainerScreenHandler(id, inventory, this);
    }

    public static <T extends BlockEntity> void tick(World worldIn, BlockPos pos, BlockState state, BlockEntity tileentity) {
        if (!worldIn.isClient) {
            if (tileentity instanceof TrashCanTileEntity) {
                TrashCanTileEntity te = (TrashCanTileEntity) worldIn.getBlockEntity(pos);
                if (te.ticksLeft > 0) {
                    te.ticksLeft--;
                    if (state.get(type) != 1)
                        worldIn.setBlockState(pos, state.with(type, 1), Block.NOTIFY_ALL);
                } else if (state.get(type) != 0)
                    worldIn.setBlockState(pos, state.with(type, 0), Block.NOTIFY_ALL);
            }
        }
    }
}
