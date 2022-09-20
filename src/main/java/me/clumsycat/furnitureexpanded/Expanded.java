package me.clumsycat.furnitureexpanded;

import me.clumsycat.furnitureexpanded.entities.SeatEntity;
import me.clumsycat.furnitureexpanded.registries.RegistryHandler;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Expanded implements ModInitializer {
	public static final String MOD_ID = "furnitureexpanded";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static final EntityType<SeatEntity> SEAT = Registry.register(
			Registry.ENTITY_TYPE,
			new Identifier(Expanded.MOD_ID, "seatentity"),
			FabricEntityTypeBuilder.<SeatEntity>create(SpawnGroup.MISC, SeatEntity::new).dimensions(EntityDimensions.fixed(0f, 0f)).build()
	);
	public static final ItemGroup TAB = FabricItemGroupBuilder.build(
			new Identifier(MOD_ID, "general"),
			() -> new ItemStack(RegistryHandler.CARDBOX.asItem()));

	@Override
	public void onInitialize() {
		RegistryHandler.init();
	}

}
