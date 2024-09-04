package net.bilivrayka.callofequestria.event;

import net.bilivrayka.callofequestria.CallOfEquestria;
import net.bilivrayka.callofequestria.networking.ModMessages;
import net.bilivrayka.callofequestria.networking.packet.MagicC2SPacket;
import net.bilivrayka.callofequestria.util.KeyBinding;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class ClientEvents {
    @Mod.EventBusSubscriber(modid = CallOfEquestria.MOD_ID, value = Dist.CLIENT)
    public static class ClientForgeEvents {
        @SubscribeEvent
        public static void onKeyInput(InputEvent.Key event) {
            if(KeyBinding.MAGIC_KEY.consumeClick()) {
                ModMessages.sendToServer(new MagicC2SPacket());
            }
        }
    }

    @Mod.EventBusSubscriber(modid = CallOfEquestria.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModBusEvents {
        @SubscribeEvent
        public static void onKeyRegister(RegisterKeyMappingsEvent event) {
            event.register(KeyBinding.MAGIC_KEY);
            event.register(KeyBinding.FLY_TOWARDS_KEY);
            event.register(KeyBinding.FLY_BACKWARDS_KEY);
        }
        /*
        @SubscribeEvent
        public static void registerGuiOverlays(RegisterGuiOverlaysEvent event) {
            event.registerAboveAll("thirst", ThirstHudOverlay.HUD_THIRST);
        }

        @SubscribeEvent
        public static void registerRenderers(final EntityRenderersEvent.RegisterRenderers event) {
            event.registerBlockEntityRenderer(ModBlockEntities.GEM_INFUSING_STATION.get(),
                    GemInfusingStationBlockEntityRenderer::new);
        }
        */
    }
}
