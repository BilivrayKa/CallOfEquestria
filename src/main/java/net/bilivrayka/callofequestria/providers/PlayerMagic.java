package net.bilivrayka.callofequestria.providers;

import net.bilivrayka.callofequestria.entity.custom.BlockUtils;
import net.bilivrayka.callofequestria.entity.custom.FloatingBlockEntity;
import net.minecraft.BlockUtil;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;


public class PlayerMagic {
    private int magic;
    private int[] cooldowns = new int[9];
    private BlockState magicGrabbedBlockState;
    private FloatingBlockEntity floatingBlockEntity;
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
    }

    public void saveNBTData(CompoundTag nbt) {
        nbt.putInt("magic", magic);
        nbt.putString("blockState", magicGrabbedBlockState.getBlock().builtInRegistryHolder().key().location().toString());
        nbt.putIntArray("cooldowns", cooldowns);
        nbt.putBoolean("grabbed", isBlockGrabbed);
    }

    public void loadNBTData(CompoundTag nbt) {
        magic = nbt.getInt("magic");
        magicGrabbedBlockState = BlockUtils.getBlockStateFromString(nbt.getString("blockState"));
        cooldowns = nbt.getIntArray("cooldowns");
        isBlockGrabbed = nbt.getBoolean("grabbed");
    }

}
