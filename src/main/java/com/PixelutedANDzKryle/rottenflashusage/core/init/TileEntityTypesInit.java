package com.PixelutedANDzKryle.rottenflashusage.core.init;

import com.PixelutedANDzKryle.rottenflashusage.RottenFlashUsage;
import com.PixelutedANDzKryle.rottenflashusage.common.te.RottenFlashExtractorTileEntity;

import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class TileEntityTypesInit {

	public static final DeferredRegister<TileEntityType<?>> TILE_ENTITY_TYPES = DeferredRegister
			.create(ForgeRegistries.TILE_ENTITIES, RottenFlashUsage.MOD_ID);

	// MACHINE TILE ENTITIES

	public static final RegistryObject<TileEntityType<RottenFlashExtractorTileEntity>> ROTTEN_FLASH_EXTRACTOR_TILE_ENTITY_TYPE = TILE_ENTITY_TYPES
			.register("rotten_flash_extractor", () -> TileEntityType.Builder.of(RottenFlashExtractorTileEntity::new, BlockInit.ROTTEN_FLESH_EXTRACTOR.get()).build(null));
}
