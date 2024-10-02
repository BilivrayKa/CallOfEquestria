package net.bilivrayka.callofequestria.entity.custom;

import com.mojang.logging.LogUtils;
import net.bilivrayka.callofequestria.entity.ModEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.slf4j.Logger;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class FloatingBlockEntity extends Entity {

    public static final Logger LOGGER = LogUtils.getLogger();
    private BlockState blockState;
    private BlockState savedBlockState;
    private UUID ownerUUID;
    private ServerPlayer player;
    private static final EntityDataAccessor<String> PLAYER = SynchedEntityData.defineId(FloatingBlockEntity.class, EntityDataSerializers.STRING);
    private static final EntityDataAccessor<String> BLOCKSTATE = SynchedEntityData.defineId(FloatingBlockEntity.class, EntityDataSerializers.STRING);
    private Set<String> tags = new HashSet<>();

    public FloatingBlockEntity(EntityType<?> entityType, Level world) {
        super(entityType, world);
        //this.addTag(blockState.getBlock().builtInRegistryHolder().key().location().toString());
    }

    public FloatingBlockEntity(Level world, BlockPos pos, BlockState blockState, ServerPlayer player) {
        this(ModEntities.FLOATING_BLOCK.get(), world);
        this.blockState = blockState;
        this.ownerUUID = player.getUUID();
        setOwnerUUID(player.getUUID().toString());
        setBlockState(blockState.getBlock().builtInRegistryHolder().key().location().toString());

        this.setPos(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
    }

    @Override
    public void tick() {
        super.tick();
        this.addTag(blockState());
        UUID uuid = UUID.fromString(ownerUUID());
        if(this.getServer() != null){
            player = this.getServer().getPlayerList().getPlayer(uuid);
        }
        if (player == null) {
        } else {
            Vec3 lookDirection = player.getLookAngle();

            double distance = 3.0;
            Vec3 playerEyePosition = player.getEyePosition(1.0F);
            Vec3 targetPosition = playerEyePosition.add(lookDirection.scale(distance));

            Vec3 currentPosition = this.position();
            Vec3 moveVector = targetPosition.subtract(currentPosition).scale(0.1);
            this.setPos(currentPosition.add(moveVector));
        }
    }

    public String ownerUUID() {
        return this.entityData.get(PLAYER);
    }

    public void setOwnerUUID(String ownerUUID) {
        this.entityData.set(PLAYER, ownerUUID);
    }

    public String blockState() {
        return this.entityData.get(BLOCKSTATE);
    }

    public void setBlockState(String blockState) {
        this.entityData.set(BLOCKSTATE, blockState);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag tag) {
        if (tag.contains("OwnerUUID")) {
            setOwnerUUID(tag.getString("OwnerUUID"));
        }
        if (tag.contains("BlockState")) {
            setBlockState(tag.getString("BlockState"));
        }
    }
    @Override
    protected void defineSynchedData() {
        this.entityData.define(PLAYER, "");
        this.entityData.define(BLOCKSTATE, "minecraft:sand");
    }


    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {
        tag.putString("OwnerUUID", this.ownerUUID());
        tag.putString("BlockState", this.blockState());
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return new ClientboundAddEntityPacket(this);
    }
}
