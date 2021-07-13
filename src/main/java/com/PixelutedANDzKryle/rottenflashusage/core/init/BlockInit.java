package com.PixelutedANDzKryle.rottenflashusage.core.init;

import com.PixelutedANDzKryle.rottenflashusage.RottenFlashUsage;
import com.PixelutedANDzKryle.rottenflashusage.common.Blocks.RottenFlashExtractor;

import net.minecraft.block.Block;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BlockInit {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, RottenFlashUsage.MOD_ID);

    // Machines

    public static final RegistryObject<Block> ROTTEN_FLESH_EXTRACTOR = BLOCKS.register("rotten_flesh_extractor", RottenFlashExtractor::new);
}
