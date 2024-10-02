package net.bilivrayka.callofequestria.entity.custom;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class BlockUtils {

    public static BlockState getBlockStateFromString(String blockName) {
        ResourceLocation blockId = new ResourceLocation(blockName);

        Block block = BuiltInRegistries.BLOCK.get(blockId);
        if (block == null) {
            return Blocks.ACACIA_LEAVES.defaultBlockState();
        }

        return block.defaultBlockState();
    }
}
