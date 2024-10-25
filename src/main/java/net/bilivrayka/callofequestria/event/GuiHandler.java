package net.bilivrayka.callofequestria.event;

import net.bilivrayka.callofequestria.CallOfEquestria;
import net.bilivrayka.callofequestria.gui.CutieMarkScreen;
import net.bilivrayka.callofequestria.gui.RaceChooseScreen;
import net.bilivrayka.callofequestria.util.CutieMarkButton;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraft.client.gui.components.Button;

import java.util.function.Supplier;


@Mod.EventBusSubscriber(modid = CallOfEquestria.MOD_ID, value = Dist.CLIENT)
public class GuiHandler {

    @SubscribeEvent
    public static void onGuiInit(ScreenEvent.Init.Post event) {
        if (event.getScreen() instanceof InventoryScreen inventoryScreen) {

            int x = inventoryScreen.getGuiLeft();
            int y = inventoryScreen.getGuiTop();

            int buttonX = x + 135;
            int buttonY = y + 60;

            //CutieMarkButton button = new CutieMarkButton(buttonX, buttonY, 20, 20, (Component) Component.literal(""), GuiHandler::onButtonPress, (Button.CreateNarration) Component.literal(""));
            CutieMarkButton button = new CutieMarkButton(Button.builder(Component.literal("B"), GuiHandler::onButtonPress)
                    .bounds(buttonX,buttonY,20,20)
                    .tooltip(Tooltip.create(Component.literal(""))));
            event.addListener(button);
        }
    }

    private static void onButtonPress(Button button) {
        //Minecraft.getInstance().setScreen(new CutieMarkScreen());
        /*
        Minecraft.getInstance().player.sendSystemMessage(Component.literal("Button Pressed!"));
         */
    }
}
