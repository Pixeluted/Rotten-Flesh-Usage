package com.PixelutedANDzKryle.rottenflashusage;

import com.PixelutedANDzKryle.rottenflashusage.core.init.BlockInit;
import com.PixelutedANDzKryle.rottenflashusage.core.init.ContainerTypesInit;
import com.PixelutedANDzKryle.rottenflashusage.core.init.ItemInit;
import com.PixelutedANDzKryle.rottenflashusage.core.init.TileEntityTypesInit;

import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@Mod("rottenflashusage")
@Mod.EventBusSubscriber(modid = RottenFlashUsage.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class RottenFlashUsage
{
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MOD_ID = "rottenflashusage";

    public RottenFlashUsage() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        BlockInit.BLOCKS.register(bus);
        TileEntityTypesInit.TILE_ENTITY_TYPES.register(bus);
        ContainerTypesInit.CONTAINER_TYPES.register(bus);
        ItemInit.ITEMS.register(bus);

        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public static void onRegisterItems(final RegistryEvent.Register<Item> event) {
        BlockInit.BLOCKS.getEntries().stream().map(RegistryObject::get).forEach(block -> {
            event.getRegistry().register(new BlockItem(block, new Item.Properties().tab(ItemGroup.TAB_MISC))
                    .setRegistryName(block.getRegistryName()));
        });
    }
}
