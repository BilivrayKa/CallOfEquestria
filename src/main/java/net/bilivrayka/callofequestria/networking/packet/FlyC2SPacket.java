package net.bilivrayka.callofequestria.networking.packet;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class FlyC2SPacket {
    private final boolean isFly;

    public FlyC2SPacket(boolean isFly) {
        this.isFly = isFly;
    }

    public FlyC2SPacket(FriendlyByteBuf buf) {
        this.isFly = buf.readBoolean();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeBoolean(isFly);
    }
    public boolean getIsFly() {
        return isFly;
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            Player player = context.getSender();
            if(getIsFly()){
                player.getAbilities().mayfly = true;
            } else {
                player.getAbilities().mayfly = false;
                player.getAbilities().flying = false;
            }
            player.onUpdateAbilities();

        });
        return true;
    }
}
