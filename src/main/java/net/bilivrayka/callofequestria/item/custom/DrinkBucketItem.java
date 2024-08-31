package net.bilivrayka.callofequestria.item.custom;

import net.bilivrayka.callofequestria.block.ModBlocks;
import net.bilivrayka.callofequestria.fluid.ModFluids;
import net.bilivrayka.callofequestria.item.ModItems;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public class DrinkBucketItem extends Item {

    protected final Fluid fluid;

    public DrinkBucketItem(Fluid fluid, Properties pProperties) {
        super(pProperties);
        this.fluid = fluid;
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level world = context.getLevel();
        BlockHitResult hitResult = getHitResult(world, context.getPlayer());
        FluidState fluidState = world.getFluidState(hitResult.getBlockPos());
        ItemStack pStack = new ItemStack(ModItems.APPLE_VODKA.get());
        if(fluid == ModFluids.SOURCE_APPLE_JUICE.get()){
            pStack = new ItemStack(ModItems.MUG_APPLE.get());
        }
        if (hitResult == null) {
            return InteractionResult.PASS;
        }

        if (context.getPlayer() instanceof ServerPlayer player){
            if(fluidState.getType() == fluid.defaultFluidState().getType()){
                CriteriaTriggers.CONSUME_ITEM.trigger(player, context.getItemInHand());
                player.getItemInHand(player.getUsedItemHand()).shrink(1);
                player.getInventory().add(pStack);
                world.setBlock(hitResult.getBlockPos(), Blocks.AIR.defaultBlockState(),3);
                world.updateNeighborsAt(hitResult.getBlockPos(), Blocks.AIR);
                if (!player.getInventory().add(pStack)) {
                    player.drop(pStack, false);
                }
            }
        }

        return super.useOn(context);
    }

    private BlockHitResult getHitResult(Level world, Player player) {
        Vec3 eyePosition = player.getEyePosition(1.0F);
        Vec3 lookDirection = player.getViewVector(1.0F);
        Vec3 targetPosition = eyePosition.add(lookDirection.x * 5.0D, lookDirection.y * 5.0D, lookDirection.z * 5.0D);

        // Cast a ray to find where it intersects a block
        return world.clip(new ClipContext(eyePosition, targetPosition, ClipContext.Block.OUTLINE, ClipContext.Fluid.SOURCE_ONLY, player));
    }
}
