package net.bilivrayka.callofequestria.networking.packet;

import net.minecraft.advancements.Advancement;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.ServerAdvancementManager;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class AdvancementC2SPacket {
    private final ResourceLocation advancementName;

    public AdvancementC2SPacket(ResourceLocation cardIndex) {
        this.advancementName = cardIndex;
    }

    public static void encode(AdvancementC2SPacket packet, FriendlyByteBuf buf) {
        buf.writeResourceLocation (packet.advancementName);
    }

    public static AdvancementC2SPacket decode(FriendlyByteBuf buf) {
        return new AdvancementC2SPacket(buf.readResourceLocation());
    }

    public static void handle(AdvancementC2SPacket packet, Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            MinecraftServer server = context.get().getSender().getServer();
            ServerAdvancementManager advancementManager = server.getAdvancements();
            var playerAdvancements = context.get().getSender().getAdvancements();
            Advancement advancement = advancementManager.getAdvancement(packet.advancementName);
            playerAdvancements.award(advancement, "requirement");
        });
        context.get().setPacketHandled(true);
    }
}
