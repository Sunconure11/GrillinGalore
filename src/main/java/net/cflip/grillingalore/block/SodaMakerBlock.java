package net.cflip.grillingalore.block;

import net.cflip.grillingalore.block.entity.SodaMakerBlockEntity;
import net.cflip.grillingalore.registry.ModBlockEntities;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class SodaMakerBlock extends BlockWithEntity {
	protected static final VoxelShape SHAPE = VoxelShapes.union(Block.createCuboidShape(1.0, 0.0, 1.0, 15.0, 2.0, 15.0), Block.createCuboidShape(6.0, 0.0, 6.0, 10.0, 14.0, 10.0));

	public SodaMakerBlock(Settings settings) {
		super(settings);
	}

	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return SHAPE;
	}

	@Override
	public BlockRenderType getRenderType(BlockState state) {
		return BlockRenderType.MODEL;
	}

	@Override
	public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return new SodaMakerBlockEntity(pos, state);
	}

	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		if (world.isClient)
			return ActionResult.SUCCESS;

		BlockEntity blockEntity = world.getBlockEntity(pos);
		if (blockEntity instanceof SodaMakerBlockEntity sodaMakerBlockEntity)
			player.openHandledScreen(sodaMakerBlockEntity);

		return ActionResult.CONSUME;
	}

	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
		return SodaMakerBlock.checkType(type, ModBlockEntities.SODA_MAKER, SodaMakerBlockEntity::tick);
	}
}
