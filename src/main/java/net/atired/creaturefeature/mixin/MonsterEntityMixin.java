package net.atired.creaturefeature.mixin;

import net.atired.creaturefeature.Config;
import net.atired.creaturefeature.accessors.LivingEntityGoopAccessor;
import net.atired.creaturefeature.networking.payloads.PathogenPayload;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.tags.BiomeTags;
import net.minecraftforge.common.Tags;
import net.minecraftforge.network.PacketDistributor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Mob.class)
public abstract class MonsterEntityMixin extends LivingEntity {

    protected MonsterEntityMixin(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
    }
    @Inject(method = "finalizeSpawn",at=@At("HEAD"))
    private void finaliseCFspawnUndead(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, SpawnGroupData spawnGroupData, CompoundTag dataTag, CallbackInfoReturnable<SpawnGroupData> cir){
        boolean undead = getType() == EntityType.ZOMBIE || getType() == EntityType.SKELETON || getType() == EntityType.STRAY
                || getType() == EntityType.HUSK || getType() == EntityType.DROWNED || getType() == EntityType.ZOMBIFIED_PIGLIN
                || getType() == EntityType.WITHER || getType() == EntityType.WITHER_SKELETON || getType() == EntityType.PHANTOM
                || getType() == EntityType.ZOMBIE_VILLAGER || getType() == EntityType.ZOMBIE_HORSE || getType() == EntityType.SKELETON_HORSE;
        if(undead&&(!Config.PATHOGEN.get()||level.getBiome(getOnPos()).is(BiomeTags.IS_OVERWORLD))){
            if(this instanceof LivingEntityGoopAccessor accessor && Math.random()>0.92){
                accessor.setBacterial(9);
            }
        }
    }
}
