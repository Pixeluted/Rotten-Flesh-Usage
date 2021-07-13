package com.PixelutedANDzKryle.rottenflashusage.client.screen;

import com.PixelutedANDzKryle.rottenflashusage.RottenFlashUsage;
import com.PixelutedANDzKryle.rottenflashusage.common.container.RottenFlashExtractorContainer;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class RottenFlashExtractorScreen extends ContainerScreen<RottenFlashExtractorContainer> {


	private static final ResourceLocation ROTTEN_FLASH_EXTRACTOR_GUI = new ResourceLocation(RottenFlashUsage.MOD_ID, "textures/gui/rotten_flash_extractor.png");

	public RottenFlashExtractorScreen(RottenFlashExtractorContainer screenContainer, PlayerInventory inv,
			ITextComponent titleIn) {
		
		super(screenContainer, inv, titleIn);
		
		this.leftPos = 0;
		this.topPos = 0;
		this.imageWidth = 175;
		this.imageHeight = 166;
		
	}

	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(matrixStack);
		super.render(matrixStack, mouseX, mouseY, partialTicks);
		this.renderTooltip(matrixStack, mouseX, mouseY);
	}
	
	@SuppressWarnings("deprecation")
	@Override
	protected void renderBg(MatrixStack matrixStack, float partialTicks, int Mousex, int Mousey) {
		RenderSystem.color4f(1f, 1f , 1f, 1f);
		this.minecraft.textureManager.bind(ROTTEN_FLASH_EXTRACTOR_GUI);
		int x = (this.width - this.imageWidth) / 2;
		int y = (this.height - this.imageHeight) / 2;
		
		// GUI Render
		this.blit(matrixStack, x, y, 0, 0, this.imageWidth, this.imageHeight);
		
		// Arrow Animation
		this.blit(matrixStack, x + 72 , y + 32, 175, 2, this.menu.getProccess() + 1, 17);
		
		// Fuel Animation
		
		this.blit(matrixStack, x + 49, y + 32, 175, 19, 18, this.menu.getFuel() + 1);
	}
	
	
}
