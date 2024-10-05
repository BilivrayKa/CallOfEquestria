package net.bilivrayka.callofequestria.networking.packet;

import com.mojang.logging.LogUtils;
import net.bilivrayka.callofequestria.CallOfEquestria;
import net.bilivrayka.callofequestria.providers.ClientRacePacket;
import net.bilivrayka.callofequestria.providers.PlayerMagicProvider;
import net.bilivrayka.callofequestria.providers.PlayerRaceDataProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraftforge.network.NetworkEvent;
import org.slf4j.Logger;

import java.util.function.Supplier;

public class RaceSyncS2CPacket {

    public static final Logger LOGGER = LogUtils.getLogger();
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
            Minecraft.getInstance().player.getCapability(PlayerRaceDataProvider.PLAYER_RACE_DATA).ifPresent(data -> {
                data.setSelectedRace(race);
                CompoundTag nbt = new CompoundTag();
                data.saveNBTData(nbt);
                Minecraft.getInstance().player.getPersistentData().put("player_race_data", nbt);
                LOGGER.debug("Local Race: " + data.getSelectedRace());
            });
            /*
            context.getSender().getCapability(PlayerRaceDataProvider.PLAYER_RACE_DATA).ifPresent(data ->{
                data.setSelectedRace(race);
                CompoundTag nbt = new CompoundTag();
                data.saveNBTData(nbt);
                Minecraft.getInstance().player.getPersistentData().put(CallOfEquestria.MOD_ID, nbt);
            });

             */
            //ClientRacePacket.set(race);
        });
        return true;
    }
}
