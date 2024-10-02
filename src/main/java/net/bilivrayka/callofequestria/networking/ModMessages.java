package net.bilivrayka.callofequestria.networking;

import net.bilivrayka.callofequestria.CallOfEquestria;
import net.bilivrayka.callofequestria.networking.packet.*;
import net.bilivrayka.callofequestria.networking.packet.spell.BlinkSpellC2SPacket;
import net.bilivrayka.callofequestria.networking.packet.spell.BlockGrabC2SPacket;
import net.bilivrayka.callofequestria.networking.packet.spell.MagicProjectileC2SPacket;
import net.bilivrayka.callofequestria.networking.packet.spell.RepelSpellC2SPacket;
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
/*
        net.messageBuilder(MagicC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(MagicC2SPacket::new)
                .encoder(MagicC2SPacket::toBytes)
                .consumerMainThread(MagicC2SPacket::handle)
                .add();

 */

        net.messageBuilder(FlyC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(FlyC2SPacket::new)
                .encoder(FlyC2SPacket::toBytes)
                .consumerMainThread(FlyC2SPacket::handle)
                .add();
        net.messageBuilder(FlyStateC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(FlyStateC2SPacket::new)
                .encoder(FlyStateC2SPacket::toBytes)
                .consumerMainThread(FlyStateC2SPacket::handle)
                .add();

        net.messageBuilder(RaceC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(RaceC2SPacket::decode)
                .encoder(RaceC2SPacket::encode)
                .consumerMainThread(RaceC2SPacket::handle)
                .add();
        net.messageBuilder(AdvancementC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(AdvancementC2SPacket::decode)
                .encoder(AdvancementC2SPacket::encode)
                .consumerMainThread(AdvancementC2SPacket::handle)
                .add();
        net.messageBuilder(RepelSpellC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(RepelSpellC2SPacket::decode)
                .encoder(RepelSpellC2SPacket::encode)
                .consumerMainThread(RepelSpellC2SPacket::handle)
                .add();
        net.messageBuilder(BlinkSpellC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(BlinkSpellC2SPacket::decode)
                .encoder(BlinkSpellC2SPacket::encode)
                .consumerMainThread(BlinkSpellC2SPacket::handle)
                .add();
        net.messageBuilder(MagicProjectileC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(MagicProjectileC2SPacket::new)
                .encoder(MagicProjectileC2SPacket::toBytes)
                .consumerMainThread(MagicProjectileC2SPacket::handle)
                .add();
        net.messageBuilder(BlockGrabC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(BlockGrabC2SPacket::decode)
                .encoder(BlockGrabC2SPacket::encode)
                .consumerMainThread(BlockGrabC2SPacket::handle)
                .add();
        /*
        net.messageBuilder(GUIRaceS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(GUIRaceS2CPacket::decode)
                .encoder(GUIRaceS2CPacket::encode)
                .consumerMainThread(GUIRaceS2CPacket::handle)
                .add();

         */
        net.messageBuilder(MagicSyncS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(MagicSyncS2CPacket::new)
                .encoder(MagicSyncS2CPacket::toBytes)
                .consumerMainThread(MagicSyncS2CPacket::handle)
                .add();
        net.messageBuilder(RaceSyncS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(RaceSyncS2CPacket::new)
                .encoder(RaceSyncS2CPacket::toBytes)
                .consumerMainThread(RaceSyncS2CPacket::handle)
                .add();

    }
    public static <MSG> void sendToServer(MSG message) {
        INSTANCE.sendToServer(message);
    }

    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
    }
}