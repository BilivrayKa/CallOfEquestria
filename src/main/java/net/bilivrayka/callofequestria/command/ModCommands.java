package net.bilivrayka.callofequestria.command;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "callofequestria")
public class ModCommands {
    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event) {
        //RaceChanger.register(event.getDispatcher());
    }

    public static void registerEvents() {
        MinecraftForge.EVENT_BUS.register(ModCommands.class);
    }
}