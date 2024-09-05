package net.bilivrayka.callofequestria.networking;

import net.bilivrayka.callofequestria.CallOfEquestria;
import net.bilivrayka.callofequestria.networking.packet.FlyC2SPacket;
import net.bilivrayka.callofequestria.networking.packet.MagicC2SPacket;
import net.bilivrayka.callofequestria.networking.packet.RaceC2SPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class ModMessages {
    private static SimpleChannel INSTANCE;

    private static int packetId = 0;
    private static int id() {
        return packetId++;
    }

    public static void register() {
        SimpleChannel net = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation(CallOfEquestria.MOD_ID, "messages"))
                .networkProtocolVersion(() -> "1.0")
                .clientAcceptedVersions(s -> true)
                .serverAcceptedVersions(s -> true)
                .simpleChannel();

        INSTANCE = net;

        net.messageBuilder(MagicC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(MagicC2SPacket::new)
                .encoder(MagicC2SPacket::toBytes)
                .consumerMainThread(MagicC2SPacket::handle)
                .add();

        net.messageBuilder(FlyC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(FlyC2SPacket::new)
                .encoder(FlyC2SPacket::toBytes)
                .consumerMainThread(FlyC2SPacket::handle)
                .add();

        net.messageBuilder(RaceC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(RaceC2SPacket::decode)
                .encoder(RaceC2SPacket::encode)
                .consumerMainThread(RaceC2SPacket::handle)
                .add();

    }

    public static <MSG> void sendToServer(MSG message) {
        INSTANCE.sendToServer(message);
    }

    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
    }
}