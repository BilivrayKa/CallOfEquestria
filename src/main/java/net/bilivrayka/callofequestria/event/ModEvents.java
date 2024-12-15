package net.bilivrayka.callofequestria.event;

import com.mojang.logging.LogUtils;
import net.bilivrayka.callofequestria.CallOfEquestria;
import net.bilivrayka.callofequestria.networking.packet.*;
import net.bilivrayka.callofequestria.data.*;
import net.bilivrayka.callofequestria.networking.ModMessages;
import net.bilivrayka.callofequestria.util.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.item.*;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CandleBlock;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.entity.BeehiveBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.entity.EntityTravelToDimensionEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
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
import org.checkerframework.checker.units.qual.A;
import org.slf4j.Logger;

import java.util.*;

@Mod.EventBusSubscriber(modid = CallOfEquestria.MOD_ID)
public class ModEvents {
    public static final Logger LOGGER = LogUtils.getLogger();
    private static final Map<ServerPlayer, Integer> messageCounters = new HashMap<>();
    private static final int MESSAGE_THRESHOLD = 25;

    @SubscribeEvent
    public static void onAttachCapabilitiesPlayer(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player) {
            if (!event.getObject().getCapability(PlayerMagicDataProvider.PLAYER_MAGIC).isPresent()) {
                event.addCapability(new ResourceLocation(CallOfEquestria.MOD_ID, "properties"), new PlayerMagicDataProvider());

            }
        }
    }

    @SubscribeEvent
    public static void onPlayerCloned(PlayerEvent.Clone event) {
        if(event.getOriginal().level().isClientSide)
            return;
        if (event.isWasDeath()) {
            Player original = event.getOriginal();
            original.reviveCaps();
            original.getCapability(PlayerMagicDataProvider.PLAYER_MAGIC).ifPresent(oldData -> {
                event.getEntity().getCapability(PlayerMagicDataProvider.PLAYER_MAGIC).ifPresent(newData -> {
                    newData.copyFrom(oldData);
                });
            });
            original.invalidateCaps();
        }
    }

    @SubscribeEvent
    public static void onPlayerChangeDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
    }

    @SubscribeEvent
    public static void onEntityChangeDimension(EntityTravelToDimensionEvent event) {

    }

    @SubscribeEvent
    public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
        event.register(PlayerMagicData.class);
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {

    }

    @SubscribeEvent
    public static void onItemPickup(PlayerEvent.ItemPickupEvent event) {
        Player player = event.getEntity();
        ItemStack item = event.getStack();

        if (item.getItem() instanceof PotionItem) {
            Potion potion = PotionUtils.getPotion(item);

            if (potion == Potions.REGENERATION || potion == Potions.HEALING) {
                ResourceLocation NURSE_REDHEART_AD = new ResourceLocation(CallOfEquestria.MOD_ID, "nurse_redheart");
                ModMessages.sendToServer(new AdvancementC2SPacket(NURSE_REDHEART_AD));
                }
        }

        if (item.getItem() instanceof RecordItem){
            ResourceLocation DJ_PON3_AD = new ResourceLocation(CallOfEquestria.MOD_ID, "dj_pon3");
            ModMessages.sendToServer(new AdvancementC2SPacket(DJ_PON3_AD));
        }
    }

    @SubscribeEvent
    public static void onBlockPlace(BlockEvent.EntityPlaceEvent event) {
        if (!(event.getEntity() instanceof Player player)) {
            return;
        }

        if (event.getPlacedBlock().getBlock() instanceof CandleBlock) {
            ResourceLocation ALOE_VERA = new ResourceLocation(CallOfEquestria.MOD_ID, "aloe_vera");
            ModMessages.sendToServer(new AdvancementC2SPacket(ALOE_VERA));
        }
    }

    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        Player player = event.getPlayer();
        BlockState state = event.getState();
        BlockPos pos = event.getPos();
        Level level = player.level();

        if (state.is(Blocks.SMOOTH_BASALT) || state.is(Blocks.CALCITE) || state.is(Blocks.AMETHYST_BLOCK)) {
            if (isGeodeStructure(level, pos)) {
                ResourceLocation RARITY_AD = new ResourceLocation(CallOfEquestria.MOD_ID, "rarity");
                ModMessages.sendToServer(new AdvancementC2SPacket(RARITY_AD));
            }
        }
    }

    private static boolean isGeodeStructure(Level level, BlockPos pos) {
        int basaltCount = 0;
        int calciteCount = 0;
        int amethystCount = 0;

        for (BlockPos nearby : BlockPos.betweenClosed(pos.offset(-3, -3, -3), pos.offset(3, 3, 3))) {
            BlockState state = level.getBlockState(nearby);
            if (state.is(Blocks.SMOOTH_BASALT)) basaltCount++;
            if (state.is(Blocks.CALCITE)) calciteCount++;
            if (state.is(Blocks.AMETHYST_BLOCK)) amethystCount++;
        }

        return basaltCount >= 10 && calciteCount >= 5 && amethystCount >= 5;
    }

    @SubscribeEvent
    public static void onPlayerDamage(LivingDamageEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) {
            return;
        }
        boolean isCollisionDamage = event.getSource().getMsgId().equals("flyIntoWall");
        if (isCollisionDamage && event.getSource().is(DamageTypes.FALL)) {
            ResourceLocation DERPY_AD = new ResourceLocation(CallOfEquestria.MOD_ID, "derpy");
            ModMessages.sendToServer(new AdvancementC2SPacket(DERPY_AD));
        }
    }

    @SubscribeEvent
    public static void onServerChat(ServerChatEvent event) {
        ServerPlayer player = event.getPlayer();
        int messageCount = messageCounters.getOrDefault(player, 0) + 1;
        String message = event.getMessage().toString().toLowerCase(Locale.ROOT);
        messageCounters.put(player, messageCount);
        if (messageCount >= MESSAGE_THRESHOLD) {
            ResourceLocation MINUETTE_AD = new ResourceLocation(CallOfEquestria.MOD_ID, "minuette");
            ModMessages.sendToServer(new AdvancementC2SPacket(MINUETTE_AD));
        }
        if (message.contains("party")) {
            ResourceLocation PINKIE_PIE_AD = new ResourceLocation(CallOfEquestria.MOD_ID, "pinkie_pie");
            ModMessages.sendToServer(new AdvancementC2SPacket(PINKIE_PIE_AD));
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
    public static void onPlayerBlockInteract(PlayerInteractEvent.RightClickBlock event) {
        Player player = event.getEntity();
        BlockState blockState = event.getLevel().getBlockState(event.getPos());
    }

    @SubscribeEvent
    public static void onPlayerBlockBreak(BlockEvent.BreakEvent event) {
        Player player = event.getPlayer();
    }

    @SubscribeEvent
    public static void onItemSmelted(PlayerEvent.ItemSmeltedEvent event) {
        Player player = event.getEntity();
        ItemStack smeltedItem = event.getSmelting();
    }

    @SubscribeEvent
    public static void onPlayerLoggedOut(PlayerEvent.PlayerLoggedOutEvent event) {
        if (!event.getEntity().level().isClientSide && event.getEntity() instanceof ServerPlayer) {
            ServerPlayer player = (ServerPlayer) event.getEntity();
            player.getCapability(PlayerMagicDataProvider.PLAYER_MAGIC).ifPresent(data -> {
                CompoundTag nbt = new CompoundTag();
                data.saveNBTData(nbt);
                player.getPersistentData().put("properties", nbt);
            });
        }
    }

    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        if (!event.getEntity().level().isClientSide && event.getEntity() instanceof ServerPlayer) {
            ServerPlayer player = (ServerPlayer) event.getEntity();
            player.getCapability(PlayerMagicDataProvider.PLAYER_MAGIC).ifPresent(data -> {
                CompoundTag nbt = player.getPersistentData().getCompound(CallOfEquestria.MOD_ID);
                data.loadNBTData(nbt);
            });
        }
    }

}
