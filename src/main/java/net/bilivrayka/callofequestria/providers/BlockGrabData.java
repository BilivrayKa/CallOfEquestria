package net.bilivrayka.callofequestria.providers;

import net.bilivrayka.callofequestria.CallOfEquestria;
import net.bilivrayka.callofequestria.entity.custom.FloatingBlockEntity;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = CallOfEquestria.MOD_ID, value = Dist.CLIENT)
public class BlockGrabData {
    private static boolean isBlockPickedUp;
    private static FallingBlockEntity fallingBlock = null;
    private static Player player;
    private static BlockState blockState;
    private static FloatingBlockEntity entity;

    public static void toggle() {
        BlockGrabData.isBlockPickedUp = !isBlockPickedUp;
    }

    public static boolean set(boolean isBlockPickedUp) {
        return BlockGrabData.isBlockPickedUp = isBlockPickedUp;
    }
    public static FallingBlockEntity set(FallingBlockEntity fallingBlock) {
        return BlockGrabData.fallingBlock = fallingBlock;
    }
    public static Player set(Player player) {
        return BlockGrabData.player = player;
    }
    public static BlockState set(BlockState blockState) {
        return BlockGrabData.blockState = blockState;
    }
    public static FloatingBlockEntity set(FloatingBlockEntity entity) {
        return BlockGrabData.entity = entity;
    }

    public static boolean get() {
        return isBlockPickedUp;
    }
    public static BlockState getBlockState() {
        return blockState;
    }
    public static Player getPlayer() {
        return player;
    }
    public static FloatingBlockEntity getFloatingEntity() {
        return entity;
    }
    public static FallingBlockEntity getFallingBlockEntity() {
        return fallingBlock;
    }
}
