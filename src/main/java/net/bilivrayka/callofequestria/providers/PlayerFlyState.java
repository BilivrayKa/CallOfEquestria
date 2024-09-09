package net.bilivrayka.callofequestria.providers;

import net.minecraft.nbt.CompoundTag;

public class PlayerFlyState {

    private boolean isFlying;

    public boolean getFlyState() {
        return isFlying;
    }
    public void changeFlyState() {
        this.isFlying = !isFlying;
    }
    public void trueFlyState() {
        this.isFlying = true;
    }
    public void falseFlyState() {
        this.isFlying = false;
    }

    public void copyFrom(PlayerFlyState source) {
        this.isFlying = source.isFlying;
    }

    public void saveNBTData(CompoundTag nbt) {
        nbt.putBoolean("isFlying", isFlying);
    }

    public void loadNBTData(CompoundTag nbt) {
        isFlying = nbt.getBoolean("isFlying");
    }
}
