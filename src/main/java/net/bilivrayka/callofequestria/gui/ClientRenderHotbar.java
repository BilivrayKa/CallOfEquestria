package net.bilivrayka.callofequestria.gui;

public class ClientRenderHotbar {
    private static boolean isMagicHotbar;

    public static void set(boolean isMagicHotbar) {
        ClientRenderHotbar.isMagicHotbar = !isMagicHotbar;
    }

    public static void toggle() {
        ClientRenderHotbar.isMagicHotbar = !isMagicHotbar;
    }

    public static boolean get() {
        return isMagicHotbar;
    }
}
