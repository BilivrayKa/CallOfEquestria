package net.bilivrayka.callofequestria.networking.packet.spell;

import com.mojang.logging.LogUtils;
import net.bilivrayka.callofequestria.CallOfEquestria;
import net.bilivrayka.callofequestria.data.PlayerMagicDataProvider;
import net.bilivrayka.callofequestria.entity.custom.FloatingBlockEntity;
import net.bilivrayka.callofequestria.data.VillagerProfessionHandler;
import net.bilivrayka.callofequestria.networking.ModMessages;
import net.bilivrayka.callofequestria.networking.packet.MagicSpellUsedS2CPacket;
import net.minecraft.advancements.Advancement;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.ServerAdvancementManager;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkEvent;
import org.slf4j.Logger;

import java.util.function.Supplier;

public class BlockGrabC2SPacket {
    public static final Logger LOGGER = LogUtils.getLogger();
    private static BlockState savedBlockState;
    private static boolean isMagicBlockGrabbed;
    private static FloatingBlockEntity entity;
    private static FloatingBlockEntity floatingBlockEntity;
    private static boolean isEmptyBlockRelative;
    private static boolean isEmptyBlock;
    private static boolean isAir;
    private static CompoundTag inventoryTag;
    private static ItemStack[] ironTools = {
            new ItemStack(Items.IRON_PICKAXE),
            new ItemStack(Items.IRON_AXE),
            new ItemStack(Items.IRON_SHOVEL),
            new ItemStack(Items.IRON_HOE),
            new ItemStack(Items.IRON_SWORD),
            new ItemStack(Items.SHEARS)
    };
    public BlockGrabC2SPacket() {

    }

    public static void encode(BlockGrabC2SPacket msg, FriendlyByteBuf buffer) {
    }

    public static BlockGrabC2SPacket decode(FriendlyByteBuf buffer) {
        return new BlockGrabC2SPacket();
    }

