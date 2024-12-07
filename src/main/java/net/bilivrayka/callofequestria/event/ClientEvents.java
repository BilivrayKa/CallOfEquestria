package net.bilivrayka.callofequestria.event;

import ca.weblite.objc.Client;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.logging.LogUtils;
import net.bilivrayka.callofequestria.CallOfEquestria;
import net.bilivrayka.callofequestria.data.*;
import net.bilivrayka.callofequestria.networking.ModMessages;
import net.bilivrayka.callofequestria.networking.packet.*;
import net.bilivrayka.callofequestria.util.KeyBinding;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.BookViewScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.*;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;

import java.awt.*;

public class ClientEvents {
    @Mod.EventBusSubscriber(modid = CallOfEquestria.MOD_ID, value = Dist.CLIENT)
    public static class ClientForgeEvents {
        public static final Logger LOGGER = LogUtils.getLogger();
        private static long lastUsedTime = 0;

        @SubscribeEvent
        public static void onKeyInput(InputEvent.Key event) {

        }

        @SubscribeEvent
        public static void onClientTick(TickEvent.ClientTickEvent event) {

        }

        @SubscribeEvent
        public static void onPlayerTick(TickEvent.PlayerTickEvent event) {

        }

        @SubscribeEvent
        public static void onScreenOpen(ScreenEvent.Opening event) {
            Screen screen = event.getScreen();
            if (screen instanceof BookViewScreen) {
                ResourceLocation SPARKLE_AD = new ResourceLocation(CallOfEquestria.MOD_ID, "sparkle");
                ModMessages.sendToServer(new AdvancementC2SPacket(SPARKLE_AD));
            }
        }

        @SubscribeEvent
        public static void onPlayerCraft(PlayerEvent.ItemCraftedEvent event) {
            ItemStack itemStack = event.getCrafting();
            if (itemStack.getItem() instanceof DyeableLeatherItem) {
                DyeableLeatherItem dyeableItem = (DyeableLeatherItem) itemStack.getItem();
                int color = dyeableItem.getColor(itemStack);
                if (color != -1) {
                    ResourceLocation RARITY_AD = new ResourceLocation(CallOfEquestria.MOD_ID, "rarity");
                    ModMessages.sendToServer(new AdvancementC2SPacket(RARITY_AD));

                }
            } else if (itemStack.getItem() == Items.CAKE) {
                ResourceLocation PINKIE_PIE_AD = new ResourceLocation(CallOfEquestria.MOD_ID, "pinkie_pie");
                ModMessages.sendToServer(new AdvancementC2SPacket(PINKIE_PIE_AD));
            }
        }
        @SubscribeEvent
        public static void onBreakingSomeVillagerLive(BlockEvent.BreakEvent event) {
            BlockState state = event.getState();
            AABB aabb = new AABB(event.getPos()).inflate(5.0D);
            if (VillagerProfessionHandler.isProfessionBlock(state.getBlock())) {
                event.getLevel().getEntitiesOfClass(Villager.class, aabb).forEach(villager -> {
                    Block workStation = VillagerProfessionHandler.getBlockByProfession(villager.getVillagerData().getProfession());
                    boolean hasProfession = workStation == state.getBlock();
                    if (hasProfession) {
                        ResourceLocation STARLIGHT_AD = new ResourceLocation(CallOfEquestria.MOD_ID, "starlight");
                        ModMessages.sendToServer(new AdvancementC2SPacket(STARLIGHT_AD));
                    }
                });
            }
        }



        @Mod.EventBusSubscriber(modid = CallOfEquestria.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
        public static class ClientModBusEvents {
            @SubscribeEvent
            public static void onKeyRegister(RegisterKeyMappingsEvent event) {
            }

            @SubscribeEvent
            public static void registerGuiOverlays(RegisterGuiOverlaysEvent event) {
            }
        }
    }
}