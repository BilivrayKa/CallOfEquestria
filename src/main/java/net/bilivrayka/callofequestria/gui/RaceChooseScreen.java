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
        this.renderBackground(guiGraphics); // Рендеринг фона

        // Координаты для карточек
        int cardWidth = 100;
        int cardHeight = 150;
        int spacing = 20;
        int centerX = (this.width - 3 * cardWidth - 2 * spacing) / 2;
        int centerY = (this.height - cardHeight) / 2;
        /*
        RenderSystem.setShaderTexture(0, CARD_TEXTURE_1);
        RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();


         */
        guiGraphics.blit(CARD_TEXTURE_1, centerX, centerY, 0, 0, cardWidth, cardHeight, cardWidth, cardHeight);
        guiGraphics.blit(CARD_TEXTURE_2, centerX + cardWidth + spacing, centerY, 0, 0, cardWidth, cardHeight, cardWidth, cardHeight);
        guiGraphics.blit(CARD_TEXTURE_3, centerX + 2 * (cardWidth + spacing), centerY, 0, 0, cardWidth, cardHeight, cardWidth, cardHeight);

        guiGraphics.drawCenteredString(this.font, "EarthPony", centerX + cardWidth / 2, centerY + cardHeight + 10, 0xFFFFFF);
        guiGraphics.drawCenteredString(this.font, "Pegasus", centerX + (cardWidth + spacing) + cardWidth / 2, centerY + cardHeight + 10, 0xFFFFFF);
        guiGraphics.drawCenteredString(this.font, "Unicorn", centerX + 2 * (cardWidth + spacing) + cardWidth / 2, centerY + cardHeight + 10, 0xFFFFFF);

        super.render(guiGraphics, mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        int cardWidth = 100;
        int cardHeight = 150;
        int spacing = 20;
        int centerX = (this.width - 3 * cardWidth - 2 * spacing) / 2;
        int centerY = (this.height - cardHeight) / 2;

        if (mouseX >= centerX && mouseX <= centerX + cardWidth && mouseY >= centerY && mouseY <= centerY + cardHeight) {
            this.selectedCard = 1;
            this.onCardSelected(1);
        } else if (mouseX >= centerX + cardWidth + spacing && mouseX <= centerX + 2 * cardWidth + spacing && mouseY >= centerY && mouseY <= centerY + cardHeight) {
            this.selectedCard = 2;
            this.onCardSelected(2);
        } else if (mouseX >= centerX + 2 * (cardWidth + spacing) && mouseX <= centerX + 3 * cardWidth + 2 * spacing && mouseY >= centerY && mouseY <= centerY + cardHeight) {
            this.selectedCard = 3;
            this.onCardSelected(3);
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
