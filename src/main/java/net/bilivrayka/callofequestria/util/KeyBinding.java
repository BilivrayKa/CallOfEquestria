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
    public static final String KEY_FLY_RIGHT = "key.callofequestria.flytright";
    public static final String KEY_FLY_LEFT = "key.callofequestria.backleft";
    public static final String KEY_USE_SPELL = "key.callofequestria.usespell";

    public static final KeyMapping MAGIC_KEY = new KeyMapping(KEY_MAGIC, KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_V, KEY_CATEGORY_EQUESTRIA);
    public static final KeyMapping FLY_TOWARDS_KEY = new KeyMapping(KEY_FLY_TOWARDS, KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_W, KEY_CATEGORY_EQUESTRIA);
    public static final KeyMapping FLY_BACKWARDS_KEY = new KeyMapping(KEY_FLY_BACKWARDS, KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_S, KEY_CATEGORY_EQUESTRIA);
    public static final KeyMapping FLY_RIGHT_KEY = new KeyMapping(KEY_FLY_RIGHT, KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_D, KEY_CATEGORY_EQUESTRIA);
    public static final KeyMapping FLY_LEFT_KEY = new KeyMapping(KEY_FLY_LEFT, KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_A, KEY_CATEGORY_EQUESTRIA);
    public static final KeyMapping USE_SPELL_KEY = new KeyMapping(KEY_USE_SPELL, KeyConflictContext.IN_GAME,
            InputConstants.Type.MOUSE, GLFW.GLFW_MOUSE_BUTTON_RIGHT, KEY_CATEGORY_EQUESTRIA);


}
