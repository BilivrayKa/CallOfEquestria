package net.bilivrayka.callofequestria.block;

import com.mojang.blaze3d.platform.Lighting;
import net.bilivrayka.callofequestria.CallOfEquestria;
import net.bilivrayka.callofequestria.block.custom.BuddingClusterBlock;
import net.bilivrayka.callofequestria.block.custom.CrystalClusterBlock;
import net.bilivrayka.callofequestria.block.custom.PlushBaseBlock;
import net.bilivrayka.callofequestria.block.custom.ZinniaBushBlock;
import net.bilivrayka.callofequestria.item.ModItems;
import net.bilivrayka.callofequestria.sound.ModSounds;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, CallOfEquestria.MOD_ID);
    //tak ne pisat blyat
    public static final RegistryObject<Block> ZINNIA_BUSH = BLOCKS.register("zinnia_bush",
            () -> new ZinniaBushBlock(BlockBehaviour.Properties.copy(Blocks.SWEET_BERRY_BUSH).noOcclusion().noCollission()));
    public static final RegistryObject<Block> CRYSTAL_CLUSTER_BLOCK = BLOCKS.register("crystal_cluster_block",
            () -> new CrystalClusterBlock(7,3,BlockBehaviour.Properties.copy(Blocks.AMETHYST_CLUSTER).lightLevel((state) -> 9)));
    public static final RegistryObject<Block> BUDDING_CRYSTAL_BLOCK = BLOCKS.register("budding_crystal_block",
            () -> new BuddingClusterBlock(BlockBehaviour.Properties.copy(Blocks.BUDDING_AMETHYST)));
    //tak pisat
    public static final RegistryObject<Block> SMALL_CRYSTAL_BUD_BLOCK = registerBlock("small_crystal_bud_block",
            () -> new CrystalClusterBlock(3,3,BlockBehaviour.Properties.copy(Blocks.AMETHYST_CLUSTER).lightLevel((state) -> 6)));
    public static final RegistryObject<Block> MEDIUM_CRYSTAL_BUD_BLOCK = registerBlock("medium_crystal_bud_block",
            () -> new CrystalClusterBlock(4,3,BlockBehaviour.Properties.copy(Blocks.AMETHYST_CLUSTER).lightLevel((state) -> 7)));
    public static final RegistryObject<Block> LARGE_CRYSTAL_BUD_BLOCK = registerBlock("large_crystal_bud_block",
            () -> new CrystalClusterBlock(5,3,BlockBehaviour.Properties.copy(Blocks.AMETHYST_CLUSTER).lightLevel((state) -> 8)));


    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
