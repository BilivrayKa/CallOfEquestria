package net.bilivrayka.callofequestria.networking.packet.spell;

import net.minecraft.client.particle.Particle;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;

import javax.swing.text.html.parser.Entity;
import java.util.List;
import java.util.function.Supplier;

public class RepelSpellC2SPacket {

    public RepelSpellC2SPacket() {}

    public static void encode(RepelSpellC2SPacket msg, FriendlyByteBuf buffer) {
    }

    public static RepelSpellC2SPacket decode(FriendlyByteBuf buffer) {
        return new RepelSpellC2SPacket();
    }

    public static void handle(RepelSpellC2SPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer player = ctx.get().getSender();
            if (player != null) {
                castRepelSpell(player, 5.0, 2.0);
            }
        });
        ctx.get().setPacketHandled(true);
    }

    private static void castRepelSpell(ServerPlayer player, double radius, double force) {
        AABB area = new AABB(player.blockPosition()).inflate(radius);
        player.level().getEntitiesOfClass(LivingEntity.class, area).forEach(entity -> {
            Vec3 entityPosition = entity.position();
            Vec3 direction = entityPosition.subtract(player.position().x,player.position().y - 2,player.position().z).normalize();
            //Vec3 forceVector = direction.multiply();
            entity.setDeltaMovement(direction);
        });
        player.level().playSound(null, player.getOnPos(), SoundEvents.WITHER_BREAK_BLOCK, SoundSource.PLAYERS,1,1);
        player.level().addParticle(ParticleTypes.EXPLOSION,1,1,1,1,1,1);
    }
}
