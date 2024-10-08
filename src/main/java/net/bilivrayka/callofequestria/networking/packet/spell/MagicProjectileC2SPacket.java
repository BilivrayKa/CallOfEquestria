package net.bilivrayka.callofequestria.networking.packet.spell;

import net.bilivrayka.callofequestria.entity.ModEntities;
import net.bilivrayka.callofequestria.entity.custom.MagicProjectile;
import net.bilivrayka.callofequestria.networking.ModMessages;
import net.bilivrayka.callofequestria.networking.packet.MagicSpellUsedS2CPacket;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class MagicProjectileC2SPacket {
    private final Vec3 position;
    private final Vec3 direction;

    public MagicProjectileC2SPacket(Vec3 position, Vec3 direction) {
        this.position = position;
        this.direction = direction;
    }

    public MagicProjectileC2SPacket(FriendlyByteBuf buf) {
        this.position = new Vec3(buf.readDouble(), buf.readDouble(), buf.readDouble());
        this.direction = new Vec3(buf.readDouble(), buf.readDouble(), buf.readDouble());
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeDouble(position.x);
        buf.writeDouble(position.y);
        buf.writeDouble(position.z);
        buf.writeDouble(direction.x);
        buf.writeDouble(direction.y);
        buf.writeDouble(direction.z);
    }

    // Обработка пакета на сервере
    public void handle(Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            ServerPlayer player = context.get().getSender();
            ServerLevel world = context.get().getSender().serverLevel();

            MagicProjectile projectile = new MagicProjectile(ModEntities.MAGIC_PROJECTILE.get(), world);
            projectile.setPos(player.getX(), player.getY() + player.getEyeHeight(), player.getZ());
            Vec3 lookVec = player.getLookAngle();
            projectile.shoot(lookVec.x, lookVec.y, lookVec.z, 3F, 0.1F);
            world.addFreshEntity(projectile);
            player.level().playSound(null, player.getOnPos(), SoundEvents.AMETHYST_BLOCK_RESONATE, SoundSource.PLAYERS,1,1);
            world.sendParticles(ParticleTypes.WITCH,
                    projectile.getX(), projectile.getY() - 0.5f, projectile.getZ(),
                    10, 0.1f, 0.1f, 0.1f, 0.1f);
            ModMessages.sendToPlayer(new MagicSpellUsedS2CPacket(1,20,0), player);
        });
        context.get().setPacketHandled(true);
    }
}
