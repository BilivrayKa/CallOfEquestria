package net.bilivrayka.callofequestria.item;

import net.bilivrayka.callofequestria.CallOfEquestria;
import net.bilivrayka.callofequestria.block.ModBlocks;
import net.bilivrayka.callofequestria.block.PlushReg;
import net.bilivrayka.callofequestria.block.custom.PlushBaseBlock;
import net.bilivrayka.callofequestria.item.custom.*;
import net.bilivrayka.callofequestria.sound.ModSounds;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, CallOfEquestria.MOD_ID);

    public static final RegistryObject<Item> WOOD_PLANK = ITEMS.register("wood_plank",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> IRON_NAILS = ITEMS.register("iron_nails",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> WOOD_NAILS = ITEMS.register("wood_nails",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> LONG_STICK = ITEMS.register("long_stick",
            () -> new LongStickItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> BARK = ITEMS.register("bark",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> MUG = ITEMS.register("mug",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> MOD_BOTTLE = ITEMS.register("mod_bottle",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> JAR = ITEMS.register("jar",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> MUG_APPLE = ITEMS.register("mug_apple",
            () -> new DrinkItem(ModItems.MUG.get(),new Item.Properties().food(ModFoods.MUG_APPLE)));
    public static final RegistryObject<Item> MUFIIN = ITEMS.register("muffin",
            () -> new Item(new Item.Properties().food(ModFoods.MUFFIN)));
    public static final RegistryObject<Item> APPLE_VODKA = ITEMS.register("apple_vodka",
            () -> new DrinkItem(ModItems.MOD_BOTTLE.get(), new Item.Properties().food(ModFoods.APPLE_VODKA)));
    public static final RegistryObject<Item> NOTCH_APPLE_JAM = ITEMS.register("notch_apple_jam",
            () -> new DrinkItem(ModItems.JAR.get(), new Item.Properties().food(ModFoods.NOTCH_APPLE_JAM)));
    public static final RegistryObject<Item> HAYBURGER = ITEMS.register("hayburger",
            () -> new Yay(new Item.Properties().food(ModFoods.HAYBURGER)));

    public static final RegistryObject<Item> FLOWER_DUST = ITEMS.register("flower_dust",
            () -> new FlowerDustItem(new Item.Properties().food(ModFoods.FLOWER_DUST)));
    public static final RegistryObject<Item> FLOWER_DUST_BLACK = ITEMS.register("flower_dust_black",
            () -> new FlowerDustItem(new Item.Properties().food(ModFoods.FLOWER_DUST_BLACK)));
    public static final RegistryObject<Item> FLOWER_DUST_BLUE = ITEMS.register("flower_dust_blue",
            () -> new FlowerDustItem(new Item.Properties().food(ModFoods.FLOWER_DUST_BLUE)));
    public static final RegistryObject<Item> FLOWER_DUST_BROWN = ITEMS.register("flower_dust_brown",
            () -> new FlowerDustItem(new Item.Properties().food(ModFoods.FLOWER_DUST_BROWN)));
    public static final RegistryObject<Item> FLOWER_DUST_CYAN = ITEMS.register("flower_dust_cyan",
            () -> new FlowerDustItem(new Item.Properties().food(ModFoods.FLOWER_DUST_CYAN)));
    public static final RegistryObject<Item> FLOWER_DUST_GRAY = ITEMS.register("flower_dust_gray",
            () -> new FlowerDustItem(new Item.Properties().food(ModFoods.FLOWER_DUST_GRAY)));
    public static final RegistryObject<Item> FLOWER_DUST_GREEN = ITEMS.register("flower_dust_green",
            () -> new FlowerDustItem(new Item.Properties().food(ModFoods.FLOWER_DUST_GREEN)));
    public static final RegistryObject<Item> FLOWER_DUST_LIGHTBLUE = ITEMS.register("flower_dust_lightblue",
            () -> new FlowerDustItem(new Item.Properties().food(ModFoods.FLOWER_DUST_LIGHTBLUE)));
    public static final RegistryObject<Item> FLOWER_DUST_LIGHTGRAY = ITEMS.register("flower_dust_lightgray",
            () -> new FlowerDustItem(new Item.Properties().food(ModFoods.FLOWER_DUST_LIGHTGRAY)));
    public static final RegistryObject<Item> FLOWER_DUST_LIME = ITEMS.register("flower_dust_lime",
            () -> new FlowerDustItem(new Item.Properties().food(ModFoods.FLOWER_DUST_LIME)));
    public static final RegistryObject<Item> FLOWER_DUST_MAGENTA = ITEMS.register("flower_dust_magenta",
            () -> new FlowerDustItem(new Item.Properties().food(ModFoods.FLOWER_DUST_MAGENTA)));
    public static final RegistryObject<Item> FLOWER_DUST_ORANGE = ITEMS.register("flower_dust_orange",
            () -> new FlowerDustItem(new Item.Properties().food(ModFoods.FLOWER_DUST_ORANGE)));
    public static final RegistryObject<Item> FLOWER_DUST_PINK = ITEMS.register("flower_dust_pink",
            () -> new FlowerDustItem(new Item.Properties().food(ModFoods.FLOWER_DUST_PINK)));
    public static final RegistryObject<Item> FLOWER_DUST_PURPLE = ITEMS.register("flower_dust_purple",
            () -> new FlowerDustItem(new Item.Properties().food(ModFoods.FLOWER_DUST_PURPLE)));
    public static final RegistryObject<Item> FLOWER_DUST_RED = ITEMS.register("flower_dust_red",
            () -> new FlowerDustItem(new Item.Properties().food(ModFoods.FLOWER_DUST_RED)));
    public static final RegistryObject<Item> FLOWER_DUST_WHITE = ITEMS.register("flower_dust_white",
            () -> new FlowerDustItem(new Item.Properties().food(ModFoods.FLOWER_DUST_WHITE)));
    public static final RegistryObject<Item> FLOWER_DUST_YELLOW = ITEMS.register("flower_dust_yellow",
            () -> new FlowerDustItem(new Item.Properties().food(ModFoods.FLOWER_DUST_YELLOW)));
    public static final RegistryObject<Item> ZINNIA_BUSH_FLOWER = ITEMS.register("zinnia_bush_flower",
            () -> new ItemNameBlockItem(ModBlocks.ZINNIA_BUSH.get(), new Item.Properties()));
    public static final RegistryObject<Item> BUDDING_CRYSTAL = ITEMS.register("budding_crystal",
            () -> new ItemNameBlockItem(ModBlocks.BUDDING_CRYSTAL_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> CRYSTAL_CLUSTER = ITEMS.register("crystal_cluster",
            () -> new ItemNameBlockItem(ModBlocks.CRYSTAL_CLUSTER_BLOCK.get(), new Item.Properties()));

    public static final RegistryObject<Item> PLUSH_PINKIE_PIE = ITEMS.register("plush_pinkie_pie",
            () -> new PlushItem(ModSounds.SQUEE2.get(),ModSounds.SQUEE3.get(),PlushReg.PLUSH_PINKIE_PIE.get(), new Item.Properties()));
    public static final RegistryObject<Item> PLUSH_ROSE = ITEMS.register("plush_rose",
            () -> new PlushItem(ModSounds.ROSE1.get(),ModSounds.ROSE2.get(),PlushReg.PLUSH_ROSE.get(), new Item.Properties()));
    public static final RegistryObject<Item> PLUSH_FLUTTERSHY = ITEMS.register("plush_fluttershy",
            () -> new PlushItem(ModSounds.YAY.get(),ModSounds.YAY2.get(), (PlushReg.PLUSH_FLUTTERSHY.get()), new Item.Properties()));
    public static final RegistryObject<Item> PLUSH_APPLEJACK = ITEMS.register("plush_applejack",
            () -> new PlushItem(ModSounds.APPLEJACK1.get(),ModSounds.APPLEJACK2.get(), PlushReg.PLUSH_APPLEJACK.get(), new Item.Properties()));
    public static final RegistryObject<Item> PLUSH_RAINBOW_DASH = ITEMS.register("plush_rainbow_dash",
            () -> new PlushItem(ModSounds.RAINBOW_DASH1.get(),ModSounds.RAINBOW_DASH2.get(), PlushReg.PLUSH_RAINBOW_DASH.get(), new Item.Properties()));
    public static final RegistryObject<Item> PLUSH_RARITY = ITEMS.register("plush_rarity",
            () -> new PlushItem(ModSounds.RARITY1.get(),ModSounds.RARITY2.get(), PlushReg.PLUSH_RARITY.get(), new Item.Properties()));
    public static final RegistryObject<Item> PLUSH_SPARKLE = ITEMS.register("plush_sparkle",
            () -> new PlushItem(ModSounds.SPARKLE1.get(),ModSounds.SPARKLE2.get(), PlushReg.PLUSH_SPARKLE.get(), new Item.Properties()));
    public static final RegistryObject<Item> PLUSH_DERPY = ITEMS.register("plush_derpy",
            () -> new PlushItem(ModSounds.DERPY1.get(),ModSounds.DERPY2.get(), PlushReg.PLUSH_DERPY.get(), new Item.Properties()));
    public static final RegistryObject<Item> PLUSH_STARLIGHT = ITEMS.register("plush_starlight",
            () -> new PlushItem(ModSounds.STARLIGHT1.get(),ModSounds.STARLIGHT2.get(), PlushReg.PLUSH_STARLIGHT.get(), new Item.Properties()));
    public static final RegistryObject<Item> PLUSH_TRIXIE = ITEMS.register("plush_trixie",
            () -> new PlushItem(ModSounds.TRIXIE1.get(),ModSounds.TRIXIE2.get(), PlushReg.PLUSH_TRIXIE.get(), new Item.Properties()));


    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}

