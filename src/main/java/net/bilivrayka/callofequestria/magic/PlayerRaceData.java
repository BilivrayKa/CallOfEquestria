package net.bilivrayka.callofequestria.magic;

import net.minecraft.nbt.CompoundTag;

public class PlayerRaceData {
    private int selectedRace;

    public void setSelectedRace(int raceIndex) {
        this.selectedRace = raceIndex;
    }

    public int getSelectedRace() {
        return selectedRace;
    }
    public void copyFrom(PlayerRaceData source) {
        this.selectedRace = source.selectedRace;
    }
    public void saveNBTData(CompoundTag nbt) {
        nbt.putInt("SelectedRace", selectedRace);
    }

    public void loadNBTData(CompoundTag nbt) {
        selectedRace = nbt.getInt("SelectedRace");
    }
}