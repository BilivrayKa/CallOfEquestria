package net.bilivrayka.callofequestria.networking.packet;

import com.mojang.logging.LogUtils;
import net.bilivrayka.callofequestria.data.PlayerMagicDataProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.NetworkEvent;
import org.slf4j.Logger;

import java.util.function.Supplier;

public class GrabbedBlockOnDeathC2SPacket {
    public static final Logger LOGGER = LogUtils.getLogger();

    public GrabbedBlockOnDeathC2SPacket() {
    }

    public GrabbedBlockOnDeathC2SPacket(FriendlyByteBuf buf) {

    }

    public void toBytes(FriendlyByteBuf buf) {

    }

    public boolean handle(Supplier<NetworkEvent.Context> context) {
        NetworkEvent.Context ctx = context.get();
        ctx.enqueueWork(() -> {
            ServerPlayer player = ctx.getSender();
            player.getCapability(PlayerMagicDataProvider.PLAYER_MAGIC).ifPresent(magic -> {
                if(magic.getFloatingBlockEntity() != null){
                    BlockPos blockPos = new BlockPos(player.getOnPos().getX(),player.getOnPos().getY()+1,player.getOnPos().getZ());
                    player.level().setBlock(blockPos, magic.getMagicGrabbedBlockState(), 3);
                    BlockEntity blockEntity = player.level().getBlockEntity(blockPos);
                    if (blockEntity instanceof Container container) {
                        CompoundTag savedInventoryTag = magic.getSavedBlockGrabbedInventory();
                        loadInventoryFromNBT(container, savedInventoryTag);
                    }
                    magic.getFloatingBlockEntity().remove(Entity.RemovalReason.KILLED);
                    magic.setFloatingBlockEntity(null);
                }
            });
        });
        ctx.setPacketHandled(true);
        return true;
    }

    public static void loadInventoryFromNBT(Container container, CompoundTag tag) {
        NonNullList<ItemStack> inventory = NonNullList.withSize(container.getContainerSize(), ItemStack.EMPTY);
        ContainerHelper.loadAllItems(tag, inventory);
        for (int i = 0; i < container.getContainerSize(); i++) {
            container.setItem(i, inventory.get(i));
        }
    }
}
