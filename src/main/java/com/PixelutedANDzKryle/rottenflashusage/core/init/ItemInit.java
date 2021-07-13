package com.PixelutedANDzKryle.rottenflashusage.core.init;

import com.PixelutedANDzKryle.rottenflashusage.RottenFlashUsage;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;


public class ItemInit {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, RottenFlashUsage.MOD_ID);

    
    public static final RegistryObject<Item> MEAL_CRASH = ITEMS.register("meal_crash", () -> new Item(new Item.Properties()
    		.stacksTo(64)));
}
