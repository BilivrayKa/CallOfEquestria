package net.bilivrayka.callofequestria.event;

import com.mojang.blaze3d.vertex.PoseStack;
import net.bilivrayka.callofequestria.CallOfEquestria;
import net.bilivrayka.callofequestria.block.ModBlocks;
import net.bilivrayka.callofequestria.gui.*;
import net.bilivrayka.callofequestria.item.ModItems;
import net.bilivrayka.callofequestria.networking.packet.spell.BlinkSpellC2SPacket;
import net.bilivrayka.callofequestria.networking.packet.spell.BlockGrabC2SPacket;
import net.bilivrayka.callofequestria.networking.packet.spell.MagicProjectileC2SPacket;
import net.bilivrayka.callofequestria.providers.*;
import net.bilivrayka.callofequestria.networking.ModMessages;
import net.bilivrayka.callofequestria.networking.packet.*;
import net.bilivrayka.callofequestria.networking.packet.spell.RepelSpellC2SPacket;
import net.bilivrayka.callofequestria.util.KeyBinding;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.BookViewScreen;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.*;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;

import java.util.Map;

public class ClientEvents {
    @Mod.EventBusSubscriber(modid = CallOfEquestria.MOD_ID, value = Dist.CLIENT)
    public static class ClientForgeEvents {
        private static boolean isMagicHotbarActive;
        private static final MagicHotbarInventory magicHotbarInventory = new MagicHotbarInventory();
        private static final ResourceLocation MAGIC_HOTBAR_TEXTURE = new ResourceLocation(CallOfEquestria.MOD_ID, "textures/gui/hotbar/magic.png");
        private static final ResourceLocation SELECTED_MAGIC_SLOT_TEXTURE = new ResourceLocation(CallOfEquestria.MOD_ID, "textures/gui/hotbar/selected.png");
        //private static final long COOLDOWN_TIME_MS = 1000;
        private static final int[] magicCost = new int[9];
        static {
            magicCost[0] = 1;
            magicCost[1] = 3;
            magicCost[2] = 2;
            magicCost[3] = 1;
            magicCost[4] = 1;
            magicCost[5] = 1;
            magicCost[6] = 1;
            magicCost[7] = 1;
            magicCost[8] = 1;
        }
        private static long lastUsedTime = 0;


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
            /*TODO после армейки доделай хули*/
            if (KeyBinding.MAGIC_KEY.consumeClick()) {
                ClientForgeEvents.isMagicHotbarActive = !isMagicHotbarActive;
            }
        }

