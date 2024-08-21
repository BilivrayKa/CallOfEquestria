package net.bilivrayka.callofequestria.networking.packet;

import net.bilivrayka.callofequestria.CallOfEquestria;
import net.bilivrayka.callofequestria.magic.PlayerFlyState;
import net.bilivrayka.callofequestria.magic.PlayerFlyStateProvider;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class FlyC2SPacket {

    public FlyC2SPacket() {
    }

    public FlyC2SPacket(FriendlyByteBuf buf) {

    }

    public void toBytes(FriendlyByteBuf buf) {

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            Player player = context.getSender();

        });
        return true;
    }
}
