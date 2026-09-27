package net.atired.creaturefeature.items;

import net.atired.creaturefeature.networking.CFNetwork;

import net.atired.creaturefeature.accessors.LivingEntityGoopAccessor;
import net.atired.creaturefeature.init.CFAchievements;
import net.atired.creaturefeature.init.CFItemInit;
import net.atired.creaturefeature.init.CFParticleInit;
import net.atired.creaturefeature.networking.payloads.DeAmpPayload;
import net.atired.creaturefeature.networking.payloads.VelSyncPayload;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.phys.Vec3;

public class HeavyAxeItem extends AxeItem {
    public HeavyAxeItem(Tier tier, float damage, float speed, Properties properties) {
        super(tier, damage, speed, properties);
    }
    public boolean isValidRepairItem(ItemStack stack, ItemStack repairCandidate) {
        return repairCandidate.is(CFItemInit.BLITZ_ROD.get());
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, Player player, Entity entity) {
        player.level().addParticle(CFParticleInit.CLEAVE_PARTICLE.get(),entity.getX(),entity.getY(0.66),entity.getZ(),player.getLookAngle().x,player.getLookAngle().y,player.getLookAngle().z);
        if(entity instanceof LivingEntity living && player.canAttack(living)&&entity instanceof LivingEntityGoopAccessor accessor){
            living.move(MoverType.SELF,new Vec3(0,0.09,0));
            if(entity.level() instanceof ServerLevel serverLevel){
                CFNetwork.sendToTrackingEntity(entity,new DeAmpPayload(entity.getId(),true));
                if(entity instanceof Horse && player instanceof ServerPlayer player1){
                    CFAchievements.PUNCH_EVERYONE.trigger(player1);
                }
                accessor.setFallDamageAmped(true);
            }
        }
        return super.onLeftClickEntity(stack, player, entity);
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {

        Vec3 dir = attacker.getLookAngle().multiply(1,0.2,1).add(0,0.2,0).normalize().scale(1.6).add(0,1.0,0);
        if(target.level() instanceof ServerLevel serverLevel){
            target.addDeltaMovement(dir);
            CFNetwork.sendToTrackingChunk(serverLevel,target.chunkPosition(),new VelSyncPayload(target.getId(),dir.x,dir.y,dir.z));

        }
        return super.hurtEnemy(stack, target, attacker);
    }
}
