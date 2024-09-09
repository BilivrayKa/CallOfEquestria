package net.bilivrayka.callofequestria.networking.packet;

import net.bilivrayka.callofequestria.providers.ClientRacePacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class RaceSyncS2CPacket {

    private final int race;

    public RaceSyncS2CPacket(int race) {
        this.race = race;
    }

    public RaceSyncS2CPacket(FriendlyByteBuf buf) {
        this.race = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(race);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ClientRacePacket.set(race);
        });
        return true;
    }
}
