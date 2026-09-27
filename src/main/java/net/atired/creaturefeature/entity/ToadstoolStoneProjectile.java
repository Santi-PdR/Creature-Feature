package net.atired.creaturefeature.entity;

import net.atired.creaturefeature.init.CFEntityInit;
import net.minecraft.world.entity.EntityType;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

/** A server-authoritative, colliding rune-stone projectile thrown by Toadstool. */
public class ToadstoolStoneProjectile extends ThrowableProjectile {
    public ToadstoolStoneProjectile(EntityType<? extends ToadstoolStoneProjectile> type, Level level) {
        super(type, level);
    }

    public ToadstoolStoneProjectile(Level level, LivingEntity owner) {
        super(CFEntityInit.TOADSTOOL_STONE.get(), owner, level);
    }

    @Override
    protected void onHit(HitResult result) {
        super.onHit(result);
        if (!level().isClientSide) {
            discard();
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        if (!level().isClientSide && result.getEntity() != getOwner()) {
            result.getEntity().hurt(damageSources().flyIntoWall(), 4.0F);
            Vec3 knockback = result.getEntity().position().subtract(position()).normalize().scale(0.85).add(0.0, 0.2, 0.0);
            result.getEntity().push(knockback.x, knockback.y, knockback.z);
            discard();
        }
    }

    @Override
    protected void defineSynchedData() {
    }

    @Override
    public void tick() {
        super.tick();
        if (tickCount > 80) {
            discard();
        }
    }
}
