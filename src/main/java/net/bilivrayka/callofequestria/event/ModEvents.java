package net.bilivrayka.callofequestria.event;

import com.mojang.logging.LogUtils;
import net.bilivrayka.callofequestria.Ponified;
import net.bilivrayka.callofequestria.magic.PlayerMagic;
import net.bilivrayka.callofequestria.magic.PlayerMagicProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.BookViewScreen;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.server.ServerLifecycleHooks;
import org.slf4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Mod.EventBusSubscriber(modid = Ponified.MOD_ID)
public class ModEvents {
    public static final Logger LOGGER = LogUtils.getLogger();
    private static final Map<ServerPlayer, Integer> messageCounters = new HashMap<>();
    private static final int MESSAGE_THRESHOLD = 25;
    //private static final KeyMapping flyKey = new KeyMapping("key.callofequestria.flytowards", GLFW.GLFW_KEY_W, "key.categories.movement");
    //@Mod.EventBusSubscriber(modid = CallOfEquestria.MOD_ID)
    //public static class ServerForgeEvents {
    @SubscribeEvent
    public static void onAttachCapabilitiesPlayer(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player) {
            if (!event.getObject().getCapability(PlayerMagicProvider.PLAYER_MAGIC).isPresent()) {
                event.addCapability(new ResourceLocation(Ponified.MOD_ID, "properties"), new PlayerMagicProvider());
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
            ResourceLocation DERPY_AD = new ResourceLocation("ponified", "derpy");
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
            ResourceLocation SPARKLE_AD = new ResourceLocation("ponified", "sparkle");
            AdvancementRewardHandler.giveAdvancement(player, SPARKLE_AD);
        }
    }

    @SubscribeEvent
    public static void onBreakingSomeVillagerLive(BlockEvent.BreakEvent event) {
        BlockState state = event.getState();
        ServerPlayer player = (ServerPlayer) event.getPlayer();
        AABB aabb = new AABB(event.getPos()).inflate(5.0D);
        if (VillagerProfessionHandler.isProfessionBlock(state.getBlock())) {
            event.getLevel().getEntitiesOfClass(Villager.class, aabb).forEach(villager -> {
                Block workStation = VillagerProfessionHandler.getBlockByProfession(villager.getVillagerData().getProfession());
                //boolean hasProfession = VillagerProfession.NONE != villager.getVillagerData().getProfession();
                boolean hasProfession = workStation == state.getBlock();
                if (hasProfession) {
                    ResourceLocation STARLIGHT_AD = new ResourceLocation("ponified", "starlight");
                    AdvancementRewardHandler.giveAdvancement(player, STARLIGHT_AD);
                }
            });
        }
    }
    @SubscribeEvent
    public static void onPlayerCraft(PlayerEvent.ItemCraftedEvent event) {
        UUID uuid = Minecraft.getInstance().player.getUUID();
        MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
        ServerPlayer player = server.getPlayerList().getPlayer(uuid);
        ItemStack itemStack = event.getCrafting();
        if (itemStack.getItem() instanceof DyeableLeatherItem){
            DyeableLeatherItem dyeableItem = (DyeableLeatherItem) itemStack.getItem();
            int color = dyeableItem.getColor(itemStack);
            if (color != -1) {
                ResourceLocation RARITY_AD = new ResourceLocation("ponified", "rarity");
                AdvancementRewardHandler.giveAdvancement(player, RARITY_AD);

            }
        } else if (itemStack.getItem() == Items.CAKE) {
            ResourceLocation PINKIE_PIE_AD = new ResourceLocation("ponified", "pinkie_pie");
            AdvancementRewardHandler.giveAdvancement(player, PINKIE_PIE_AD);
        }
    }

    @SubscribeEvent
    public static void onServerChat(ServerChatEvent event) {
        ServerPlayer player = event.getPlayer();
        int messageCount = messageCounters.getOrDefault(player, 0) + 1;
        messageCounters.put(player, messageCount);
        if (messageCount >= MESSAGE_THRESHOLD) {
            ResourceLocation MINUETTE_AD = new ResourceLocation("ponified", "minuette");
            AdvancementRewardHandler.giveAdvancement(player, MINUETTE_AD);
        }
    }
    @SubscribeEvent
    public static void onPlayerInteract(PlayerInteractEvent.EntityInteract event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            ItemStack itemStack = player.getMainHandItem();

            if (itemStack.getItem() == Items.FLINT_AND_STEEL) {
                Entity targetEntity = event.getTarget();
                if (targetEntity instanceof Creeper) {
                    ResourceLocation ROSE_AD = new ResourceLocation("ponified", "rose");
                    AdvancementRewardHandler.giveAdvancement(player, ROSE_AD);

                }
            }
        }
    }
}
