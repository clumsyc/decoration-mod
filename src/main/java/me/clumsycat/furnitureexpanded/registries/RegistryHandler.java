package me.clumsycat.furnitureexpanded.registries;

import me.clumsycat.furnitureexpanded.Expanded;
import me.clumsycat.furnitureexpanded.blocks.*;
import me.clumsycat.furnitureexpanded.blocks.tileentities.CardboxTileEntity;
import me.clumsycat.furnitureexpanded.blocks.tileentities.ClockSignTileEntity;
import me.clumsycat.furnitureexpanded.blocks.tileentities.FileCabinetTileEntity;
import me.clumsycat.furnitureexpanded.blocks.tileentities.TrashCanTileEntity;
import me.clumsycat.furnitureexpanded.items.BlockItemBase;
import me.clumsycat.furnitureexpanded.items.ItemBase;
import me.clumsycat.furnitureexpanded.items.ItemBaseFinite;
import me.clumsycat.furnitureexpanded.items.ItemCardbox;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@SuppressWarnings("unused")
public class RegistryHandler {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Expanded.MOD_ID);
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Expanded.MOD_ID);
    public static final DeferredRegister<TileEntityType<?>> TILES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, Expanded.MOD_ID);
    //public static final DeferredRegister<ContainerType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, Expanded.MOD_ID);
    //public static final DeferredRegister<IRecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, Expanded.MOD_ID);
    //public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Expanded.MOD_ID);
    //public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, Expanded.MOD_ID);



    public static void init() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ITEMS.register(modEventBus);
        BLOCKS.register(modEventBus);
        TILES.register(modEventBus);
        //CONTAINERS.register(modEventBus);
        //RECIPE_SERIALIZERS.register(modEventBus);
        //SOUND_EVENTS.register(modEventBus);
        //PARTICLES.register(modEventBus);
    }
    // Items
    public static final RegistryObject<ItemBase> BASKET = ITEMS.register("basket",  () -> new ItemBase(1)); //TODO: attach inventory
    public static final RegistryObject<ItemBaseFinite> TAPE = ITEMS.register("tape",  () -> new ItemBaseFinite(1, 16));
    public static final RegistryObject<ItemBaseFinite> SAW = ITEMS.register("saw",  () -> new ItemBaseFinite(1, 64));

    // Items
    public static final RegistryObject<ItemBase> SAWDUST = ITEMS.register("sawdust",  () -> new ItemBase(64));

    // Block Items
    public static final RegistryObject<Item> TOILET_ITEM = ITEMS.register("toilet", () -> new BlockItemBase(RegistryHandler.TOILET.get(), 64));
    public static final RegistryObject<Item> BATHROOM_SINK_ITEM = ITEMS.register("bathroom_sink", () -> new BlockItemBase(RegistryHandler.BATHROOM_SINK.get(), 64));
    public static final RegistryObject<Item> CARDBOX_ITEM = ITEMS.register("cardbox", () -> new ItemCardbox(RegistryHandler.CARDBOX.get()));
    public static final RegistryObject<Item> FILE_CABINET_ITEM = ITEMS.register("file_cabinet", () -> new BlockItemBase(RegistryHandler.FILE_CABINET.get(), 64));
    public static final RegistryObject<Item> CLOCK_SIGN_ITEM = ITEMS.register("clock_sign", () -> new BlockItemBase(RegistryHandler.CLOCK_SIGN.get(), 64));
    public static final RegistryObject<Item> SHOWER_BOX_ITEM = ITEMS.register("shower_box", () -> new BlockItemBase(RegistryHandler.SHOWER_BOX.get(), 64));
    public static final RegistryObject<Item> TOWEL_BAR_ITEM = ITEMS.register("towel_bar", () -> new BlockItemBase(RegistryHandler.TOWEL_BAR.get(), 64));
    public static final RegistryObject<Item> PAPER_HOLDER_ITEM = ITEMS.register("paper_holder", () -> new BlockItemBase(RegistryHandler.PAPER_HOLDER.get(), 64));
    public static final RegistryObject<Item> TRASH_CAN_ITEM = ITEMS.register("trash_can", () -> new BlockItemBase(RegistryHandler.TRASH_CAN.get(), 64));
    public static final RegistryObject<Item> MIRROR_ITEM = ITEMS.register("mirror", () -> new BlockItemBase(RegistryHandler.MIRROR.get(), 64));



    // [[ Blocks ]] Bathroom section
    public static final RegistryObject<Block> TOILET = BLOCKS.register("toilet", Toilet::new);
    public static final RegistryObject<Block> BATHROOM_SINK = BLOCKS.register("bathroom_sink", BathroomSink::new);
    public static final RegistryObject<Block> SHOWER_BOX = BLOCKS.register("shower_box", ShowerBox::new);
    public static final RegistryObject<Block> TOWEL_BAR = BLOCKS.register("towel_bar", TowelBar::new);
    public static final RegistryObject<Block> PAPER_HOLDER = BLOCKS.register("paper_holder", PaperHolder::new);
    public static final RegistryObject<Block> TRASH_CAN = BLOCKS.register("trash_can", TrashCan::new);
    public static final RegistryObject<Block> MIRROR = BLOCKS.register("mirror", MirrorBlock::new);


    // [[ Blocks ]] Bedroom section

    // [[ Blocks ]] Living Room section

    // [[ Blocks ]] Kitchen

    // [[ Blocks ]] Office section
    public static final RegistryObject<Block> FILE_CABINET = BLOCKS.register("file_cabinet", FileCabinet::new);

    // [[ Blocks ]] Others
    public static final RegistryObject<Block> CARDBOX = BLOCKS.register("cardbox", Cardbox::new);
    public static final RegistryObject<Block> CLOCK_SIGN = BLOCKS.register("clock_sign", ClockSign::new);





    /**
     *
     * TILE ENTITIES
     *
     */

    public static final RegistryObject<TileEntityType<CardboxTileEntity>> CARDBOX_TE = TILES.register("cardbox", () -> TileEntityType.Builder.of(CardboxTileEntity::new, CARDBOX.get()).build(null));
    public static final RegistryObject<TileEntityType<FileCabinetTileEntity>> FILE_CABINET_TE = TILES.register("file_cabinet", () -> TileEntityType.Builder.of(FileCabinetTileEntity::new, FILE_CABINET.get()).build(null));
    public static final RegistryObject<TileEntityType<ClockSignTileEntity>> CLOCK_SIGN_TE = TILES.register("clock_sign", () -> TileEntityType.Builder.of(ClockSignTileEntity::new, CLOCK_SIGN.get()).build(null));
    public static final RegistryObject<TileEntityType<TrashCanTileEntity>> TRASH_CAN_TE = TILES.register("trash_can", () -> TileEntityType.Builder.of(TrashCanTileEntity::new, TRASH_CAN.get()).build(null));
}
