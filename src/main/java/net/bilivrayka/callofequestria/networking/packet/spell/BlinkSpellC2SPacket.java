package net.bilivrayka.callofequestria.networking.packet.spell;

import net.bilivrayka.callofequestria.data.PlayerMagicDataProvider;
import net.bilivrayka.callofequestria.networking.ModMessages;
import net.bilivrayka.callofequestria.networking.packet.MagicSpellUsedS2CPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class BlinkSpellC2SPacket {

    public BlinkSpellC2SPacket() {

    }

    public static void encode(BlinkSpellC2SPacket msg, FriendlyByteBuf buffer) {
    }

    public static BlinkSpellC2SPacket decode(FriendlyByteBuf buffer) {
        return new BlinkSpellC2SPacket();
    }

    public static void handle(BlinkSpellC2SPacket msg, Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            teleportPlayer(context.get().getSender());
        });
        context.get().setPacketHandled(true);
    }

    private static void teleportPlayer(ServerPlayer player) {
        Vec3 lookVec = player.getLookAngle();
        Vec3 startPos = player.getEyePosition();
        Vec3 endPos = startPos.add(lookVec.scale(10));

        BlockHitResult hitResult = player.level().clip(new ClipContext(startPos, endPos, ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, player));


        BlockPos targetPos;
        if (hitResult.getType() == HitResult.Type.BLOCK) {
            targetPos = hitResult.getBlockPos().above();
        } else {
            targetPos = new BlockPos((int)Math.floor(endPos.x),(int)Math.floor(endPos.y), (int)Math.floor(endPos.z));
        }
        if (player.level().isEmptyBlock(targetPos) && player.level().isEmptyBlock(targetPos.above())
                && !player.level().isEmptyBlock(targetPos.below()) && player.onGround()) {
            player.serverLevel().sendParticles(ParticleTypes.PORTAL,
                    player.getX(), player.getY() + 0.5f, player.getZ(),
                    10, 0.1f, 0.1f, 0.1f, 0.1f);
            player.teleportTo(targetPos.getX() + 0.5, targetPos.getY(), targetPos.getZ() + 0.5);
            player.level().playSound(null, player.getOnPos(), SoundEvents.ENDERMAN_TELEPORT, SoundSource.PLAYERS,1,1);
            player.serverLevel().sendParticles(ParticleTypes.PORTAL,
                    player.getX(), player.getY() + 0.5f, player.getZ(),
                    10, 0.1f, 0.1f, 0.1f, 0.1f);
            player.getCapability(PlayerMagicDataProvider.PLAYER_MAGIC).ifPresent(magic -> {
                ModMessages.sendToPlayer(new MagicSpellUsedS2CPacket(5,100,1), player);
            });
        }
    }
}
