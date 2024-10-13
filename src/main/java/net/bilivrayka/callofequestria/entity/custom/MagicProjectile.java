package net.bilivrayka.callofequestria.entity.custom;

import net.bilivrayka.callofequestria.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

import java.util.Objects;
import java.util.Random;

public class MagicProjectile extends ThrowableItemProjectile {
    /*
    private final float initialYaw;
    private final float initialPitch;

     */
    private int lifetime;

    public MagicProjectile(EntityType<? extends ThrowableItemProjectile> type, Level world) {
        super(type, world);
        this.lifetime = 0;
    }

    @Override
    public void tick() {
        super.tick();

        lifetime++;

        if (lifetime > 20 * 60) {
            this.remove(RemovalReason.KILLED);
        }
    }

    @Override
    protected Item getDefaultItem(){
        return ModBlocks.MAGIC_PROJECTILE.get().asItem();
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        if(this.level().isClientSide){
           return;
        } else {
            if (level().getNearestPlayer(result.getEntity(), 20) != null) {
                result.getEntity().hurt(damageSources().playerAttack(level().getNearestPlayer(result.getEntity(), 20)), 5.0F);
            } else {
                result.getEntity().hurt(damageSources().magic(), 7.0F);
            }
            breakProjectile(this.level(),result.getEntity().getOnPos());
        }
        this.discard();
    }

    @Override
    protected void onHitBlock(BlockHitResult pResult) {
        breakProjectile(this.level(),pResult.getBlockPos());
        this.discard();
        super.onHitBlock(pResult);
    }

    private void breakProjectile(Level level, BlockPos blockPos) {
        level.playSound(null, blockPos, SoundEvents.AMETHYST_BLOCK_BREAK, SoundSource.PLAYERS, 1f, 1f);

        int numberOfParticles = 15;
        for (int i = 0; i < numberOfParticles; i++) {
            spawnParticle(blockPos);
        }
    }

    private void spawnParticle(BlockPos position) {
        Random rnd = new Random();
        Vec3 direction = new Vec3(
                (rnd.nextFloat(-4f,4f)),
                (rnd.nextFloat(-4f,4f)),
                (rnd.nextFloat(-4f,4f)));
        level().addParticle(ParticleTypes.WITCH,
                position.getCenter().x, position.getCenter().y, position.getCenter().z,
                direction.x, direction.y, direction.z);
    }
}
