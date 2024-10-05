package net.bilivrayka.callofequestria.networking.packet;

import net.bilivrayka.callofequestria.providers.PlayerRaceDataProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class IsMagicHotbarActiveSyncS2CPacket {

    public IsMagicHotbarActiveSyncS2CPacket() {
    }

    public IsMagicHotbarActiveSyncS2CPacket(FriendlyByteBuf buf) {
    }

    public void toBytes(FriendlyByteBuf buf) {
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            Minecraft.getInstance().player.getCapability(PlayerRaceDataProvider.PLAYER_RACE_DATA).ifPresent(races -> {
                races.setMagicHotbar(false);
                CompoundTag nbt = new CompoundTag();
                races.saveNBTData(nbt);
                Minecraft.getInstance().player.getPersistentData().put("player_race_data", nbt);
            });
        });
        return true;
    }
}
