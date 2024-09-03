package net.bilivrayka.callofequestria.event;

import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import java.util.HashSet;
import java.util.Set;

public class VillagerProfessionHandler {

    private static final Set<Block> PROFESSION_BLOCKS = new HashSet<>();

    static {
        PROFESSION_BLOCKS.add(Blocks.LECTERN);
        PROFESSION_BLOCKS.add(Blocks.COMPOSTER);
        PROFESSION_BLOCKS.add(Blocks.SMITHING_TABLE);
        PROFESSION_BLOCKS.add(Blocks.BLAST_FURNACE);
        PROFESSION_BLOCKS.add(Blocks.CAULDRON);
        PROFESSION_BLOCKS.add(Blocks.BARREL);
        PROFESSION_BLOCKS.add(Blocks.STONECUTTER);
        PROFESSION_BLOCKS.add(Blocks.FLETCHING_TABLE);
        PROFESSION_BLOCKS.add(Blocks.BREWING_STAND);
        PROFESSION_BLOCKS.add(Blocks.CARTOGRAPHY_TABLE);
        PROFESSION_BLOCKS.add(Blocks.LOOM);
        PROFESSION_BLOCKS.add(Blocks.GRINDSTONE);
        PROFESSION_BLOCKS.add(Blocks.SMOKER);
        PROFESSION_BLOCKS.add(Blocks.BELL);
    }

    public static boolean isProfessionBlock(Block block) {
        return PROFESSION_BLOCKS.contains(block);
    }

    public static Block getBlockByProfession(VillagerProfession profession) {
        if (profession.equals(VillagerProfession.ARMORER)) {
            return Blocks.BLAST_FURNACE;
        } else if (profession.equals(VillagerProfession.BUTCHER)) {
            return Blocks.SMOKER;
        } else if (profession.equals(VillagerProfession.CARTOGRAPHER)) {
            return Blocks.CARTOGRAPHY_TABLE;
        } else if (profession.equals(VillagerProfession.CLERIC)) {
            return Blocks.BREWING_STAND;
        } else if (profession.equals(VillagerProfession.FARMER)) {
            return Blocks.COMPOSTER;
        } else if (profession.equals(VillagerProfession.FISHERMAN)) {
            return Blocks.BARREL;
        } else if (profession.equals(VillagerProfession.FLETCHER)) {
            return Blocks.FLETCHING_TABLE;
        } else if (profession.equals(VillagerProfession.LEATHERWORKER)) {
            return Blocks.CAULDRON;
        } else if (profession.equals(VillagerProfession.LIBRARIAN)) {
            return Blocks.LECTERN;
        } else if (profession.equals(VillagerProfession.MASON)) {
            return Blocks.STONECUTTER;
        } else if (profession.equals(VillagerProfession.SHEPHERD)) {
            return Blocks.LOOM;
        } else if (profession.equals(VillagerProfession.TOOLSMITH)) {
            return Blocks.SMITHING_TABLE;
        } else if (profession.equals(VillagerProfession.WEAPONSMITH)) {
            return Blocks.GRINDSTONE;
        } else{
            return Blocks.AIR;
        }
    }
}
