package net.bilivrayka.callofequestria.networking.packet;

import net.bilivrayka.callofequestria.magic.PlayerRaceDataProvider;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class RaceC2SPacket {

    private final int cardIndex;

    public RaceC2SPacket(int cardIndex) {
        this.cardIndex = cardIndex;
    }

    public static void encode(RaceC2SPacket packet, FriendlyByteBuf buf) {
        buf.writeInt(packet.cardIndex);
    }

    public static RaceC2SPacket decode(FriendlyByteBuf buf) {
        return new RaceC2SPacket(buf.readInt());
    }

    public static boolean handle(RaceC2SPacket packet, Supplier<NetworkEvent.Context> context) {
        NetworkEvent.Context ctx = context.get();
        ctx.enqueueWork(() -> {
            Player player = ctx.getSender();
            if (player != null) {
                player.getCapability(PlayerRaceDataProvider.PLAYER_RACE_DATA).ifPresent(data -> {
                    data.setSelectedRace(packet.cardIndex);

                    AttributeInstance maxHealthAttribute = player.getAttribute(Attributes.MAX_HEALTH);
                    switch (data.getSelectedRace()){
                        case 1:
                            maxHealthAttribute.setBaseValue((float) 20);
                            break;
                        case 2:
                            maxHealthAttribute.setBaseValue((float) 16);
                            break;
                        case 3:
                            maxHealthAttribute.setBaseValue((float) 18);
                            break;
                        default:
                            break;
                    }
                    if (player.getHealth() > player.getMaxHealth()) {
                        player.setHealth((float) player.getMaxHealth());
                    }
                });
            }
        });
        ctx.setPacketHandled(true);
        return true;
    }
}
