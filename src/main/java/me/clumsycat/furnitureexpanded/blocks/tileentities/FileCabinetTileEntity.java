package me.clumsycat.furnitureexpanded.blocks.tileentities;

import me.clumsycat.furnitureexpanded.blocks.FileCabinet;
import me.clumsycat.furnitureexpanded.registries.RegistryHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class FileCabinetTileEntity extends RandomizableContainerBlockEntity {
    private NonNullList<ItemStack> chestContents = NonNullList.withSize(54, ItemStack.EMPTY);
    private int numPlayersUsing = 0;
    public FileCabinetTileEntity(BlockPos pPos, BlockState pBlockState) {
        super(RegistryHandler.FILE_CABINET_TE.get(), pPos, pBlockState);
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
    protected Component getDefaultName() {
        return new TranslatableComponent("block.furnitureexpanded.file_cabinet");
    }

    @Override
    protected AbstractContainerMenu createMenu(int id, Inventory player) {
        return ChestMenu.sixRows(id, player, this);
    }

    @Override
    public void startOpen(Player player) {
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
        }
    }

    @Override
    public void stopOpen(Player player) {
        if (!player.isSpectator()) {
            --this.numPlayersUsing;
        }
    }

    private void scheduleTick() {
        this.getLevel().scheduleTick(this.getBlockPos(), this.getBlockState().getBlock(), 5);
    }

    public static <T extends BlockEntity> void tick(Level worldIn, BlockPos pos, BlockState state, BlockEntity tileentity) {
        if (!worldIn.isClientSide) {
            if (tileentity instanceof FileCabinetTileEntity) {
                ((FileCabinetTileEntity) tileentity).tickLogic(worldIn, pos, state, (FileCabinetTileEntity) tileentity);
            }
        }
    }

    private void tickLogic(Level worldIn, BlockPos pos, BlockState state, FileCabinetTileEntity te) {
        if (getNumPlayersUsing(worldIn, pos) > 0) {
            this.scheduleTick();
        } else {
            BlockState stateUP = worldIn.getBlockState(pos.above());
            if (!state.is(RegistryHandler.FILE_CABINET.get())) {
                this.setRemoved();
                return;
            }
            if (!worldIn.isEmptyBlock(pos.above())) {
                if (stateUP.is(RegistryHandler.FILE_CABINET.get()))
                    if (stateUP.getValue(FileCabinet.type) == 2) {
                        this.playSound(stateUP, SoundEvents.BARREL_CLOSE);
                        this.setOpenProperty(stateUP, 1);
                    }
            }
        }
    }


    public static int getNumPlayersUsing(BlockGetter worldIn, BlockPos pos) {
        BlockState blockstate = worldIn.getBlockState(pos);
        if (blockstate.hasBlockEntity()) {
            BlockEntity blockentity = worldIn.getBlockEntity(pos);
            if (blockentity instanceof FileCabinetTileEntity) {
                return ((FileCabinetTileEntity)blockentity).getNPS();
            }
        }
        return 0;
    }

    public int getNPS() {
        return this.numPlayersUsing;
    }

    private void setOpenProperty(BlockState state, int open) {
        if (!this.getLevel().isEmptyBlock(getBlockPos().above())) {
            this.getLevel().setBlockAndUpdate(this.getBlockPos().above(), state.setValue(FileCabinet.type, open));
        }
    }

    private void playSound(BlockState state, SoundEvent sound) {
        Vec3i vector3i = this.getLevel().getBlockState(getBlockPos().above()).getValue(FileCabinet.face).getNormal();
        double d0 = (double)this.getBlockPos().getX() + 0.5D + (double)vector3i.getX() / 2.0D;
        double d1 = (double)this.getBlockPos().getY() + 0.5D + (double)vector3i.getY() / 2.0D;
        double d2 = (double)this.getBlockPos().getZ() + 0.5D + (double)vector3i.getZ() / 2.0D;
        this.getLevel().playSound(null, d0, d1, d2, sound, SoundSource.BLOCKS, 0.5F, this.getLevel().random.nextFloat() * 0.1F + 0.9F);
    }
}
