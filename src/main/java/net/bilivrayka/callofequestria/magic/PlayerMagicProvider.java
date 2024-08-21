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

public class PlayerMagicProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static Capability<PlayerMagic> PLAYER_MAGIC = CapabilityManager.get(new CapabilityToken<PlayerMagic>() { });

    private PlayerMagic magic = null;
    private final LazyOptional<PlayerMagic> optional = LazyOptional.of(this::createPlayerMagic);

    private PlayerMagic createPlayerMagic() {
        if(this.magic == null) {
            this.magic = new PlayerMagic();
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
