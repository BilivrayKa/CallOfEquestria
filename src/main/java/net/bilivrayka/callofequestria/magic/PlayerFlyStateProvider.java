package net.bilivrayka.callofequestria.magic;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PlayerFlyStateProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static Capability<PlayerFlyState> PLAYER_FLY_STATE = CapabilityManager.get(new CapabilityToken<PlayerFlyState>() { });

    private PlayerFlyState isFlying = null;
    private final LazyOptional<PlayerFlyState> optional = LazyOptional.of(this::createPlayerFlyState);

    private PlayerFlyState createPlayerFlyState() {
        if(this.isFlying == null) {
            this.isFlying = new PlayerFlyState();
        }

        return this.isFlying;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == PLAYER_FLY_STATE) {
            return optional.cast();
        }

        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createPlayerFlyState().saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createPlayerFlyState().loadNBTData(nbt);
    }
}