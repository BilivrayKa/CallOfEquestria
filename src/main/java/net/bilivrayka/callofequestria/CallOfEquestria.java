package net.bilivrayka.callofequestria;

import com.mojang.logging.LogUtils;
import net.bilivrayka.callofequestria.block.ModBlocks;
import net.bilivrayka.callofequestria.block.PlushReg;
import net.bilivrayka.callofequestria.entity.ModEntities;
import net.bilivrayka.callofequestria.fluid.ModFluidTypes;
import net.bilivrayka.callofequestria.fluid.ModFluids;
import net.bilivrayka.callofequestria.item.ModCreativeModTabs;
import net.bilivrayka.callofequestria.item.ModItems;
import net.bilivrayka.callofequestria.networking.ModMessages;
import net.bilivrayka.callofequestria.sound.ModSounds;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("callofequestria")
public class CallOfEquestria {
    public static final String MOD_ID = "callofequestria";
    public static final Logger LOGGER = LogUtils.getLogger();


    public CallOfEquestria() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::setup);

        ModCreativeModTabs.register(modEventBus);

        ModItems.register(modEventBus);
        ModSounds.register(modEventBus);
        ModBlocks.register(modEventBus);
        PlushReg.register(modEventBus);
        ModFluids.register(modEventBus);
        ModFluidTypes.register(modEventBus);
        //ModEntities.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {
        // Common setup code
        event.enqueueWork(ModMessages::register);

        /*
        //ItemBlockRenderTypes.setRenderLayer(ModBlocks.PRESSING_TROUGH.get(), RenderType.cutout());
        ModBlocks.PRESSING_TROUGH.get().getStateDefinition().getPossibleStates().forEach(state -> {
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.PRESSING_TROUGH.get(), RenderType.translucent());
        });

         */

        
    }

    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            ItemBlockRenderTypes.setRenderLayer(PlushReg.PLUSH_MAUD_PIE.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(PlushReg.PLUSH_DARING_DO.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(PlushReg.PLUSH_GRANNY_SMITH.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(PlushReg.PLUSH_LOTUS_BLOSSOM.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(PlushReg.PLUSH_ALOE_VERA.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(PlushReg.PLUSH_OCTAVIA_MELODY.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(PlushReg.PLUSH_DJ_PON3.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(PlushReg.PLUSH_SPITFIRE.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.APPLE_BLOCK.get(), RenderType.cutoutMipped());
            ItemBlockRenderTypes.setRenderLayer(ModFluids.SOURCE_APPLE_JUICE.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(ModFluids.FLOWING_APPLE_JUICE.get(), RenderType.translucent());
        }

    }
}
