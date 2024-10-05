package net.bilivrayka.callofequestria.networking.packet;

import net.bilivrayka.callofequestria.networking.ModMessages;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class MagicS2CPacket {
    private final int race;

    public MagicS2CPacket(int race) {
        this.race = race;
    }

    public MagicS2CPacket(FriendlyByteBuf buf) {
        this.race = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(race);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ModMessages.sendToServer(new RaceC2SPacket(race));
        });
        return true;
    }
}
