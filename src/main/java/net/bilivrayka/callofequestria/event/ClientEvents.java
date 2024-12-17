package net.bilivrayka.callofequestria.event;

import com.mojang.logging.LogUtils;
import net.bilivrayka.callofequestria.CallOfEquestria;
import net.bilivrayka.callofequestria.data.*;
import net.bilivrayka.callofequestria.networking.ModMessages;
import net.bilivrayka.callofequestria.networking.packet.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.BookViewScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.*;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.item.ItemExpireEvent;
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
            if(Minecraft.getInstance().player == null){
                return;
            }
            boolean allSlotsFullOfSugar = true;
            ItemStack chestItem = Minecraft.getInstance().player.getItemBySlot(EquipmentSlot.CHEST);

            Player player = Minecraft.getInstance().player;
            if (player.isOnFire() && player.isFallFlying()) {
                ModMessages.sendToServer(new AdvancementC2SPacket(AdvancementData.SPITFIRE_AD));
            }

            for (ItemStack stack : player.getInventory().items) {
                if (stack.isEmpty() || stack.getItem() != Items.SUGAR) {
                    allSlotsFullOfSugar = false;
                    break;
                }
            }

            if (allSlotsFullOfSugar) {
                ModMessages.sendToServer(new AdvancementC2SPacket(AdvancementData.SUGAR_BELLE_AD));
            }

            if (chestItem.getItem() == Items.ELYTRA && chestItem.getDamageValue() + 1 >= chestItem.getMaxDamage()) {
                ModMessages.sendToServer(new AdvancementC2SPacket(AdvancementData.LIGHTNING_DUST_AD));
            }
        }

        @SubscribeEvent
        public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        }

        @SubscribeEvent
        public static void onScreenOpen(ScreenEvent.Opening event) {
            Screen screen = event.getScreen();
            if (screen instanceof BookViewScreen) {
                ModMessages.sendToServer(new AdvancementC2SPacket(AdvancementData.SPARKLE_AD));
            }
        }

        @SubscribeEvent
        public static void onPlayerCraft(PlayerEvent.ItemCraftedEvent event) {
            ItemStack itemStack = event.getCrafting();
            if (itemStack.getItem() == Items.NOTE_BLOCK){
                ModMessages.sendToServer(new AdvancementC2SPacket(AdvancementData.OCTAVIA_MELODY_AD));
            }
        }

        @SubscribeEvent
        public static void onItemBreak(ItemExpireEvent event) {
            ItemStack item = event.getEntity().getItem();


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
                        ModMessages.sendToServer(new AdvancementC2SPacket(AdvancementData.STARLIGHT_AD));
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