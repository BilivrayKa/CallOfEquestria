package net.bilivrayka.callofequestria.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.bilivrayka.callofequestria.CallOfEquestria;
import net.bilivrayka.callofequestria.data.PlayerMagicDataProvider;
import net.bilivrayka.callofequestria.data.PlayerRaceDataProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class MagicHudOverlay {

    private static final ResourceLocation EMPTY = new ResourceLocation(CallOfEquestria.MOD_ID,
            "textures/hud/empty.png");
    private static final ResourceLocation FILLED_FEATHER = new ResourceLocation(CallOfEquestria.MOD_ID,
            "textures/hud/filled_feather.png");
    private static final ResourceLocation FILLED_MAGIC = new ResourceLocation(CallOfEquestria.MOD_ID,
            "textures/hud/filled_magic.png");

    private static ResourceLocation REVELANT_HUD = null;

    public static final IGuiOverlay HUD_GUI = ((gui, guiGraphics, partialTick, screenWidth, screenHeight) -> {
        Player player = Minecraft.getInstance().player;
        int x = screenWidth / 2;
        int y = screenHeight;
        player.getCapability(PlayerRaceDataProvider.PLAYER_RACE_DATA).ifPresent(races -> {
            switch (races.getSelectedRace()){
                case 1:
                    REVELANT_HUD = EMPTY;
                    return;
                case 2:
                    REVELANT_HUD = FILLED_FEATHER;
                    break;
                case 3:
                    REVELANT_HUD = FILLED_MAGIC;
                    break;
                default:
                    REVELANT_HUD = EMPTY;
                    break;
            }
        });
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.enableDepthTest();
        RenderSystem.setShaderTexture(0, REVELANT_HUD);
        RenderSystem.enableBlend();
        RenderSystem.enableCull();


        if (player.getVehicle() instanceof LivingEntity livingEntity) {
            float health = livingEntity.getHealth();
            y -= 10 * (int) Math.floor(health / 20);
        }
        if (player.isInWater()) {
            y -= 10;
        }

        int finalY = y;
        player.getCapability(PlayerMagicDataProvider.PLAYER_MAGIC).ifPresent(magic -> {
            for(int i = 0; i < 10; i++) {
                if((int) Math.floor(magic.getMagic()) > i && !player.isCreative() && !player.isSpectator()) {
                    guiGraphics.blit(REVELANT_HUD, x + 9 + (i * 8), finalY -48, 0, 0, 9, 9,
                            9, 9);
                } else {
                    break;
                }
            }
        });
    });
}

