package net.bilivrayka.callofequestria.providers;

public class ClientRacePacket {
    private static int race;

    public static void set(int race) {
        ClientRacePacket.race = race;
    }

    public static int getRace() {
        return race;
    }
}
