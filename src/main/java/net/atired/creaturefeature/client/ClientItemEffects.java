package net.atired.creaturefeature.client;

import net.atired.creaturefeature.networking.CFNetwork;
import net.atired.creaturefeature.networking.payloads.C2SVelSyncPayload;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

/** Client-only presentation and input effects invoked from common item classes. */
public final class ClientItemEffects {
    private ClientItemEffects() {}

    public static void showNanTitle(Player player) {
        CreatureFeatureClient.PROXY.nan_title = 1.0F;
        player.displayClientMessage(Component.empty(), true);
    }

    public static void wobbleOpenMind(ItemStack stack) {
        CreatureFeatureClient.PROXY.wobble = 1.0F;
        CreatureFeatureClient.PROXY.wobblyItem = stack;
    }

    public static void applyVertigoHornImpulse(Player player) {
        if (!(player instanceof LocalPlayer localPlayer)) {
            return;
        }
        Vec3 direction = new Vec3(localPlayer.input.leftImpulse, 0.0, localPlayer.input.forwardImpulse)
                .normalize()
                .scale(1.66)
                .yRot(-localPlayer.getYHeadRot() / 180.0F * 3.14F);
        localPlayer.addDeltaMovement(direction);
        CFNetwork.sendToServer(new C2SVelSyncPayload(localPlayer.getId(), direction.x, direction.y, direction.z));
    }
}
