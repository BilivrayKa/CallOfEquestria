package net.bilivrayka.callofequestria.util;

import net.bilivrayka.callofequestria.CallOfEquestria;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.Registries;

public class ModTags {
    public static class Blocks {
        public static final TagKey<Block> ORES = create("ores");
        public static final TagKey<Block> OVERWORLD_ORES = create("overworld_ores");
        public static final TagKey<Block> NETHER_ORES = create("nether_ores");
        public static final TagKey<Block> WOOD = create("wood");

        private static TagKey<Block> create(String name) {
            return TagKey.create(Registries.BLOCK, new ResourceLocation(CallOfEquestria.MOD_ID, name));
        }
    }
}
