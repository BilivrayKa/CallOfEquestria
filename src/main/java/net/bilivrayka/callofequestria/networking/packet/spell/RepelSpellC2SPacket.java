package net.bilivrayka.callofequestria.networking.packet.spell;

import com.mojang.logging.LogUtils;
import net.bilivrayka.callofequestria.networking.ModMessages;
import net.bilivrayka.callofequestria.networking.packet.MagicSpellUsedS2CPacket;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;
import org.slf4j.Logger;

import java.util.function.Supplier;

public class RepelSpellC2SPacket {
    public static final Logger LOGGER = LogUtils.getLogger();
    public RepelSpellC2SPacket() {}

    public static void encode(RepelSpellC2SPacket msg, FriendlyByteBuf buffer) {
    }

    public static RepelSpellC2SPacket decode(FriendlyByteBuf buffer) {
        return new RepelSpellC2SPacket();
    }

    public static void handle(RepelSpellC2SPacket msg, Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            ServerPlayer player = context.get().getSender();
            if (player != null) {
                castRepelSpell(player, 5.0, 2.0);
            }
        });
        context.get().setPacketHandled(true);
    }

    private static void castRepelSpell(ServerPlayer player, double radius, double force) {
        AABB area = new AABB(player.blockPosition()).inflate(radius);
        player.level().getEntitiesOfClass(LivingEntity.class, area).forEach(entity -> {
            if(entity.getUUID() != player.getUUID()) {
                Vec3 entityPosition = entity.position();
                Vec3 direction = entityPosition.subtract(player.position().x,player.position().y - 2,player.position().z).normalize();
                entity.setDeltaMovement(direction);
                entity.hurtMarked = true;
            }
        });
        player.level().getEntitiesOfClass(Projectile.class, area).forEach(entity -> {
            Vec3 entityPosition = entity.position();
            Vec3 direction = entityPosition.subtract(player.position().x, player.getEyeY(), player.position().z).normalize();
            Vec3 playerLook = player.getLookAngle();
            if (entity instanceof Arrow) {
                entity.shoot(playerLook.x, playerLook.y, playerLook.z, 2, 0);
            } else {
                entity.setDeltaMovement(playerLook.scale(2));
            }
        });
        player.level().playSound(null, player.getOnPos(), SoundEvents.GENERIC_EXPLODE, SoundSource.PLAYERS,1,1);
        player.serverLevel().sendParticles(ParticleTypes.EXPLOSION,
                player.position().x, player.position().y + 0.5, player.position().z,
                10,0.4,0.2,0.4,0.3);
        ModMessages.sendToPlayer(new MagicSpellUsedS2CPacket(5,100,2), player);
    }
}
