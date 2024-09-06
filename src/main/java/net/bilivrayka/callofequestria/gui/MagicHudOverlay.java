package net.bilivrayka.callofequestria.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.bilivrayka.callofequestria.CallOfEquestria;
import net.bilivrayka.callofequestria.magic.PlayerMagic;
import net.bilivrayka.callofequestria.magic.PlayerRaceData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class MagicHudOverlay {

    private static final ResourceLocation FILLED_MAGIC = new ResourceLocation(CallOfEquestria.MOD_ID,
            "textures/hud/filled_magic.png");
    /*
    private static final ResourceLocation EMPTY_MAGIC = new ResourceLocation(CallOfEquestria.MOD_ID,
            "textures/hud/empty_magic.png");

     */
    public static final IGuiOverlay HUD_GUI = ((gui, guiGraphics, partialTick, screenWidth, screenHeight) -> {
        int x = screenWidth / 2;
        int y = screenHeight;

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        //RenderSystem.setShaderTexture(0, EMPTY_MAGIC);
/*
        for(int i = 0; i < 10; i++) {
            guiGraphics.blit(EMPTY_MAGIC, x + 8 + (i * 8), y -51, 0, 0, 13, 12,
                    13, 12);
        }
        RenderSystem.setShaderTexture(0, EMPTY_MAGIC);

 */
        for(int i = 0; i < 10; i++) {
            if(ClientMagicData.get() > i) {
                guiGraphics.blit(FILLED_MAGIC, x + 9 + (i * 8), y -48, 0, 0, 9, 9,
                        9, 9);
            } else {
                break;
            }
        }
    });
}

