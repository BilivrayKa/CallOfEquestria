package net.bilivrayka.callofequestria.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ClientRacePacket {
    private static int race;

    public static void set(int race) {
        ClientRacePacket.race = race;
    }

    public static int getRace() {
        return race;
    }
}
