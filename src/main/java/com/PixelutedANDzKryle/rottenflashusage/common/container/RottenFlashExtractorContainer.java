package com.PixelutedANDzKryle.rottenflashusage.common.container;

import java.util.Objects;

import com.PixelutedANDzKryle.rottenflashusage.common.te.RottenFlashExtractorTileEntity;
import com.PixelutedANDzKryle.rottenflashusage.core.init.BlockInit;
import com.PixelutedANDzKryle.rottenflashusage.core.init.ContainerTypesInit;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.IntArray;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class RottenFlashExtractorContainer extends Container {

	private final IIntArray data;
	
	public final RottenFlashExtractorTileEntity te;
	private final IWorldPosCallable canInteractWithCallable;

	public RottenFlashExtractorContainer(final int windowId, final PlayerInventory playerInv, final RottenFlashExtractorTileEntity te, IIntArray data) {
		super(ContainerTypesInit.ROTTEN_FLASH_CONTAINER_TYPE.get(), windowId);
		this.te = te;
		this.data = data;
		this.canInteractWithCallable = IWorldPosCallable.create(te.getLevel(), te.getBlockPos());
		
		// Tile Entity
		
		this.addSlot(new Slot((IInventory) te, 0, 50 , 13)); // Rotten Flesh Input
		this.addSlot(new Slot((IInventory) te, 1, 50 , 52)); // Fuel Input
		this.addSlot(new Slot((IInventory) te, 2, 104 , 33)); // Output
		
		// Main Player Inventory
		for (int row = 0; row < 3; row++) {
			for (int col = 0 ; col < 9; col++) {
				this.addSlot(new Slot(playerInv, col + row * 9 + 9, 8 + col * 18, 166 - (4 - row) * 18 - 10));
			}
		}
		
		// Player Hotbar
		for (int col = 0; col < 9; col++) {
			this.addSlot(new Slot(playerInv, col, 8 + col * 18, 142));
		}
		
		addDataSlots(this.data);
	}
	
	public RottenFlashExtractorContainer(final int windowId, final PlayerInventory playerInv, final PacketBuffer data) {
		this(windowId, playerInv, getTileEntity(playerInv, data), new IntArray(4));
	}
	
	private static RottenFlashExtractorTileEntity getTileEntity(final PlayerInventory playerInv, final PacketBuffer data) {
		Objects.requireNonNull(playerInv,"Player inv cannot be null");
		Objects.requireNonNull(data,"Pack buffer cannot be null");
		final TileEntity te = playerInv.player.level.getBlockEntity(data.readBlockPos());
		
		if (te instanceof RottenFlashExtractorTileEntity) {
			return (RottenFlashExtractorTileEntity) te;
		}
		throw new IllegalStateException("Tile entity is null");
	}
	
	public boolean canInteractWith(PlayerEntity playerIn) {
		return stillValid(canInteractWithCallable, playerIn, BlockInit.ROTTEN_FLESH_EXTRACTOR.get());
	}

	@Override
	public boolean stillValid(PlayerEntity player) {
		return true;
	}
	
	public ItemStack quickMoveStack(PlayerEntity playerIn, int index) {
		ItemStack stack = ItemStack.EMPTY;
		Slot slot = this.getSlot(index);
		if (slot != null && slot.hasItem()) {
			ItemStack stack1 = slot.getItem();
			stack = stack1.copy();
			if (index < RottenFlashExtractorTileEntity.ContainerSize
					&& !this.moveItemStackTo(stack1, RottenFlashExtractorTileEntity.ContainerSize, this.slots.size(), true)) {
				return ItemStack.EMPTY;
			}
			if (!this.moveItemStackTo(stack1, 0, RottenFlashExtractorTileEntity.ContainerSize, false)) {
				return ItemStack.EMPTY;
			}

			if (stack1.isEmpty()) {
				slot.set(ItemStack.EMPTY);
			} else {
				slot.setChanged();
			}
		}
		return stack;
	}
	
	@OnlyIn(Dist.CLIENT)
	public int getProccess() {
		int procces = data.get(0);
		int maxTick = data.get(1);
		return maxTick != 0 && procces != 0 ? procces * 24 / maxTick : 0; 
	}
	
	@OnlyIn(Dist.CLIENT)
	public int getFuel() {
		int fuel = data.get(2);
		int maxFuel = data.get(3);
		return maxFuel != 0 && fuel != 0 ? fuel * 24 / maxFuel : 0; 
	}

}
