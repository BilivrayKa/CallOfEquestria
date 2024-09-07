package net.bilivrayka.callofequestria.event;

import com.mojang.logging.LogUtils;
import net.bilivrayka.callofequestria.CallOfEquestria;
import net.bilivrayka.callofequestria.gui.ClientRacePacket;
import net.bilivrayka.callofequestria.gui.ClientRenderHotbar;
import net.bilivrayka.callofequestria.gui.RaceChooseScreen;
import net.bilivrayka.callofequestria.magic.PlayerMagic;
import net.bilivrayka.callofequestria.magic.PlayerMagicProvider;
import net.bilivrayka.callofequestria.magic.PlayerRaceData;
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
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.fml.LogicalSide;
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

@Mod.EventBusSubscriber(modid = CallOfEquestria.MOD_ID)
public class ModEvents {
    public static final Logger LOGGER = LogUtils.getLogger();
    private static final Map<ServerPlayer, Integer> messageCounters = new HashMap<>();
    private static final int MESSAGE_THRESHOLD = 25;
    private static int race;

    @SubscribeEvent
    public static void onAttachCapabilitiesPlayer(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player) {
            if (!event.getObject().getCapability(PlayerMagicProvider.PLAYER_MAGIC).isPresent()) {
                event.addCapability(new ResourceLocation(CallOfEquestria.MOD_ID, "properties"), new PlayerMagicProvider());

            }
            if (!event.getObject().getCapability(PlayerRaceDataProvider.PLAYER_RACE_DATA).isPresent()) {
                event.addCapability(new ResourceLocation(CallOfEquestria.MOD_ID, "player_race_data"), new PlayerRaceDataProvider());
            }
        }
    }



    @SubscribeEvent
    public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
        event.register(PlayerMagic.class);
        event.register(PlayerRaceData.class);
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        Player player = event.player;
        player.getCapability(PlayerRaceDataProvider.PLAYER_RACE_DATA).ifPresent(data -> {
            race = data.getSelectedRace();
        });
/*
        if (event.side == LogicalSide.SERVER && !event.player.isCreative()) {
            event.player.getCapability(PlayerMagicProvider.PLAYER_MAGIC).ifPresent(magic -> {
                float tick = event.player.getRandom().nextFloat();
                if (magic.getMagic() <= 10 && tick < 0.035f && event.player.onGround()) {
                    event.player.getAbilities().mayfly = true;
                    event.player.onUpdateAbilities();
                    magic.addMagic(1);
                    ModMessages.sendToPlayer(new MagicSyncS2CPacket(magic.getMagic()), ((ServerPlayer) event.player));
                }
                if (event.player.getAbilities().flying && tick < 0.002f && race == 2) {
                    magic.subMagic(1);
                    ModMessages.sendToPlayer(new MagicSyncS2CPacket(magic.getMagic()), ((ServerPlayer) event.player));
                }
                if (magic.getMagic() <= 0 && race == 2) {
                    event.player.getAbilities().mayfly = false;
                    event.player.getAbilities().flying = false;
                    event.player.onUpdateAbilities();
                }
            });
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
            ResourceLocation DERPY_AD = new ResourceLocation(CallOfEquestria.MOD_ID, "derpy");
            ModMessages.sendToServer(new AdvancementC2SPacket(DERPY_AD));
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


    @SubscribeEvent
    public static void onServerChat(ServerChatEvent event) {
        ServerPlayer player = event.getPlayer();
        int messageCount = messageCounters.getOrDefault(player, 0) + 1;
        messageCounters.put(player, messageCount);
        if (messageCount >= MESSAGE_THRESHOLD) {
            ResourceLocation MINUETTE_AD = new ResourceLocation(CallOfEquestria.MOD_ID, "minuette");
            ModMessages.sendToServer(new AdvancementC2SPacket(MINUETTE_AD));
        }
    }
    @SubscribeEvent
    public static void onPlayerInteract(PlayerInteractEvent.EntityInteract event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            ItemStack itemStack = player.getMainHandItem();
            if (itemStack.getItem() == Items.FLINT_AND_STEEL) {
                Entity targetEntity = event.getTarget();
                if (targetEntity instanceof Creeper) {
                    ResourceLocation ROSE_AD = new ResourceLocation(CallOfEquestria.MOD_ID, "rose");
                    ModMessages.sendToServer(new AdvancementC2SPacket(ROSE_AD));

                }
            }
        }
    }
    @SubscribeEvent
    public static void onPlayerLoggedOut(PlayerEvent.PlayerLoggedOutEvent event) {
        if (!event.getEntity().level().isClientSide && event.getEntity() instanceof ServerPlayer) {
            ServerPlayer player = (ServerPlayer) event.getEntity();
            player.getCapability(PlayerRaceDataProvider.PLAYER_RACE_DATA).ifPresent(data -> {
                CompoundTag nbt = new CompoundTag();
                data.saveNBTData(nbt);
                player.getPersistentData().put(CallOfEquestria.MOD_ID, nbt);
            });

        }
    }
    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        if (!event.getEntity().level().isClientSide && event.getEntity() instanceof ServerPlayer) {
            ClientRenderHotbar.set(false);
            ServerPlayer player = (ServerPlayer) event.getEntity();
            player.getCapability(PlayerRaceDataProvider.PLAYER_RACE_DATA).ifPresent(data -> {
                CompoundTag nbt = player.getPersistentData().getCompound(CallOfEquestria.MOD_ID);
                data.loadNBTData(nbt);
                ClientRacePacket.set(data.getSelectedRace());
                //int selectedRace = data.getSelectedRace();
                //onPlayerLoggedInClient(selectedRace);
                //ModMessagesClient.CHANNEL.send(PacketDistributor.PLAYER.with(() -> player), new ClientRacePacket(selectedRace));
            });
        }
    }

/*
    @OnlyIn(Dist.CLIENT)
    public static void onPlayerLoggedInClient(int race) {
        if(race <= 0){
            //Minecraft.getInstance().execute(() -> {
                System.out.println("PlayerLoggedInEvent triggered on client!");
                Minecraft.getInstance().setScreen(new RaceChooseScreen());
            //});
        }
    }

 */
  /*
    public static void onPlayerFirstRaceChoose(PlayerEvent.PlayerLoggedInEvent event) {
        if(event.getEntity().level().isClientSide && event.getEntity() instanceof ServerPlayer){

            //ServerPlayer player = (ServerPlayer) event.getEntity();
            //ModMessages.sendToPlayer(new GUIRaceS2CPacket(), player);
        }
     }
   */

}
