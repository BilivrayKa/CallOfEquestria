package net.bilivrayka.callofequestria.util;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.KeyConflictContext;
import org.lwjgl.glfw.GLFW;

public class KeyBinding {
    public static final String KEY_CATEGORY_EQUESTRIA = "key.category.callofequestria.equestria";
    public static final String KEY_MAGIC = "key.callofequestria.magic";

    public static final KeyMapping MAGIC_KEY = new KeyMapping(KEY_MAGIC, KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_V, KEY_CATEGORY_EQUESTRIA);
}
