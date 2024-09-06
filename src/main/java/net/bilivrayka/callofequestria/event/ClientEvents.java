package net.bilivrayka.callofequestria.event;

import net.bilivrayka.callofequestria.CallOfEquestria;
import net.bilivrayka.callofequestria.gui.ClientMagicData;
import net.bilivrayka.callofequestria.gui.ClientRacePacket;
import net.bilivrayka.callofequestria.gui.MagicHudOverlay;
import net.bilivrayka.callofequestria.gui.RaceChooseScreen;
import net.bilivrayka.callofequestria.magic.PlayerMagicProvider;
import net.bilivrayka.callofequestria.magic.PlayerRaceDataProvider;
import net.bilivrayka.callofequestria.networking.ModMessages;
import net.bilivrayka.callofequestria.networking.packet.AdvancementC2SPacket;
import net.bilivrayka.callofequestria.networking.packet.MagicSyncS2CPacket;
import net.bilivrayka.callofequestria.networking.packet.RaceC2SPacket;
import net.bilivrayka.callofequestria.util.KeyBinding;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.BookViewScreen;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.server.ServerLifecycleHooks;

import java.util.UUID;

public class ClientEvents {
    @Mod.EventBusSubscriber(modid = CallOfEquestria.MOD_ID, value = Dist.CLIENT)
    public static class ClientForgeEvents {
        @SubscribeEvent
        public static void onKeyInput(InputEvent.Key event) {
            /*
            if(KeyBinding.MAGIC_KEY.consumeClick()) {
                ModMessages.sendToServer(new MagicC2SPacket());
            }

             */
        }
        private static int race;
        @SubscribeEvent
        public static void onPlayerRaceChoose(TickEvent.RenderTickEvent event) {
            if (event.phase == TickEvent.Phase.START) {
                Minecraft instance = Minecraft.getInstance();
                if (instance.screen == null && instance.player != null) {
                    int race = ClientRacePacket.getRace();
                    if (race <= 0 && !(Minecraft.getInstance().screen instanceof RaceChooseScreen)) {
                        Minecraft.getInstance().setScreen(new RaceChooseScreen());
                    }
                }
            }
        }
        @SubscribeEvent
        public static void onScreenOpen(ScreenEvent.Opening event){
            Screen screen = event.getScreen();
            if (screen instanceof BookViewScreen) {
                ResourceLocation SPARKLE_AD = new ResourceLocation(CallOfEquestria.MOD_ID, "sparkle");
                ModMessages.sendToServer(new AdvancementC2SPacket(SPARKLE_AD));
            }
        }
        @SubscribeEvent
        public static void onPlayerCraft(PlayerEvent.ItemCraftedEvent event) {
            ItemStack itemStack = event.getCrafting();
            if (itemStack.getItem() instanceof DyeableLeatherItem){
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
        public static void onPlayerCloned(PlayerEvent.Clone event) {
            if(event.isWasDeath()) {
                //event.getOriginal().reviveCaps();
                event.getOriginal().getCapability(PlayerRaceDataProvider.PLAYER_RACE_DATA).ifPresent(oldData -> {
                    event.getEntity().getCapability(PlayerRaceDataProvider.PLAYER_RACE_DATA).ifPresent(newData -> {
                        ModMessages.sendToServer(new RaceC2SPacket(oldData.getSelectedRace()));
                    });
                });
                //event.getOriginal().invalidateCaps();
                event.getOriginal().getCapability(PlayerMagicProvider.PLAYER_MAGIC).ifPresent(oldStore -> {
                    event.getOriginal().getCapability(PlayerMagicProvider.PLAYER_MAGIC).ifPresent(newStore -> {
                        newStore.copyFrom(oldStore);
                    });
                });
            }
        }
        @SubscribeEvent
        public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
            Player player = event.player;
            int race = ClientRacePacket.getRace();
            if(event.side == LogicalSide.CLIENT && !event.player.isCreative()) {
                event.player.getCapability(PlayerMagicProvider.PLAYER_MAGIC).ifPresent(magic -> {
                    float tick = event.player.getRandom().nextFloat();
                    if(magic.getMagic() <= 10 && tick < 0.035f && event.player.onGround()) {
                        event.player.getAbilities().mayfly = true;
                        event.player.onUpdateAbilities();
                        magic.addMagic(1);
                        ClientMagicData.set(magic.getMagic());
                       // ModMessages.sendToPlayer(new MagicSyncS2CPacket(magic.getMagic()), ((ServerPlayer) event.player));
                    }
                    if(event.player.getAbilities().flying && tick < 0.002f && race == 2) {
                        magic.subMagic(1);
                        ClientMagicData.set(magic.getMagic());
                        //ModMessages.sendToPlayer(new MagicSyncS2CPacket(magic.getMagic()), ((ServerPlayer) event.player));
                    }
                    if(magic.getMagic() <= 0 && race == 2){
                        event.player.getAbilities().mayfly = false;
                        event.player.getAbilities().flying = false;
                        event.player.onUpdateAbilities();
                    }
                });
            }
            if(event.side == LogicalSide.CLIENT && !event.player.isCreative() && race != 2){
                event.player.getAbilities().mayfly = false;
                event.player.getAbilities().flying = false;
                event.player.onUpdateAbilities();
            }
            if(event.side == LogicalSide.CLIENT && event.player.getAbilities().flying
                    && !event.player.isCreative() && race == 2){

                //Player player = event.player;
                Vec3 lookDirection = player.getLookAngle();

                if(KeyBinding.FLY_TOWARDS_KEY.isDown()){
                    player.setDeltaMovement(player.getDeltaMovement().add(lookDirection.scale(0.025).x,
                            lookDirection.scale(0.10).y,lookDirection.scale(0.025).z));
                }
                if(KeyBinding.FLY_BACKWARDS_KEY.isDown()){
                    player.setDeltaMovement(player.getDeltaMovement().add(lookDirection.scale(-0.025).x,
                            lookDirection.scale(-0.10).y,lookDirection.scale(-0.025).z));
                }
            }
            if(event.player.getAbilities().flying && !event.player.isCreative() && race == 2){
                event.player.setPose(Pose.SWIMMING);
                int solidBlocks = 0;

                for (int i = 1; i != 20; i++){
                    BlockPos blockpos = new BlockPos(event.player.getBlockX(),
                            event.player.getBlockY()-i,event.player.getBlockZ());
                    if(!event.player.level().getBlockState(blockpos).isAir()){
                        solidBlocks++;
                    }
                }
                if(solidBlocks == 0){
                    event.player.setDeltaMovement(event.player.getDeltaMovement().x,-0.25, event.player.getDeltaMovement().z);
                }

            }
        }
    }

    @Mod.EventBusSubscriber(modid = CallOfEquestria.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModBusEvents {
        @SubscribeEvent
        public static void onKeyRegister(RegisterKeyMappingsEvent event) {
            //event.register(KeyBinding.MAGIC_KEY);
            event.register(KeyBinding.FLY_TOWARDS_KEY);
            event.register(KeyBinding.FLY_BACKWARDS_KEY);
        }
        @SubscribeEvent
        public static void registerGuiOverlays(RegisterGuiOverlaysEvent event) {
            event.registerAboveAll("magic", MagicHudOverlay.HUD_GUI);
        }

    }
}
