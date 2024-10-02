package net.bilivrayka.callofequestria.entity.custom;

import net.bilivrayka.callofequestria.block.ModBlocks;
import net.bilivrayka.callofequestria.block.custom.PlushBaseBlock;
import net.bilivrayka.callofequestria.entity.ModEntities;
import net.bilivrayka.callofequestria.item.ModItems;
import net.bilivrayka.callofequestria.item.custom.PlushItem;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

public class MagicProjectile extends ThrowableItemProjectile {
    /*
    private final float initialYaw;
    private final float initialPitch;

     */

    public MagicProjectile(EntityType<? extends ThrowableItemProjectile> type, Level world) {
        super(type, world);
    }

    @Override
    protected Item getDefaultItem(){
        return ModBlocks.MAGIC_PROJECTILE.get().asItem();
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);

        if (!this.level().isClientSide) {
            result.getEntity().hurt(damageSources().magic(), 5.0F);
        }

        this.discard();
    }
    @Override
    protected void onHitBlock(BlockHitResult pResult) {
        this.discard();
        super.onHitBlock(pResult);
    }
}
