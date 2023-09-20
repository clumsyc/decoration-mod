package me.clumsycat.furnitureexpanded.registries;

import me.clumsycat.furnitureexpanded.Expanded;
import me.clumsycat.furnitureexpanded.blocks.*;
import me.clumsycat.furnitureexpanded.blocks.tileentities.*;
import me.clumsycat.furnitureexpanded.entities.SeatEntity;
import me.clumsycat.furnitureexpanded.items.*;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@SuppressWarnings("unused")
public class RegistryHandler {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Expanded.MOD_ID);
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Expanded.MOD_ID);
    public static final DeferredRegister<BlockEntityType<?>> TILES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Expanded.MOD_ID);
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Expanded.MOD_ID);
    public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, Expanded.MOD_ID);
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Expanded.MOD_ID);
    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Expanded.MOD_ID);

    public static void init() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ITEMS.register(modEventBus);
        BLOCKS.register(modEventBus);
        TILES.register(modEventBus);
        ENTITIES.register(modEventBus);
        PARTICLES.register(modEventBus);
        SOUNDS.register(modEventBus);
        CREATIVE_TABS.register(modEventBus);
    }


    public static final RegistryObject<CreativeModeTab> EXPANDED_TAB = CREATIVE_TABS.register("expanded_tab", () ->
            CreativeModeTab.builder().icon(() -> new ItemStack(RegistryHandler.CARDBOX_ITEM.get()))
                    .title(Component.translatable("item_group."+Expanded.MOD_ID+".expanded_tab")).build());



    // Items
    public static final RegistryObject<ItemBaseFinite> TAPE = ITEMS.register("tape",  () -> new ItemBaseFinite(1, 16));
    public static final RegistryObject<ItemBaseFinite> SAW = ITEMS.register("saw",  () -> new ItemBaseFinite(1, 64));
    public static final RegistryObject<ItemBasket> BASKET = ITEMS.register("basket", ItemBasket::new);


    // Crafting Items
    public static final RegistryObject<ItemBase> SAWDUST = ITEMS.register("sawdust",  () -> new ItemBase(64));


    // [[ Blocks ]] Bathroom section
    public static final RegistryObject<Block> TOILET = BLOCKS.register("toilet", Toilet::new);
    public static final RegistryObject<Item> TOILET_ITEM = ITEMS.register("toilet", () -> new BlockItemBase(RegistryHandler.TOILET.get(), 64));

    public static final RegistryObject<Block> BATHROOM_SINK = BLOCKS.register("bathroom_sink", BathroomSink::new);
    public static final RegistryObject<Item> BATHROOM_SINK_ITEM = ITEMS.register("bathroom_sink", () -> new BlockItemBase(RegistryHandler.BATHROOM_SINK.get(), 64));

    public static final RegistryObject<Block> BATHTUB = BLOCKS.register("bathtub", BathTub::new);
    public static final RegistryObject<Item> BATHTUB_ITEM = ITEMS.register("bathtub", () -> new BlockItemBase(RegistryHandler.BATHTUB.get(), 64));

    public static final RegistryObject<Block> SHOWER_BOX = BLOCKS.register("shower_box", ShowerBox::new);
    public static final RegistryObject<Item> SHOWER_BOX_ITEM = ITEMS.register("shower_box", () -> new BlockItemBase(RegistryHandler.SHOWER_BOX.get(), 64));

    public static final RegistryObject<Block> SHOWER_HEAD = BLOCKS.register("shower_head", ShowerHead::new);
    public static final RegistryObject<Item> SHOWER_HEAD_ITEM = ITEMS.register("shower_head", () -> new BlockItemBase(RegistryHandler.SHOWER_HEAD.get(), 64));
    public static final RegistryObject<BlockEntityType<ShowerHeadTileEntity>> SHOWER_HEAD_TE = TILES.register("shower_head", () -> BlockEntityType.Builder.of(ShowerHeadTileEntity::new, SHOWER_HEAD.get()).build(null));

    public static final RegistryObject<Block> TOWEL_BAR = BLOCKS.register("towel_bar", TowelBar::new);
    public static final RegistryObject<Item> TOWEL_BAR_ITEM = ITEMS.register("towel_bar", () -> new BlockItemBase(RegistryHandler.TOWEL_BAR.get(), 64));

    public static final RegistryObject<Block> PAPER_HOLDER = BLOCKS.register("paper_holder", PaperHolder::new);
    public static final RegistryObject<Item> PAPER_HOLDER_ITEM = ITEMS.register("paper_holder", () -> new BlockItemBase(RegistryHandler.PAPER_HOLDER.get(), 64));

    public static final RegistryObject<Block> TRASH_CAN = BLOCKS.register("trash_can", TrashCan::new);
    public static final RegistryObject<Item> TRASH_CAN_ITEM = ITEMS.register("trash_can", () -> new BlockItemBase(RegistryHandler.TRASH_CAN.get(), 64));
    public static final RegistryObject<BlockEntityType<TrashCanTileEntity>> TRASH_CAN_TE = TILES.register("trash_can", () -> BlockEntityType.Builder.of(TrashCanTileEntity::new, TRASH_CAN.get()).build(null));

    public static final RegistryObject<Block> MIRROR = BLOCKS.register("mirror", MirrorBlock::new);
    public static final RegistryObject<Item> MIRROR_ITEM = ITEMS.register("mirror", () -> new BlockItemBase(RegistryHandler.MIRROR.get(), 64));


    // [[ Blocks ]] Bedroom section


    // [[ Blocks ]] Living Room section


    // [[ Blocks ]] Kitchen


    // [[ Blocks ]] Office section
    public static final RegistryObject<Block> FILE_CABINET = BLOCKS.register("file_cabinet", FileCabinet::new);
    public static final RegistryObject<Item> FILE_CABINET_ITEM = ITEMS.register("file_cabinet", () -> new BlockItemBase(RegistryHandler.FILE_CABINET.get(), 64));
    public static final RegistryObject<BlockEntityType<FileCabinetTileEntity>> FILE_CABINET_TE = TILES.register("file_cabinet", () -> BlockEntityType.Builder.of(FileCabinetTileEntity::new, FILE_CABINET.get()).build(null));


    // [[ Blocks ]] Others
    public static final RegistryObject<Block> CARDBOX = BLOCKS.register("cardbox", Cardbox::new);
    public static final RegistryObject<Item> CARDBOX_ITEM = ITEMS.register("cardbox", () -> new ItemCardbox(RegistryHandler.CARDBOX.get()));
    public static final RegistryObject<BlockEntityType<CardboxTileEntity>> CARDBOX_TE = TILES.register("cardbox", () -> BlockEntityType.Builder.of(CardboxTileEntity::new, CARDBOX.get()).build(null));

    public static final RegistryObject<Block> CLOCK_SIGN = BLOCKS.register("clock_sign", ClockSign::new);
    public static final RegistryObject<Item> CLOCK_SIGN_ITEM = ITEMS.register("clock_sign", () -> new BlockItemBase(RegistryHandler.CLOCK_SIGN.get(), 64));
    public static final RegistryObject<BlockEntityType<ClockSignTileEntity>> CLOCK_SIGN_TE = TILES.register("clock_sign", () -> BlockEntityType.Builder.of(ClockSignTileEntity::new, CLOCK_SIGN.get()).build(null));



    /**
     *
     * ENTITIES
     *
     */

    public static final RegistryObject<EntityType<SeatEntity>> SEAT = ENTITIES.register("seat",
            () -> EntityType.Builder.<SeatEntity>of(SeatEntity::new, MobCategory.MISC)
                    .build(String.valueOf(new ResourceLocation(Expanded.MOD_ID, "seat"))));

    /**
     *
     * SOUNDS
     *
     */
    
    public static final RegistryObject<SoundEvent> SHOWER_SFX = SOUNDS.register("shower_sfx", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(Expanded.MOD_ID, "shower_sfx")));

    /**
     *
     * PARTICLES
     *
     */

    public static final RegistryObject<SimpleParticleType> SHOWER_PARTICLES = PARTICLES.register("shower_particles", () -> new SimpleParticleType(true));

}
