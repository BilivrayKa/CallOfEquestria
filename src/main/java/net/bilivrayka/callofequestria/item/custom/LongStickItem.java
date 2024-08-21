package net.bilivrayka.callofequestria.item.custom;


import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.Random;

public class LongStickItem extends Item {
    private static final int MAX_DURABILITY = 3;

    public LongStickItem(Properties pProperties) {
        super(pProperties.durability(MAX_DURABILITY));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        if (pPlayer.onGround()) {
            Vec3 lookDirection = pPlayer.getLookAngle();
            double horizontalSpeed = 1.5;
            double verticalSpeed = 0.7;

            Vec3 dashVelocity = new Vec3(lookDirection.x * horizontalSpeed, verticalSpeed, lookDirection.z * horizontalSpeed);
            pPlayer.setDeltaMovement(dashVelocity);

            pLevel.playSound(null, new BlockPos(pPlayer.blockPosition()), SoundEvents.CAMEL_DASH, SoundSource.PLAYERS, 1.0F, 1.0F);

            pPlayer.getCooldowns().addCooldown(this, 300);

            Random random = new Random();
            ItemStack stack = pPlayer.getItemInHand(pUsedHand);
            if (random.nextDouble() < 0.33) {
                stack.hurtAndBreak(1, pPlayer, (p) -> {
                    p.broadcastBreakEvent(pUsedHand);
                });
            }

            return InteractionResultHolder.sidedSuccess(pPlayer.getItemInHand(pUsedHand), pLevel.isClientSide());
        }
        return super.use(pLevel, pPlayer, pUsedHand);
    }
}
