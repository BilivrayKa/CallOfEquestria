package net.bilivrayka.callofequestria.networking.packet.spell;

import com.mojang.logging.LogUtils;
import net.bilivrayka.callofequestria.CallOfEquestria;
import net.bilivrayka.callofequestria.entity.custom.FloatingBlockEntity;
import net.bilivrayka.callofequestria.entity.custom.FloatingBlockRenderer;
import net.bilivrayka.callofequestria.providers.BlockGrabData;
import net.bilivrayka.callofequestria.providers.PlayerMagic;
import net.bilivrayka.callofequestria.providers.PlayerMagicProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.network.NetworkEvent;
import org.apache.logging.log4j.core.jmx.Server;
import org.slf4j.Logger;

import java.util.function.Supplier;

public class BlockGrabC2SPacket {
    //public static final Logger LOGGER = LogUtils.getLogger();
    private static BlockState savedBlockState;
    private static boolean isMagicBlockGrabbed;
    private static FloatingBlockEntity entity;
    private static FloatingBlockEntity floatingBlockEntity;
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
            BlockHitResult hitResult = player.level().clip(new ClipContext(
                    player.getEyePosition(),
                    player.getEyePosition().add(player.getLookAngle().scale(5.0)),
                    ClipContext.Block.OUTLINE,
                    ClipContext.Fluid.NONE,
                    player
            ));
            BlockPos blockPos = hitResult.getBlockPos();
            BlockState blockState = player.level().getBlockState(blockPos);
            player.getCapability(PlayerMagicProvider.PLAYER_MAGIC).ifPresent(magic -> {
                savedBlockState = magic.getMagicGrabbedBlockState();
                isMagicBlockGrabbed = magic.isBlockGrabbed();
            });
            //BlockGrabData.set(player);
            if (!isMagicBlockGrabbed && !blockState.isAir()) {
                FloatingBlockEntity entity = new FloatingBlockEntity(world, blockPos, blockState, player);
                player.getCapability(PlayerMagicProvider.PLAYER_MAGIC).ifPresent(magic -> {
                    magic.changeMagicGrabbedBlockState(blockState);
                    magic.setMagicGrabble(true);
                    magic.setFloatingBlockEntity(entity);
                });
                world.setBlock(blockPos, Blocks.AIR.defaultBlockState(), 3);
                world.addFreshEntity(entity);
                //BlockGrabData.toggle();
                //BlockGrabData.set(blockState);
                //BlockGrabData.set(entity);
            } else if(isMagicBlockGrabbed) {
                world.setBlock(blockPos.above(), savedBlockState, 3);
                //BlockGrabData.getFloatingEntity().remove(Entity.RemovalReason.KILLED);
                player.getCapability(PlayerMagicProvider.PLAYER_MAGIC).ifPresent(magic -> {
                    magic.setMagicGrabble(false);
                    magic.getFloatingBlockEntity().remove(Entity.RemovalReason.KILLED);
                });
                //BlockGrabData.toggle();
            }

        });
        context.get().setPacketHandled(true);
    }
}
