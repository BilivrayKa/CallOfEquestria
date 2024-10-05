package net.bilivrayka.callofequestria.event;

import com.mojang.logging.LogUtils;
import net.bilivrayka.callofequestria.CallOfEquestria;
import net.bilivrayka.callofequestria.networking.packet.*;
import net.bilivrayka.callofequestria.providers.*;
import net.bilivrayka.callofequestria.gui.ClientRenderHotbar;
import net.bilivrayka.callofequestria.networking.ModMessages;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
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
import org.slf4j.Logger;

import java.util.HashMap;
import java.util.Map;

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
                event.addCapability(new ResourceLocation(CallOfEquestria.MOD_ID, "server_player_race_data"), new PlayerRaceDataProvider());
            }
        }

        /*
        if (event.getObject() instanceof LocalPlayer) {
            if (!event.getObject().getCapability(PlayerMagicProvider.PLAYER_MAGIC).isPresent()) {
                event.addCapability(new ResourceLocation(CallOfEquestria.MOD_ID, "properties"), new PlayerMagicProvider());

            }
            if (!event.getObject().getCapability(PlayerRaceDataProvider.PLAYER_RACE_DATA).isPresent()) {
                event.addCapability(new ResourceLocation(CallOfEquestria.MOD_ID, "player_race_data"), new PlayerRaceDataProvider());
            }
        }

         */
    }
    /*
    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event){
        event.getServer().ge
    }

     */
    @SubscribeEvent
    public static void onPlayerCloned(PlayerEvent.Clone event) {
        if(event.getOriginal().level().isClientSide)
            return;
        if (event.isWasDeath()) {

            Player original = event.getOriginal();
            ServerPlayer player = (ServerPlayer) event.getEntity();
            original.reviveCaps();
            if(true){
                original.getCapability(PlayerRaceDataProvider.PLAYER_RACE_DATA).ifPresent(oldData -> {
                    event.getEntity().getCapability(PlayerRaceDataProvider.PLAYER_RACE_DATA).ifPresent(newData -> {
                        newData.copyFrom(oldData);
                        LOGGER.debug("Race: "+newData.getSelectedRace());
                        //ModMessages.sendToServer(new RaceC2SPacket(newData.getSelectedRace()));
                        ModMessages.sendToPlayer(new MagicS2CPacket(newData.getSelectedRace()),player);
                    });
                });
            }
            original.getCapability(PlayerMagicProvider.PLAYER_MAGIC).ifPresent(oldData -> {
                event.getEntity().getCapability(PlayerMagicProvider.PLAYER_MAGIC).ifPresent(newData -> {
                    newData.copyFrom(oldData);
                    /*
                    if(newData.getFloatingBlockEntity() != null){
                        BlockPos blockPos = new BlockPos(player.getOnPos().getX(),player.getOnPos().getY()+1,player.getOnPos().getZ());
                        player.level().setBlock(blockPos, newData.getMagicGrabbedBlockState(), 3);
                        BlockEntity blockEntity = player.level().getBlockEntity(blockPos);
                        if (blockEntity instanceof Container container) {
                            player.getCapability(PlayerMagicProvider.PLAYER_MAGIC).ifPresent(magic -> {
                                CompoundTag savedInventoryTag = magic.getSavedBlockGrabbedInventory();
                                loadInventoryFromNBT(container, savedInventoryTag);
                            });
                        }
                        newData.getFloatingBlockEntity().remove(Entity.RemovalReason.KILLED);
                    }

                     */
                    newData.setMagicGrabble(false);
                    /*
                    if(newData.getMagicGrabbedBlockState() != null){
                        newData.getFloatingBlockEntity().remove(Entity.RemovalReason.KILLED);
                    }

                     */

                });
            });
            original.invalidateCaps();
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
    }
/*
    @SubscribeEvent
    public void onPlayerBreakBlock(BlockEvent.BreakEvent event){
        Player player = event.getPlayer();
        player.getCapability(PlayerRaceDataProvider.PLAYER_RACE_DATA).ifPresent(races -> {
            boolean isMagicHotbarActive = races.getIsMagicHotbarActive();
            if(isMagicHotbarActive){
                event.setCanceled(true);
            }
        });
    }


 */
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
                data.setMagicHotbar(false);
                ModMessages.sendToPlayer(new IsMagicHotbarActiveSyncS2CPacket(), player);
                CompoundTag nbt = new CompoundTag();
                data.saveNBTData(nbt);
                player.getPersistentData().put("server_player_race_data", nbt);
            });
            player.getCapability(PlayerMagicProvider.PLAYER_MAGIC).ifPresent(data -> {
                if(data.getFloatingBlockEntity() != null){
                    BlockPos blockPos = new BlockPos(player.getOnPos().getX(),player.getOnPos().getY()+1,player.getOnPos().getZ());
                    player.level().setBlock(blockPos, data.getMagicGrabbedBlockState(), 3);
                    BlockEntity blockEntity = player.level().getBlockEntity(blockPos);
                    if (blockEntity instanceof Container container) {
                        player.getCapability(PlayerMagicProvider.PLAYER_MAGIC).ifPresent(magic -> {
                            CompoundTag savedInventoryTag = magic.getSavedBlockGrabbedInventory();
                            if(magic.getSavedBlockGrabbedInventory() != null){
                                loadInventoryFromNBT(container, savedInventoryTag);
                                magic.saveBlockGrabbedInventory(null);
                            }
                        });
                    }
                    data.getFloatingBlockEntity().remove(Entity.RemovalReason.KILLED);
                }
                data.setMagicGrabble(false);
                CompoundTag nbt = new CompoundTag();
                data.saveNBTData(nbt);
                player.getPersistentData().put(CallOfEquestria.MOD_ID, nbt);
            });
        }
    }
    public static void loadInventoryFromNBT(Container container, CompoundTag tag) {
        NonNullList<ItemStack> inventory = NonNullList.withSize(container.getContainerSize(), ItemStack.EMPTY);
        ContainerHelper.loadAllItems(tag, inventory);
        for (int i = 0; i < container.getContainerSize(); i++) {
            container.setItem(i, inventory.get(i));
        }
    }

    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        if (!event.getEntity().level().isClientSide && event.getEntity() instanceof ServerPlayer) {

            ClientRenderHotbar.set(false);
            ServerPlayer player = (ServerPlayer) event.getEntity();

            player.getCapability(PlayerRaceDataProvider.PLAYER_RACE_DATA).ifPresent(data -> {
                data.setMagicHotbar(false);
                ModMessages.sendToPlayer(new IsMagicHotbarActiveSyncS2CPacket(), player);
                CompoundTag nbt = player.getPersistentData().getCompound("server_player_race_data");
                data.loadNBTData(nbt);
                ModMessages.sendToPlayer(new RaceSyncS2CPacket(data.getSelectedRace()), player);
            });
            player.getCapability(PlayerMagicProvider.PLAYER_MAGIC).ifPresent(data -> {
                CompoundTag nbt = player.getPersistentData().getCompound(CallOfEquestria.MOD_ID);
                data.loadNBTData(nbt);
            });
        }
    }

}
