package net.bilivrayka.callofequestria.item;

import net.bilivrayka.callofequestria.CallOfEquestria;
import net.bilivrayka.callofequestria.block.ModBlocks;
import net.bilivrayka.callofequestria.block.PlushReg;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
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
                        pOutput.accept(ModItems.FLOWER_DUST.get());
                        pOutput.accept(ModItems.FLOWER_DUST_BLACK.get());
                        pOutput.accept(ModItems.FLOWER_DUST_BLUE.get());
                        pOutput.accept(ModItems.FLOWER_DUST_BROWN.get());
                        pOutput.accept(ModItems.FLOWER_DUST_CYAN.get());
                        pOutput.accept(ModItems.FLOWER_DUST_GRAY.get());
                        pOutput.accept(ModItems.FLOWER_DUST_GREEN.get());
                        pOutput.accept(ModItems.FLOWER_DUST_LIGHTBLUE.get());
                        pOutput.accept(ModItems.FLOWER_DUST_LIGHTGRAY.get());
                        pOutput.accept(ModItems.FLOWER_DUST_LIME.get());
                        pOutput.accept(ModItems.FLOWER_DUST_MAGENTA.get());
                        pOutput.accept(ModItems.FLOWER_DUST_ORANGE.get());
                        pOutput.accept(ModItems.FLOWER_DUST_PINK.get());
                        pOutput.accept(ModItems.FLOWER_DUST_PURPLE.get());
                        pOutput.accept(ModItems.FLOWER_DUST_RED.get());
                        pOutput.accept(ModItems.FLOWER_DUST_WHITE.get());
                        pOutput.accept(ModItems.FLOWER_DUST_YELLOW.get());
                        pOutput.accept(ModItems.ZINNIA_BUSH_FLOWER.get());
                        pOutput.accept(ModItems.CRYSTAL_CLUSTER.get());
                        pOutput.accept(ModItems.BUDDING_CRYSTAL.get());
                        pOutput.accept(ModItems.MUG.get());
                        pOutput.accept(ModItems.MOD_BOTTLE.get());
                        pOutput.accept(ModItems.MUG_APPLE.get());
                        pOutput.accept(ModItems.MUFIIN.get());
                        pOutput.accept(ModItems.APPLE_VODKA.get());
                        pOutput.accept(ModItems.HAYBURGER.get());
                        pOutput.accept(ModItems.NOTCH_APPLE_JAM.get());
                        pOutput.accept(ModBlocks.SMALL_CRYSTAL_BUD_BLOCK.get());
                        pOutput.accept(ModBlocks.MEDIUM_CRYSTAL_BUD_BLOCK.get());
                        pOutput.accept(ModBlocks.LARGE_CRYSTAL_BUD_BLOCK.get());
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
                    })
                    .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
