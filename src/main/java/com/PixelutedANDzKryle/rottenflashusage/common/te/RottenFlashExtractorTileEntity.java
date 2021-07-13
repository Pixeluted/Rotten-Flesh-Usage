package com.PixelutedANDzKryle.rottenflashusage.common.te;


import com.PixelutedANDzKryle.rottenflashusage.RottenFlashUsage;
import com.PixelutedANDzKryle.rottenflashusage.common.Blocks.RottenFlashExtractor;
import com.PixelutedANDzKryle.rottenflashusage.common.container.RottenFlashExtractorContainer;
import com.PixelutedANDzKryle.rottenflashusage.core.init.ItemInit;
import com.PixelutedANDzKryle.rottenflashusage.core.init.TileEntityTypesInit;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.IIntArray;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.ForgeHooks;

public class RottenFlashExtractorTileEntity extends LockableLootTileEntity implements ITickableTileEntity{
	
	public static final int ContainerSize = 3;
	
	protected NonNullList<ItemStack> items = NonNullList.withSize(ContainerSize, ItemStack.EMPTY);
	
	private boolean is_Cooking = false;
	private final int max_Progress = 100;
	public  int cooking_Progress = 0;
	private int fuel_Level = 0;
	private int max_fuel_level = 300;
	
	
	public final IIntArray data = new IIntArray() {

		@Override
		public int get(int index) {
			switch (index) {
				case 0:
					return cooking_Progress;
				case 1:
					return max_Progress;
				case 2:
					return fuel_Level;
				case 3:
					return max_fuel_level;
				default:
					throw new IndexOutOfBoundsException();
			}
		}

		@Override
		public void set(int index, int value) {
			switch (index) {
				case 0:
					throw new IllegalStateException("This value can not be change");
				case 1:
					throw new IllegalStateException("This value can not be change");
				case 2:
					throw new IllegalStateException("This value can not be change");
				case 3:
					throw new IllegalStateException("This value can not be change");
				default :
					throw new IndexOutOfBoundsException();
			}
		}

		@Override
		public int getCount() {
			return 4;
		}
		
	};
	
	
	public RottenFlashExtractorTileEntity(TileEntityType<?> tileEntityTypeIn) {
		super(tileEntityTypeIn);
	}
	
	public RottenFlashExtractorTileEntity() {
		this(TileEntityTypesInit.ROTTEN_FLASH_EXTRACTOR_TILE_ENTITY_TYPE.get());
	}
	
	@Override
	public void tick() {		
		assert level != null;
		if (!level.isClientSide) {
			
			ItemStack inputSlot = items.get(0);
			ItemStack fuelSlot = items.get(1);
			ItemStack outputSlot = items.get(2);
			
			if (inputSlot.getItem().equals(Items.ROTTEN_FLESH)) { // Check if we have rotten flesh to extract
				
				if (!is_Cooking) {
					if (fuel_Level >= 5) { // Check if we have enough fuel level to start smelting.
						is_Cooking = true;
						setLIT(true);
					}else if (canBeBurn(fuelSlot)) { // If we dont, check if there is coal in fuelSlot, If there is coal we fill up fuelLevel and start smelting
						fuel_Level = max_fuel_level;
						if (fuelSlot.getCount() > 0) {
							fuelSlot.shrink(1);
						}else {
							items.set(1, ItemStack.EMPTY);
						}
						setChanged();
						is_Cooking = true;
						setLIT(true);
					}
				}
			}
			
			if (is_Cooking) {
				
				if (fuel_Level >= 5) { // Do we have still enough fuel level to continue
					cooking_Progress = cooking_Progress + 1;
					fuel_Level -= 1;
				}else if (fuel_Level < 5 && canBeBurn(fuelSlot)) { // We dont have fuel but we have coal
					fuel_Level = max_fuel_level;
					if (fuelSlot.getCount() > 0) {
						fuelSlot.shrink(1);
					}else {
						items.set(1, ItemStack.EMPTY);
					}
					cooking_Progress = cooking_Progress + 1;
					fuel_Level -= 1;
					setChanged();
				} else if (fuel_Level < 5 && !(canBeBurn(fuelSlot))) { // We dont have enough fuel
					if (!(fuel_Level < 5 && canBeBurn(fuelSlot))) {
						cooking_Progress = 0;
					}
					System.out.print("We dont have fuel");
					fuel_Level = 0;
					is_Cooking = false;
					setLIT(false);
				}
				
				if (inputSlot.getItem().equals(Items.AIR)) {
					is_Cooking = false;
					cooking_Progress = 0;
					setLIT(false);
				}
				
				if (cooking_Progress >= max_Progress) {
					cooking_Progress = 0;
					setLIT(false);
					is_Cooking = false;
					
					customShrink(inputSlot, 1 , 0);
					
					if (outputSlot.getCount() > 0) {
						outputSlot.grow(1);
					}else {
						items.set(2, new ItemStack(ItemInit.MEAL_CRASH.get()));
					}
					setChanged();
				}
			}else {
				if (fuel_Level != 0) {
					fuel_Level -= 1;
				}
			}
		}
	}
	
	private void setLIT(boolean lit) {
		level.setBlock(worldPosition, getBlockState().setValue(RottenFlashExtractor.Is_On, lit), ContainerSize);
	}
	
	private void customShrink(ItemStack stack, int toShrink, int ItemIndex) {
		if (stack.getCount() > 0) {
			stack.shrink(toShrink);
		}else {
			items.set(ItemIndex, ItemStack.EMPTY);
		}
	}
	
	private boolean canBeBurn(ItemStack stack) {
		if (ForgeHooks.getBurnTime(stack) > 0) {
			return true;
		}else {
			return false;
		}
	}

	@Override
	public int getContainerSize() {
		return ContainerSize;
	}

	@Override
	protected NonNullList<ItemStack> getItems() {
		return this.items;
	}

	@Override
	protected void setItems(NonNullList<ItemStack> itemsIn) {
		this.items = itemsIn;
	}

	@Override
	protected ITextComponent getDefaultName() {
		return new TranslationTextComponent("container." + RottenFlashUsage.MOD_ID + ".rotten_flesh_extractor");
	}

	@Override
	protected Container createMenu(int id, PlayerInventory player) {
		return new RottenFlashExtractorContainer(id, player, this, this.data);
	}

	public CompoundNBT write(CompoundNBT compound) {
		super.save(compound);
		if (!this.trySaveLootTable(compound)) {
			ItemStackHelper.saveAllItems(compound, items);
			
		}
		
		return compound;
	}
	
	public void read(BlockState state, CompoundNBT nbt) {
		super.load(state, nbt);
		this.items = NonNullList.withSize(ContainerSize, ItemStack.EMPTY);
		if (!this.tryLoadLootTable(nbt)) {
			ItemStackHelper.loadAllItems(nbt, items);
		}
	}
	
}
