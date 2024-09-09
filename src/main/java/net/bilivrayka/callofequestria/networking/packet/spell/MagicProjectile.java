package net.bilivrayka.callofequestria.networking.packet.spell;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;

public class MagicProjectile extends Arrow {

    public MagicProjectile(EntityType<Arrow> type, Level world) {
        super(type, world);
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);

        if (!this.level().isClientSide) {
            result.getEntity().hurt(damageSources().magic(), 5.0F);
        }

        this.discard();
    }
}
