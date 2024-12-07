package net.bilivrayka.callofequestria.item;

import net.bilivrayka.callofequestria.CallOfEquestria;
import net.bilivrayka.callofequestria.block.ModBlocks;
import net.bilivrayka.callofequestria.block.PlushReg;
import net.bilivrayka.callofequestria.block.custom.PlushBaseBlock;
import net.bilivrayka.callofequestria.fluid.ModFluids;
import net.bilivrayka.callofequestria.item.custom.*;
import net.bilivrayka.callofequestria.sound.ModSounds;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static net.minecraft.world.item.Items.registerBlock;

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

    public static final RegistryObject<Item> APPLE_JUICE_BUCKET = ITEMS.register("apple_juice_bucket",
            () -> new BucketItem(ModFluids.SOURCE_APPLE_JUICE,new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));

    public static final RegistryObject<Item> MUG = ITEMS.register("mug",
            () -> new DrinkBucketItem(ModFluids.SOURCE_APPLE_JUICE.get(),new Item.Properties()));
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
    /*
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
    */
    public static final RegistryObject<Item> ZINNIA_BUSH_FLOWER = ITEMS.register("zinnia_bush_flower",
            () -> new ItemNameBlockItem(ModBlocks.ZINNIA_BUSH.get(), new Item.Properties()));
    public static final RegistryObject<Item> BUDDING_CRYSTAL = ITEMS.register("budding_crystal",
            () -> new ItemNameBlockItem(ModBlocks.BUDDING_CRYSTAL_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> CRYSTAL_CLUSTER = ITEMS.register("crystal_cluster",
            () -> new ItemNameBlockItem(ModBlocks.CRYSTAL_CLUSTER_BLOCK.get(), new Item.Properties()));


    //Дальше бога нет
    public static final RegistryObject<Item> PLUSH_PINKIE_PIE = ITEMS.register("plush_pinkie_pie",
            () -> new PlushItem(ModSounds.PINKIE1.get(),ModSounds.PINKIE2.get(),ModSounds.STEREO_PINKIE1.get(),ModSounds.STEREO_PINKIE2.get(),PlushReg.PLUSH_PINKIE_PIE.get(), new Item.Properties()));
    public static final RegistryObject<Item> PLUSH_ROSE = ITEMS.register("plush_rose",
            () -> new PlushItem(ModSounds.ROSE1.get(),ModSounds.ROSE2.get(),ModSounds.STEREO_ROSE1.get(),ModSounds.STEREO_ROSE2.get(),PlushReg.PLUSH_ROSE.get(), new Item.Properties()));
    public static final RegistryObject<Item> PLUSH_FLUTTERSHY = ITEMS.register("plush_fluttershy",
            () -> new PlushItem(ModSounds.YAY.get(),ModSounds.YAY2.get(),ModSounds.STEREO_YAY.get(),ModSounds.STEREO_YAY2.get(), (PlushReg.PLUSH_FLUTTERSHY.get()), new Item.Properties()));
    public static final RegistryObject<Item> PLUSH_APPLEJACK = ITEMS.register("plush_applejack",
            () -> new PlushItem(ModSounds.APPLEJACK1.get(),ModSounds.APPLEJACK2.get(),ModSounds.STEREO_APPLEJACK1.get(),ModSounds.STEREO_APPLEJACK2.get(), PlushReg.PLUSH_APPLEJACK.get(), new Item.Properties()));
    public static final RegistryObject<Item> PLUSH_RAINBOW_DASH = ITEMS.register("plush_rainbow_dash",
            () -> new PlushItem(ModSounds.RAINBOW_DASH1.get(),ModSounds.RAINBOW_DASH2.get(),ModSounds.STEREO_RAINBOW_DASH1.get(),ModSounds.STEREO_RAINBOW_DASH2.get(), PlushReg.PLUSH_RAINBOW_DASH.get(), new Item.Properties()));
    public static final RegistryObject<Item> PLUSH_RARITY = ITEMS.register("plush_rarity",
            () -> new PlushItem(ModSounds.RARITY1.get(),ModSounds.RARITY2.get(),ModSounds.STEREO_RARITY1.get(),ModSounds.STEREO_RARITY2.get(), PlushReg.PLUSH_RARITY.get(), new Item.Properties()));
    public static final RegistryObject<Item> PLUSH_SPARKLE = ITEMS.register("plush_sparkle",
            () -> new PlushItem(ModSounds.SPARKLE1.get(),ModSounds.SPARKLE2.get(),ModSounds.STEREO_SPARKLE1.get(),ModSounds.STEREO_SPARKLE2.get(), PlushReg.PLUSH_SPARKLE.get(), new Item.Properties()));
    public static final RegistryObject<Item> PLUSH_DERPY = ITEMS.register("plush_derpy",
            () -> new PlushItem(ModSounds.DERPY1.get(),ModSounds.DERPY2.get(),ModSounds.STEREO_DERPY1.get(),ModSounds.STEREO_DERPY2.get(), PlushReg.PLUSH_DERPY.get(), new Item.Properties()));
    public static final RegistryObject<Item> PLUSH_STARLIGHT = ITEMS.register("plush_starlight",
            () -> new PlushItem(ModSounds.STARLIGHT1.get(),ModSounds.STARLIGHT2.get(),ModSounds.STEREO_STARLIGHT1.get(),ModSounds.STEREO_STARLIGHT2.get(), PlushReg.PLUSH_STARLIGHT.get(), new Item.Properties()));
    public static final RegistryObject<Item> PLUSH_TRIXIE = ITEMS.register("plush_trixie",
            () -> new PlushItem(ModSounds.TRIXIE1.get(),ModSounds.TRIXIE2.get(),ModSounds.STEREO_TRIXIE1.get(),ModSounds.STEREO_TRIXIE2.get(), PlushReg.PLUSH_TRIXIE.get(), new Item.Properties()));
    public static final RegistryObject<Item> PLUSH_SWIRL = ITEMS.register("plush_swirl",
            () -> new PlushItem(ModSounds.SWIRL1.get(),ModSounds.SWIRL2.get(),ModSounds.STEREO_SWIRL1.get(),ModSounds.STEREO_SWIRL2.get(), PlushReg.PLUSH_SWIRL.get(), new Item.Properties()));
    public static final RegistryObject<Item> PLUSH_MINUETTE = ITEMS.register("plush_minuette",
            () -> new PlushItem(ModSounds.MINUETTE1.get(),ModSounds.MINUETTE2.get(),ModSounds.STEREO_MINUETTE1.get(),ModSounds.STEREO_MINUETTE2.get(), PlushReg.PLUSH_MINUETTE.get(), new Item.Properties()));
    public static final RegistryObject<Item> PLUSH_MAUD_PIE = ITEMS.register("plush_maud_pie",
            () -> new PlushItem(ModSounds.MAUD_PIE1.get(),ModSounds.MAUD_PIE2.get(),ModSounds.STEREO_MAUD_PIE1.get(),ModSounds.STEREO_MAUD_PIE2.get(), PlushReg.PLUSH_MAUD_PIE.get(), new Item.Properties()));
    public static final RegistryObject<Item> PLUSH_MARBLE_PIE = ITEMS.register("plush_marble_pie",
            () -> new PlushItem(ModSounds.MARBLE_PIE1.get(),ModSounds.MARBLE_PIE2.get(),ModSounds.STEREO_MARBLE_PIE1.get(),ModSounds.STEREO_MARBLE_PIE2.get(), PlushReg.PLUSH_MARBLE_PIE.get(), new Item.Properties()));
    public static final RegistryObject<Item> PLUSH_DARING_DO = ITEMS.register("plush_daring_do",
            () -> new PlushItem(ModSounds.DARING_DO1.get(),ModSounds.DARING_DO2.get(),ModSounds.STEREO_DARING_DO1.get(),ModSounds.STEREO_DARING_DO2.get(), PlushReg.PLUSH_DARING_DO.get(), new Item.Properties()));

    public static final RegistryObject<Item> PLUSH_GRANNY_SMITH = ITEMS.register("plush_granny_smith",
            () -> new PlushItem(ModSounds.GRANNY_SMITH1.get(),ModSounds.GRANNY_SMITH2.get(),ModSounds.STEREO_GRANNY_SMITH1.get(),ModSounds.STEREO_GRANNY_SMITH2.get(), PlushReg.PLUSH_GRANNY_SMITH.get(), new Item.Properties()));
    public static final RegistryObject<Item> PLUSH_LIMESTONE_PIE = ITEMS.register("plush_limestone_pie",
            () -> new PlushItem(ModSounds.LIMESTONE_PIE1.get(),ModSounds.LIMESTONE_PIE2.get(),ModSounds.STEREO_LIMESTONE_PIE1.get(),ModSounds.STEREO_LIMESTONE_PIE2.get(), PlushReg.PLUSH_LIMESTONE_PIE.get(), new Item.Properties()));
    public static final RegistryObject<Item> PLUSH_NURSE_REDHEART = ITEMS.register("plush_nurse_redheart",
            () -> new PlushItem(ModSounds.NURSE_REDHEART1.get(),ModSounds.NURSE_REDHEART2.get(),ModSounds.STEREO_NURSE_REDHEART1.get(),ModSounds.STEREO_NURSE_REDHEART2.get(), PlushReg.PLUSH_NURSE_REDHEART.get(), new Item.Properties()));
    public static final RegistryObject<Item> PLUSH_LILY_VALLEY = ITEMS.register("plush_lily_valley",
            () -> new PlushItem(ModSounds.LILY_VALLEY1.get(),ModSounds.LILY_VALLEY2.get(),ModSounds.STEREO_LILY_VALLEY1.get(),ModSounds.STEREO_LILY_VALLEY2.get(), PlushReg.PLUSH_LILY_VALLEY.get(), new Item.Properties()));
    public static final RegistryObject<Item> PLUSH_DAISY = ITEMS.register("plush_daisy",
            () -> new PlushItem(ModSounds.DAISY1.get(),ModSounds.DAISY2.get(),ModSounds.STEREO_DAISY1.get(),ModSounds.STEREO_DAISY2.get(), PlushReg.PLUSH_DAISY.get(), new Item.Properties()));
    public static final RegistryObject<Item> PLUSH_LOTUS_BLOSSOM = ITEMS.register("plush_lotus_blossom",
            () -> new PlushItem(ModSounds.LOTUS_BLOSSOM1.get(),ModSounds.LOTUS_BLOSSOM2.get(),ModSounds.STEREO_LOTUS_BLOSSOM1.get(),ModSounds.STEREO_LOTUS_BLOSSOM2.get(), PlushReg.PLUSH_LOTUS_BLOSSOM.get(), new Item.Properties()));
    public static final RegistryObject<Item> PLUSH_ALOE_VERA = ITEMS.register("plush_aloe_vera",
            () -> new PlushItem(ModSounds.ALOE_VERA1.get(),ModSounds.ALOE_VERA2.get(),ModSounds.STEREO_ALOE_VERA1.get(),ModSounds.STEREO_ALOE_VERA2.get(), PlushReg.PLUSH_ALOE_VERA.get(), new Item.Properties()));
    public static final RegistryObject<Item> PLUSH_OCTAVIA_MELODY = ITEMS.register("plush_octavia_melody",
            () -> new PlushItem(ModSounds.OCTAVIA_MELODY1.get(),ModSounds.OCTAVIA_MELODY2.get(),ModSounds.STEREO_OCTAVIA_MELODY1.get(),ModSounds.STEREO_OCTAVIA_MELODY2.get(), PlushReg.PLUSH_OCTAVIA_MELODY.get(), new Item.Properties()));
    public static final RegistryObject<Item> PLUSH_DJ_PON3 = ITEMS.register("plush_dj_pon3",
            () -> new PlushItem(ModSounds.DJ_PON31.get(),ModSounds.DJ_PON32.get(),ModSounds.STEREO_DJ_PON31.get(),ModSounds.STEREO_DJ_PON32.get(), PlushReg.PLUSH_DJ_PON3.get(), new Item.Properties()));
    public static final RegistryObject<Item> PLUSH_CHEERILEE = ITEMS.register("plush_cheerilee",
            () -> new PlushItem(ModSounds.SQUEE2.get(),ModSounds.SQUEE3.get(),ModSounds.STEREO_SQUEE2.get(),ModSounds.STEREO_SQUEE3.get(), PlushReg.PLUSH_CHEERILEE.get(), new Item.Properties()));
    public static final RegistryObject<Item> PLUSH_SPITFIRE = ITEMS.register("plush_spitfire",
            () -> new PlushItem(ModSounds.SQUEE2.get(),ModSounds.SQUEE3.get(),ModSounds.STEREO_SQUEE2.get(),ModSounds.STEREO_SQUEE3.get(), PlushReg.PLUSH_SPITFIRE.get(), new Item.Properties()));
    public static final RegistryObject<Item> PLUSH_CHERRY_BERRY = ITEMS.register("plush_cherry_berry",
            () -> new PlushItem(ModSounds.SQUEE2.get(),ModSounds.SQUEE3.get(),ModSounds.STEREO_SQUEE2.get(),ModSounds.STEREO_SQUEE3.get(), PlushReg.PLUSH_CHERRY_BERRY.get(), new Item.Properties()));
    public static final RegistryObject<Item> PLUSH_SUGAR_BELLE = ITEMS.register("plush_sugar_belle",
            () -> new PlushItem(ModSounds.SQUEE2.get(),ModSounds.SQUEE3.get(),ModSounds.STEREO_SQUEE2.get(),ModSounds.STEREO_SQUEE3.get(), PlushReg.PLUSH_SUGAR_BELLE.get(), new Item.Properties()));
    public static final RegistryObject<Item> PLUSH_TWILIGHT_VELVET = ITEMS.register("plush_twilight_velvet",
            () -> new PlushItem(ModSounds.SQUEE2.get(),ModSounds.SQUEE3.get(),ModSounds.STEREO_SQUEE2.get(),ModSounds.STEREO_SQUEE3.get(), PlushReg.PLUSH_TWILIGHT_VELVET.get(), new Item.Properties()));
    public static final RegistryObject<Item> PLUSH_LIGHTNING_DUST = ITEMS.register("plush_lightning_dust",
            () -> new PlushItem(ModSounds.SQUEE2.get(),ModSounds.SQUEE3.get(),ModSounds.STEREO_SQUEE2.get(),ModSounds.STEREO_SQUEE3.get(), PlushReg.PLUSH_LIGHTNING_DUST.get(), new Item.Properties()));

    public static final RegistryObject<Item> BOULDER = ITEMS.register("boulder",
            () -> new Item(new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}

