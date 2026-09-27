package net.atired.creaturefeature.entity;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class ToadstoolEntity extends Monster {
    private static final EntityDataAccessor<Boolean> CHARGING = SynchedEntityData.defineId(ToadstoolEntity.class, EntityDataSerializers.BOOLEAN);
    /** Drives the original crouching/uncurling pose. */
    public float lerpedonFours = 1.0F;
    private int throwCooldown = 100;
    private int chargeTicks;

    public ToadstoolEntity(EntityType<? extends Monster> type, Level level) {
        super(type, level);
    }

    @Override
    protected void registerGoals() {
        goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
        goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 1.0));
        goalSelector.addGoal(4, new MeleeAttackGoal(this, 1.0, true));
        targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, false));
        goalSelector.addGoal(1, new AvoidEntityGoal<>(this, Player.class, 12.0F, 1.5, 2.1, player -> chargeTicks > 0));
        super.registerGoals();
    }

    @Override
    public void tick() {
        super.tick();
        if (level().isClientSide) {
            lerpedonFours = Mth.lerp(0.25F, lerpedonFours, isCharging() ? 0.0F : 1.0F);
            if (isCharging() && tickCount % 3 == 0) {
                level().addParticle(ParticleTypes.WITCH, getRandomX(0.45), getY(0.7), getRandomZ(0.45), 0.0, 0.08, 0.0);
            }
            return;
        }

        if (chargeTicks > 0) {
            chargeTicks--;
            if (chargeTicks == 0) {
                entityData.set(CHARGING, false);
                throwStone();
                throwCooldown = 100;
            }
        } else if (getTarget() instanceof Player target && target.isAlive()) {
            if (throwCooldown > 0) {
                throwCooldown--;
            }
            if (throwCooldown <= 15 && distanceToSqr(target) > 16.0) {
                chargeTicks = 15;
                entityData.set(CHARGING, true);
                getLookControl().setLookAt(target, 30.0F, 30.0F);
            }
        } else {
            throwCooldown = Math.max(throwCooldown, 20);
        }
    }

    public boolean isCharging() {
        return entityData.get(CHARGING);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(CHARGING, false);
    }

    private void throwStone() {
        if (!(getTarget() instanceof Player target) || !target.isAlive() || !(level() instanceof net.minecraft.server.level.ServerLevel serverLevel)) {
            return;
        }
        ToadstoolStoneProjectile stone = new ToadstoolStoneProjectile(level(), this);
        stone.setPos(getX(), getY(), getZ());
        Vec3 aim = target.getEyePosition().subtract(stone.position());
        if (aim.lengthSqr() > 1.0E-6) {
            stone.shoot(aim.x, aim.y + Math.min(aim.horizontalDistance() * 0.08, 1.0), aim.z, 1.6F, 0.0F);
        }
        serverLevel.addFreshEntity(stone);
        serverLevel.sendParticles(ParticleTypes.WITCH, getX(), getEyeY(), getZ(), 8, 0.3, 0.3, 0.3, 0.06);
    }

    @Override
    public void push(double x, double y, double z) {
        setDeltaMovement(getDeltaMovement().add(x * 0.2, y * 0.2, z * 0.2));
        hasImpulse = true;
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("ThrowCooldown", throwCooldown);
        tag.putInt("ChargeTicks", chargeTicks);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        throwCooldown = tag.getInt("ThrowCooldown");
        chargeTicks = tag.getInt("ChargeTicks");
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType,
                                        @Nullable SpawnGroupData spawnGroupData, @Nullable net.minecraft.nbt.CompoundTag tag) {
        return super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData, tag);
    }

    public static AttributeSupplier.Builder createToadstoolAttributes() {
        return Monster.createMonsterAttributes().add(Attributes.FOLLOW_RANGE, 20.0).add(Attributes.MOVEMENT_SPEED, 0.14)
                .add(Attributes.ATTACK_DAMAGE, 6.0).add(Attributes.MAX_HEALTH, 25.0);
    }
}
