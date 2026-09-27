package net.atired.creaturefeature.client;

import net.atired.creaturefeature.CreatureFeature;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.client.event.RenderLevelStageEvent;

import java.io.IOException;

/** Client gameplay and render callbacks that belong to Forge's runtime event bus. */
@EventBusSubscriber(modid = CreatureFeature.MODID, bus = EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public final class CreatureFeatureClientForgeEvents {
    private CreatureFeatureClientForgeEvents() {}

    @SubscribeEvent
    public static void renderPre(net.minecraftforge.client.event.RenderLivingEvent.Pre<?, ?> event) {
        CreatureFeatureClient.renderModelEvent(event);
    }

    @SubscribeEvent
    public static void renderPost(net.minecraftforge.client.event.RenderLivingEvent.Post<?, ?> event) {
        CreatureFeatureClient.postRenderEntity(event);
    }

    @SubscribeEvent
    public static void renderLevel(RenderLevelStageEvent event) {
        CreatureFeatureClient.clientWorldRenderStage(event);
    }

    @SubscribeEvent
    public static void clientTick(TickEvent.ClientTickEvent event) throws IOException {
        CreatureFeatureClient.clientTickEvent(event);
    }
}
