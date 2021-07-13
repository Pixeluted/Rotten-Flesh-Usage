package com.PixelutedANDzKryle.rottenflashusage.core.init;

import com.PixelutedANDzKryle.rottenflashusage.RottenFlashUsage;
import com.PixelutedANDzKryle.rottenflashusage.common.container.RottenFlashExtractorContainer;

import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ContainerTypesInit {

	public static final DeferredRegister<ContainerType<?>> CONTAINER_TYPES = DeferredRegister
			.create(ForgeRegistries.CONTAINERS, RottenFlashUsage.MOD_ID);
	
	public static final RegistryObject<ContainerType<RottenFlashExtractorContainer>> ROTTEN_FLASH_CONTAINER_TYPE = CONTAINER_TYPES.register("rotten_flash_extractor", () -> IForgeContainerType.create(RottenFlashExtractorContainer::new));

}
