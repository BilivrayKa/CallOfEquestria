package net.bilivrayka.callofequestria.providers;

public class ClientMagicData {
    private static int playerMagic;

    public static void set(int playerMagic) {
        ClientMagicData.playerMagic = playerMagic;
    }

    public static int get() {
        return playerMagic;
    }
}
