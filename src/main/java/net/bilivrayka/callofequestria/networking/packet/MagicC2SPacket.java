package net.bilivrayka.callofequestria.networking.packet;

import net.bilivrayka.callofequestria.magic.PlayerRaceDataProvider;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

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
            player.getCapability(PlayerRaceDataProvider.PLAYER_RACE_DATA).ifPresent(data -> {
                int race = data.getSelectedRace();
                if(player.getAbilities().flying && race == 2){
                    player.getAbilities().mayfly = false;
                    player.getAbilities().flying = false;
                    player.sendSystemMessage(Component.literal("Fly is off"));
                } else if (race == 2){
                    player.getAbilities().mayfly = true;
                    player.getAbilities().flying = true;
                    player.sendSystemMessage(Component.literal("Fly is on"));
                }
                player.onUpdateAbilities();
            });
        });
        return true;
    }
}
