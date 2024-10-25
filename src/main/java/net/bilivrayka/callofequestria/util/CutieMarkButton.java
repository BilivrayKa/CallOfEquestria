package net.bilivrayka.callofequestria.util;

import com.mojang.blaze3d.systems.RenderSystem;
import net.bilivrayka.callofequestria.CallOfEquestria;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;

public class CutieMarkButton extends Button {
    private static final ResourceLocation CUTIE_MARK_BUTTON_TEXTURE = new ResourceLocation(CallOfEquestria.MOD_ID, "textures/gui/cutie_mark_button.png");
    private static final ResourceLocation HOVER_CUTIE_MARK_BUTTON_TEXTURE = new ResourceLocation(CallOfEquestria.MOD_ID, "textures/gui/hover_cutie_mark_button.png");

    public CutieMarkButton(Builder builder) {
        super(builder);
    }
/*
    public CutieMarkButton(int pX, int pY, int pWidth, int pHeight, Component pMessage, OnPress pOnPress, CreateNarration pCreateNarration) {
        super(pX, pY, pWidth, pHeight, pMessage, pOnPress, pCreateNarration);
    }

 */

    @Override
    public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        boolean isHovered = this.isHoveredOrFocused();
        ResourceLocation texture = isHovered ? HOVER_CUTIE_MARK_BUTTON_TEXTURE : CUTIE_MARK_BUTTON_TEXTURE;

        RenderSystem.setShaderTexture(0, texture);

        guiGraphics.blit(texture, this.getX(), this.getY(), 0, 0, this.width, this.height, 20, 20);

        //guiGraphics.drawCenteredString(this.font, this.getMessage(), this.getX() + this.width / 2, this.getY() + (this.height - 8) / 2, 0xFFFFFF);
    }
}