    public static void handle(BlockGrabC2SPacket msg, Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            ServerPlayer player = context.get().getSender();
            Level world = context.get().getSender().level();
            BlockHitResult hitResult = player.level().clip(new ClipContext(player.getEyePosition(),
                    player.getEyePosition().add(player.getLookAngle().scale(5.0)), ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, player
            ));
            BlockPos blockPos = hitResult.getBlockPos();
            BlockState blockState = player.level().getBlockState(blockPos);
            isAir = blockState.isAir();
            isEmptyBlockRelative = player.level().isEmptyBlock(blockPos.relative(hitResult.getDirection()));
            isEmptyBlock = player.level().isEmptyBlock(blockPos);
            player.getCapability(PlayerMagicDataProvider.PLAYER_MAGIC).ifPresent(magic -> {
                savedBlockState = magic.getMagicGrabbedBlockState();
                isMagicBlockGrabbed = magic.isBlockGrabbed();
            });
            if (!isMagicBlockGrabbed && !isAir && isMineable(blockState)) {
                if (blockState.hasBlockEntity()) {
                    BlockEntity blockEntity = player.level().getBlockEntity(blockPos);
                    if (blockEntity instanceof Container container) {
                        player.getCapability(PlayerMagicDataProvider.PLAYER_MAGIC).ifPresent(magic -> {
                            inventoryTag = saveInventoryToNBT(container);
                            container.clearContent();
                            LOGGER.debug(inventoryTag.toString());
                            magic.saveBlockGrabbedInventory(inventoryTag);
                        });
                    }
                }
                entity = new FloatingBlockEntity(world, blockPos, blockState, player);
                onBreakingSomeVillagerLive(blockPos,blockState,world,player);
                player.getCapability(PlayerMagicDataProvider.PLAYER_MAGIC).ifPresent(magic -> {
                    magic.changeMagicGrabbedBlockState(blockState);
                    magic.setMagicGrabble(true);
                    magic.setFloatingBlockEntity(entity);
                });
                ModMessages.sendToPlayer(new MagicSpellUsedS2CPacket(3,20,3), player);
                world.setBlock(blockPos, Blocks.AIR.defaultBlockState(), 3);
                world.addFreshEntity(entity);
                player.level().playSound(null, blockPos.relative(hitResult.getDirection()
                ), blockState.getSoundType().getBreakSound(), SoundSource.BLOCKS, 1.0F, 1);
            } else if(isMagicBlockGrabbed && isEmptyBlockRelative && !isEmptyBlock) {
                world.setBlock(blockPos.relative(hitResult.getDirection()), savedBlockState, 3);
                if (player.level().getBlockState(blockPos.relative(hitResult.getDirection())).hasBlockEntity()) {
                    BlockEntity blockEntity = world.getBlockEntity(blockPos.relative(hitResult.getDirection()));
                    if (blockEntity instanceof Container container) {
                        player.getCapability(PlayerMagicDataProvider.PLAYER_MAGIC).ifPresent(magic -> {
                            CompoundTag savedInventoryTag = magic.getSavedBlockGrabbedInventory();
                            loadInventoryFromNBT(container, savedInventoryTag);
                            magic.saveBlockGrabbedInventory(null);
                        });
                    }
                }
                player.getCapability(PlayerMagicDataProvider.PLAYER_MAGIC).ifPresent(magic -> {
                    magic.setMagicGrabble(false);
                    magic.getFloatingBlockEntity().remove(Entity.RemovalReason.KILLED);
                    magic.setFloatingBlockEntity(null);
                    ModMessages.sendToPlayer(new MagicSpellUsedS2CPacket(3,20,3), player);
                    BlockPos placeBlockPos = blockPos.relative(hitResult.getDirection());
                    player.level().playSound(null, blockPos.relative(hitResult.getDirection()
                    ), player.level().getBlockState(placeBlockPos).getSoundType().getPlaceSound(), SoundSource.BLOCKS, 1.0F, 1);
                });
            }
            player.getCapability(PlayerMagicDataProvider.PLAYER_MAGIC).ifPresent(magic -> {
                CompoundTag nbt = new CompoundTag();
                magic.saveNBTData(nbt);
                player.getPersistentData().put("properties", nbt);
            });
        });
        context.get().setPacketHandled(true);
    }

    public static boolean isMineable(BlockState blockState){
        for (ItemStack tool : ironTools) {
            if (tool.isCorrectToolForDrops(blockState) && !isMultiBlock(blockState)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isMultiBlock(BlockState blockState) {
        Block block = blockState.getBlock();

        if (block instanceof DoorBlock) {
            return true;
        }

        if (block instanceof BedBlock) {
            return true;
        }
        /*
        if (block instanceof ChestBlock) {
            return true;
        }

         */

        return false;
    }

    public static CompoundTag saveInventoryToNBT(Container container) {
        CompoundTag tag = new CompoundTag();
        NonNullList<ItemStack> inventory = NonNullList.withSize(container.getContainerSize(), ItemStack.EMPTY);
        for (int i = 0; i < container.getContainerSize(); i++) {
            inventory.set(i, container.getItem(i));
        }
        ContainerHelper.saveAllItems(tag, inventory);
        return tag;
    }

    public static void loadInventoryFromNBT(Container container, CompoundTag tag) {
        NonNullList<ItemStack> inventory = NonNullList.withSize(container.getContainerSize(), ItemStack.EMPTY);
        ContainerHelper.loadAllItems(tag, inventory);
        for (int i = 0; i < container.getContainerSize(); i++) {
            container.setItem(i, inventory.get(i));
        }
    }

    public static void onBreakingSomeVillagerLive(BlockPos blockPos,BlockState state, Level level, ServerPlayer player) {
        AABB aabb = new AABB(blockPos).inflate(5.0D);
        if (VillagerProfessionHandler.isProfessionBlock(state.getBlock())) {
            level.getEntitiesOfClass(Villager.class, aabb).forEach(villager -> {
                Block workStation = VillagerProfessionHandler.getBlockByProfession(villager.getVillagerData().getProfession());
                boolean hasProfession = workStation == state.getBlock();
                if (hasProfession) {
                    ResourceLocation STARLIGHT_AD = new ResourceLocation(CallOfEquestria.MOD_ID, "starlight");
                    MinecraftServer server = level.getServer();
                    ServerAdvancementManager advancementManager = server.getAdvancements();
                    var playerAdvancements = player.getAdvancements();
                    Advancement advancement = advancementManager.getAdvancement(STARLIGHT_AD);
                    playerAdvancements.award(advancement, "requirement");
                }
            });
        }
    }
}
