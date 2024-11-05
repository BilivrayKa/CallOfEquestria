package net.bilivrayka.callofequestria.event;

import ca.weblite.objc.Client;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.logging.LogUtils;
import net.bilivrayka.callofequestria.CallOfEquestria;
import net.bilivrayka.callofequestria.gui.*;
import net.bilivrayka.callofequestria.networking.packet.spell.BlinkSpellC2SPacket;
import net.bilivrayka.callofequestria.networking.packet.spell.BlockGrabC2SPacket;
import net.bilivrayka.callofequestria.networking.packet.spell.MagicProjectileC2SPacket;
import net.bilivrayka.callofequestria.data.*;
import net.bilivrayka.callofequestria.networking.ModMessages;
import net.bilivrayka.callofequestria.networking.packet.*;
import net.bilivrayka.callofequestria.networking.packet.spell.RepelSpellC2SPacket;
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
        private static boolean isMagicHotbarActive;
        private static final ResourceLocation MAGIC_HOTBAR_TEXTURE = new ResourceLocation(CallOfEquestria.MOD_ID, "textures/gui/hotbar/magic.png");
        private static final ResourceLocation SELECTED_MAGIC_SLOT_TEXTURE = new ResourceLocation(CallOfEquestria.MOD_ID, "textures/gui/hotbar/selected.png");
        private static final ResourceLocation MAGIC_GUI = new ResourceLocation(CallOfEquestria.MOD_ID, "textures/gui/magic.png");
        private static final int[] magicCost = new int[9];
        private static BlockPos previousLightPos = null;
        static {
            magicCost[0] = 1;
            magicCost[1] = 5;
            magicCost[2] = 5;
            magicCost[3] = 3;
            magicCost[4] = 1;
            magicCost[5] = 1;
            magicCost[6] = 1;
            magicCost[7] = 1;
            magicCost[8] = 1;
        }
        private static long lastUsedTime = 0;
        private static int race;


        @SubscribeEvent
        public static void onKeyInput(InputEvent.Key event) {
            /*
            if(KeyBinding.MAGIC_KEY.consumeClick()) {
                ModMessages.sendToServer(new MagicC2SPacket());
            }

             */
        }

        @SubscribeEvent
        public static void onClientTick(TickEvent.ClientTickEvent event) {
            if(Minecraft.getInstance().player != null){
                Minecraft.getInstance().player.getCapability(PlayerRaceDataProvider.PLAYER_RACE_DATA).ifPresent(races ->{
                    isMagicHotbarActive = races.getIsMagicHotbarActive();
                    ModMessages.sendToServer(new IsMagicHotbarActiveSyncC2SPacket(isMagicHotbarActive));
                });
            }
            if (KeyBinding.MAGIC_KEY.consumeClick()) {
                Minecraft.getInstance().player.getCapability(PlayerMagicDataProvider.PLAYER_MAGIC).ifPresent(magic ->{
                    Minecraft.getInstance().player.getCapability(PlayerRaceDataProvider.PLAYER_RACE_DATA).ifPresent(races ->{
                        if(races.getSelectedRace() == 3 && magic.getMagic() >= 1){
                            magic.subMagic(1);
                            races.toogleMagicHotbar();
                            Minecraft.getInstance().player.playSound(SoundEvents.ALLAY_THROW,1,1);
                        }
                    });
                });
            }
        }

        @SubscribeEvent
        public static void onPlayerInteract(PlayerInteractEvent event) {
            Player player = event.getEntity();
            player.getCapability(PlayerRaceDataProvider.PLAYER_RACE_DATA).ifPresent(races -> {
                boolean isMagicHotbarActive = races.getIsMagicHotbarActive();
                if (isMagicHotbarActive && event.getHand() == InteractionHand.MAIN_HAND) {
                    if(event.isCancelable()){
                        event.setCanceled(true);
                    }
                }
            });
        }

        @SubscribeEvent
        public static void onPlayerAttack(AttackEntityEvent event) {
            Player player = event.getEntity();
            player.getCapability(PlayerRaceDataProvider.PLAYER_RACE_DATA).ifPresent(races -> {
                boolean isMagicHotbarActive = races.getIsMagicHotbarActive();
                if(isMagicHotbarActive){
                    event.setCanceled(true);
                }
            });
        }

        @SubscribeEvent
        public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
            LocalPlayer player = Minecraft.getInstance().player;
            if(player == null){
               return;
            }
            if(player.isDeadOrDying()){
                ModMessages.sendToServer(new GrabbedBlockOnDeathC2SPacket());
            }
            player.getCapability(PlayerRaceDataProvider.PLAYER_RACE_DATA).ifPresent(races -> {
                race = races.getSelectedRace();
            });

            float tick;
            float constantTick = 0.005f;
            if (Minecraft.getInstance().isSameThread()) {
                tick = player.getRandom().nextFloat();
            } else {
                tick = 1f;
            }
            player.getCapability(PlayerMagicDataProvider.PLAYER_MAGIC).ifPresent(magic -> {
                if(player.onGround()){
                    if (magic.getMagic() < 10 && tick < 0.001f) {
                        magic.addMagic(1);
                    }
                    magic.addMagic(constantTick);
                } else {
                    magic.addMagic(constantTick/3);
                }
                magic.doCooldowns();
                if (!player.isCreative() && race == 2 && Minecraft.getInstance().getConnection() != null) {
                    if (magic.getMagic() == 10) {
                        ModMessages.sendToServer(new FlyC2SPacket(true));
                    }
                    if (player.getAbilities().flying && player.getRandom().nextFloat() < 0.005f) {
                        magic.subMagic(1);
                    }
                    if (magic.getMagic() <= 0.9f) {
                        ModMessages.sendToServer(new FlyC2SPacket(false));
                    }
                }
                if(magic.isDynamicLight()){
                    BlockPos lightPos = player.blockPosition().above();
                    int lightLevel = 15;
                    updateClientLight(player.clientLevel, lightPos, lightLevel, magic.isDynamicLight());
                }else if(!magic.isDynamicLight() && previousLightPos != null){
                    player.clientLevel.setBlock(previousLightPos, Blocks.AIR.defaultBlockState(), 3);
                    previousLightPos = null;
                }

            });

            if (player.getAbilities().flying && !player.isCreative() && race == 2) {
                Vec3 lookDirection = player.getLookAngle();
                Vec3 horizontalDirection = lookDirection.cross(new Vec3(0, 1, 0)).normalize();

                if (KeyBinding.FLY_TOWARDS_KEY.isDown()) {
                    player.setDeltaMovement(player.getDeltaMovement().add(lookDirection.scale(0.010).x,
                            lookDirection.scale(0.030).y, lookDirection.scale(0.010).z));
                } else if (KeyBinding.FLY_BACKWARDS_KEY.isDown()) {
                    player.setDeltaMovement(player.getDeltaMovement().add(lookDirection.scale(-0.010).x,
                            lookDirection.scale(-0.030).y, lookDirection.scale(-0.010).z));
                }
                if (KeyBinding.FLY_LEFT_KEY.isDown()) {
                    player.setDeltaMovement(player.getDeltaMovement().add(horizontalDirection.scale(-0.010).x,
                            0, horizontalDirection.scale(-0.010).z));
                } else if (KeyBinding.FLY_RIGHT_KEY.isDown()) {
                    player.setDeltaMovement(player.getDeltaMovement().add(horizontalDirection.scale(0.010).x,
                            0, horizontalDirection.scale(0.010).z));
                }
            }
            if (player.getAbilities().flying && !player.isCreative() && race == 2) {
                ModMessages.sendToServer(new FlyStateC2SPacket());
                player.setPose(Pose.FALL_FLYING);
                int solidBlocks = 0;

                for (int i = 1; i != 20; i++) {
                    BlockPos blockpos = new BlockPos(player.getBlockX(),
                            player.getBlockY() - i, player.getBlockZ());
                    if (!player.level().getBlockState(blockpos).isAir()) {
                        solidBlocks++;
                    }
                }
                if (solidBlocks == 0) {
                    player.setDeltaMovement(player.getDeltaMovement().x, -0.25, player.getDeltaMovement().z);
                }
            }
        }

        private static void updateClientLight(Level level, BlockPos pos, int lightLevel, boolean isDynamicLight) {
            if (previousLightPos != null && !previousLightPos.equals(pos)) {
                level.setBlock(previousLightPos, Blocks.AIR.defaultBlockState(), 3);
                previousLightPos = null;
            }

            if (level.getBrightness(LightLayer.BLOCK, pos) < lightLevel && level.getBlockState(pos).is(Blocks.AIR)) {
                level.setBlock(pos, Blocks.LIGHT.defaultBlockState().setValue(BlockStateProperties.LEVEL, lightLevel), 3);
                previousLightPos = pos;
            }
        }

        @SubscribeEvent
        public static void onRenderOverlay(RenderGuiOverlayEvent.Pre event) {
            if (event.getOverlay().equals(VanillaGuiOverlay.ITEM_NAME.type()) && isMagicHotbarActive) {
                event.setCanceled(true);
            }
            if (event.getOverlay().equals(VanillaGuiOverlay.HOTBAR.type()) && isMagicHotbarActive) {
                Minecraft instance = Minecraft.getInstance();
                GuiGraphics guiGraphics = event.getGuiGraphics();
                int screenWidth = instance.getWindow().getGuiScaledWidth();
                int screenHeight = instance.getWindow().getGuiScaledHeight();
                int hotbarWidth = 182;
                int hotbarHeight = 22;
                int hotbarX = (screenWidth - hotbarWidth) / 2;
                int hotbarY = screenHeight - hotbarHeight;

                guiGraphics.blit(MAGIC_HOTBAR_TEXTURE, hotbarX, hotbarY, 0, 0, hotbarWidth, hotbarHeight, hotbarWidth, hotbarHeight);

                int selectedSlot = instance.player.getInventory().selected;
                switch (selectedSlot) {
                    case 5,6,7,8:
                        instance.player.getInventory().selected = 0;
                        break;
                }
                int slotSize = 20;
                if(selectedSlot <= 4){
                    int slotX = hotbarX + 40 + 1 + selectedSlot * (slotSize);
                    guiGraphics.blit(SELECTED_MAGIC_SLOT_TEXTURE, slotX, hotbarY + 1, 0, 0, slotSize, slotSize, slotSize, slotSize);

                }
                RenderSystem.enableBlend();
                RenderSystem.defaultBlendFunc();
                RenderSystem.setShaderColor(1F, 1F, 1.0F, 0.85F);
                guiGraphics.blit(MAGIC_GUI, 0, 0, instance.getWindow().getGuiScaledWidth(), instance.getWindow().getGuiScaledHeight(), 0, 0, 256, 256, 256, 256);
                RenderSystem.disableBlend();

                event.setCanceled(true);
            }
        }
