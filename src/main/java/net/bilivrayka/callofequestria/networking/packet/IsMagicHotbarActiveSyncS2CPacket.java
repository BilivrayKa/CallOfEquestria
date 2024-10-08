package net.bilivrayka.callofequestria.networking.packet;

import net.bilivrayka.callofequestria.data.PlayerRaceDataProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class IsMagicHotbarActiveSyncS2CPacket {
    private final boolean isActive;

    public IsMagicHotbarActiveSyncS2CPacket(boolean isActive) {
        this.isActive = isActive;
    }

    public IsMagicHotbarActiveSyncS2CPacket(FriendlyByteBuf buf) {
        this.isActive = buf.readBoolean();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeBoolean(isActive);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            Minecraft.getInstance().player.getCapability(PlayerRaceDataProvider.PLAYER_RACE_DATA).ifPresent(races -> {
                races.setMagicHotbar(isActive);
                CompoundTag nbt = new CompoundTag();
                races.saveNBTData(nbt);
                Minecraft.getInstance().player.getPersistentData().put("player_race_data", nbt);
            });
        });
        return true;
    }
}
