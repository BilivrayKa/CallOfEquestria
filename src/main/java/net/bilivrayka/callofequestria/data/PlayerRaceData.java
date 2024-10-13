package net.bilivrayka.callofequestria.data;

import net.minecraft.nbt.CompoundTag;

public class PlayerRaceData {
    private int selectedRace;
    private boolean isMagicHotbarActive = false;

    public void setSelectedRace(int raceIndex) {
        this.selectedRace = raceIndex;
    }
    public void setMagicHotbar(boolean isMagicHotbarActive) {
        this.isMagicHotbarActive = isMagicHotbarActive;
    }
    public void toogleMagicHotbar() {
        this.isMagicHotbarActive = !isMagicHotbarActive;
    }

    public int getSelectedRace() {
        return selectedRace;
    }
    public boolean getIsMagicHotbarActive(){
        return isMagicHotbarActive;
    }
    public void copyFrom(PlayerRaceData source) {
        this.selectedRace = source.selectedRace;
        this.isMagicHotbarActive = source.isMagicHotbarActive;
    }
    public void saveNBTData(CompoundTag nbt) {
        nbt.putInt("selectedRace", selectedRace);
        nbt.putInt("isMagicHotbarActive", selectedRace);
    }

    public void loadNBTData(CompoundTag nbt) {
       selectedRace = nbt.getInt("selectedRace");
       isMagicHotbarActive = nbt.getBoolean("isMagicHotbarActive");
    }
}