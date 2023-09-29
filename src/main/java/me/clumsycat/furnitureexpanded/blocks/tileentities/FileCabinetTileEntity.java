package me.clumsycat.furnitureexpanded.blocks.tileentities;

import me.clumsycat.furnitureexpanded.blocks.FileCabinet;
import me.clumsycat.furnitureexpanded.registries.RegistryHandler;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.LootableContainerBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;

public class FileCabinetTileEntity extends LootableContainerBlockEntity {
    private DefaultedList<ItemStack> chestContents = DefaultedList.ofSize(54, ItemStack.EMPTY);
    private int numPlayersUsing = 0;
    public FileCabinetTileEntity(BlockPos pPos, BlockState pBlockState) {
        super(RegistryHandler.FILE_CABINET_TE, pPos, pBlockState);
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

    @Override
    public int size() {
        return 54;
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
        return Text.translatable("block.furnitureexpanded.file_cabinet");
    }

    @Override
    protected ScreenHandler createScreenHandler(int id, PlayerInventory inventory) {
        return GenericContainerScreenHandler.createGeneric9x6(id, inventory, this);
    }

    @Override
    public void onOpen(PlayerEntity player) {
        if (!player.isSpectator()) {
            if (this.numPlayersUsing < 0) {
                this.numPlayersUsing = 0;
            }
            ++this.numPlayersUsing;
            BlockState stateUP = this.getWorld().getBlockState(getPos().up());
            if (!this.getWorld().isAir(getPos().up())) {
                if (stateUP.get(FileCabinet.type) == 1) {
                    this.playSound(stateUP, SoundEvents.BLOCK_BARREL_OPEN);
                    this.setOpenProperty(stateUP, 2);
                }
            }
        }
    }

    @Override
    public void onClose(PlayerEntity player) {
        if (!player.isSpectator()) {
            --this.numPlayersUsing;
        }
    }

    private void scheduleTick() {
        this.getWorld().scheduleBlockTick(this.getPos(), this.getCachedState().getBlock(), 5);
    }

    public static <T extends BlockEntity> void tick(World worldIn, BlockPos pos, BlockState state, BlockEntity tileentity) {
        if (!worldIn.isClient) {
            if (tileentity instanceof FileCabinetTileEntity) {
                ((FileCabinetTileEntity) tileentity).tickLogic(worldIn, pos, state, (FileCabinetTileEntity) tileentity);
            }
        }
    }

    private void tickLogic(World worldIn, BlockPos pos, BlockState state, FileCabinetTileEntity te) {
        if (getNumPlayersUsing(worldIn, pos) > 0) {
            this.scheduleTick();
        } else {
            BlockState stateUP = worldIn.getBlockState(pos.up());
            if (!state.isOf(RegistryHandler.FILE_CABINET)) {
                this.markRemoved();
                return;
            }
            if (!worldIn.isAir(pos.up())) {
                if (stateUP.isOf(RegistryHandler.FILE_CABINET))
                    if (stateUP.get(FileCabinet.type) == 2) {
                        this.playSound(stateUP, SoundEvents.BLOCK_BARREL_CLOSE);
                        this.setOpenProperty(stateUP, 1);
                    }
            }
        }
    }


    public static int getNumPlayersUsing(World worldIn, BlockPos pos) {
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
        if (this.getWorld().getBlockState(getPos().up()).isOf(this.getCachedState().getBlock())) {
            this.getWorld().setBlockState(this.getPos().up(), state.with(FileCabinet.type, open));
        }
    }

    private void playSound(BlockState state, SoundEvent sound) {
        Vec3i vector3i = state.get(FileCabinet.face).getVector();
        double d0 = (double)this.getPos().getX() + 0.5D + (double)vector3i.getX() / 2.0D;
        double d1 = (double)this.getPos().getY() + 0.5D + (double)vector3i.getY() / 2.0D;
        double d2 = (double)this.getPos().getZ() + 0.5D + (double)vector3i.getZ() / 2.0D;
        this.getWorld().playSound(null, d0, d1, d2, sound, SoundCategory.BLOCKS, 0.5F, this.getWorld().random.nextFloat() * 0.1F + 0.9F);
    }
}
