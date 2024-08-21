package net.bilivrayka.callofequestria.block.custom;

import net.bilivrayka.callofequestria.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.AmethystBlock;
import net.minecraft.world.level.block.AmethystClusterBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;

public class BuddingClusterBlock extends AmethystBlock {
    public static final int GROWTH_CHANCE = 5;
    private static final Direction[] DIRECTIONS = Direction.values();

    public BuddingClusterBlock(BlockBehaviour.Properties pProperties) {
        super(pProperties);
    }

    public void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        if (pRandom.nextInt(5) == 0) {
            Direction $$4 = DIRECTIONS[pRandom.nextInt(DIRECTIONS.length)];
            BlockPos $$5 = pPos.relative($$4);
            BlockState $$6 = pLevel.getBlockState($$5);
            Block $$7 = null;
            if (canClusterGrowAtState($$6)) {
                $$7 = ModBlocks.SMALL_CRYSTAL_BUD_BLOCK.get();
            } else if ($$6.is(ModBlocks.SMALL_CRYSTAL_BUD_BLOCK.get()) && $$6.getValue(AmethystClusterBlock.FACING) == $$4) {
                $$7 = ModBlocks.MEDIUM_CRYSTAL_BUD_BLOCK.get();
            } else if ($$6.is(ModBlocks.MEDIUM_CRYSTAL_BUD_BLOCK.get()) && $$6.getValue(AmethystClusterBlock.FACING) == $$4) {
                $$7 = ModBlocks.LARGE_CRYSTAL_BUD_BLOCK.get();
            } else if ($$6.is(ModBlocks.LARGE_CRYSTAL_BUD_BLOCK.get()) && $$6.getValue(AmethystClusterBlock.FACING) == $$4) {
                $$7 = ModBlocks.CRYSTAL_CLUSTER_BLOCK.get();
            }

            if ($$7 != null) {
                BlockState $$8 = (BlockState)((BlockState)$$7.defaultBlockState().setValue(AmethystClusterBlock.FACING, $$4)).setValue(AmethystClusterBlock.WATERLOGGED, $$6.getFluidState().getType() == Fluids.WATER);
                pLevel.setBlockAndUpdate($$5, $$8);
            }

        }
    }

    public static boolean canClusterGrowAtState(BlockState pState) {
        return pState.isAir() || pState.is(Blocks.WATER) && pState.getFluidState().getAmount() == 8;
    }
}
