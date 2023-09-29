package me.clumsycat.furnitureexpanded.registries;

import me.clumsycat.furnitureexpanded.Expanded;
import me.clumsycat.furnitureexpanded.blocks.*;
import me.clumsycat.furnitureexpanded.blocks.tileentities.*;
import me.clumsycat.furnitureexpanded.items.*;
import me.clumsycat.furnitureexpanded.mixin.RemainderAcessor;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class RegistryHandler {
    public static final Block TOILET = new Toilet();
    public static final Block BATHROOM_SINK = new BathroomSink();
    public static final Block BATHTUB = new BathTub();
    public static final Block SHOWER_BOX = new ShowerBox();
    public static final Block SHOWER_HEAD = new ShowerHead();
    public static final Block TOWEL_BAR = new TowelBar();
    public static final Block PAPER_HOLDER = new PaperHolder();
    public static final Block TRASH_CAN = new TrashCan();
    public static final Block MIRROR = new MirrorBlock();
    public static final Block FILE_CABINET = new FileCabinet();
    public static final Block CARDBOX = new Cardbox();
    public static final Block CLOCK_SIGN = new ClockSign();


    public static final Item BASKET = new ItemBasket();
    public static final Item TAPE = new ItemBaseFinite(1, 16);
    public static final Item SAW = new ItemBaseFinite(1, 64);
    public static final Item SAWDUST = new ItemBase(64);


    public static void init() {
        CreativeExpandedTab.registerItemGroup();

        registerBlock("toilet", TOILET);
        registerBlock("bathroom_sink", BATHROOM_SINK);
        registerBlock("bathtub", BATHTUB);
        registerBlock("shower_box", SHOWER_BOX);
        registerBlock("shower_head", SHOWER_HEAD);
        registerBlock("towel_bar", TOWEL_BAR);
        registerBlock("paper_holder", PAPER_HOLDER);
        registerBlock("trash_can", TRASH_CAN);
        registerBlock("mirror", MIRROR);
        registerBlock("file_cabinet", FILE_CABINET);
        registerBlock("cardbox", CARDBOX);
        registerBlock("clock_sign", CLOCK_SIGN);


        registerItemBlock("toilet", new BlockItemBase(TOILET, 64));
        registerItemBlock("bathroom_sink", new BlockItemBase(BATHROOM_SINK, 64));
        registerItemBlock("bathtub", new BlockItemBase(BATHTUB, 64));
        registerItemBlock("shower_box", new BlockItemBase(SHOWER_BOX, 64));
        registerItemBlock("shower_head", new BlockItemBase(SHOWER_HEAD, 64));
        registerItemBlock("towel_bar", new BlockItemBase(TOWEL_BAR, 64));
        registerItemBlock("paper_holder", new BlockItemBase(PAPER_HOLDER, 64));
        registerItemBlock("trash_can", new BlockItemBase(TRASH_CAN, 64));
        registerItemBlock("mirror", new BlockItemBase(MIRROR, 64));
        registerItemBlock("file_cabinet", new BlockItemBase(FILE_CABINET, 64));
        registerItemBlock("clock_sign", new BlockItemBase(CLOCK_SIGN, 64));
        registerItemBlock("cardbox", new ItemCardbox(CARDBOX));


        registerItem("basket", BASKET);
        registerItem("tape", TAPE);
        registerItemRemainder("saw", SAW);
        registerItem("sawdust", SAWDUST);


        Registry.register(Registries.SOUND_EVENT, SHOWER_SFX_ID, SHOWER_SFX);

        Registry.register(Registries.PARTICLE_TYPE, SHOWER_PARTICLE_ID, SHOWER_PARTICLE);

    }

    private static Item registerItemRemainder(String name, Item item) {
        Item rm = registerItem(name, item);
        ((RemainderAcessor) rm).addRecipeRemainder(rm);
        return rm;
    }

    private static Item registerItem(String name, Item item) {
        Item i = Registry.register(Registries.ITEM, new Identifier(Expanded.MOD_ID, name), item);
        CreativeExpandedTab.addItemToExpandedTab(i);
        return i;
    }

    private static Item registerItemBlock(String name, BlockItem blockItem) {
        BlockItem bi = Registry.register(Registries.ITEM, new Identifier(Expanded.MOD_ID, name), blockItem);
        CreativeExpandedTab.addItemToExpandedTab(bi);
        return bi;
    }

    private static Block registerBlock(String name, Block block) {
        return Registry.register(Registries.BLOCK, new Identifier(Expanded.MOD_ID, name), block);
    }


    /**
     *
     * TILE ENTITIES
     *
     */

    public static final BlockEntityType<CardboxTileEntity> CARDBOX_TE = Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(Expanded.MOD_ID, "cardbox"), FabricBlockEntityTypeBuilder.create(CardboxTileEntity::new, CARDBOX).build());
    public static final BlockEntityType<FileCabinetTileEntity> FILE_CABINET_TE = Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(Expanded.MOD_ID, "file_cabinet"), FabricBlockEntityTypeBuilder.create(FileCabinetTileEntity::new, FILE_CABINET).build());
    public static final BlockEntityType<ClockSignTileEntity> CLOCK_SIGN_TE = Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(Expanded.MOD_ID, "clock_sign"), FabricBlockEntityTypeBuilder.create(ClockSignTileEntity::new, CLOCK_SIGN).build());
    public static final BlockEntityType<TrashCanTileEntity> TRASH_CAN_TE = Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(Expanded.MOD_ID, "trash_can"), FabricBlockEntityTypeBuilder.create(TrashCanTileEntity::new, TRASH_CAN).build());
    public static final BlockEntityType<ShowerHeadTileEntity> SHOWER_HEAD_TE = Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(Expanded.MOD_ID, "shower_head"), FabricBlockEntityTypeBuilder.create(ShowerHeadTileEntity::new, SHOWER_HEAD).build());



    public static final Identifier SHOWER_SFX_ID = new Identifier(Expanded.MOD_ID, "shower_sfx");
    public static SoundEvent SHOWER_SFX = SoundEvent.of(SHOWER_SFX_ID);


    public static final Identifier SHOWER_PARTICLE_ID = new Identifier(Expanded.MOD_ID, "shower_particle");
    public static final DefaultParticleType SHOWER_PARTICLE = FabricParticleTypes.simple();

}
