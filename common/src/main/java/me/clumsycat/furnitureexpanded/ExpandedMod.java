package me.clumsycat.furnitureexpanded;


import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import me.clumsycat.furnitureexpanded.blocks.*;
import me.clumsycat.furnitureexpanded.blocks.tileentities.*;
import me.clumsycat.furnitureexpanded.entities.SeatEntity;
import me.clumsycat.furnitureexpanded.items.*;
import me.clumsycat.furnitureexpanded.recipes.DamageableItemRemainderRecipe;
import me.clumsycat.furnitureexpanded.util.enums.BedsheetPatterns;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class ExpandedMod {
    public static final String MOD_ID = "furnitureexpanded";

    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(MOD_ID, Registries.CREATIVE_MODE_TAB);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(MOD_ID, Registries.ITEM);
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(MOD_ID, Registries.BLOCK);
    public static final DeferredRegister<BlockEntityType<?>> TILES = DeferredRegister.create(MOD_ID, Registries.BLOCK_ENTITY_TYPE);
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(MOD_ID, Registries.ENTITY_TYPE);
    public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(MOD_ID, Registries.PARTICLE_TYPE);
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(MOD_ID, Registries.SOUND_EVENT);
    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(MOD_ID, Registries.MENU);
    public static final DeferredRegister<RecipeSerializer<?>> RECIPES = DeferredRegister.create(MOD_ID, Registries.RECIPE_SERIALIZER);


    public static final RegistrySupplier<CreativeModeTab> EXPANDED_TAB = TABS.register("expanded_tab", () ->
            CreativeTabRegistry.create(Component.translatable("itemGroup." + MOD_ID + ".expanded_tab"),
                    () -> new ItemStack(ExpandedMod.CARDBOX_ITEM.get())));


    // Items
    public static final RegistrySupplier<ItemBaseFinite> SAW = ITEMS.register("saw",  () -> new ItemBaseFinite(1, 64));
    public static final RegistrySupplier<ItemBaseFinite> TAPE = ITEMS.register("tape",  () -> new ItemBaseFinite(1, 16));

    public static final RegistrySupplier<ItemBasket> BASKET = ITEMS.register("basket", ItemBasket::new);


    // Crafting Items
    public static final RegistrySupplier<ItemBase> SAWDUST = ITEMS.register("sawdust",  () -> new ItemBase(64));


    // [[ Blocks ]] Bathroom section
    public static final RegistrySupplier<Block> TOILET = BLOCKS.register("toilet", Toilet::new);
    public static final RegistrySupplier<Item> TOILET_ITEM = ITEMS.register("toilet", () -> new BlockItemBase(ExpandedMod.TOILET.get(), 64));

    public static final RegistrySupplier<Block> BATHROOM_SINK = BLOCKS.register("bathroom_sink", BathroomSink::new);
    public static final RegistrySupplier<Item> BATHROOM_SINK_ITEM = ITEMS.register("bathroom_sink", () -> new BlockItemBase(ExpandedMod.BATHROOM_SINK.get(), 64));

    public static final RegistrySupplier<Block> BATHTUB = BLOCKS.register("bathtub", BathTub::new);
    public static final RegistrySupplier<Item> BATHTUB_ITEM = ITEMS.register("bathtub", () -> new BlockItemBase(ExpandedMod.BATHTUB.get(), 64));

    public static final RegistrySupplier<Block> SHOWER_BOX = BLOCKS.register("shower_box", ShowerBox::new);
    public static final RegistrySupplier<Item> SHOWER_BOX_ITEM = ITEMS.register("shower_box", () -> new BlockItemBase(ExpandedMod.SHOWER_BOX.get(), 64));

    public static final RegistrySupplier<Block> SHOWER_HEAD = BLOCKS.register("shower_head", ShowerHead::new);
    public static final RegistrySupplier<Item> SHOWER_HEAD_ITEM = ITEMS.register("shower_head", () -> new BlockItemBase(ExpandedMod.SHOWER_HEAD.get(), 64));
    public static final RegistrySupplier<BlockEntityType<ShowerHeadTileEntity>> SHOWER_HEAD_TE = TILES.register("shower_head", () -> BlockEntityType.Builder.of(ShowerHeadTileEntity::new, SHOWER_HEAD.get()).build(null));

    public static final RegistrySupplier<Block> TOWEL_BAR = BLOCKS.register("towel_bar", TowelBar::new);
    public static final RegistrySupplier<Item> TOWEL_BAR_ITEM = ITEMS.register("towel_bar", () -> new BlockItemBase(ExpandedMod.TOWEL_BAR.get(), 64));

    public static final RegistrySupplier<Block> PAPER_HOLDER = BLOCKS.register("paper_holder", PaperHolder::new);
    public static final RegistrySupplier<Item> PAPER_HOLDER_ITEM = ITEMS.register("paper_holder", () -> new BlockItemBase(ExpandedMod.PAPER_HOLDER.get(), 64));

    public static final RegistrySupplier<Block> TRASH_CAN = BLOCKS.register("trash_can", TrashCan::new);
    public static final RegistrySupplier<Item> TRASH_CAN_ITEM = ITEMS.register("trash_can", () -> new BlockItemBase(ExpandedMod.TRASH_CAN.get(), 64));
    public static final RegistrySupplier<BlockEntityType<TrashCanTileEntity>> TRASH_CAN_TE = TILES.register("trash_can", () -> BlockEntityType.Builder.of(TrashCanTileEntity::new, TRASH_CAN.get()).build(null));

    public static final RegistrySupplier<Block> MIRROR = BLOCKS.register("mirror", MirrorBlock::new);
    public static final RegistrySupplier<Item> MIRROR_ITEM = ITEMS.register("mirror", () -> new BlockItemBase(ExpandedMod.MIRROR.get(), 64));


    // [[ Blocks ]] Bedroom section
    public static final RegistrySupplier<Block> ALARM_CLOCK = BLOCKS.register("alarm_clock", AlarmClock::new);
    public static final RegistrySupplier<Item> ALARM_CLOCK_ITEM = ITEMS.register("alarm_clock", () -> new BlockItemBase(ExpandedMod.ALARM_CLOCK.get(), 64));
    public static final RegistrySupplier<BlockEntityType<AlarmClockTileEntity>> ALARM_CLOCK_TE = TILES.register("alarm_clock", () -> BlockEntityType.Builder.of(AlarmClockTileEntity::new, ALARM_CLOCK.get()).build(null));

    public static final RegistrySupplier<Block> MODERN_BED = BLOCKS.register("modern_bed", ModernBed::new);
    public static final RegistrySupplier<Item> MODERN_BED_ITEM = ITEMS.register("modern_bed", () -> new BlockItemBase(ExpandedMod.MODERN_BED.get(), 64));
    public static final RegistrySupplier<BlockEntityType<ModernBedTileEntity>> MODERN_BED_TE = TILES.register("modern_bed", () -> BlockEntityType.Builder.of(ModernBedTileEntity::new, MODERN_BED.get()).build(null));

    public static final RegistrySupplier<Block> NIGHTSTAND = BLOCKS.register("nightstand", Nightstand::new);
    public static final RegistrySupplier<Item> NIGHTSTAND_ITEM = ITEMS.register("nightstand", () -> new BlockItemBase(ExpandedMod.NIGHTSTAND.get(), 64));
    public static final RegistrySupplier<BlockEntityType<NightstandTileEntity>> NIGHTSTAND_TE = TILES.register("nightstand", () -> BlockEntityType.Builder.of(NightstandTileEntity::new, NIGHTSTAND.get()).build(null));

    public static final RegistrySupplier<Block> BEDSIDE_TABLE = BLOCKS.register("bedside_table", BedsideTable::new);
    public static final RegistrySupplier<Item> BEDSIDE_TABLE_ITEM = ITEMS.register("bedside_table", () -> new BlockItemBase(ExpandedMod.BEDSIDE_TABLE.get(), 64));

    // [[ Blocks ]] Living Room section


    // [[ Blocks ]] Kitchen


    // [[ Blocks ]] Office section
    public static final RegistrySupplier<Block> FILE_CABINET = BLOCKS.register("file_cabinet", FileCabinet::new);
    public static final RegistrySupplier<Item> FILE_CABINET_ITEM = ITEMS.register("file_cabinet", () -> new BlockItemBase(ExpandedMod.FILE_CABINET.get(), 64));
    public static final RegistrySupplier<BlockEntityType<FileCabinetTileEntity>> FILE_CABINET_TE = TILES.register("file_cabinet", () -> BlockEntityType.Builder.of(FileCabinetTileEntity::new, FILE_CABINET.get()).build(null));


    // [[ Blocks ]] Others
    public static final RegistrySupplier<Block> CARDBOX = BLOCKS.register("cardbox", Cardbox::new);
    public static final RegistrySupplier<Item> CARDBOX_ITEM = ITEMS.register("cardbox", () -> new ItemCardbox(ExpandedMod.CARDBOX.get()));
    public static final RegistrySupplier<BlockEntityType<CardboxTileEntity>> CARDBOX_TE = TILES.register("cardbox", () -> BlockEntityType.Builder.of(CardboxTileEntity::new, CARDBOX.get()).build(null));

    public static final RegistrySupplier<Block> CLOCK_SIGN = BLOCKS.register("clock_sign", ClockSign::new);
    public static final RegistrySupplier<Item> CLOCK_SIGN_ITEM = ITEMS.register("clock_sign", () -> new BlockItemBase(ExpandedMod.CLOCK_SIGN.get(), 64));
    public static final RegistrySupplier<BlockEntityType<ClockSignTileEntity>> CLOCK_SIGN_TE = TILES.register("clock_sign", () -> BlockEntityType.Builder.of(ClockSignTileEntity::new, CLOCK_SIGN.get()).build(null));


    // Bedsheets
    public static final RegistrySupplier<ItemBase> BEDSHEET_TEMPLATE = ITEMS.register("bedsheet_template", () -> new ItemBase(64));

    public static final RegistrySupplier<ItemBedsheet> BEDSHEET_BAMBOO_WHITE = ITEMS.register("bedsheet_bamboo_white", () -> new ItemBedsheet(64, BedsheetPatterns.BAMBOO, DyeColor.WHITE, ChatFormatting.WHITE));
    public static final RegistrySupplier<ItemBedsheet> BEDSHEET_BAMBOO_ORANGE = ITEMS.register("bedsheet_bamboo_orange", () -> new ItemBedsheet(64, BedsheetPatterns.BAMBOO, DyeColor.ORANGE, ChatFormatting.GOLD));
    public static final RegistrySupplier<ItemBedsheet> BEDSHEET_BAMBOO_MAGENTA = ITEMS.register("bedsheet_bamboo_magenta", () -> new ItemBedsheet(64, BedsheetPatterns.BAMBOO, DyeColor.MAGENTA, ChatFormatting.LIGHT_PURPLE));
    public static final RegistrySupplier<ItemBedsheet> BEDSHEET_BAMBOO_LIGHT_BLUE = ITEMS.register("bedsheet_bamboo_light_blue", () -> new ItemBedsheet(64, BedsheetPatterns.BAMBOO, DyeColor.LIGHT_BLUE, ChatFormatting.AQUA));
    public static final RegistrySupplier<ItemBedsheet> BEDSHEET_BAMBOO_YELLOW = ITEMS.register("bedsheet_bamboo_yellow", () -> new ItemBedsheet(64, BedsheetPatterns.BAMBOO, DyeColor.YELLOW, ChatFormatting.YELLOW));
    public static final RegistrySupplier<ItemBedsheet> BEDSHEET_BAMBOO_LIME = ITEMS.register("bedsheet_bamboo_lime", () -> new ItemBedsheet(64, BedsheetPatterns.BAMBOO, DyeColor.LIME, ChatFormatting.GREEN));
    public static final RegistrySupplier<ItemBedsheet> BEDSHEET_BAMBOO_PINK = ITEMS.register("bedsheet_bamboo_pink", () -> new ItemBedsheet(64, BedsheetPatterns.BAMBOO, DyeColor.PINK, ChatFormatting.LIGHT_PURPLE));
    public static final RegistrySupplier<ItemBedsheet> BEDSHEET_BAMBOO_GRAY = ITEMS.register("bedsheet_bamboo_gray", () -> new ItemBedsheet(64, BedsheetPatterns.BAMBOO, DyeColor.GRAY, ChatFormatting.DARK_GRAY));
    public static final RegistrySupplier<ItemBedsheet> BEDSHEET_BAMBOO_LIGHT_GRAY = ITEMS.register("bedsheet_bamboo_light_gray", () -> new ItemBedsheet(64, BedsheetPatterns.BAMBOO, DyeColor.LIGHT_GRAY, ChatFormatting.GRAY));
    public static final RegistrySupplier<ItemBedsheet> BEDSHEET_BAMBOO_CYAN = ITEMS.register("bedsheet_bamboo_cyan", () -> new ItemBedsheet(64, BedsheetPatterns.BAMBOO, DyeColor.CYAN, ChatFormatting.DARK_AQUA));
    public static final RegistrySupplier<ItemBedsheet> BEDSHEET_BAMBOO_PURPLE = ITEMS.register("bedsheet_bamboo_purple", () -> new ItemBedsheet(64, BedsheetPatterns.BAMBOO, DyeColor.PURPLE, ChatFormatting.DARK_PURPLE));
    public static final RegistrySupplier<ItemBedsheet> BEDSHEET_BAMBOO_BLUE = ITEMS.register("bedsheet_bamboo_blue", () -> new ItemBedsheet(64, BedsheetPatterns.BAMBOO, DyeColor.BLUE, ChatFormatting.DARK_BLUE));
    public static final RegistrySupplier<ItemBedsheet> BEDSHEET_BAMBOO_BROWN = ITEMS.register("bedsheet_bamboo_brown", () -> new ItemBedsheet(64, BedsheetPatterns.BAMBOO, DyeColor.BROWN, ChatFormatting.RED));
    public static final RegistrySupplier<ItemBedsheet> BEDSHEET_BAMBOO_GREEN = ITEMS.register("bedsheet_bamboo_green", () -> new ItemBedsheet(64, BedsheetPatterns.BAMBOO, DyeColor.GREEN, ChatFormatting.DARK_GREEN));
    public static final RegistrySupplier<ItemBedsheet> BEDSHEET_BAMBOO_RED = ITEMS.register("bedsheet_bamboo_red", () -> new ItemBedsheet(64, BedsheetPatterns.BAMBOO, DyeColor.RED, ChatFormatting.DARK_RED));
    public static final RegistrySupplier<ItemBedsheet> BEDSHEET_BAMBOO_BLACK = ITEMS.register("bedsheet_bamboo_black", () -> new ItemBedsheet(64, BedsheetPatterns.BAMBOO, DyeColor.BLACK, ChatFormatting.DARK_GRAY));

    public static final RegistrySupplier<ItemBedsheet> BEDSHEET_BARK_WHITE = ITEMS.register("bedsheet_bark_white", () -> new ItemBedsheet(64, BedsheetPatterns.BARK, DyeColor.WHITE, ChatFormatting.WHITE));
    public static final RegistrySupplier<ItemBedsheet> BEDSHEET_BARK_ORANGE = ITEMS.register("bedsheet_bark_orange", () -> new ItemBedsheet(64, BedsheetPatterns.BARK, DyeColor.ORANGE, ChatFormatting.GOLD));
    public static final RegistrySupplier<ItemBedsheet> BEDSHEET_BARK_MAGENTA = ITEMS.register("bedsheet_bark_magenta", () -> new ItemBedsheet(64, BedsheetPatterns.BARK, DyeColor.MAGENTA, ChatFormatting.LIGHT_PURPLE));
    public static final RegistrySupplier<ItemBedsheet> BEDSHEET_BARK_LIGHT_BLUE = ITEMS.register("bedsheet_bark_light_blue", () -> new ItemBedsheet(64, BedsheetPatterns.BARK, DyeColor.LIGHT_BLUE, ChatFormatting.AQUA));
    public static final RegistrySupplier<ItemBedsheet> BEDSHEET_BARK_YELLOW = ITEMS.register("bedsheet_bark_yellow", () -> new ItemBedsheet(64, BedsheetPatterns.BARK, DyeColor.YELLOW, ChatFormatting.YELLOW));
    public static final RegistrySupplier<ItemBedsheet> BEDSHEET_BARK_LIME = ITEMS.register("bedsheet_bark_lime", () -> new ItemBedsheet(64, BedsheetPatterns.BARK, DyeColor.LIME, ChatFormatting.GREEN));
    public static final RegistrySupplier<ItemBedsheet> BEDSHEET_BARK_PINK = ITEMS.register("bedsheet_bark_pink", () -> new ItemBedsheet(64, BedsheetPatterns.BARK, DyeColor.PINK, ChatFormatting.LIGHT_PURPLE));
    public static final RegistrySupplier<ItemBedsheet> BEDSHEET_BARK_GRAY = ITEMS.register("bedsheet_bark_gray", () -> new ItemBedsheet(64, BedsheetPatterns.BARK, DyeColor.GRAY, ChatFormatting.DARK_GRAY));
    public static final RegistrySupplier<ItemBedsheet> BEDSHEET_BARK_LIGHT_GRAY = ITEMS.register("bedsheet_bark_light_gray", () -> new ItemBedsheet(64, BedsheetPatterns.BARK, DyeColor.LIGHT_GRAY, ChatFormatting.GRAY));
    public static final RegistrySupplier<ItemBedsheet> BEDSHEET_BARK_CYAN = ITEMS.register("bedsheet_bark_cyan", () -> new ItemBedsheet(64, BedsheetPatterns.BARK, DyeColor.CYAN, ChatFormatting.DARK_AQUA));
    public static final RegistrySupplier<ItemBedsheet> BEDSHEET_BARK_PURPLE = ITEMS.register("bedsheet_bark_purple", () -> new ItemBedsheet(64, BedsheetPatterns.BARK, DyeColor.PURPLE, ChatFormatting.DARK_PURPLE));
    public static final RegistrySupplier<ItemBedsheet> BEDSHEET_BARK_BLUE = ITEMS.register("bedsheet_bark_blue", () -> new ItemBedsheet(64, BedsheetPatterns.BARK, DyeColor.BLUE, ChatFormatting.DARK_BLUE));
    public static final RegistrySupplier<ItemBedsheet> BEDSHEET_BARK_BROWN = ITEMS.register("bedsheet_bark_brown", () -> new ItemBedsheet(64, BedsheetPatterns.BARK, DyeColor.BROWN, ChatFormatting.RED));
    public static final RegistrySupplier<ItemBedsheet> BEDSHEET_BARK_GREEN = ITEMS.register("bedsheet_bark_green", () -> new ItemBedsheet(64, BedsheetPatterns.BARK, DyeColor.GREEN, ChatFormatting.DARK_GREEN));
    public static final RegistrySupplier<ItemBedsheet> BEDSHEET_BARK_RED = ITEMS.register("bedsheet_bark_red", () -> new ItemBedsheet(64, BedsheetPatterns.BARK, DyeColor.RED, ChatFormatting.DARK_RED));
    public static final RegistrySupplier<ItemBedsheet> BEDSHEET_BARK_BLACK = ITEMS.register("bedsheet_bark_black", () -> new ItemBedsheet(64, BedsheetPatterns.BARK, DyeColor.BLACK, ChatFormatting.DARK_GRAY));

    public static final RegistrySupplier<ItemBedsheet> BEDSHEET_CORAL_WHITE = ITEMS.register("bedsheet_coral_white", () -> new ItemBedsheet(64, BedsheetPatterns.CORAL, DyeColor.WHITE, ChatFormatting.WHITE));
    public static final RegistrySupplier<ItemBedsheet> BEDSHEET_CORAL_ORANGE = ITEMS.register("bedsheet_coral_orange", () -> new ItemBedsheet(64, BedsheetPatterns.CORAL, DyeColor.ORANGE, ChatFormatting.GOLD));
    public static final RegistrySupplier<ItemBedsheet> BEDSHEET_CORAL_MAGENTA = ITEMS.register("bedsheet_coral_magenta", () -> new ItemBedsheet(64, BedsheetPatterns.CORAL, DyeColor.MAGENTA, ChatFormatting.LIGHT_PURPLE));
    public static final RegistrySupplier<ItemBedsheet> BEDSHEET_CORAL_LIGHT_BLUE = ITEMS.register("bedsheet_coral_light_blue", () -> new ItemBedsheet(64, BedsheetPatterns.CORAL, DyeColor.LIGHT_BLUE, ChatFormatting.AQUA));
    public static final RegistrySupplier<ItemBedsheet> BEDSHEET_CORAL_YELLOW = ITEMS.register("bedsheet_coral_yellow", () -> new ItemBedsheet(64, BedsheetPatterns.CORAL, DyeColor.YELLOW, ChatFormatting.YELLOW));
    public static final RegistrySupplier<ItemBedsheet> BEDSHEET_CORAL_LIME = ITEMS.register("bedsheet_coral_lime", () -> new ItemBedsheet(64, BedsheetPatterns.CORAL, DyeColor.LIME, ChatFormatting.GREEN));
    public static final RegistrySupplier<ItemBedsheet> BEDSHEET_CORAL_PINK = ITEMS.register("bedsheet_coral_pink", () -> new ItemBedsheet(64, BedsheetPatterns.CORAL, DyeColor.PINK, ChatFormatting.LIGHT_PURPLE));
    public static final RegistrySupplier<ItemBedsheet> BEDSHEET_CORAL_GRAY = ITEMS.register("bedsheet_coral_gray", () -> new ItemBedsheet(64, BedsheetPatterns.CORAL, DyeColor.GRAY, ChatFormatting.DARK_GRAY));
    public static final RegistrySupplier<ItemBedsheet> BEDSHEET_CORAL_LIGHT_GRAY = ITEMS.register("bedsheet_coral_light_gray", () -> new ItemBedsheet(64, BedsheetPatterns.CORAL, DyeColor.LIGHT_GRAY, ChatFormatting.GRAY));
    public static final RegistrySupplier<ItemBedsheet> BEDSHEET_CORAL_CYAN = ITEMS.register("bedsheet_coral_cyan", () -> new ItemBedsheet(64, BedsheetPatterns.CORAL, DyeColor.CYAN, ChatFormatting.DARK_AQUA));
    public static final RegistrySupplier<ItemBedsheet> BEDSHEET_CORAL_PURPLE = ITEMS.register("bedsheet_coral_purple", () -> new ItemBedsheet(64, BedsheetPatterns.CORAL, DyeColor.PURPLE, ChatFormatting.DARK_PURPLE));
    public static final RegistrySupplier<ItemBedsheet> BEDSHEET_CORAL_BLUE = ITEMS.register("bedsheet_coral_blue", () -> new ItemBedsheet(64, BedsheetPatterns.CORAL, DyeColor.BLUE, ChatFormatting.DARK_BLUE));
    public static final RegistrySupplier<ItemBedsheet> BEDSHEET_CORAL_BROWN = ITEMS.register("bedsheet_coral_brown", () -> new ItemBedsheet(64, BedsheetPatterns.CORAL, DyeColor.BROWN, ChatFormatting.RED));
    public static final RegistrySupplier<ItemBedsheet> BEDSHEET_CORAL_GREEN = ITEMS.register("bedsheet_coral_green", () -> new ItemBedsheet(64, BedsheetPatterns.CORAL, DyeColor.GREEN, ChatFormatting.DARK_GREEN));
    public static final RegistrySupplier<ItemBedsheet> BEDSHEET_CORAL_RED = ITEMS.register("bedsheet_coral_red", () -> new ItemBedsheet(64, BedsheetPatterns.CORAL, DyeColor.RED, ChatFormatting.DARK_RED));
    public static final RegistrySupplier<ItemBedsheet> BEDSHEET_CORAL_BLACK = ITEMS.register("bedsheet_coral_black", () -> new ItemBedsheet(64, BedsheetPatterns.CORAL, DyeColor.BLACK, ChatFormatting.DARK_GRAY));

    public static final RegistrySupplier<ItemBedsheet> BEDSHEET_MUSHROOM_WHITE = ITEMS.register("bedsheet_mushroom_white", () -> new ItemBedsheet(64, BedsheetPatterns.MUSHROOM, DyeColor.WHITE, ChatFormatting.WHITE));
    public static final RegistrySupplier<ItemBedsheet> BEDSHEET_MUSHROOM_ORANGE = ITEMS.register("bedsheet_mushroom_orange", () -> new ItemBedsheet(64, BedsheetPatterns.MUSHROOM, DyeColor.ORANGE, ChatFormatting.GOLD));
    public static final RegistrySupplier<ItemBedsheet> BEDSHEET_MUSHROOM_MAGENTA = ITEMS.register("bedsheet_mushroom_magenta", () -> new ItemBedsheet(64, BedsheetPatterns.MUSHROOM, DyeColor.MAGENTA, ChatFormatting.LIGHT_PURPLE));
    public static final RegistrySupplier<ItemBedsheet> BEDSHEET_MUSHROOM_LIGHT_BLUE = ITEMS.register("bedsheet_mushroom_light_blue", () -> new ItemBedsheet(64, BedsheetPatterns.MUSHROOM, DyeColor.LIGHT_BLUE, ChatFormatting.AQUA));
    public static final RegistrySupplier<ItemBedsheet> BEDSHEET_MUSHROOM_YELLOW = ITEMS.register("bedsheet_mushroom_yellow", () -> new ItemBedsheet(64, BedsheetPatterns.MUSHROOM, DyeColor.YELLOW, ChatFormatting.YELLOW));
    public static final RegistrySupplier<ItemBedsheet> BEDSHEET_MUSHROOM_LIME = ITEMS.register("bedsheet_mushroom_lime", () -> new ItemBedsheet(64, BedsheetPatterns.MUSHROOM, DyeColor.LIME, ChatFormatting.GREEN));
    public static final RegistrySupplier<ItemBedsheet> BEDSHEET_MUSHROOM_PINK = ITEMS.register("bedsheet_mushroom_pink", () -> new ItemBedsheet(64, BedsheetPatterns.MUSHROOM, DyeColor.PINK, ChatFormatting.LIGHT_PURPLE));
    public static final RegistrySupplier<ItemBedsheet> BEDSHEET_MUSHROOM_GRAY = ITEMS.register("bedsheet_mushroom_gray", () -> new ItemBedsheet(64, BedsheetPatterns.MUSHROOM, DyeColor.GRAY, ChatFormatting.DARK_GRAY));
    public static final RegistrySupplier<ItemBedsheet> BEDSHEET_MUSHROOM_LIGHT_GRAY = ITEMS.register("bedsheet_mushroom_light_gray", () -> new ItemBedsheet(64, BedsheetPatterns.MUSHROOM, DyeColor.LIGHT_GRAY, ChatFormatting.GRAY));
    public static final RegistrySupplier<ItemBedsheet> BEDSHEET_MUSHROOM_CYAN = ITEMS.register("bedsheet_mushroom_cyan", () -> new ItemBedsheet(64, BedsheetPatterns.MUSHROOM, DyeColor.CYAN, ChatFormatting.DARK_AQUA));
    public static final RegistrySupplier<ItemBedsheet> BEDSHEET_MUSHROOM_PURPLE = ITEMS.register("bedsheet_mushroom_purple", () -> new ItemBedsheet(64, BedsheetPatterns.MUSHROOM, DyeColor.PURPLE, ChatFormatting.DARK_PURPLE));
    public static final RegistrySupplier<ItemBedsheet> BEDSHEET_MUSHROOM_BLUE = ITEMS.register("bedsheet_mushroom_blue", () -> new ItemBedsheet(64, BedsheetPatterns.MUSHROOM, DyeColor.BLUE, ChatFormatting.DARK_BLUE));
    public static final RegistrySupplier<ItemBedsheet> BEDSHEET_MUSHROOM_BROWN = ITEMS.register("bedsheet_mushroom_brown", () -> new ItemBedsheet(64, BedsheetPatterns.MUSHROOM, DyeColor.BROWN, ChatFormatting.RED));
    public static final RegistrySupplier<ItemBedsheet> BEDSHEET_MUSHROOM_GREEN = ITEMS.register("bedsheet_mushroom_green", () -> new ItemBedsheet(64, BedsheetPatterns.MUSHROOM, DyeColor.GREEN, ChatFormatting.DARK_GREEN));
    public static final RegistrySupplier<ItemBedsheet> BEDSHEET_MUSHROOM_RED = ITEMS.register("bedsheet_mushroom_red", () -> new ItemBedsheet(64, BedsheetPatterns.MUSHROOM, DyeColor.RED, ChatFormatting.DARK_RED));
    public static final RegistrySupplier<ItemBedsheet> BEDSHEET_MUSHROOM_BLACK = ITEMS.register("bedsheet_mushroom_black", () -> new ItemBedsheet(64, BedsheetPatterns.MUSHROOM, DyeColor.BLACK, ChatFormatting.DARK_GRAY));

    public static final RegistrySupplier<ItemBedsheet> BEDSHEET_EXTRA1_BEE_NEST = ITEMS.register("bedsheet_extra1_bee_nest", () -> new ItemBedsheet(64, BedsheetPatterns.EXTRA1, DyeColor.WHITE, ChatFormatting.GOLD));
    public static final RegistrySupplier<ItemBedsheet> BEDSHEET_EXTRA1_BLAST_FURNACE = ITEMS.register("bedsheet_extra1_blast_furnace", () -> new ItemBedsheet(64, BedsheetPatterns.EXTRA1, DyeColor.ORANGE, ChatFormatting.DARK_GRAY));
    public static final RegistrySupplier<ItemBedsheet> BEDSHEET_EXTRA1_CHORUS = ITEMS.register("bedsheet_extra1_chorus", () -> new ItemBedsheet(64, BedsheetPatterns.EXTRA1, DyeColor.MAGENTA, ChatFormatting.DARK_PURPLE));
    public static final RegistrySupplier<ItemBedsheet> BEDSHEET_EXTRA1_CORAL = ITEMS.register("bedsheet_extra1_coral", () -> new ItemBedsheet(64, BedsheetPatterns.EXTRA1, DyeColor.LIGHT_BLUE, ChatFormatting.LIGHT_PURPLE));
    public static final RegistrySupplier<ItemBedsheet> BEDSHEET_EXTRA1_CRIMSON_STEM = ITEMS.register("bedsheet_extra1_crimson_stem", () -> new ItemBedsheet(64, BedsheetPatterns.EXTRA1, DyeColor.YELLOW, ChatFormatting.DARK_RED));
    public static final RegistrySupplier<ItemBedsheet> BEDSHEET_EXTRA1_HAY = ITEMS.register("bedsheet_extra1_hay", () -> new ItemBedsheet(64, BedsheetPatterns.EXTRA1, DyeColor.LIME, ChatFormatting.YELLOW));
    public static final RegistrySupplier<ItemBedsheet> BEDSHEET_EXTRA1_HONEYCOMB = ITEMS.register("bedsheet_extra1_honeycomb", () -> new ItemBedsheet(64, BedsheetPatterns.EXTRA1, DyeColor.PINK, ChatFormatting.GOLD));
    public static final RegistrySupplier<ItemBedsheet> BEDSHEET_EXTRA1_MAGMA = ITEMS.register("bedsheet_extra1_magma", () -> new ItemBedsheet(64, BedsheetPatterns.EXTRA1, DyeColor.GRAY, ChatFormatting.RED));
    public static final RegistrySupplier<ItemBedsheet> BEDSHEET_EXTRA1_STRIPPED_ACACIA_LOG = ITEMS.register("bedsheet_extra1_stripped_acacia_log", () -> new ItemBedsheet(64, BedsheetPatterns.EXTRA1, DyeColor.LIGHT_GRAY, ChatFormatting.GOLD));
    public static final RegistrySupplier<ItemBedsheet> BEDSHEET_EXTRA1_WARPED_STEM = ITEMS.register("bedsheet_extra1_warped_stem", () -> new ItemBedsheet(64, BedsheetPatterns.EXTRA1, DyeColor.CYAN, ChatFormatting.DARK_AQUA));


    /**
     * Entities
     */
    public static final RegistrySupplier<EntityType<SeatEntity>> SEAT = ENTITIES.register("seat",
            () -> EntityType.Builder.<SeatEntity>of(SeatEntity::new, MobCategory.MISC)
                    .build(String.valueOf(new ResourceLocation(ExpandedMod.MOD_ID, "seat"))));

    /**
     * Sounds
     */
    public static final RegistrySupplier<SoundEvent> SHOWER_SFX = SOUNDS.register("shower_sfx", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(ExpandedMod.MOD_ID, "shower_sfx")));

    /**
     * Particles
     */
    public static final RegistrySupplier<SimpleParticleType> SHOWER_PARTICLES = PARTICLES.register("shower_particle", () ->
            ExpandedExpectPlatform.getSimpleParticle(false));

    /**
     * Recipes
     */
    public static final RegistrySupplier<DamageableItemRemainderRecipe.Serializer> RECIPES_DAMAGEABLE_ITEM = RECIPES.register("damageable_item_remainder", DamageableItemRemainderRecipe.Serializer::new);

    public static void init() {
        BLOCKS.register();
        TILES.register();
        ITEMS.register();
        TABS.register();
        ENTITIES.register();
        PARTICLES.register();
        SOUNDS.register();
        MENUS.register();
        RECIPES.register();
    }
}
