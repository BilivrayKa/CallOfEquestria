package net.bilivrayka.callofequestria.data;


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

public class PlayerMagicDataProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static Capability<PlayerMagicData> PLAYER_MAGIC = CapabilityManager.get(new CapabilityToken<PlayerMagicData>() { });

    private PlayerMagicData magic = null;
    private final LazyOptional<PlayerMagicData> optional = LazyOptional.of(this::createPlayerMagic);

    private PlayerMagicData createPlayerMagic() {
        if(this.magic == null) {
            this.magic = new PlayerMagicData();
        }

        return this.magic;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == PLAYER_MAGIC) {
            return optional.cast();
        }

        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createPlayerMagic().saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createPlayerMagic().loadNBTData(nbt);
    }
}
