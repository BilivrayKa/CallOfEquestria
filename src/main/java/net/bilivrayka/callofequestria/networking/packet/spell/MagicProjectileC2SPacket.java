package net.bilivrayka.callofequestria.networking.packet.spell;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
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
            //ServerPlayer player = context.get().getSender();
            ServerLevel world = context.get().getSender().serverLevel();

            // Создаём и запускаем снаряд на сервере
            MagicProjectile projectile = new MagicProjectile(EntityType.ARROW, world);
            projectile.setPos(position.x, position.y, position.z);
            projectile.setDeltaMovement(direction.normalize().scale(1.5));

            world.addFreshEntity(projectile);
        });
        context.get().setPacketHandled(true);
    }
}
