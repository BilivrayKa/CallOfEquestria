package net.bilivrayka.callofequestria.event;

import com.mojang.logging.LogUtils;
import net.bilivrayka.callofequestria.CallOfEquestria;
import net.bilivrayka.callofequestria.item.ModItems;
import net.bilivrayka.callofequestria.magic.PlayerFlyStateProvider;
import net.bilivrayka.callofequestria.magic.PlayerMagic;
import net.bilivrayka.callofequestria.magic.PlayerMagicProvider;
import net.bilivrayka.callofequestria.networking.packet.FlyC2SPacket;
import net.bilivrayka.callofequestria.networking.packet.MagicC2SPacket;
import net.bilivrayka.callofequestria.util.KeyBinding;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.BookViewScreen;
import net.minecraft.core.BlockPos;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.level.block.AirBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.player.AdvancementEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.server.ServerLifecycleHooks;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;
import java.util.logging.Level;

@Mod.EventBusSubscriber(modid = CallOfEquestria.MOD_ID)
public class ModEvents {
    public static final Logger LOGGER = LogUtils.getLogger();
    //private static final KeyMapping flyKey = new KeyMapping("key.callofequestria.flytowards", GLFW.GLFW_KEY_W, "key.categories.movement");
    //@Mod.EventBusSubscriber(modid = CallOfEquestria.MOD_ID)
    //public static class ServerForgeEvents {
    @SubscribeEvent
    public static void onAttachCapabilitiesPlayer(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player) {
            if (!event.getObject().getCapability(PlayerMagicProvider.PLAYER_MAGIC).isPresent()) {
                event.addCapability(new ResourceLocation(CallOfEquestria.MOD_ID, "properties"), new PlayerMagicProvider());
            }
            /*
            if (!event.getObject().getCapability(PlayerFlyStateProvider.PLAYER_FLY_STATE).isPresent()) {
                event.addCapability(new ResourceLocation(CallOfEquestria.MOD_ID, "flyProp"), new PlayerFlyStateProvider());
            }

             */
        }
    }
    //}
    @SubscribeEvent
    public static void onPlayerCloned(PlayerEvent.Clone event) {
        if(event.isWasDeath()) {
            event.getOriginal().getCapability(PlayerMagicProvider.PLAYER_MAGIC).ifPresent(oldStore -> {
                event.getOriginal().getCapability(PlayerMagicProvider.PLAYER_MAGIC).ifPresent(newStore -> {
                    newStore.copyFrom(oldStore);
                });
            });
            /*
            event.getOriginal().getCapability(PlayerFlyStateProvider.PLAYER_FLY_STATE).ifPresent(oldStore -> {
                event.getOriginal().getCapability(PlayerFlyStateProvider.PLAYER_FLY_STATE).ifPresent(newStore -> {
                    newStore.copyFrom(oldStore);
                });
            });

             */
        }
    }

    @SubscribeEvent
    public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
        event.register(PlayerMagic.class);
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        /*
        if(false){
            if(event.side == LogicalSide.SERVER && !event.player.isCreative()) {
                event.player.getCapability(PlayerMagicProvider.PLAYER_MAGIC).ifPresent(magic -> {
                    float tick = event.player.getRandom().nextFloat();

                    if(magic.getMagic() != 10 && tick < 0.035f) {
                        magic.addMagic(1);
                    }
                    if(event.player.getAbilities().flying && tick < 0.037f){
                        magic.subMagic(1);
                        //event.player.sendSystemMessage(Component.literal("" + magic.getMagic()));
                    }
                    if(magic.getMagic() <= 0){
                        event.player.getAbilities().mayfly = false;
                        event.player.getAbilities().flying = false;
                        event.player.onUpdateAbilities();
                    }
                });

            }
            if(event.side == LogicalSide.CLIENT && event.player.getAbilities().flying
                    && !event.player.isCreative()){

                Player player = event.player;
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

            if(event.player.getAbilities().flying && !event.player.isCreative()){
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

         */
    }
    @SubscribeEvent
    public static void onPlayerDamage(LivingDamageEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) {
            return;
        }
        boolean isCollisionDamage = event.getSource().getMsgId().equals("flyIntoWall");
        if (isCollisionDamage) {
            ResourceLocation DERPY_AD = new ResourceLocation("callofequestria", "derpy");
            AdvancementRewardHandler.giveAdvancement(player,DERPY_AD);
        }
    }
    @SubscribeEvent
    public static void onScreenOpen(ScreenEvent.Opening event){
        Screen screen = event.getScreen();
        if (screen instanceof BookViewScreen) {
            UUID uuid = Minecraft.getInstance().player.getUUID();
            MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
            ServerPlayer player = server.getPlayerList().getPlayer(uuid);
            ResourceLocation SPARKLE_AD = new ResourceLocation("callofequestria", "sparkle");
            AdvancementRewardHandler.giveAdvancement(player, SPARKLE_AD);
        }
    }


    /*
    @SubscribeEvent
    public static void onAdvancement(AdvancementEvent event) {
        ServerPlayer player = (ServerPlayer) event.getEntity();
        LOGGER.info("Advancement triggered: " + event.getAdvancement().getId().getPath());

        if (event.getAdvancement().getId().getPath().equals("dash") && !player.level().isClientSide) {
            AdvancementProgress progress = player.getAdvancements().getOrStartProgress(event.getAdvancement());

            if (progress.isDone()) {
                ItemStack rewardItem = new ItemStack(ModItems.PLUSH_RAINBOW_DASH.get());
                player.addItem(rewardItem);
                LOGGER.info("Rewards added to player: " + player.getName().getString());
            }
        }
    }

     */
}
