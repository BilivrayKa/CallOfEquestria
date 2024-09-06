package net.bilivrayka.callofequestria.networking.packet;

import net.bilivrayka.callofequestria.gui.RaceChooseScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class GUIRaceS2CPacket {

    public GUIRaceS2CPacket() {
    }

    public static void encode(GUIRaceS2CPacket packet, FriendlyByteBuf buf) {
    }

    public static GUIRaceS2CPacket decode(FriendlyByteBuf buf) {
        return new GUIRaceS2CPacket();
    }

    public static void handle(GUIRaceS2CPacket packet, Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            if (context.get().getDirection().getReceptionSide().isClient()) {
                Minecraft.getInstance().setScreen(new RaceChooseScreen());
            }
        });
        context.get().setPacketHandled(true);
    }
}
