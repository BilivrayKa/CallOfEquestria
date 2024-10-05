package net.bilivrayka.callofequestria.providers;

import net.bilivrayka.callofequestria.entity.custom.BlockUtils;
import net.bilivrayka.callofequestria.entity.custom.FloatingBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.UUID;


public class PlayerMagic {
    private int magic;
    private int[] cooldowns = new int[9];
    private BlockState magicGrabbedBlockState;
    private FloatingBlockEntity floatingBlockEntity;
    private CompoundTag savedBlockGrabbedInventory;
    private boolean isBlockGrabbed = false;
    private final int MIN_MAGIC = 0;
    private final int MAX_MAGIC = 10;

    public int getMagic() {
        return magic;
    }
    public BlockState getMagicGrabbedBlockState(){
        return magicGrabbedBlockState;
    }
    public int getCooldowns(int i) {
        return cooldowns[i];
    }
    public FloatingBlockEntity getFloatingBlockEntity() {
        return floatingBlockEntity;
    }
    public CompoundTag getSavedBlockGrabbedInventory() {
        return savedBlockGrabbedInventory;
    }
    public boolean isBlockGrabbed(){
        return isBlockGrabbed;
    }
    public void addMagic(int add) {
        this.magic = Math.min(magic + add, MAX_MAGIC);
    }
    public void subMagic(int sub) {
        this.magic = Math.max(magic - sub, MIN_MAGIC);
    }
    public void changeMagicGrabbedBlockState(BlockState blockState) {
        this.magicGrabbedBlockState = blockState;
    }
    public void setMagicGrabble(boolean isBlockGrabbed){
        this.isBlockGrabbed = isBlockGrabbed;
    }
    public void setCooldowns(int slot, int tick) {
        this.cooldowns[slot] = tick;
    }
    public void setFloatingBlockEntity(FloatingBlockEntity floatingBlockEntity){
        this.floatingBlockEntity = floatingBlockEntity;
    }
    public void saveBlockGrabbedInventory(CompoundTag savedBlockGrabbedInventory){
        this.savedBlockGrabbedInventory = savedBlockGrabbedInventory;
    }
    public void doCooldowns() {
        for (int i = 0; i < this.cooldowns.length; i++) {
            if (this.cooldowns[i] > 0) {
                this.cooldowns[i]--;
            }
        }
    }

    public void copyFrom(PlayerMagic source) {
        this.magic = source.magic;
        this.magicGrabbedBlockState = source.magicGrabbedBlockState;
        this.cooldowns = source.cooldowns;
        this.isBlockGrabbed = source.isBlockGrabbed;
        this.floatingBlockEntity = source.floatingBlockEntity;
        this.savedBlockGrabbedInventory = source.savedBlockGrabbedInventory;
    }

    public void saveNBTData(CompoundTag nbt) {
        nbt.putInt("magic", magic);
        if(magicGrabbedBlockState != null){nbt.putString("blockState", magicGrabbedBlockState.getBlock().builtInRegistryHolder().key().location().toString());}
        nbt.putIntArray("cooldowns", cooldowns);
        nbt.putBoolean("grabbed", isBlockGrabbed);
        /*
        if(floatingBlockEntity != null){
            nbt.putString("floatingBlockEntity", floatingBlockEntity.getStringUUID());
        }

         */
        if(savedBlockGrabbedInventory != null){
            nbt.put("savedBlockGrabbedInventory", savedBlockGrabbedInventory);
        }
    }

    public void loadNBTData(CompoundTag nbt) {
        magic = nbt.getInt("magic");
        magicGrabbedBlockState = BlockUtils.getBlockStateFromString(nbt.getString("blockState"));
        cooldowns = nbt.getIntArray("cooldowns");
        isBlockGrabbed = nbt.getBoolean("grabbed");
        //floatingBlockEntity = nbt.getString("floatingBlockEntity");
        savedBlockGrabbedInventory = nbt.getCompound("savedBlockGrabbedInventory");
    }

}
