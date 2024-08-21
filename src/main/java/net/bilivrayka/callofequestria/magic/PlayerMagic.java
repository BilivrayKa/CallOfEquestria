package net.bilivrayka.callofequestria.magic;

import net.minecraft.nbt.CompoundTag;

public class PlayerMagic {
    private int magic;
    private final int MIN_MAGIC = 0;
    private final int MAX_MAGIC = 10;

    public int getMagic() {
        return magic;
    }

    public void addMagic(int add) {
        this.magic = Math.min(magic + add, MAX_MAGIC);
    }

    public void subMagic(int sub) {
        this.magic = Math.max(magic - sub, MIN_MAGIC);
    }

    public void copyFrom(PlayerMagic source) {
        this.magic = source.magic;
    }

    public void saveNBTData(CompoundTag nbt) {
        nbt.putInt("magic", magic);
    }

    public void loadNBTData(CompoundTag nbt) {
        magic = nbt.getInt("magic");
    }
}
