package net.bilivrayka.callofequestria.networking.packet;

import net.bilivrayka.callofequestria.magic.PlayerMagicProvider;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

public class MagicC2SPacket {
    public MagicC2SPacket() {

    }

    public MagicC2SPacket(FriendlyByteBuf buf) {

    }

    public void toBytes(FriendlyByteBuf buf) {

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();

            ServerLevel level = (ServerLevel) player.level();

            if(player.getAbilities().flying){
                player.getAbilities().mayfly = false;
                player.getAbilities().flying = false;
                player.sendSystemMessage(Component.literal("Fly is off"));
                //player.setPose(Pose.STANDING);
            } else {
                player.getAbilities().mayfly = true;
                player.getAbilities().flying = true;
                player.sendSystemMessage(Component.literal("Fly is on"));
                //player.setPose(Pose.SWIMMING);
            }

            player.onUpdateAbilities();

        });
        return true;
    }
}