/*
        @SubscribeEvent
        public static void onRenderTick(TickEvent.RenderTickEvent event) {
            Minecraft instance = Minecraft.getInstance();
            if (event.phase == TickEvent.Phase.END && instance.player != null) {
                instance.player.getCapability(PlayerMagicDataProvider.PLAYER_MAGIC).ifPresent(magic ->{
                    if(magic.getMagic() >= 1){
                        instance.player.getCapability(PlayerRaceDataProvider.PLAYER_RACE_DATA).ifPresent(races -> {
                            int race = races.getSelectedRace();
                            if (race == 0 && !(instance.screen instanceof RaceChooseScreen)) {
                                Minecraft.getInstance().setScreen(new RaceChooseScreen());
                            }
                            if(race != 0 && (instance.screen instanceof RaceChooseScreen)) {
                                Minecraft.getInstance().setScreen(null);
                            }
                        });
                    }
                });
            }
        }

 */

        @SubscribeEvent
        public static void onRenderHand(RenderHandEvent event) {
            if (isMagicHotbarActive) {
                Minecraft instance = Minecraft.getInstance();
                Player player = instance.player;

                if (player != null) {
                    event.setCanceled(true);
                    /*
                    PoseStack poseStack = event.getPoseStack();
                    poseStack.pushPose();
                    poseStack.translate(0D, 0D, -0.4D);
                    poseStack.scale(1.0F, 1.0F, 1.0F);
                    poseStack.mulPose(com.mojang.math.Axis.XP.rotationDegrees(0.0F));
                    poseStack.mulPose(com.mojang.math.Axis.XP.rotationDegrees(0.0F));
                    MultiBufferSource bufferSource = event.getMultiBufferSource();
                    ItemRenderer itemRenderer = instance.getItemRenderer();
                    ItemStack itemStack = new ItemStack(ModBlocks.MAGIC_PROJECTILE.get().asItem());
                    BakedModel bakedModel = itemRenderer.getModel(itemStack, instance.level, player, 0);

                    itemRenderer.renderStatic(new ItemStack(Items.DIAMOND_SWORD), ItemDisplayContext.THIRD_PERSON_RIGHT_HAND, event.getPackedLight(),
                            OverlayTexture.NO_OVERLAY, poseStack, bufferSource, instance.level, 0);

                     */
                    //RenderSystem.setShaderTexture(0, instance.getTextureManager().getTexture(itemStack.getItem().get));
                    /*
                    itemRenderer.render(
                            itemStack,                    // Стек предметов
                            ItemDisplayContext.FIRST_PERSON_RIGHT_HAND, // Контекст отображения
                            false,                        // Предмет находится в правой руке (true для левой руки)
                            poseStack,                    // Стек позиций
                            bufferSource,                 // Буфер для рендеринга
                            event.getPackedLight(),              // Уровень освещения
                            OverlayTexture.NO_OVERLAY,    // Уровень наложения
                            bakedModel                         // Модель для рендеринга
                    );

                     */
                }
            }
        }

        public static void castSpell(Player player) {
            player.getCapability(PlayerMagicDataProvider.PLAYER_MAGIC).ifPresent(magic -> {
                int selectedSlot = player.getInventory().selected;
                if (magic.getCooldowns(selectedSlot) <= 0 && magic.getMagic() >= magicCost[selectedSlot]) {
                    switch (selectedSlot) {
                        case 0,5:
                            Vec3 position = player.position();
                            Vec3 direction = player.getLookAngle();
                            ModMessages.sendToServer(new MagicProjectileC2SPacket(position, direction));
                            break;
                        case 1,6:
                            ModMessages.sendToServer(new BlinkSpellC2SPacket());
                            break;
                        case 2,7:
                            ModMessages.sendToServer(new RepelSpellC2SPacket());
                            break;
                        case 3,8:
                            ModMessages.sendToServer(new BlockGrabC2SPacket());
                            break;
                        case 4:
                            magic.setDynamicLight(!magic.isDynamicLight());
                            magic.setCooldowns(selectedSlot, 20);
                            magic.subMagic(magicCost[selectedSlot]);
                            break;
                        default:
                            player.sendSystemMessage(Component.literal("Ты куда звонишь?"));
                    }
                    player.inventoryMenu.broadcastChanges();
                }
            });
        }

        @SubscribeEvent
        public static void onMouseClick(InputEvent.MouseButton event) {
            Minecraft mc = Minecraft.getInstance();
            LocalPlayer player = mc.player;
            if (isMagicHotbarActive && event.getButton() == GLFW.GLFW_MOUSE_BUTTON_RIGHT && event.getAction() == GLFW.GLFW_PRESS) {
                castSpell(player);
                event.setCanceled(true);
            }
        }
        @SubscribeEvent
        public static void onMouseScroll(InputEvent.MouseScrollingEvent event) {
            Minecraft mc = Minecraft.getInstance();
            LocalPlayer player = mc.player;
            double scrollDelta = event.getScrollDelta();

            if (scrollDelta > 0 && player.getInventory().selected == 0 && isMagicHotbarActive) {
                player.getInventory().selected = 5;
            } else if (scrollDelta < 0 && player.getInventory().selected == 4 && isMagicHotbarActive) {
                player.getInventory().selected = 8;
            }

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

        /*
        @SubscribeEvent
        public static void onPlayerPlaceBlock(BlockEvent.EntityPlaceEvent event){
            Player player = (Player) event.getEntity();
            player.getCapability(PlayerRaceDataProvider.PLAYER_RACE_DATA).ifPresent(races -> {
                boolean isMagicHotbarActive = races.getIsMagicHotbarActive();
                if(isMagicHotbarActive && event.getEntity() instanceof Player){
                    event.setCanceled(true);
                }
            });
        }

         */



        @Mod.EventBusSubscriber(modid = CallOfEquestria.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
        public static class ClientModBusEvents {
            @SubscribeEvent
            public static void onKeyRegister(RegisterKeyMappingsEvent event) {
                event.register(KeyBinding.MAGIC_KEY);
                //event.register(KeyBinding.FLY_TOWARDS_KEY);
                //event.register(KeyBinding.FLY_BACKWARDS_KEY);
            }

            @SubscribeEvent
            public static void registerGuiOverlays(RegisterGuiOverlaysEvent event) {
                //event.registerAboveAll("magic", MagicHudOverlay.HUD_GUI);
                //event.registerBelowAll("magic", MagicHudOverlay.HUD_GUI);
            }
        }
    }
}