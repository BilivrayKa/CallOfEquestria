package net.bilivrayka.callofequestria.data;

import net.minecraft.nbt.CompoundTag;

import java.util.UUID;

public class PlayerMagicData {
    private int magic;
    private final int MAX_MAGIC = 100;
    private UUID curedVillagerId;

    public float getMagic() {
        return magic;
    }

    public UUID getCuredVillagerId() {
        return curedVillagerId;
    }

    public void addMagic(int add) {
        this.magic = Math.min(magic + add, MAX_MAGIC);
    }

    public void setCuredVillagerId(int add) {
        this.magic = Math.min(magic + add, MAX_MAGIC);
    }

    public void copyFrom(PlayerMagicData source) {
        this.magic = source.magic;
    }

    public void saveNBTData(CompoundTag nbt) {
        nbt.putInt("magic", magic);
    }

    public void loadNBTData(CompoundTag nbt) {
        magic = nbt.getInt("magic");
    }

}
