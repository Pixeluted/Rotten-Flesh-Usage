package com.PixelutedANDzKryle.rottenflashusage.common.Blocks;


import com.PixelutedANDzKryle.rottenflashusage.common.te.RottenFlashExtractorTileEntity;
import com.PixelutedANDzKryle.rottenflashusage.core.init.TileEntityTypesInit;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.network.NetworkHooks;

public class RottenFlashExtractor extends Block {

	public static final BooleanProperty Is_On = BooleanProperty.create("is_on");
	public static final DirectionProperty FACING = DirectionProperty.create("facing", Direction.Plane.HORIZONTAL);

	
	
	public RottenFlashExtractor() {
		super(AbstractBlock.Properties.of(Material.METAL).harvestTool(ToolType.PICKAXE).harvestLevel(2).strength(5f, 6f)
				.lightLevel(state -> state.getValue(Is_On) ? 14 : 0));
		registerDefaultState(this.defaultBlockState().setValue(Is_On, false).setValue(FACING, Direction.NORTH));
	}

	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(FACING, Is_On);
		super.createBlockStateDefinition(builder);
	}

	public BlockState getStateForPlacement(BlockItemUseContext context) {
		return defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
	}
	 
	public boolean hasTileEntity(BlockState state) {
		return true;
	}

	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return TileEntityTypesInit.ROTTEN_FLASH_EXTRACTOR_TILE_ENTITY_TYPE.get().create();
	}

	@SuppressWarnings("deprecation")
	@Override
	public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player,
			Hand handIn, BlockRayTraceResult hit) {
		
		if (!worldIn.isClientSide) {
			TileEntity te = worldIn.getBlockEntity(pos);
			
			if (te instanceof RottenFlashExtractorTileEntity) {
				NetworkHooks.openGui((ServerPlayerEntity) player, (RottenFlashExtractorTileEntity) te, pos);
			}
		}
		
		return super.use(state, worldIn, pos, player, handIn, hit);
	}
	

}
