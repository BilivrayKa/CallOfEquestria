package net.bilivrayka.callofequestria.item;

import net.bilivrayka.callofequestria.CallOfEquestria;
import net.bilivrayka.callofequestria.block.ModBlocks;
import net.bilivrayka.callofequestria.block.PlushReg;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeModTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, CallOfEquestria.MOD_ID);

    public static final RegistryObject<CreativeModeTab> STUFF_TAB = CREATIVE_MODE_TABS.register("stuff_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.ZINNIA_BUSH_FLOWER.get()))
                    .title(Component.translatable("creativetab.stuff_tab"))
                    .displayItems((pParameters, pOutput) -> {
                        pOutput.accept(ModItems.WOOD_PLANK.get());
                        pOutput.accept(ModItems.IRON_NAILS.get());
                        pOutput.accept(ModItems.WOOD_NAILS.get());
                        pOutput.accept(ModItems.LONG_STICK.get());
                        pOutput.accept(ModItems.BARK.get());
                        pOutput.accept(ModItems.ZINNIA_BUSH_FLOWER.get());
                        pOutput.accept(ModItems.CRYSTAL_CLUSTER.get());
                        pOutput.accept(ModItems.BUDDING_CRYSTAL.get());
                        pOutput.accept(ModItems.MUG.get());
                        pOutput.accept(ModItems.JAR.get());
                        pOutput.accept(ModItems.MOD_BOTTLE.get());
                        pOutput.accept(ModItems.MUG_APPLE.get());
                        pOutput.accept(ModItems.APPLE_JUICE_BUCKET.get());
                        pOutput.accept(ModItems.MUFIIN.get());
                        pOutput.accept(ModItems.APPLE_VODKA.get());
                        pOutput.accept(ModItems.HAYBURGER.get());
                        pOutput.accept(ModItems.NOTCH_APPLE_JAM.get());
                        pOutput.accept(ModBlocks.SMALL_CRYSTAL_BUD_BLOCK.get());
                        pOutput.accept(ModBlocks.MEDIUM_CRYSTAL_BUD_BLOCK.get());
                        pOutput.accept(ModBlocks.LARGE_CRYSTAL_BUD_BLOCK.get());
                        //pOutput.accept(ModBlocks.PRESSING_TROUGH.get());
                        pOutput.accept(ModBlocks.APPLE_BLOCK.get());
                        pOutput.accept(ModItems.BOULDER.get());
                    })
                    .build());

    public static final RegistryObject<CreativeModeTab> PLUSHIES_TAB = CREATIVE_MODE_TABS.register("plushies_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(PlushReg.PLUSH_PINKIE_PIE.get()))
                    .title(Component.translatable("creativetab.plushies_tab"))
                    .displayItems((pParameters, pOutput) -> {
                        pOutput.accept(ModItems.PLUSH_PINKIE_PIE.get());
                        pOutput.accept(ModItems.PLUSH_ROSE.get());
                        pOutput.accept(ModItems.PLUSH_FLUTTERSHY.get());
                        pOutput.accept(ModItems.PLUSH_APPLEJACK.get());
                        pOutput.accept(ModItems.PLUSH_RAINBOW_DASH.get());
                        pOutput.accept(ModItems.PLUSH_RARITY.get());
                        pOutput.accept(ModItems.PLUSH_SPARKLE.get());
                        pOutput.accept(ModItems.PLUSH_DERPY.get());
                        pOutput.accept(ModItems.PLUSH_STARLIGHT.get());
                        pOutput.accept(ModItems.PLUSH_TRIXIE.get());
                        pOutput.accept(ModItems.PLUSH_SWIRL.get());
                        pOutput.accept(ModItems.PLUSH_MINUETTE.get());
                        pOutput.accept(ModItems.PLUSH_MAUD_PIE.get());
                        pOutput.accept(ModItems.PLUSH_MARBLE_PIE.get());
                        pOutput.accept(ModItems.PLUSH_DARING_DO.get());
                        pOutput.accept(ModItems.PLUSH_GRANNY_SMITH.get());
                        pOutput.accept(ModItems.PLUSH_LIMESTONE_PIE.get());
                        pOutput.accept(ModItems.PLUSH_NURSE_REDHEART.get());
                        pOutput.accept(ModItems.PLUSH_LILY_VALLEY.get());
                        pOutput.accept(ModItems.PLUSH_DAISY.get());
                        pOutput.accept(ModItems.PLUSH_LOTUS_BLOSSOM.get());
                        pOutput.accept(ModItems.PLUSH_ALOE_VERA.get());
                        pOutput.accept(ModItems.PLUSH_OCTAVIA_MELODY.get());
                        pOutput.accept(ModItems.PLUSH_DJ_PON3.get());
                        pOutput.accept(ModItems.PLUSH_CHEERILEE.get());
                        pOutput.accept(ModItems.PLUSH_SPITFIRE.get());
                        pOutput.accept(ModItems.PLUSH_CHERRY_BERRY.get());
                        pOutput.accept(ModItems.PLUSH_SUGAR_BELLE.get());
                        pOutput.accept(ModItems.PLUSH_TWILIGHT_VELVET.get());
                        pOutput.accept(ModItems.PLUSH_LIGHTNING_DUST.get());
                    })
                    .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
