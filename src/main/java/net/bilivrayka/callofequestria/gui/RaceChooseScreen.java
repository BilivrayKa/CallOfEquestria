package net.bilivrayka.callofequestria.gui;

import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.systems.RenderSystem;
import net.bilivrayka.callofequestria.CallOfEquestria;
import net.bilivrayka.callofequestria.magic.PlayerRaceDataProvider;
import net.bilivrayka.callofequestria.networking.ModMessages;
import net.bilivrayka.callofequestria.networking.packet.RaceC2SPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.server.ServerLifecycleHooks;

import java.util.UUID;

public class RaceChooseScreen extends Screen {

    private static final ResourceLocation CARD_TEXTURE_1 = new ResourceLocation(CallOfEquestria.MOD_ID, "textures/gui/races/earthpony.png");
    private static final ResourceLocation CARD_TEXTURE_2 = new ResourceLocation(CallOfEquestria.MOD_ID, "textures/gui/races/pegasus.png");
    private static final ResourceLocation CARD_TEXTURE_3 = new ResourceLocation(CallOfEquestria.MOD_ID, "textures/gui/races/unicorn.png");

    private int selectedCard = -1;

    public RaceChooseScreen() {
        super(Component.literal("Race Choose"));
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(guiGraphics);
        int cardWidth = 100;
        int cardHeight = 150;
        int spacing = 20;
        int centerX = (this.width - 3 * cardWidth - 2 * spacing) / 2;
        int centerY = (this.height - cardHeight) / 2;
        //TODO анимки карточек isHovered ? ANIMATION_FRAMES[currentFrame] : getCardTexture(i) к примеру крч хуй знает сам блядь думай
        for (int i = 0; i < 3; i++) {
            int x = centerX + i * (cardWidth + spacing);
            int y = centerY;
            boolean isHovered = isMouseOverCard(mouseX, mouseY, x, y, cardWidth, cardHeight);
            int hoverOffset = isHovered ? -10 : 0;
            guiGraphics.blit(getCardTexture(i), x, y + hoverOffset, 0, 0, cardWidth, cardHeight, cardWidth, cardHeight);
            guiGraphics.drawCenteredString(this.font, getCardLabel(i), x + cardWidth / 2, y + cardHeight + 10 + hoverOffset, 0xFFFFFF);
        }
        super.render(guiGraphics, mouseX, mouseY, partialTicks);
    }

    private boolean isMouseOverCard(double mouseX, double mouseY, int cardX, int cardY, int cardWidth, int cardHeight) {
        return mouseX >= cardX && mouseX <= cardX + cardWidth && mouseY >= cardY && mouseY <= cardY + cardHeight;
    }

    private ResourceLocation getCardTexture(int cardIndex) {
        switch (cardIndex) {
            case 0: return CARD_TEXTURE_1;
            case 1: return CARD_TEXTURE_2;
            case 2: return CARD_TEXTURE_3;
            default: return CARD_TEXTURE_1;
        }
    }

    private String getCardLabel(int cardIndex) {
        switch (cardIndex) {
            case 0: return "EarthPony";
            case 1: return "Pegasus";
            case 2: return "Unicorn";
            default: return "";
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        int cardWidth = 100;
        int cardHeight = 150;
        int spacing = 20;
        int centerX = (this.width - 3 * cardWidth - 2 * spacing) / 2;
        int centerY = (this.height - cardHeight) / 2;
        for (int i = 0; i < 3; i++) {
            int x = centerX + i * (cardWidth + spacing);
            int y = centerY;
            if (isMouseOverCard(mouseX, mouseY, x, y, cardWidth, cardHeight)) {
                this.selectedCard = i + 1;
                this.onCardSelected(i + 1);
                return true;
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    private void onCardSelected(int cardIndex) {
        ModMessages.sendToServer(new RaceC2SPacket(cardIndex));
        this.onClose();
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
