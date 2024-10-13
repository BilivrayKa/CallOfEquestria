package net.bilivrayka.callofequestria.networking.packet;

import com.mojang.logging.LogUtils;
import net.bilivrayka.callofequestria.data.PlayerRaceDataProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
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
                if(data.getSelectedRace() == 3){
                    Minecraft.getInstance().player.sendSystemMessage(
                            Component.literal("Note: ")
                                    .setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0xFFFF00)))
                                    .append(Component.literal("You have Magic Mode! Press ")
                                            .setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0xFFFFFF))))
                                    .append(Component.literal("V")
                                            .setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0xFFFF00))))
                                    .append(Component.literal("(By Default) ")
                                            .setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0xCCCCCC))))
                                    .append(Component.literal("To Toggle It ")
                                            .setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0xFFFFFF))))
                    );
                }

            });
        });
        return true;
    }
}
