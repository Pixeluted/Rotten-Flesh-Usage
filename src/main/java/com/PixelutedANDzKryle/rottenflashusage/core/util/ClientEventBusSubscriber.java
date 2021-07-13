package com.PixelutedANDzKryle.rottenflashusage.core.util;

import com.PixelutedANDzKryle.rottenflashusage.RottenFlashUsage;
import com.PixelutedANDzKryle.rottenflashusage.client.screen.RottenFlashExtractorScreen;
import com.PixelutedANDzKryle.rottenflashusage.core.init.ContainerTypesInit;

import net.minecraft.client.gui.ScreenManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = RottenFlashUsage.MOD_ID,bus = Bus.MOD, value = Dist.CLIENT)
public class ClientEventBusSubscriber {

	@SubscribeEvent
	public static void clientSetup(FMLClientSetupEvent event) {
		ScreenManager.register(ContainerTypesInit.ROTTEN_FLASH_CONTAINER_TYPE.get(), RottenFlashExtractorScreen::new);
	}
}
