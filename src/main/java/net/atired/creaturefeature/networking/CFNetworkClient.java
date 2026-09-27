package net.atired.creaturefeature.networking;

import net.atired.creaturefeature.accessors.LivingEntityGoopAccessor;
import net.atired.creaturefeature.accessors.PlayerBrainrotAccessor;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

final class CFNetworkClient {
    private CFNetworkClient() {}

    static void handle(CFNetwork.Message message) {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.level == null) return;
        Entity entity = minecraft.level.getEntity(CFNetwork.entityId(message));
        switch (CFNetwork.kind(message)) {
            case 0 -> {
                if (entity != null) entity.addDeltaMovement(new net.minecraft.world.phys.Vec3(CFNetwork.x(message), CFNetwork.y(message), CFNetwork.z(message)));
            }
            case 3 -> {
                if (entity instanceof LivingEntityGoopAccessor accessor) accessor.setFallDamageAmped(CFNetwork.flag(message));
            }
            case 4 -> {
                if (minecraft.player instanceof PlayerBrainrotAccessor accessor) accessor.setDelayedDamage(CFNetwork.amount(message) + 0.1F);
            }
            case 5 -> {
                if (entity instanceof LivingEntityGoopAccessor accessor) accessor.setBacterial(CFNetwork.amount(message));
            }
            case 6 -> {
                if (entity instanceof PlayerBrainrotAccessor accessor) accessor.setRabies(1.0F);
            }
            case 7 -> {
                if (entity instanceof LivingEntityGoopAccessor accessor) accessor.setSquashed(0.8F);
            }
            default -> { }
        }
    }
}
