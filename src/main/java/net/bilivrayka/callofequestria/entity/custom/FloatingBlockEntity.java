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
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.slf4j.Logger;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class FloatingBlockEntity extends Entity {

    private static final EntityDataAccessor<String> PLAYER = SynchedEntityData.defineId(FloatingBlockEntity.class, EntityDataSerializers.STRING);
    private static final EntityDataAccessor<String> BLOCKSTATE = SynchedEntityData.defineId(FloatingBlockEntity.class, EntityDataSerializers.STRING);
    //private static final EntityDataAccessor<CompoundTag> SAVED_INVENTORY = SynchedEntityData.defineId(FloatingBlockEntity.class, EntityDataSerializers.COMPOUND_TAG);
    private ServerPlayer player;
    //private CompoundTag savedInventoryTag;

    public FloatingBlockEntity(EntityType<?> entityType, Level world) {
        super(entityType, world);
    }

    public FloatingBlockEntity(Level world, BlockPos pos, BlockState blockState, ServerPlayer player) {
        this(ModEntities.FLOATING_BLOCK.get(), world);
        /*
        if(savedInventory != null){
            setInventory(savedInventory);
        }

         */
        //this.savedInventoryTag = savedInventory;
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
            return;
        }
        Vec3 lookDirection = player.getLookAngle();
        double distance = 3.0;
        Vec3 playerEyePosition = player.getEyePosition(1.0F);
        Vec3 targetPosition = playerEyePosition.add(lookDirection.scale(distance));
        Vec3 currentPosition = this.position();
        Vec3 moveVector = targetPosition.subtract(currentPosition);
        double maxSpeed = 0.75;
        double smoothFactor = 0.05;
        Vec3 interpolatedMove = moveVector.scale(smoothFactor);
        if (interpolatedMove.length() > maxSpeed) {
            interpolatedMove = interpolatedMove.normalize().scale(maxSpeed);
        }
        this.setDeltaMovement(interpolatedMove);
        this.move(MoverType.PLAYER, this.getDeltaMovement());
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
/*
    public CompoundTag savedInventory() {
        return this.entityData.get(SAVED_INVENTORY);
    }

    public void setInventory(CompoundTag savedInventory) {
        this.entityData.set(SAVED_INVENTORY, savedInventory);
    }

    public CompoundTag getSavedInventoryTag() {
        return savedInventory();
    }

 */

    @Override
    protected void readAdditionalSaveData(CompoundTag tag) {
        if (tag.contains("OwnerUUID")) {
            setOwnerUUID(tag.getString("OwnerUUID"));
        }
        if (tag.contains("BlockState")) {
            setBlockState(tag.getString("BlockState"));
        }
        /*
        if (tag.contains("SavedInventory")) {
            this.savedInventoryTag = tag.getCompound("SavedInventory");
        }

         */
    }
    @Override
    protected void defineSynchedData() {
        this.entityData.define(PLAYER, "");
        this.entityData.define(BLOCKSTATE, "minecraft:sand");
        //this.entityData.define(SAVED_INVENTORY, this.savedInventoryTag);
    }


    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {
        tag.putString("OwnerUUID", this.ownerUUID());
        tag.putString("BlockState", this.blockState());
        /*
        if (this.savedInventoryTag != null) {
            tag.put("SavedInventory", this.savedInventoryTag);
        }

         */
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return new ClientboundAddEntityPacket(this);
    }
}
