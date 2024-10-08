package net.bilivrayka.callofequestria.block;

import net.bilivrayka.callofequestria.CallOfEquestria;
import net.bilivrayka.callofequestria.block.custom.PlushBaseBlock;
import net.bilivrayka.callofequestria.sound.ModSounds;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class PlushReg {
    public static final DeferredRegister<Block> PLUSHIES =
            DeferredRegister.create(ForgeRegistries.BLOCKS, CallOfEquestria.MOD_ID);

    public static final RegistryObject<Block> PLUSH_PINKIE_PIE = registerBlock("plush_pinkie_pie",
            () -> new PlushBaseBlock(ModSounds.PINKIE1.get(),ModSounds.PINKIE2.get(), BlockBehaviour.Properties.copy(Blocks.PINK_WOOL).strength(2).noOcclusion()));
    public static final RegistryObject<Block> PLUSH_ROSE = registerBlock("plush_rose",
            () -> new PlushBaseBlock(ModSounds.ROSE1.get(),ModSounds.ROSE2.get(), BlockBehaviour.Properties.copy(Blocks.PINK_WOOL).strength(2).noOcclusion()));
    public static final RegistryObject<Block> PLUSH_FLUTTERSHY = registerBlock("plush_fluttershy",
            () -> new PlushBaseBlock(ModSounds.YAY.get(),ModSounds.YAY2.get(), BlockBehaviour.Properties.copy(Blocks.YELLOW_WOOL).strength(2).noOcclusion()));
    public static final RegistryObject<Block> PLUSH_APPLEJACK = registerBlock("plush_applejack",
            () -> new PlushBaseBlock(ModSounds.APPLEJACK1.get(),ModSounds.APPLEJACK2.get(), BlockBehaviour.Properties.copy(Blocks.ORANGE_WOOL).strength(2).noOcclusion()));
    public static final RegistryObject<Block> PLUSH_RAINBOW_DASH = registerBlock("plush_rainbow_dash",
            () -> new PlushBaseBlock(ModSounds.RAINBOW_DASH1.get(),ModSounds.RAINBOW_DASH2.get(), BlockBehaviour.Properties.copy(Blocks.CYAN_WOOL).strength(2).noOcclusion()));
    public static final RegistryObject<Block> PLUSH_RARITY = registerBlock("plush_rarity",
            () -> new PlushBaseBlock(ModSounds.RARITY1.get(),ModSounds.RARITY2.get(), BlockBehaviour.Properties.copy(Blocks.WHITE_WOOL).strength(2).noOcclusion()));
    public static final RegistryObject<Block> PLUSH_SPARKLE = registerBlock("plush_sparkle",
            () -> new PlushBaseBlock(ModSounds.SPARKLE1.get(),ModSounds.SPARKLE2.get(), BlockBehaviour.Properties.copy(Blocks.MAGENTA_WOOL).strength(2).noOcclusion()));
    public static final RegistryObject<Block> PLUSH_DERPY = registerBlock("plush_derpy",
            () -> new PlushBaseBlock(ModSounds.DERPY1.get(),ModSounds.DERPY2.get(), BlockBehaviour.Properties.copy(Blocks.GRAY_WOOL).strength(2).noOcclusion()));
    public static final RegistryObject<Block> PLUSH_STARLIGHT = registerBlock("plush_starlight",
            () -> new PlushBaseBlock(ModSounds.STARLIGHT1.get(),ModSounds.STARLIGHT2.get(), BlockBehaviour.Properties.copy(Blocks.CYAN_WOOL).strength(2).noOcclusion()));
    public static final RegistryObject<Block> PLUSH_TRIXIE = registerBlock("plush_trixie",
            () -> new PlushBaseBlock(ModSounds.TRIXIE1.get(),ModSounds.TRIXIE2.get(), BlockBehaviour.Properties.copy(Blocks.CYAN_WOOL).strength(2).noOcclusion()));
    public static final RegistryObject<Block> PLUSH_SWIRL = registerBlock("plush_swirl",
            () -> new PlushBaseBlock(ModSounds.SWIRL1.get(),ModSounds.SWIRL2.get(), BlockBehaviour.Properties.copy(Blocks.CYAN_WOOL).strength(2).noOcclusion()));
    public static final RegistryObject<Block> PLUSH_MINUETTE = registerBlock("plush_minuette",
            () -> new PlushBaseBlock(ModSounds.MINUETTE1.get(),ModSounds.MINUETTE2.get(), BlockBehaviour.Properties.copy(Blocks.CYAN_WOOL).strength(2).noOcclusion()));
    public static final RegistryObject<Block> PLUSH_MAUD_PIE = registerBlock("plush_maud_pie",
            () -> new PlushBaseBlock(ModSounds.MAUD_PIE1.get(),ModSounds.MAUD_PIE2.get(), BlockBehaviour.Properties.copy(Blocks.GRAY_WOOL).strength(2).noOcclusion()));
    public static final RegistryObject<Block> PLUSH_MARBLE_PIE = registerBlock("plush_marble_pie",
            () -> new PlushBaseBlock(ModSounds.MARBLE_PIE1.get(),ModSounds.MARBLE_PIE2.get(), BlockBehaviour.Properties.copy(Blocks.GRAY_WOOL).strength(2).noOcclusion()));
    public static final RegistryObject<Block> PLUSH_DARING_DO = registerBlock("plush_daring_do",
            () -> new PlushBaseBlock(ModSounds.DARING_DO1.get(),ModSounds.DARING_DO2.get(), BlockBehaviour.Properties.copy(Blocks.BROWN_WOOL).strength(2).noOcclusion()));

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = PLUSHIES.register(name, block);
        return toReturn;
    }

    public static void register(IEventBus eventBus) {
        PLUSHIES.register(eventBus);

    }
}
