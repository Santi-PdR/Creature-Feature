package net.atired.creaturefeature.mixin;

import net.atired.creaturefeature.client.CreatureFeatureClient;
import net.atired.creaturefeature.networking.CFNetwork;
import net.atired.creaturefeature.networking.payloads.C2SManPayload;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public abstract class PlayerClientMixin {
    @Unique private int creaturefeature$counter;
    @Unique private int creaturefeature$aged;
    @Unique private double creaturefeature$oldX;
    @Unique private double creaturefeature$oldZ;

    @Inject(method = "tick", at = @At("HEAD"))
    private void creaturefeature$clientEasterEggTick(CallbackInfo ci) {
        Player player = (Player) (Object) this;
        creaturefeature$aged++;
        if (player.getY() > 127.8
                && player.getYRot() < -89.0F && player.getYRot() > -91.0F
                && CreatureFeatureClient.PROXY.searchingForHim
                && player.level().dimensionType().respawnAnchorWorks()
                && creaturefeature$aged % 50 == 48 - creaturefeature$counter * 2) {
            if (Math.abs(creaturefeature$oldX - player.getX()) > 0.02
                    && Math.abs(creaturefeature$oldZ - player.getZ()) < 0.02) {
                creaturefeature$counter++;
                creaturefeature$aged = 0;
                player.playSound(SoundEvents.BELL_RESONATE, creaturefeature$counter / 5.0F, creaturefeature$counter / 10.0F);
                player.playSound(SoundEvents.ARROW_HIT_PLAYER, creaturefeature$counter / 5.0F, creaturefeature$counter / 10.0F);
                CreatureFeatureClient.PROXY.manShader = creaturefeature$counter / 24.0F;
                if (creaturefeature$counter > 23) {
                    player.playSound(SoundEvents.WARDEN_SONIC_BOOM, creaturefeature$counter / 5.0F, creaturefeature$counter / 10.0F);
                    creaturefeature$counter = 0;
                    CreatureFeatureClient.PROXY.searchingForHim = false;
                    CreatureFeatureClient.PROXY.manShader = 0.0F;
                    CFNetwork.sendToServer(new C2SManPayload(player.getId()));
                }
            }
        } else if (player.getY() < 127.8
                || !(player.getYRot() < -89.0F && player.getYRot() > -91.0F)
                || !player.level().dimensionType().respawnAnchorWorks()) {
            creaturefeature$counter = 0;
        }
        creaturefeature$oldX = player.getX();
        creaturefeature$oldZ = player.getZ();
    }
}
