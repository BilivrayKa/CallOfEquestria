package net.bilivrayka.callofequestria.networking.packet;

import net.bilivrayka.callofequestria.data.PlayerMagicDataProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class MagicSpellUsedS2CPacket {

    private final int magic;
    private final int cooldown;
    private final int slot;

    public MagicSpellUsedS2CPacket(int magic, int cooldown, int slot) {
        this.magic = magic;
        this.cooldown = cooldown;
        this.slot = slot;
    }

    public MagicSpellUsedS2CPacket(FriendlyByteBuf buf) {
        this.magic = buf.readInt();
        this.cooldown = buf.readInt();
        this.slot = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(magic);
        buf.writeInt(cooldown);
        buf.writeInt(slot);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            LocalPlayer player = Minecraft.getInstance().player;
            player.getCapability(PlayerMagicDataProvider.PLAYER_MAGIC).ifPresent(data -> {
                data.subMagic(magic);
                data.setCooldowns(slot,cooldown);
            });
        });
        return true;
    }
}
