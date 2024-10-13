package net.bilivrayka.callofequestria.networking.packet;

import net.bilivrayka.callofequestria.data.PlayerRaceDataProvider;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class IsMagicHotbarActiveSyncC2SPacket {

    private final boolean isMagicHotbarActive;
    public IsMagicHotbarActiveSyncC2SPacket(boolean isMagicHotbarActive) {
        this.isMagicHotbarActive = isMagicHotbarActive;
    }

    public static void encode(IsMagicHotbarActiveSyncC2SPacket packet, FriendlyByteBuf buf) {
        buf.writeBoolean(packet.isMagicHotbarActive);
    }

    public static IsMagicHotbarActiveSyncC2SPacket decode(FriendlyByteBuf buf) {
        return new IsMagicHotbarActiveSyncC2SPacket(buf.readBoolean());
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            context.getSender().getCapability(PlayerRaceDataProvider.PLAYER_RACE_DATA).ifPresent(races -> {
                races.setMagicHotbar(isMagicHotbarActive);
            });
        });
        return true;
    }
}