        @SubscribeEvent
        public static void onPlayerCloned(PlayerEvent.Clone event) {
            if (event.isWasDeath()) {
                event.getOriginal().reviveCaps();
                event.getOriginal().getCapability(PlayerRaceDataProvider.PLAYER_RACE_DATA).ifPresent(oldData -> {
                    event.getEntity().getCapability(PlayerRaceDataProvider.PLAYER_RACE_DATA).ifPresent(newData -> {
                        ModMessages.sendToServer(new RaceC2SPacket(oldData.getSelectedRace()));
                    });
                });
                event.getOriginal().invalidateCaps();
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
            float tick = event.player.getRandom().nextFloat();
            event.player.getCapability(PlayerMagicProvider.PLAYER_MAGIC).ifPresent(magic -> {
                if (magic.getMagic() < 10 && tick < 0.035f && event.player.onGround()) {
                    magic.addMagic(1);
                    //ClientMagicData.set(magic.getMagic());
                }
                magic.doCooldowns();
            });
            if (!event.player.isCreative() && race == 2 && Minecraft.getInstance().getConnection() != null) {
                event.player.getCapability(PlayerMagicProvider.PLAYER_MAGIC).ifPresent(magic -> {
                    if (magic.getMagic() <= 10 && tick < 0.035f && event.player.onGround()) {
                        ModMessages.sendToServer(new FlyC2SPacket(true));
                    }
                    if (event.player.getAbilities().flying && tick < 0.002f) {
                        magic.subMagic(1);
                        //ClientMagicData.set(magic.getMagic());
                    }
                    if (magic.getMagic() <= 0) {
                        ModMessages.sendToServer(new FlyC2SPacket(false));
                    }
                });
            }
            if (!event.player.isCreative() && race != 2 && Minecraft.getInstance().getConnection() != null) {
                ModMessages.sendToServer(new FlyC2SPacket(false));
            }
            if (event.player.getAbilities().flying && !event.player.isCreative() && race == 2) {
                Vec3 lookDirection = player.getLookAngle();
                Vec3 horizontalDirection = lookDirection.cross(new Vec3(0, 1, 0)).normalize();

                if (KeyBinding.FLY_TOWARDS_KEY.isDown()) {
                    player.setDeltaMovement(player.getDeltaMovement().add(lookDirection.scale(0.025).x,
                            lookDirection.scale(0.10).y, lookDirection.scale(0.025).z));
                } else if (KeyBinding.FLY_BACKWARDS_KEY.isDown()) {
                    player.setDeltaMovement(player.getDeltaMovement().add(lookDirection.scale(-0.025).x,
                            lookDirection.scale(-0.10).y, lookDirection.scale(-0.025).z));
                }
                if (KeyBinding.FLY_LEFT_KEY.isDown()) {
                    player.setDeltaMovement(player.getDeltaMovement().add(horizontalDirection.scale(-0.025).x,
                            0, horizontalDirection.scale(-0.025).z));
                } else if (KeyBinding.FLY_RIGHT_KEY.isDown()) {
                    player.setDeltaMovement(player.getDeltaMovement().add(horizontalDirection.scale(0.025).x,
                            0, horizontalDirection.scale(0.025).z));
                }
            }
            if (event.player.getAbilities().flying && !event.player.isCreative() && race == 2) {
                ModMessages.sendToServer(new FlyStateC2SPacket());
                event.player.setPose(Pose.FALL_FLYING);
                int solidBlocks = 0;

                for (int i = 1; i != 20; i++) {
                    BlockPos blockpos = new BlockPos(event.player.getBlockX(),
                            event.player.getBlockY() - i, event.player.getBlockZ());
                    if (!event.player.level().getBlockState(blockpos).isAir()) {
                        solidBlocks++;
                    }
                }
                if (solidBlocks == 0) {
                    event.player.setDeltaMovement(event.player.getDeltaMovement().x, -0.25, event.player.getDeltaMovement().z);
                }
            }
/*
            if (event.player instanceof ServerPlayer serverPlayer) {
                for (int i = 0; i < cooldowns.length; i++) {
                    if (cooldowns[i] > 0) {
                        cooldowns[i]--;
                    }
                }
            }

 */
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
                int slotSize = 20;
                int slotX = hotbarX + 1 + selectedSlot * (slotSize);

                guiGraphics.blit(SELECTED_MAGIC_SLOT_TEXTURE, slotX, hotbarY + 1, 0, 0, slotSize, slotSize, slotSize, slotSize);
                event.setCanceled(true);
            }
        }

        @SubscribeEvent
        public static void onRenderTick(TickEvent.RenderTickEvent event) {
            Minecraft instance = Minecraft.getInstance();
            if (event.phase == TickEvent.Phase.START && instance.player != null) {
                int race = ClientRacePacket.getRace();
                if (race <= 0 && !(instance.screen instanceof RaceChooseScreen)) {
                    Minecraft.getInstance().setScreen(new RaceChooseScreen());
                }
            }
        }

