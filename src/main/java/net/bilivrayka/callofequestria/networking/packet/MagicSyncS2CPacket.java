package net.bilivrayka.callofequestria.networking.packet;

import net.bilivrayka.callofequestria.providers.ClientMagicData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class MagicSyncS2CPacket {

    private final int magic;

    public MagicSyncS2CPacket(int magic) {
        this.magic = magic;
    }

    public MagicSyncS2CPacket(FriendlyByteBuf buf) {
        this.magic = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(magic);
    }
    public int getMagic() {
        return magic;
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            //ClientMagicData.set(getMagic());
        });
        return true;
    }
}
