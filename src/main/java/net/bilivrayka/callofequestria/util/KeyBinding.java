package net.bilivrayka.callofequestria.util;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.KeyConflictContext;
import org.lwjgl.glfw.GLFW;

public class KeyBinding {
    public static final String KEY_CATEGORY_EQUESTRIA = "key.category.callofequestria.equestria";
    public static final String KEY_MAGIC = "key.callofequestria.magic";
    public static final String KEY_FLY_TOWARDS = "key.callofequestria.flytowards";
    public static final String KEY_FLY_BACKWARDS = "key.callofequestria.backtowards";

    public static final KeyMapping MAGIC_KEY = new KeyMapping(KEY_MAGIC, KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_C, KEY_CATEGORY_EQUESTRIA);
    public static final KeyMapping FLY_TOWARDS_KEY = new KeyMapping(KEY_FLY_TOWARDS, KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_W, KEY_CATEGORY_EQUESTRIA);
    public static final KeyMapping FLY_BACKWARDS_KEY = new KeyMapping(KEY_FLY_BACKWARDS, KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_S, KEY_CATEGORY_EQUESTRIA);


}