        @SubscribeEvent
        public static void onRenderHand(RenderHandEvent event) {
            if (isMagicHotbarActive) {
                Minecraft instance = Minecraft.getInstance();
                Player player = instance.player;

                if (player != null) {
                    event.setCanceled(true);
                    PoseStack poseStack = event.getPoseStack();
                    poseStack.pushPose();
                    poseStack.translate(0.45D, -0.25D, -0.4D);
                    poseStack.scale(1.0F, 1.0F, 1.0F);
                    poseStack.mulPose(com.mojang.math.Axis.XP.rotationDegrees(0.0F));
                    poseStack.mulPose(com.mojang.math.Axis.XP.rotationDegrees(0.0F));
                    MultiBufferSource bufferSource = event.getMultiBufferSource();
                    ItemRenderer itemRenderer = instance.getItemRenderer();
                    ItemStack itemStack = new ItemStack(ModItems.PLUSH_SPARKLE.get());
                    BakedModel bakedModel = itemRenderer.getModel(itemStack, instance.level, player, 0);
                    /*
                    itemRenderer.renderStatic(new ItemStack(Items.DIAMOND_SWORD), ItemDisplayContext.THIRD_PERSON_RIGHT_HAND, event.getPackedLight(),
                            OverlayTexture.NO_OVERLAY, poseStack, bufferSource, instance.level, 0);

                     */
                    //RenderSystem.setShaderTexture(0, instance.getTextureManager().getTexture(itemStack.getItem().get));
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
                }
            }
        }

        public static void castSpell(Player player) {
            player.getCapability(PlayerMagicProvider.PLAYER_MAGIC).ifPresent(magic -> {
                int selectedSlot = player.getInventory().selected;
                if (magic.getCooldowns(selectedSlot) <= 0 && magic.getMagic() >= magicCost[selectedSlot]) {
                    switch (selectedSlot) {
                        case 0:
                            Vec3 position = player.position();
                            Vec3 direction = player.getLookAngle();
                            ModMessages.sendToServer(new MagicProjectileC2SPacket(position, direction));
                            magic.setCooldowns(selectedSlot, 20);
                            break;
                        case 1:
                            ModMessages.sendToServer(new BlinkSpellC2SPacket());
                            magic.setCooldowns(selectedSlot, 100);
                            break;
                        case 2:
                            ModMessages.sendToServer(new RepelSpellC2SPacket());
                            magic.setCooldowns(selectedSlot, 20);
                            break;
                        case 3:
                            ModMessages.sendToServer(new BlockGrabC2SPacket());
                            magic.setCooldowns(selectedSlot, 20);
                            break;
                        case 4:

                            break;
                        case 5:
                            //TODO
                            break;
                        case 6:

                            break;
                        case 7:

                            break;
                        case 8:

                            break;
                        default:
                            player.sendSystemMessage(Component.literal("Ты куда звонишь?"));
                    }
                    magic.subMagic(magicCost[selectedSlot]);
                    //ClientMagicData.set(magic.getMagic());
                }
            });
        }

        @SubscribeEvent
        public static void onMouseClick(InputEvent.MouseButton event) {
            Minecraft mc = Minecraft.getInstance();
            Player player = mc.player;
            if (isMagicHotbarActive && event.getButton() == GLFW.GLFW_MOUSE_BUTTON_RIGHT && event.getAction() == GLFW.GLFW_PRESS) {
                castSpell(player);
                event.setCanceled(true);
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
        public static void onPlayerInteract(PlayerInteractEvent event) {
            if (isMagicHotbarActive && event.getHand() == InteractionHand.MAIN_HAND && !(event instanceof PlayerInteractEvent.RightClickEmpty) && !(event instanceof PlayerInteractEvent.LeftClickEmpty)
                    && !(event instanceof PlayerInteractEvent.LeftClickBlock) && !(event instanceof PlayerInteractEvent.RightClickBlock)) {
                event.setCanceled(true);
            }
        }
        @SubscribeEvent
        public static void onPlayerPlaceBlock(BlockEvent.EntityPlaceEvent event){
            if(isMagicHotbarActive && event.getEntity() instanceof Player){
                event.setCanceled(true);
            }
        }
        @SubscribeEvent
        public static void onPlayerBreakBlock(BlockEvent.BreakEvent event){
            if(isMagicHotbarActive) {
                event.setCanceled(true);
            }
        }

        @SubscribeEvent
        public static void onPlayerAttack(AttackEntityEvent event) {
            Player player = event.getEntity();
            if (isMagicHotbarActive) {
                event.setCanceled(true);
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
}