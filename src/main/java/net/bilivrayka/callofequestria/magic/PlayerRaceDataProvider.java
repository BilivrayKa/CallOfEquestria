package net.bilivrayka.callofequestria.magic;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PlayerRaceDataProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {

    public static final Capability<PlayerRaceData> PLAYER_RACE_DATA = CapabilityManager.get(new CapabilityToken<>() {});
    private final LazyOptional<PlayerRaceData> instance = LazyOptional.of(PlayerRaceData::new);

    private PlayerRaceData playerRaceData = null;

    private PlayerRaceData createPlayerRaceData() {
        if(this.playerRaceData == null) {
            this.playerRaceData = new PlayerRaceData();
        }

        return this.playerRaceData;
    }
    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> capability, @Nullable Direction side) {
        if(capability == PLAYER_RACE_DATA) {
            return instance.cast();
        }

        return LazyOptional.empty();
    }
    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createPlayerRaceData().saveNBTData(nbt);
        return nbt;
    }
    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createPlayerRaceData().loadNBTData(nbt);
    }
}
