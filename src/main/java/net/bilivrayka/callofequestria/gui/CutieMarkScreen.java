package net.bilivrayka.callofequestria.gui;

import net.bilivrayka.callofequestria.CallOfEquestria;
import net.bilivrayka.callofequestria.networking.ModMessages;
import net.bilivrayka.callofequestria.networking.packet.RaceC2SPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;

public class CutieMarkScreen extends Screen {

    public CutieMarkScreen() {
        super(Component.literal("Cutie Mark Choose"));
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(guiGraphics);
        /*
        int cardWidth = 100;
        int cardHeight = 150;
        int spacing = 20;
        int centerX = (this.width - 3 * cardWidth - 2 * spacing) / 2;
        int centerY = (this.height - cardHeight) / 2;
        for (int i = 0; i < 3; i++) {
            int x = centerX + i * (cardWidth + spacing);
            int y = centerY;
            boolean isHovered = isMouseOverCard(mouseX, mouseY, x, y, cardWidth, cardHeight);
            int hoverOffset = isHovered ? -10 : 0;
            guiGraphics.blit(getCardTexture(i), x, y + hoverOffset, 0, 0, cardWidth, cardHeight, cardWidth, cardHeight);
            //guiGraphics.drawCenteredString(this.font, getCardLabel(i), x + cardWidth / 2, y + cardHeight + 10 + hoverOffset, 0xFFFFFF);
        }

         */
        super.render(guiGraphics, mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {

        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
