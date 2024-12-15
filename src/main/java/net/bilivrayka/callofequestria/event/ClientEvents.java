package net.bilivrayka.callofequestria.event;

import com.mojang.logging.LogUtils;
import net.bilivrayka.callofequestria.CallOfEquestria;
import net.bilivrayka.callofequestria.block.PlushReg;
import net.bilivrayka.callofequestria.data.*;
import net.bilivrayka.callofequestria.networking.ModMessages;
import net.bilivrayka.callofequestria.networking.packet.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.BookViewScreen;
import net.minecraft.client.renderer.block.BlockModelShaper;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.NoteBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.*;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;

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
            if (itemStack.getItem() == Items.NOTE_BLOCK){
                ResourceLocation OCTAVIA_MELODY_AD = new ResourceLocation(CallOfEquestria.MOD_ID, "octavia_melody");
                ModMessages.sendToServer(new AdvancementC2SPacket(OCTAVIA_MELODY_AD));
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